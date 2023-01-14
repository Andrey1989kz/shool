package ru.hogwarts.shool.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.shool.model.Avatar;
import ru.hogwarts.shool.model.Student;
import ru.hogwarts.shool.repository.AvatarRepository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

 @Service
@Transactional
public class AvatarService {
    @Value("${students.cover.dir.path}")
    private final int imagesSizeLimit = 300;
    private String imagesDir;
    private final StudentService studentService;
    private final AvatarRepository avatarRepository;
    Logger logger = LoggerFactory.getLogger(AvatarService.class);

    public AvatarService(StudentService studentService, AvatarRepository avatarRepository) {
        logger.debug("Calling constructor AvatarService");
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
    }

    public void uploadImage(Long studentId, MultipartFile avatarFile) throws IOException {
        logger.debug("Calling method uploadAvatar (studentId = {})", studentId);

        Student student = studentService.findStudent(studentId);
        Path filePath = Path.of(imagesDir, student + "." + getExtensions(Objects.requireNonNull(avatarFile.getOriginalFilename())));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024)) {
            bis.transferTo(bos);
        }
        Avatar avatar = findAvatar(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(generateImagePreview(filePath));

        avatarRepository.save(avatar);
    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private byte[] generateImagePreview(Path filePath) throws IOException {
        try (InputStream is = Files.newInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics = preview.createGraphics();
            graphics.drawImage(image, 0, 0, 100, height, null);
            graphics.dispose();

            ImageIO.write(preview, getExtension(filePath.getFileName().toString()), bos);
            return bos.toByteArray();
        }
    }

    public Avatar findAvatar(Long studentId) {
        logger.debug("Calling method findAvatar");
        return avatarRepository.findByStudent(studentId).orElse(new Avatar());
    }


    private String getExtension(String fileName) {
        logger.debug("Calling method getExtension");
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public ResponseEntity<Collection<Avatar>> getAll(Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        Collection<Avatar> avatarCollection = avatarRepository.findAll(pageRequest).getContent();
        if (avatarCollection.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(avatarCollection);
    }

}


package ru.hogwarts.shool.model;

import jakarta.persistence.*;

import java.util.Arrays;
import java.util.Objects;


    @Entity
    public class Avatar {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;
        private String filePath;
        private long fileSize;
        private String mediaType;
        @Lob
        byte[] data;

        public Avatar() {

        }

        public byte[] getData() {
            return data;
        }

        @OneToOne
        @JoinColumn(name = "student_id")
        Student student;

        public Student getStudent() {
            return student;
        }

        public void setStudent(Student student) {
            this.student = student;
        }

        public Avatar(long id, String filePath, long fileSize, String mediaType, byte[] data) {
            this.id = id;
            this.filePath = filePath;
            this.fileSize = fileSize;
            this.mediaType = mediaType;
            this.data = data;

        }
        @Override
        public String toString() {
            return "Avatar{" +
                    "id=" + id +
                    ", filePath='" + filePath + '\'' +
                    ", fileSize=" + fileSize +
                    ", mediaType='" + mediaType + '\'' +
                    ", data=" + Arrays.toString(data) +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Avatar avatar = (Avatar) o;
            return id == avatar.id && fileSize == avatar.fileSize && Objects.equals(filePath, avatar.filePath) && Objects.equals(mediaType, avatar.mediaType) && Arrays.equals(data, avatar.data);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(id, filePath, fileSize, mediaType);
            result = 31 * result + Arrays.hashCode(data);
            return result;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public long getFileSize() {
            return fileSize;
        }

        public void setFileSize(long fileSize) {
            this.fileSize = fileSize;
        }

        public String getMediaType() {
            return mediaType;
        }

        public void setMediaType(String mediaType) {
            this.mediaType = mediaType;
        }

        public void setData(byte[] data) {
            this.data = data;
        }
    }
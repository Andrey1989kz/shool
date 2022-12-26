ALTER TABLE student
    ADD CONSTRAINT age_constraint CHECK ( age >= 16 );

ALTER TABLE student
    ALTER name SET NOT NULL;

ALTER TABLE student
    ALTER age SET DEFAULT 20;

ALTER TABLE faculty
    ADD CONSTRAINT color_name_unique UNIQUE (name, color);

ALTER TABLE student
    ADD CONSTRAINT name_unique UNIQUE (name);
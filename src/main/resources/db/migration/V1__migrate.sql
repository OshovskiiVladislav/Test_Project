CREATE TABLE files (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    path VARCHAR(255),
    uploaded_at TIMESTAMP
);

CREATE TABLE lessons (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(255)
);

CREATE TABLE lessons_files (
    file_id BIGINT,
    lesson_id BIGINT,
    PRIMARY KEY (file_id, lesson_id),
    FOREIGN KEY (file_id) REFERENCES files(id),
    FOREIGN KEY (lesson_id) REFERENCES lessons(id)
);

-- TEST DATA

INSERT INTO lessons (name, description)
VALUES
('Lesson 1', 'Description of Lesson 1');
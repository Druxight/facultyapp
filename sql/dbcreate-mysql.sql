SET NAMES utf8;

DROP DATABASE IF EXISTS faculty4app ;
CREATE DATABASE faculty4app CHARACTER SET utf8;


USE faculty4app ;

-- -----------------------------------------------------
-- ROLES
-- -----------------------------------------------------
CREATE TABLE roles (
  id INT NOT NULL,
  name VARCHAR(10) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX name_UNIQUE (name) VISIBLE);

-- insert data into roles table
-- -----------------------------------------------------
-- numeration started from 0, roles are ENUM type
-- -----------------------------------------------------
INSERT INTO roles VALUES(0, 'admin');
INSERT INTO roles VALUES(1, 'student');
INSERT INTO roles VALUES(2, 'professor');
INSERT INTO roles VALUES(3, 'blocked');

-- -----------------------------------------------------
-- USERS
-- -----------------------------------------------------
CREATE TABLE users (
  id INT NOT NULL auto_increment PRIMARY KEY,
  email VARCHAR(130) NOT NULL,
  login VARCHAR(20) NOT NULL,
  password VARCHAR(20) NOT NULL,
  first_name VARCHAR(20) NOT NULL,
  last_name VARCHAR(20) NOT NULL,
  role_id INT NOT NULL,
 
  UNIQUE INDEX login_UNIQUE (login ASC) VISIBLE,
  INDEX fk_users_roles_idx (role_id ASC) VISIBLE,
  CONSTRAINT fk_users_roles
    FOREIGN KEY (role_id)
    REFERENCES roles (id)
-- cannot remove row with some ID from the roles table 
-- if there are rows in users table with ROLES_ID=ID
    ON DELETE CASCADE
	);

-- insert students 
INSERT INTO users VALUES(DEFAULT,'email1@test.com', 'student1', 'passw1', 'Oleg', 'Olegovich', 2); 
INSERT INTO users VALUES(DEFAULT,'email2@test.com', 'student2', 'passw2', 'Mark', 'Markovich', 2);
INSERT INTO users VALUES(DEFAULT,'email3@test.com', 'student3', 'passw3', 'Dmitry', 'Dmitrievich ', 2);
INSERT INTO users VALUES(DEFAULT,'email4@test.com', 'student4', 'passw4', 'Vladislav', 'Vladislavovich ', 2);
INSERT INTO users VALUES(DEFAULT, 'email5@test.com', 'student5', 'passw5', 'Rostislav', 'Rostislavovich', 2);
INSERT INTO users VALUES(DEFAULT, 'email6@test.com', 'student6', 'passw6', 'Ruslan', 'Ruslanovich', 2);
INSERT INTO users VALUES(DEFAULT, 'email7@test.com', 'student7', 'passw7', 'Nikita', 'Nikitovich', 2);
INSERT INTO users VALUES(DEFAULT, 'email8@test.com', 'student8', 'passw8', 'Stanislav', 'Stanislavovich', 2);
-- insert professors

-- id 9
INSERT INTO users VALUES(DEFAULT,'email9@test.com', 'professor1', 'passw1', 'Ivan', 'Ivanovich ', 1); 
-- id 10
INSERT INTO users VALUES(DEFAULT,'email10@test.com', 'professor2', 'passw2', 'Ruslan', 'Ruslanovich ', 1);

-- insert admin
INSERT INTO users VALUES(DEFAULT,'email11@test.com', 'admin', 'admin', 'Stanislav', 'Stanislavovich ', 0);

-- -----------------------------------------------------
-- SUBJECTS
-- -----------------------------------------------------

CREATE TABLE subjects (
  id INT NOT NULL auto_increment,
  name VARCHAR(20) NOT NULL,
  PRIMARY KEY (id));

-- insert subjects
INSERT INTO subjects VALUES(DEFAULT, 'Chemistry');
INSERT INTO subjects VALUES(DEFAULT, 'Biology');
INSERT INTO subjects VALUES(DEFAULT, 'Philosophy');
INSERT INTO subjects VALUES(DEFAULT, 'Math');

-- -----------------------------------------------------
-- COURSES
-- -----------------------------------------------------

CREATE TABLE courses (
  id INT NOT NULL auto_increment,
  name VARCHAR(20) NOT NULL,
  start_date DATE NOT NULL,
  end_date DATE NOT NULL,
  subject_id INT NOT NULL,
  professor_id INT NOT NULL,
  PRIMARY KEY (id),
  INDEX fk_courses_users1_idx (professor_id ASC) VISIBLE,
  INDEX fk_courses_subjects1_idx (subject_id ASC) VISIBLE,
-- the course has an assigned professor
  CONSTRAINT fk_courses_users1
    FOREIGN KEY (professor_id)
    REFERENCES users (id)
-- you can not delete professor if he is course teacher
    ON DELETE RESTRICT
-- users.id modifications lead to changes in the course
    ON UPDATE CASCADE,
-- course has a subject to study 
  CONSTRAINT fk_courses_subjects1
    FOREIGN KEY (subject_id)
    REFERENCES subjects (id)
-- you can not delete subject if course exist
    ON DELETE RESTRICT
-- subject.id modifications lead to changes in the course
    ON UPDATE CASCADE);

-- subject: Math, subjects.id = 2 | professor: Ivan Ivanovich, id = 9
INSERT INTO courses VALUES (DEFAULT, 'Difficult Integrals', '2020-02-12', '2020-03-12', 2, 9);
-- subject: Biology, subjects.id = 3 | professor: Ivan Ivanovich, id = 9
INSERT INTO courses VALUES (DEFAULT, 'Our Nature', '2020-03-11', '2020-04-01', 3, 9);
-- subject: Chemistry, subjects.id = 1 | professor: Ruslan Ruslanovich, id = 10
INSERT INTO courses VALUES (DEFAULT, 'New Things', '2020-04-01', '2020-6-02', 1, 10);
-- subject: Philosophy, subjects.id = 4 | professor: Ruslan Ruslanovich, id = 10
INSERT INTO courses VALUES (DEFAULT, 'Where is truth?', '2020-06-01', '2020-07-01', 4, 10);

-- -----------------------------------------------------
-- JOURNAL
-- contains user and course id`s and a mark for this pair
-- -----------------------------------------------------
CREATE TABLE rating (
  id INT NOT NULL auto_increment,
  user_id INT NOT NULL,
  course_id INT NOT NULL,
  -- if professor hasn`t assessed the student mark = 0
  mark INT NOT NULL DEFAULT 0,
  INDEX fk_rating_user1_idx (user_id ASC) VISIBLE,
  INDEX fk_rating_course1_idx (course_id ASC) VISIBLE,
  PRIMARY KEY (id), 
  	-- modidcations in users.id or courses.id lead to changes in journal
  CONSTRAINT fk_rating_user1
    FOREIGN KEY (user_id)
    REFERENCES users (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
	
  CONSTRAINT fk_rating_courses1
    FOREIGN KEY (course_id)
    REFERENCES courses (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE);
	
-- course.id = 1 => Math
INSERT INTO rating VALUES(DEFAULT, 1, 1, DEFAULT);
INSERT INTO rating VALUES(DEFAULT, 2, 1, DEFAULT);
INSERT INTO rating VALUES(DEFAULT, 3, 1, DEFAULT);
INSERT INTO rating VALUES(DEFAULT, 4, 1, DEFAULT);
-- course.id = 2 => Biology
INSERT INTO rating VALUES(DEFAULT,1, 2, DEFAULT);
INSERT INTO rating VALUES(DEFAULT, 4, 2, DEFAULT);
INSERT INTO rating VALUES(DEFAULT, 5, 2, DEFAULT);
-- course.id = 3 => Difficult Integrals
INSERT INTO rating VALUES(DEFAULT, 3, 3, DEFAULT);
INSERT INTO rating VALUES(DEFAULT, 1, 3, DEFAULT);
-- course.id = 4 => Where is truth?
INSERT INTO rating VALUES(DEFAULT, 1, 4, DEFAULT);
INSERT INTO rating VALUES(DEFAULT, 4, 4, DEFAULT);
INSERT INTO rating VALUES(DEFAULT, 6, 4, DEFAULT);

-- -----------------------------------------------------
-- test database;
-- -----------------------------------------------------
SELECT * FROM roles;
SELECT * FROM users;
SELECT * FROM subjects;
SELECT * FROM courses;
SELECT * FROM rating;




CREATE SCHEMA quiz;
USE quiz;
CREATE TABLE levels(
	id int AUTO_INCREMENT,
    level_name varchar(50) NOT NULL,
    description varchar(500),
    CONSTRAINT pk_level_id PRIMARY KEY(id)
);
ALTER TABLE levels CHANGE level_name name varchar(50); 
CREATE TABLE categories(
	id int AUTO_INCREMENT,
    category_name varchar(50) NOT NULL,
    icon_url varchar(500),
    description varchar(500),
    CONSTRAINT pk_category_id PRIMARY KEY(id)
);
ALTER TABLE categories CHANGE category_name name varchar(50); 
CREATE TABLE quizzes(
	id int AUTO_INCREMENT,
    name varchar(50) NOT NULL,
    tags varchar(300) NOT NULL,
    activity_point int NOT NULL,
    duration time,
    max_number_of_attempts int DEFAULT 1,
    is_level_override bool DEFAULT 0,
    slug_url varchar(500) NOT NULL,
    description varchar(500),
    meta_keywords varchar(300),
    meta_description varchar(500),
    icon_url varchar(500),
    quiz_instructions varchar(500),
    level_id int NOT NULL,
    category_id int NOT NULL,
    pass_percentage int NOT NULL,
    is_slug_url_access bool,
    quiz_timer bool DEFAULT 0,
    shuffle_question bool DEFAULT 1,
    shuffle_answer bool DEFAULT 1,
    display_score_result  bool DEFAULT 1,
    attempt_review bool,
    show_whether_correct bool,
    show_correct_answer bool,
    show_answer_explanation bool DEFAULT 0,
    is_save_and_resume bool DEFAULT 1,
    create_on timestamp DEFAULT CURRENT_TIMESTAMP,
    updated_on timestamp,
    created_by int NOT NULL,
    modified_by varchar(50),
    CONSTRAINT pk_quiz PRIMARY KEY(id),
    CONSTRAINT ck_activity_points CHECK(activity_point >= 0),
    CONSTRAINT ck_max_number_of_attempts CHECK(max_number_of_attempts > 0),
    CONSTRAINT fk_level_id FOREIGN KEY(level_id) REFERENCES levels(id),
    CONSTRAINT fk_category_id FOREIGN KEY(category_id) REFERENCES categories(id),
    CONSTRAINT fk_created_by FOREIGN KEY(created_by) REFERENCES employees(id),
    CONSTRAINT uk_quiz_name UNIQUE(name)
);
ALTER TABLE quizzes ADD version int NOT NULL DEFAULT 1;
ALTER TABLE quizzes CHANGE create_on created_on varchar(50);
ALTER TABLE quizzes ADD is_status bool DEFAULT 0;
CREATE TABLE quiz_pools(
	id int AUTO_INCREMENT,
    name varchar(25) NOT NULL,
    max_number_of_questions int,
    quiz_id int NOT NULL,
    CONSTRAINT pk_quiz_pool PRIMARY KEY(id),
    CONSTRAINT fk_quiz_id FOREIGN KEY(quiz_id) REFERENCES quizzes(id)
);
CREATE TABLE quiz_pool_questions(
	id int AUTO_INCREMENT,
    question_id int NOT NULL,
    quiz_pool_id int NOT NULL,
    is_sticky bool,
    is_evaluate bool,
    CONSTRAINT pk_quiz_pool_question PRIMARY KEY(id),
    CONSTRAINT fk_quiz_pool_id FOREIGN KEY(quiz_pool_id) REFERENCES quiz_pools(id)
);
CREATE TABLE employees(
	id int AUTO_INCREMENT,
    name varchar(50),
    email varchar(50),
    password varchar(20),
    role varchar(20),
    createdOn timestamp DEFAULT CURRENT_TIMESTAMP,
    modifiedOn timestamp,
    createdBy varchar(50),
    modifiedBy varchar(50),
    PRIMARY KEY(id)
);
ALTER TABLE employees ADD isActive bool;
DROP TABLE quizzes;

select id from quizzes where id = 1 or 1=1;
CREATE SCHEMA revature_quiz_db;
USE revature_quiz_db;
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
    created_by varchar(50) NOT NULL,
    modified_by varchar(50),
    CONSTRAINT pk_quiz PRIMARY KEY(id),
    CONSTRAINT ck_activity_points CHECK(activity_point >= 0),
    CONSTRAINT ck_max_number_of_attempts CHECK(max_number_of_attempts > 0),
    CONSTRAINT fk_level_id FOREIGN KEY(level_id) REFERENCES level(id),
    CONSTRAINT fk_category_id FOREIGN KEY(category_id) REFERENCES category(id)
);
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
CREATE TABLE levels(
	id int AUTO_INCREMENT,
    level_name varchar(50) NOT NULL,
    description varchar(500),
    CONSTRAINT pk_level_id PRIMARY KEY(id)
);
CREATE TABLE categories(
	id int AUTO_INCREMENT,
    category_name varchar(50) NOT NULL,
    icon_url varchar(500),
    description varchar(500),
    CONSTRAINT pk_category_id PRIMARY KEY(id)
);
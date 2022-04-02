# DB 생성
DROP DATABASE IF EXISTS jpaBoard;
CREATE DATABASE jpaBoard;
USE jpaBoard;

# 회원 테이블 생성
CREATE TABLE `user` (
    id BIGINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    reg_date DATETIME NOT NULL,
    update_date DATETIME NOT NULL,
    email VARCHAR(100) NOT NULL,
    `password` VARCHAR(150) NOT NULL,
    `name` CHAR(50) NOT NULL
);

# 게시물 테이블 생성
CREATE TABLE `article`(
    id BIGINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    reg_date DATETIME NOT NULL,
    update_date DATETIME NOT NULL,
    title VARCHAR(100) NOT NULL,
    `body` TEXT NOT NULL,
    `user_id` BIGINT UNSIGNED NOT NULL
);

SHOW TABLES;

# 회원데이터 생성
INSERT INTO `user`
SET reg_date = NOW(),
update_date = NOW(),
email = 'use1@test.com',
`password` = '1234',
`name` = '유저1';

INSERT INTO `user`
SET reg_date = NOW(),
update_date = NOW(),
email = 'use2@test.com',
`password` = '1234',
`name` = '유저2';

INSERT INTO `user`
SET reg_date = NOW(),
update_date = NOW(),
email = 'use3@test.com',
`password` = '1234',
`name` = '유저3';

#게시물데이터 생성
INSERT INTO `article`
SET reg_date = NOW(),
update_date = NOW(),
title = '제목1',
`body` = '내용1',
`user_id` = '1';

INSERT INTO `article`
SET reg_date = NOW(),
update_date = NOW(),
title = '제목2',
`body` = '내용2',
`user_id` = '2';

INSERT INTO `article`
SET reg_date = NOW(),
update_date = NOW(),
title = '제목3',
`body` = '내용3',
`user_id` = '3';

#회원테이블의 email 칼럼을 UNIQUE로 지정
ALTER TABLE `user` ADD UNIQUE INDEX (email);

SELECT * FROM `user`;
SELECT * FROM `article`;
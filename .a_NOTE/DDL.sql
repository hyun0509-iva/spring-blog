
CREATE TABLE board
(
  board_number   INT         NOT NULL AUTO_INCREMENT COMMENT '게시물 번호',
  title          TEXT        NOT NULL COMMENT '게시물 제목',
  content        TEXT       NOT NULL COMMENT '게시물 내용',
  write_datetime DATETIME    NOT NULL COMMENT '게시물 작성일',
  favorite_count INT         NOT NULL DEFAULT 0 COMMENT '게시물 좋아요 수',
  comment_count  INT         NOT NULL DEFAULT 0 COMMENT '게시물 댓글 수',
  view_count     INT         NOT NULL DEFAULT 0 COMMENT '게시물 조회 수',
  writer_email   VARCHAR(50) NOT NULL COMMENT '게시물 작성사 이메일',
  PRIMARY KEY (board_number)
) COMMENT '게시물 테이블';

CREATE TABLE comment
(
  comment_number INT         NOT NULL COMMENT AUTO_INCREMENT '댓글 번호',
  content        TEXT        NOT NULL COMMENT '댓글 내용',
  write_datetime DATETIME    NOT NULL COMMENT '댓글 작성일',
  user_email     VARCHAR(50) NOT NULL COMMENT '사용자 이메일',
  board_number   INT         NOT NULL DEFAULT AUTO_INCREMENT COMMENT '게시물 번호',
  PRIMARY KEY (comment_number)
) COMMENT '댓글 테이블';

-- ALTER TABLE comment CHANGE comment_number comment_number int not null AUTO_INCREMENT COMMENT '댓글 번호';

CREATE TABLE favorite
(
  user_email   VARCHAR(50) NOT NULL COMMENT '사용자 이메일',
  board_number INT         NOT NULL AUTO_INCREMENT COMMENT '게시물 번호',
  title        TEXT        NOT NULL COMMENT '게시물 제목'
) COMMENT '좋아요 테이블';

CREATE TABLE image
(
  board_number INT  NOT NULL AUTO_INCREMENT COMMENT '게시물 번호',
  image        TEXT NOT NULL COMMENT '게시물 이미지 URL',
  title        TEXT NOT NULL COMMENT '게시물 제목'
) COMMENT '게시물 이미지 테이블';

CREATE TABLE search_log
(
  sequence      INT     NOT NULL COMMENT '시퀀스(기록 번호)',
  search_word   TEXT    NOT NULL COMMENT '검색어',
  relation_word TEXT    NULL     COMMENT '관련 검색어',
  relation      BOOLEAN NOT NULL COMMENT '관련 검색어 여부',
  PRIMARY KEY (sequence)
) COMMENT '검색 기록 테이블';

CREATE TABLE user
(
  email          VARCHAR(50)  NOT NULL COMMENT '사용자 이메일',
  password       VARCHAR(100) NOT NULL COMMENT '사용자 패스워드',
  nickname       VARCHAR(20)  NOT NULL UNIQUE COMMENT '사용자 닉네임',
  tel_number     VARCHAR(15)  NOT NULL UNIQUE COMMENT '사용자 휴대폰번호',
  address        TEXT         NOT NULL COMMENT '사용자 주소',
  address_detail TEXT         NULL     COMMENT '사용자 상세 주소',
  profile_image  TEXT         NULL     COMMENT '사용자 프로필 사진 url',
  PRIMARY KEY (email)
) COMMENT '사용자 테이블';

ALTER TABLE image
  ADD CONSTRAINT FK_board_TO_image
    FOREIGN KEY (board_number)
    REFERENCES board (board_number);

ALTER TABLE board
  ADD CONSTRAINT FK_user_TO_board
    FOREIGN KEY (writer_email)
    REFERENCES user (email);

ALTER TABLE favorite
  ADD CONSTRAINT FK_user_TO_favorite
    FOREIGN KEY (user_email)
    REFERENCES user (email);

ALTER TABLE favorite
  ADD CONSTRAINT FK_board_TO_favorite
    FOREIGN KEY (board_number)
    REFERENCES board (board_number);

ALTER TABLE comment
  ADD CONSTRAINT FK_user_TO_comment
    FOREIGN KEY (user_email)
    REFERENCES user (email);

ALTER TABLE comment
  ADD CONSTRAINT FK_board_TO_comment
    FOREIGN KEY (board_number)
    REFERENCES board (board_number);

-- 사용자 등록
# root 계정은 보안상 취약하므로 사용자 계정을 만들어서 사용하기
CREATE USER 'developer'@'%' IDENTIFIED BY 'dong5372';
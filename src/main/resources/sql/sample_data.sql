-- noinspection SqlResolveForFile

INSERT INTO CAFE_USER(userid, password, name, email)
VALUES ('tester', 'aaa', '테스터', 'tester@testers.com');

INSERT INTO CAFE_USER(userid, password, name, email)
VALUES ('dorayaki', 'aaa', '도라에몽', 'help-me@dorae.mon');

INSERT INTO ARTICLE(writer_userId, writer_name, title, contents)
VALUES ('tester', '테스터', '샘플 게시물', '이 게시물은 샘플입니다.');

INSERT INTO ARTICLE(writer_userId, writer_name, title, contents)
VALUES ('dorayaki', '도라에몽', '샘플 게시물 두번째', '이 게시물은 두번째 샘플입니다.');

INSERT INTO REPLY(article_id, writer_userId, writer_name, contents)
VALUES ('1', 'dorayaki', '도라에몽', '팥빵이 먹고 싶다 진구야');

INSERT INTO REPLY(article_id, writer_userId, writer_name, contents)
VALUES ('1', 'tester', '테스터', '왜 여기서 진구를 찾아');

INSERT INTO REPLY(article_id, writer_userId, writer_name, contents)
VALUES ('2', 'tester', '테스터', '도와줘 도라에몽! 호눅스가 괴롭혀!');

INSERT INTO REPLY(article_id, writer_userId, writer_name, contents)
VALUES ('2', 'dorayaki', '도라에몽', '개발자가 다 너랑 같은 수준이면 인류는 끝장이야!');

-- MEMBER: 기존 예약자들 + 어드민 1명
-- login_id는 영문/숫자 조합, password는 평문 (실서비스 아님)
INSERT INTO MEMBER (name, login_id, password) VALUES ('어드민', 'admin', 'admin');
INSERT INTO MEMBER (name, login_id, password) VALUES ('김철수', 'chulsoo', 'password');
INSERT INTO MEMBER (name, login_id, password) VALUES ('이영희', 'younghee', 'password');
INSERT INTO MEMBER (name, login_id, password) VALUES ('박민수', 'minsoo', 'password');
INSERT INTO MEMBER (name, login_id, password) VALUES ('홍길동', 'gildong', 'password');
INSERT INTO MEMBER (name, login_id, password) VALUES ('정수진', 'sujin', 'password');
INSERT INTO MEMBER (name, login_id, password) VALUES ('한동훈', 'donghoon', 'password');
INSERT INTO MEMBER (name, login_id, password) VALUES ('임채원', 'chaewon', 'password');
INSERT INTO MEMBER (name, login_id, password) VALUES ('서태양', 'taeyang', 'password');
INSERT INTO MEMBER (name, login_id, password) VALUES ('유민호', 'minho', 'password');
INSERT INTO MEMBER (name, login_id, password) VALUES ('강민준', 'minjun', 'password');
INSERT INTO MEMBER (name, login_id, password) VALUES ('조현아', 'hyuna', 'password');
INSERT INTO MEMBER (name, login_id, password) VALUES ('황준혁', 'junhyuk', 'password');
INSERT INTO MEMBER (name, login_id, password) VALUES ('송미래', 'mirae', 'password');
INSERT INTO MEMBER (name, login_id, password) VALUES ('안태양', 'antaeyang', 'password');
INSERT INTO MEMBER (name, login_id, password) VALUES ('배소희', 'sohee', 'password');
INSERT INTO MEMBER (name, login_id, password) VALUES ('권지훈', 'jihoon', 'password');
INSERT INTO MEMBER (name, login_id, password) VALUES ('류지아', 'jia', 'password');
INSERT INTO MEMBER (name, login_id, password) VALUES ('전현무', 'hyunmoo', 'password');
INSERT INTO MEMBER (name, login_id, password) VALUES ('표민혁', 'minhyuk', 'password');

-- RESERVATION_TIME: 10:00 ~ 20:00 (1시간 단위, 11개)
INSERT INTO RESERVATION_TIME (start_at) VALUES ('10:00');
INSERT INTO RESERVATION_TIME (start_at) VALUES ('11:00');
INSERT INTO RESERVATION_TIME (start_at) VALUES ('12:00');
INSERT INTO RESERVATION_TIME (start_at) VALUES ('13:00');
INSERT INTO RESERVATION_TIME (start_at) VALUES ('14:00');
INSERT INTO RESERVATION_TIME (start_at) VALUES ('15:00');
INSERT INTO RESERVATION_TIME (start_at) VALUES ('16:00');
INSERT INTO RESERVATION_TIME (start_at) VALUES ('17:00');
INSERT INTO RESERVATION_TIME (start_at) VALUES ('18:00');
INSERT INTO RESERVATION_TIME (start_at) VALUES ('19:00');
INSERT INTO RESERVATION_TIME (start_at) VALUES ('20:00');

-- THEME: 5개 (인기도 차별화)
INSERT INTO THEME (name, description, thumbnail_url) VALUES ('공포의 저택', '으스스한 저택에서 탈출하세요', 'https://img.khan.co.kr/news/2021/12/31/l_2022010101000053900351432.jpg');
INSERT INTO THEME (name, description, thumbnail_url) VALUES ('우주 탐험', '광활한 우주의 비밀을 풀어보세요', 'https://imagescdn.gettyimagesbank.com/500/201801/jv11110379.jpg');
INSERT INTO THEME (name, description, thumbnail_url) VALUES ('마법 학교', '마법 학교의 숨겨진 비밀을 찾아라', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRHtPE2r2OnsA7PLT22YBX4EWbmWXcDDDyPcw&s');
INSERT INTO THEME (name, description, thumbnail_url) VALUES ('고대 유적', '고대 문명의 유적을 탐험하세요', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTbfoc4tfrkbUaKHBGhvdiTtoyzUmh3YNRsuw&s');
INSERT INTO THEME (name, description, thumbnail_url) VALUES ('탐정 사무소', '미스터리 사건을 해결하세요', 'https://img.freepik.com/free-photo/private-detective-empty-workplace-with-crime-case-evidences-board-hanging-desk-police-investigator-office-surrounded-with-murder-scene-photos-clues-night-time_482257-59756.jpg?semt=ais_hybrid&w=740&q=80');

-- RESERVATION: 30건
-- member_id 매핑:
-- 어드민=1, 김철수=2, 이영희=3, 박민수=4, 홍길동=5, 정수진=6, 한동훈=7, 임채원=8,
-- 서태양=9, 유민호=10, 강민준=11, 조현아=12, 황준혁=13, 송미래=14, 안태양=15,
-- 배소희=16, 권지훈=17, 류지아=18, 전현무=19, 표민혁=20

-- Theme 1 (공포의 저택): 10건
INSERT INTO RESERVATION (member_id, date, time_id, theme_id) VALUES (2,  '2026-05-07', 1,  1);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id) VALUES (3,  '2026-05-07', 2,  1);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id) VALUES (4,  '2026-05-08', 3,  1);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id) VALUES (5,  '2026-05-08', 4,  1);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id) VALUES (6,  '2026-05-09', 5,  1);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id) VALUES (7,  '2026-05-09', 6,  1);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id) VALUES (8,  '2026-05-10', 7,  1);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id) VALUES (9,  '2026-05-11', 8,  1);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id) VALUES (2,  '2026-05-12', 9,  1);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id) VALUES (10, '2026-05-14', 10, 1);

-- Theme 2 (우주 탐험): 8건
INSERT INTO RESERVATION (member_id, date, time_id, theme_id) VALUES (11, '2026-05-07', 3,  2);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id) VALUES (12, '2026-05-08', 4,  2);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id) VALUES (2,  '2026-05-09', 5,  2);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id) VALUES (5,  '2026-05-10', 6,  2);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id) VALUES (13, '2026-05-10', 7,  2);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id) VALUES (14, '2026-05-11', 8,  2);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id) VALUES (15, '2026-05-12', 9,  2);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id) VALUES (16, '2026-05-14', 10, 2);

-- Theme 3 (마법 학교): 6건
INSERT INTO RESERVATION (member_id, date, time_id, theme_id) VALUES (17, '2026-05-08', 1, 3);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id) VALUES (5,  '2026-05-09', 2, 3);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id) VALUES (2,  '2026-05-10', 3, 3);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id) VALUES (18, '2026-05-11', 4, 3);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id) VALUES (9,  '2026-05-12', 5, 3);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id) VALUES (9,  '2026-05-14', 6, 3);

-- Theme 4 (고대 유적): 4건
INSERT INTO RESERVATION (member_id, date, time_id, theme_id) VALUES (5,  '2026-05-09', 7,  4);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id) VALUES (19, '2026-05-10', 8,  4);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id) VALUES (9,  '2026-05-11', 9,  4);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id) VALUES (20, '2026-05-12', 10, 4);

-- Theme 5 (탐정 사무소): 2건
INSERT INTO RESERVATION (member_id, date, time_id, theme_id) VALUES (9, '2026-05-10', 11, 5);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id) VALUES (5, '2026-05-14', 1,  5);
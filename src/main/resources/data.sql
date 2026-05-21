-- MEMBER: 매니저 3명 + 일반 사용자 17명
-- id 매핑:
-- 강남매니저=1, 홍대매니저=2, 잠실매니저=3,
-- 김철수=4, 이영희=5, 박민수=6, 홍길동=7, 정수진=8, 한동훈=9, 임채원=10,
-- 서태양=11, 유민호=12, 강민준=13, 조현아=14, 황준혁=15, 송미래=16, 안태양=17,
-- 배소희=18, 권지훈=19, 류지아=20
INSERT INTO MEMBER (name, login_id, password, role) VALUES ('강남점장', 'admin1', 'admin', 'MANAGER');
INSERT INTO MEMBER (name, login_id, password, role) VALUES ('홍대점장', 'admin2', 'admin', 'MANAGER');
INSERT INTO MEMBER (name, login_id, password, role) VALUES ('잠실점장', 'admin3', 'admin', 'MANAGER');
INSERT INTO MEMBER (name, login_id, password, role) VALUES ('김철수', 'chulsoo', 'password', 'CUSTOMER');
INSERT INTO MEMBER (name, login_id, password, role) VALUES ('이영희', 'younghee', 'password', 'CUSTOMER');
INSERT INTO MEMBER (name, login_id, password, role) VALUES ('박민수', 'minsoo', 'password', 'CUSTOMER');
INSERT INTO MEMBER (name, login_id, password, role) VALUES ('홍길동', 'gildong', 'password', 'CUSTOMER');
INSERT INTO MEMBER (name, login_id, password, role) VALUES ('정수진', 'sujin', 'password', 'CUSTOMER');
INSERT INTO MEMBER (name, login_id, password, role) VALUES ('한동훈', 'donghoon', 'password', 'CUSTOMER');
INSERT INTO MEMBER (name, login_id, password, role) VALUES ('임채원', 'chaewon', 'password', 'CUSTOMER');
INSERT INTO MEMBER (name, login_id, password, role) VALUES ('서태양', 'taeyang', 'password', 'CUSTOMER');
INSERT INTO MEMBER (name, login_id, password, role) VALUES ('유민호', 'minho', 'password', 'CUSTOMER');
INSERT INTO MEMBER (name, login_id, password, role) VALUES ('강민준', 'minjun', 'password', 'CUSTOMER');
INSERT INTO MEMBER (name, login_id, password, role) VALUES ('조현아', 'hyuna', 'password', 'CUSTOMER');
INSERT INTO MEMBER (name, login_id, password, role) VALUES ('황준혁', 'junhyuk', 'password', 'CUSTOMER');
INSERT INTO MEMBER (name, login_id, password, role) VALUES ('송미래', 'mirae', 'password', 'CUSTOMER');
INSERT INTO MEMBER (name, login_id, password, role) VALUES ('안태양', 'antaeyang', 'password', 'CUSTOMER');
INSERT INTO MEMBER (name, login_id, password, role) VALUES ('배소희', 'sohee', 'password', 'CUSTOMER');
INSERT INTO MEMBER (name, login_id, password, role) VALUES ('권지훈', 'jihoon', 'password', 'CUSTOMER');
INSERT INTO MEMBER (name, login_id, password, role) VALUES ('류지아', 'jia', 'password', 'CUSTOMER');

-- STORE: 3개 (각 매니저가 담당) - member INSERT 이후라야 FK 충족
-- store id 매핑: 강남점=1, 홍대점=2, 잠실점=3
INSERT INTO STORE (name, member_id) VALUES ('강남점', 1);
INSERT INTO STORE (name, member_id) VALUES ('홍대점', 2);
INSERT INTO STORE (name, member_id) VALUES ('잠실점', 3);

-- RESERVATION_TIME: 10:00 ~ 20:00 (11개)
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

-- THEME: 5개
INSERT INTO THEME (name, description, thumbnail_url) VALUES ('공포의 저택', '으스스한 저택에서 탈출하세요', 'https://img.khan.co.kr/news/2021/12/31/l_2022010101000053900351432.jpg');
INSERT INTO THEME (name, description, thumbnail_url) VALUES ('우주 탐험', '광활한 우주의 비밀을 풀어보세요', 'https://imagescdn.gettyimagesbank.com/500/201801/jv11110379.jpg');
INSERT INTO THEME (name, description, thumbnail_url) VALUES ('마법 학교', '마법 학교의 숨겨진 비밀을 찾아라', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRHtPE2r2OnsA7PLT22YBX4EWbmWXcDDDyPcw&s');
INSERT INTO THEME (name, description, thumbnail_url) VALUES ('고대 유적', '고대 문명의 유적을 탐험하세요', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTbfoc4tfrkbUaKHBGhvdiTtoyzUmh3YNRsuw&s');
INSERT INTO THEME (name, description, thumbnail_url) VALUES ('탐정 사무소', '미스터리 사건을 해결하세요', 'https://img.freepik.com/free-photo/private-detective-empty-workplace-with-crime-case-evidences-board-hanging-desk-police-investigator-office-surrounded-with-murder-scene-photos-clues-night-time_482257-59756.jpg?semt=ais_hybrid&w=740&q=80');

-- RESERVATION: 30건, store별 13 / 8 / 9
-- store 1 (강남점): 테마1(10) + 테마3(3) = 13
-- store 2 (홍대점): 테마2(8)             = 8
-- store 3 (잠실점): 테마3(3) + 테마4(4) + 테마5(2) = 9

-- ===== store 1 (강남점): 13건 =====
INSERT INTO RESERVATION (member_id, date, time_id, theme_id, store_id) VALUES (4,  '2026-05-25', 1,  1, 1);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id, store_id) VALUES (5,  '2026-05-25', 2,  1, 1);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id, store_id) VALUES (6,  '2026-05-26', 3,  1, 1);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id, store_id) VALUES (7,  '2026-05-26', 4,  1, 1);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id, store_id) VALUES (8,  '2026-05-27', 5,  1, 1);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id, store_id) VALUES (9,  '2026-05-27', 6,  1, 1);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id, store_id) VALUES (10, '2026-05-28', 7,  1, 1);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id, store_id) VALUES (11, '2026-05-29', 8,  1, 1);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id, store_id) VALUES (4,  '2026-05-30', 9,  1, 1);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id, store_id) VALUES (12, '2026-06-01', 10, 1, 1);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id, store_id) VALUES (13, '2026-05-26', 1, 3, 1);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id, store_id) VALUES (7,  '2026-05-27', 2, 3, 1);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id, store_id) VALUES (4,  '2026-05-28', 3, 3, 1);

-- ===== store 2 (홍대점): 8건 =====
INSERT INTO RESERVATION (member_id, date, time_id, theme_id, store_id) VALUES (14, '2026-05-25', 3,  2, 2);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id, store_id) VALUES (15, '2026-05-26', 4,  2, 2);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id, store_id) VALUES (4,  '2026-05-27', 5,  2, 2);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id, store_id) VALUES (7,  '2026-05-28', 6,  2, 2);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id, store_id) VALUES (16, '2026-05-28', 7,  2, 2);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id, store_id) VALUES (17, '2026-05-29', 8,  2, 2);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id, store_id) VALUES (18, '2026-05-30', 9,  2, 2);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id, store_id) VALUES (19, '2026-06-01', 10, 2, 2);

-- ===== store 3 (잠실점): 9건 =====
INSERT INTO RESERVATION (member_id, date, time_id, theme_id, store_id) VALUES (20, '2026-05-26', 4, 3, 3);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id, store_id) VALUES (11, '2026-05-27', 5, 3, 3);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id, store_id) VALUES (11, '2026-05-28', 6, 3, 3);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id, store_id) VALUES (7,  '2026-05-27', 7,  4, 3);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id, store_id) VALUES (19, '2026-05-28', 8,  4, 3);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id, store_id) VALUES (11, '2026-05-29', 9,  4, 3);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id, store_id) VALUES (20, '2026-05-30', 10, 4, 3);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id, store_id) VALUES (11, '2026-05-28', 11, 5, 3);
INSERT INTO RESERVATION (member_id, date, time_id, theme_id, store_id) VALUES (7,  '2026-06-01', 1,  5, 3);
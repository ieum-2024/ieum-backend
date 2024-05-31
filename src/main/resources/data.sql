INSERT INTO category_tb (name) VALUES ('취업/진로');
INSERT INTO category_tb (name) VALUES ('전세/임대');
INSERT INTO category_tb (name) VALUES ('가사/집안일');
INSERT INTO category_tb (name) VALUES ('운동');
INSERT INTO category_tb (name) VALUES ('게임');
INSERT INTO category_tb (name) VALUES ('음악');

INSERT INTO subcategory_tb (id, category_id,name) VALUES (301, 1,'대학진학'), (302, 1,'창업'), (303, 1,'자격증취득'), (304, 1,'직업훈련');
INSERT INTO subcategory_tb (id, category_id,name) VALUES (305, 2,'전세자금 대출'), (306, 2,'정부지원정책'), (307, 2,'전세/임대 전의 유의사항'), (308, 2,'전세사기 위험');
INSERT INTO subcategory_tb (id, category_id,name) VALUES (309, 3,'요리'), (310, 3,'세탁'), (311, 3,'청소'), (312, 3,'비용절감');
INSERT INTO subcategory_tb (id, category_id,name) VALUES (313, 4,'야구'), (314, 4,'농구'), (315, 4,'축구'), (316, 4,'수영'), (317, 4,'등산');
INSERT INTO subcategory_tb (id, category_id,name) VALUES (318, 5,'컴퓨터게임'), (319, 5,'보드게임'), (320, 5,'카드게임'), (321, 5,'모바일게임');
INSERT INTO subcategory_tb (id, category_id,name) VALUES (322, 6,'클래식'), (323, 6,'전자음악'), (324, 6,'힙합'), (325, 6,'재즈');

INSERT INTO user_tb (user_id, name, social_id, role) VALUES (1, 'user1', 1234567890, 'GUEST');

INSERT INTO post_tb (sub_category_id, title, content, created_at, created_by) VALUES
(301, 'title1', 'content1', NOW(), 1),
(301, 'title2', 'content2', NOW(), 1),
(301, 'title3', 'content3', NOW(), 1),
(301, 'title4', 'content4', NOW(), 1),
(301, 'title5', 'content5', NOW(), 1),
(301, 'title6', 'content6', NOW(), 1),
(301, 'title7', 'content7', NOW(), 1),
(305, 'title8', 'content8', NOW(), 1),
(305, 'title9', 'content9', NOW(), 1),
(305, 'title10', 'content10', NOW(), 1),
(306, 'title11', 'content11', NOW(), 1),
(310, 'title12', 'content12', NOW(), 1),
(310, 'title13', 'content13', NOW(), 1),
(310, 'title14', 'content14', NOW(), 1),
(310, 'title15', 'content15', NOW(), 1);

INSERT INTO image_tb (post_id, image_url) VALUES
(1, 'https://cloud-termproject.s3.ap-northeast-2.amazonaws.com/2024-05-18T10_29_47.779854500-food-712665_1280.jpg'),
(1, 'https://cloud-termproject.s3.ap-northeast-2.amazonaws.com/2024-05-18T10_35_20.867758300-ERD.png'),
(2, 'https://cloud-termproject.s3.ap-northeast-2.amazonaws.com/2024-05-23T13_15_01.824049600-place1.jpg'),
(3, 'https://cloud-termproject.s3.ap-northeast-2.amazonaws.com/2024-05-23T14_22_16.184683100-place2.jpg'),
(6, 'https://cloud-termproject.s3.ap-northeast-2.amazonaws.com/2024-05-23T14_22_16.057986900-hotel1.jpg'),
(6, 'https://cloud-termproject.s3.ap-northeast-2.amazonaws.com/2024-05-24T14_50_58.042614300-place3.jpg'),
(7, 'https://cloud-termproject.s3.ap-northeast-2.amazonaws.com/2024-05-31T22_49_35.344381200-Group+24.png'),
(8, 'https://cloud-termproject.s3.ap-northeast-2.amazonaws.com/2024-05-31T22_49_35.344381200-Group+24.png');


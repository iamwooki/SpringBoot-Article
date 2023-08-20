-- article dummy data
INSERT INTO ARTICLE(title, content) VALUES ('가가가가', '1111'), ('나나나나', '2222'), ('다다다다', '3333');

--article dummy data
INSERT INTO ARTICLE(title, content) VALUES ('favorite movie?', 'Comment go'), ('favorite food?', 'Comment go'), ('your hobby?', 'Comment go');

-- comment dummy data
---- No.4 article comment
INSERT INTO comment(article_id, nickname, body) VALUES ('4','PARK', 'Openhimer'), ('4','HONG', 'Openhimer'), ('4','KWON', 'Openhimer');

---- No.5 article comment
INSERT INTO comment(article_id, nickname, body) VALUES ('5','PARK', 'chicken'), ('5','HONG', 'meat'), ('5','KWON', 'sushi');

---- No.6 article comment
INSERT INTO comment(article_id, nickname, body) VALUES ('6','PARK', 'fitness'), ('6','HONG', 'watching movie'), ('6','KWON', 'youtube');
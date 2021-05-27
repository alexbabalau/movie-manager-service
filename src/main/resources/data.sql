DROP TABLE IF EXISTS movie_rating;
DROP VIEW IF EXISTS movie_rating;
CREATE VIEW movie_rating
AS
SELECT m.id AS id, AVG(CAST(IFNULL(stars, 0) as FLOAT)) AS rating
    FROM reviews r RIGHT JOIN movies m
    ON r.movie_id = m.id
    GROUP BY m.id;
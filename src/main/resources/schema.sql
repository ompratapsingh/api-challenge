DROP TABLE IF EXISTS pet_breed_details;
DROP TABLE IF EXISTS pet_image_details;
DROP TABLE IF EXISTS pet_image_like_dislike;

CREATE TABLE pet_breed_details (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  breed_name VARCHAR(250) NOT NULL,
  origin VARCHAR(250) NOT NULL,
  colors VARCHAR(250) DEFAULT NULL,
  life_expectancy VARCHAR(250) DEFAULT NULL	 
);

CREATE TABLE pet_image_details (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  breed_id INT, 
  foreign key (breed_id) references pet_breed_details(id),
  image_url VARCHAR(250) NOT NULL
);

CREATE TABLE pet_image_like_dislike (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  like_by  VARCHAR(250) NOT NULL,
  image_id INT, 
  foreign key (image_id) references pet_image_details(id),
);

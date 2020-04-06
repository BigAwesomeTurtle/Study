DROP DATABASE IF EXISTS bar_db;

CREATE DATABASE bar_db;
\c bar_db

START TRANSACTION;


CREATE TABLE IF NOT EXISTS public.position ( 
  id SERIAL NOT NULL,
  name VARCHAR(50) NOT NULL,
  salary INT NOT NULL,
  responsibilities VARCHAR(200) NULL,
  discount NUMERIC(4,2) DEFAULT 0.00 NOT NULL,
  PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS public.bar ( 
  id SERIAL NOT NULL,
  address VARCHAR(100) NOT NULL,
  admin_id INT NULL,
  telephone VARCHAR(15) NULL,
  profit INT DEFAULT 0 NOT NULL,
  PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS public.worker ( 
  id SERIAL NOT NULL,
  name VARCHAR(50) NOT NULL,
  bar_id INT NULL,
  telephone VARCHAR(15) NULL,
  bonus INT DEFAULT 0 NOT NULL,
  entry_date DATE NOT NULL,
  position_id INT NOT NULL,
  address VARCHAR(100) NULL,
  schedule VARCHAR(50) NULL,
  PRIMARY KEY (id),

  CONSTRAINT worker_bar_id_foreign
  FOREIGN KEY (bar_id)
  REFERENCES public.bar (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,

  CONSTRAINT worker_position_id_foreign
  FOREIGN KEY (position_id)
  REFERENCES public.position (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION

);

  ALTER TABLE bar ADD CONSTRAINT bar_admin_id_foreign
  FOREIGN KEY (admin_id)
  REFERENCES public.worker (id)
  ON DELETE CASCADE
  ON UPDATE NO ACTION;


CREATE TABLE IF NOT EXISTS public.menu_category ( 
  id SERIAL NOT NULL,
  name VARCHAR(50) NOT NULL,
  adult BOOL NOT NULL,
  PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS public.menu_item ( 
  id SERIAL NOT NULL,
  name VARCHAR(50) NOT NULL,
  description VARCHAR(400) NULL,
  weight NUMERIC NULL,
  category_id INT NOT NULL,
  recomended_price INT NOT NULL,
  PRIMARY KEY (id),

  CONSTRAINT menu_item_category_id_foreign
  FOREIGN KEY (category_id)
  REFERENCES public.menu_category (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
);


CREATE TABLE IF NOT EXISTS public.bar_menu ( 
  bar_id INT NOT NULL,
  menu_item_id INT NOT NULL,
  price INT NOT NULL,
  PRIMARY KEY (bar_id, menu_item_id),

  CONSTRAINT bar_menu_bar_id_foreign
  FOREIGN KEY (bar_id)
  REFERENCES public.bar (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,

  CONSTRAINT bar_menu_menu_item_id_foreign
  FOREIGN KEY (menu_item_id)
  REFERENCES public.menu_item (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
);


CREATE TABLE IF NOT EXISTS public.promotion ( 
  id SERIAL NOT NULL,
  date_from DATE NOT NULL,
  date_to DATE NULL,
  category_id INT NOT NULL,
  menu_item_id INT NULL,
  terms VARCHAR(400) NULL,
  discount NUMERIC(4,2) DEFAULT 0.00 NOT NULL,
  PRIMARY KEY (id),

  CONSTRAINT promotion_category_id_foreign
  FOREIGN KEY (category_id)
  REFERENCES public.menu_category (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,

  CONSTRAINT promotion_menu_item_id_foreign
  FOREIGN KEY (menu_item_id)
  REFERENCES public.menu_item (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
);


CREATE TABLE IF NOT EXISTS public.regular_customer ( 
  id SERIAL NOT NULL,
  name VARCHAR(50) NOT NULL,
  telephone VARCHAR(15) NULL,
  discount NUMERIC(4,2) DEFAULT 0.00 NOT NULL,
  points INT DEFAULT 0 NOT NULL,
  favourite_bar_id INT NULL,
  PRIMARY KEY (id),

  CONSTRAINT regular_customer_favourite_bar_id_foreign
  FOREIGN KEY (favourite_bar_id)
  REFERENCES public.bar (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
);

COMMIT;
\c bar_db
START TRANSACTION;

DROP TABLE IF EXISTS public.order_history;

CREATE TABLE IF NOT EXISTS public.order_history ( 
  id SERIAL NOT NULL,
  bar_id INT NOT NULL,
  date_and_time TIMESTAMP NOT NULL,
  customer_name VARCHAR(50) NULL,
  PRIMARY KEY (id),

  CONSTRAINT order_history_bar_id_foreign
  FOREIGN KEY (bar_id)
  REFERENCES public.bar (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
);

DROP TABLE IF EXISTS public.order_items;

CREATE TABLE IF NOT EXISTS public.order_items ( 
  order_id INT NOT NULL,
  menu_item_id INT NOT NULL,
  price INT NOT NULL,
  amount INT DEFAULT 1 NOT NULL,
  PRIMARY KEY (order_id, menu_item_id),

  CONSTRAINT order_items_order_id_foreign
  FOREIGN KEY (order_id)
  REFERENCES public.order_history (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,

  CONSTRAINT order_items_menu_item_id_foreign
  FOREIGN KEY (menu_item_id)
  REFERENCES public.menu_item (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
);

DROP TABLE IF EXISTS public.worker_history;

CREATE TABLE IF NOT EXISTS public.worker_history ( 
  id SERIAL NOT NULL,
  worker_id INT NOT NULL,
  date_from DATE NOT NULL,
  date_to DATE NULL,
  bar_id INT  NULL,
  position_id INT NOT NULL,
  salary INT NOT NULL,
  PRIMARY KEY (id),

  CONSTRAINT worker_history_worker_id_foreign
  FOREIGN KEY (worker_id)
  REFERENCES public.worker (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,

  CONSTRAINT worker_history_bar_id_foreign
  FOREIGN KEY (bar_id)
  REFERENCES public.bar (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,

  CONSTRAINT worker_history_position_id_foreign
  FOREIGN KEY (position_id)
  REFERENCES public.position (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION
);

COMMIT;
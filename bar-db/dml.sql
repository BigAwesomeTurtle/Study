\c bar_db
START TRANSACTION;
/*
--------------------------------------------------
*/
SELECT * FROM bar \g 'dml_res/1.txt'
SELECT * FROM bar_menu \g | cat >>  'dml_res/1.txt'
SELECT * FROM menu_category \g | cat >> 'dml_res/1.txt'
SELECT * FROM menu_item \g | cat >> 'dml_res/1.txt'
SELECT * FROM order_history \g | cat >> 'dml_res/1.txt'
SELECT * FROM order_items \g | cat >> 'dml_res/1.txt'
SELECT * FROM position \g | cat >> 'dml_res/1.txt'
SELECT * FROM promotion \g | cat >> 'dml_res/1.txt'
SELECT * FROM regular_customer \g | cat >> 'dml_res/1.txt'
SELECT * FROM worker \g | cat >> 'dml_res/1.txt'
SELECT * FROM worker_history \g | cat >> 'dml_res/1.txt'
/*
--------------------------------------------------
*/
SELECT name,telephone 
FROM regular_customer 
WHERE name LIKE 'И%' \g 'dml_res/2.txt'

SELECT category_id, menu_item_id, discount 
FROM promotion 
WHERE discount BETWEEN 0.4 AND 0.5 \g | cat >> 'dml_res/2.txt'

SELECT name, salary 
FROM position 
WHERE salary IN (20000,35000) \g | cat >> 'dml_res/2.txt'

SELECT name, address, bar_id 
FROM worker 
WHERE address LIKE '_п%' AND bar_id BETWEEN 10 AND 20 \g | cat >> 'dml_res/2.txt'
/*
--------------------------------------------------
*/
SELECT price, amount, (price*amount) AS total 
FROM order_items \g 'dml_res/3.txt'
/*
--------------------------------------------------
*/
SELECT * FROM regular_customer 
ORDER BY discount DESC, favourite_bar_id \g 'dml_res/4.txt'
/*
--------------------------------------------------
*/
SELECT AVG(amount) AS amount_avg, MAX(price*amount) AS max_total 
FROM order_items \g 'dml_res/5.txt'

SELECT menu_item_id,AVG(price*amount) AS avg_total 
FROM order_items 
GROUP BY menu_item_id 
ORDER BY menu_item_id \g | cat >> 'dml_res/5.txt'
/*
--------------------------------------------------
*/
SELECT worker.name, position.name AS position, (position.salary+worker.bonus) AS salary 
FROM worker 
INNER JOIN position ON position.id = worker.position_id \g 'dml_res/6.txt'

SELECT order_history.id AS order_id, 
bar.address AS bar_address, 
SUM(order_items.price * order_items.amount) AS total 
FROM order_history
LEFT JOIN bar ON bar.id = order_history.bar_id 
LEFT JOIN order_items ON order_items.order_id = order_history.id 
GROUP BY order_history.id,bar.address  \g | cat >> 'dml_res/6.txt'
/*
--------------------------------------------------
*/
SELECT order_history.id AS order_id, 
bar.address AS bar_address, 
SUM(order_items.price * order_items.amount) AS total 
FROM order_history
LEFT JOIN bar ON bar.id = order_history.bar_id 
LEFT JOIN order_items ON order_items.order_id = order_history.id 
GROUP BY order_history.id,bar.address 
HAVING SUM(order_items.price * order_items.amount)>50000 \g 'dml_res/7.txt'
/*
--------------------------------------------------
*/

SELECT * FROM bar
WHERE admin_id IN 
(SELECT id FROM worker WHERE position_id=1 AND bonus=5000) \g 'dml_res/8.txt'
/*
--------------------------------------------------
*/

INSERT INTO bar (address,telephone,profit) VALUES ('ул. Политехническая, д.15','+79817610521',100000);

INSERT INTO bar_menu ( bar_id, menu_item_id, price) VALUES ((SELECT COUNT(id) FROM bar), 5, 300);

INSERT INTO menu_category (name, adult) VALUES ('Безалкогольные коктейли', False);

INSERT INTO menu_item ( name, description, weight, category_id, recomended_price) 
VALUES ('Безалкогольный мохито', 'Освежающий безалкогольный коктейль', 0.4, 
	(SELECT id FROM menu_category WHERE name= 'Безалкогольные коктейли'), 300);

INSERT INTO order_history (bar_id, date_and_time, customer_name) 
VALUES ((SELECT COUNT(id) FROM bar), '2020-03-31 15:02:03','Иванов Илья Дмитриевич');

INSERT INTO order_items (order_id,menu_item_id, price, amount) 
VALUES ((SELECT COUNT(id) FROM order_history), 5, 300, 10);

INSERT INTO position (name, salary, responsibilities, discount) 
VALUES ('Охранник', 35000, 'Охрана бара', 0.10);

INSERT INTO promotion (date_from, date_to, category_id, menu_item_id, terms, discount) 
VALUES ('2020-03-31','2020-04-30',1,NULL,'No Terms',0.25);

INSERT INTO regular_customer (name, telephone, discount, points, favourite_bar_id) 
VALUES ('Иванов Илья Дмитриевич', '79817610521', 0.15, 1000, (SELECT COUNT(id) FROM bar));

INSERT INTO worker (name, bar_id, telephone, bonus, entry_date, position_id, address, schedule) 
VALUES ('Яковлев Антон Владимирович', (SELECT COUNT(id) FROM bar), '79999999999', 5000,
'2020-03-31', 1, 'ул. Политехническая, д.32, кв.28', 'СР-ВС, 18:00-03:00');

INSERT INTO worker_history (worker_id, date_from, date_to, bar_id, position_id, salary) 
VALUES (10,'2018-05-10','2019-03-19',7,3,36000);
/*
--------------------------------------------------
*/

UPDATE bar SET admin_id=5 WHERE admin_id IS NULL;

UPDATE position SET salary = salary+5000 WHERE salary < 40000;
/*
--------------------------------------------------
*/

DELETE FROM order_items WHERE price= ( SELECT MAX(price) FROM order_items);
/*
--------------------------------------------------
*/

INSERT INTO bar (address,telephone,profit) VALUES ('ул. Тестовая, д.140','+79817613521',100000);
DELETE FROM bar WHERE id NOT IN ( SELECT bar_id FROM worker GROUP BY bar_id);

COMMIT;
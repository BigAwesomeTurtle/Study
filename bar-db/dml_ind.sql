\c bar_db
START TRANSACTION;

/*--------------------------------------------------*/
SELECT worker_id, worker.name, worker_history.bar_id, 
SUM(worker_history.date_to-worker_history.date_from) as worker_sum
FROM worker_history
LEFT JOIN worker ON worker_history.worker_id= worker.id
GROUP BY worker_history.bar_id, worker_id, worker.name
HAVING SUM(worker_history.date_to-worker_history.date_from) > (SELECT bar_detla FROM
(SELECT worker_history.bar_id as curr_bar, MAX(date_from)-MIN(date_from) as bar_detla  FROM worker_history 
	GROUP BY worker_history.bar_id) as tmp_table WHERE tmp_table.curr_bar=worker_history.bar_id)/2 
ORDER BY worker_id \g 'dml_ind_res/1.txt'
/*--------------------------------------------------*/

SELECT COUNT(tmp_table.order_id) FROM (SELECT order_id FROM order_items
GROUP BY order_id
HAVING SUM(price*amount)>50000) as tmp_table
 \g 'dml_ind_res/2.txt'

SELECT menu_item_id, menu_item.name, COUNT(order_id) FROM order_items
LEFT JOIN menu_item ON order_items.menu_item_id=menu_item.id
WHERE order_id IN (SELECT order_id FROM order_items GROUP BY order_id HAVING SUM(price*amount)>50000)
GROUP BY order_items.menu_item_id, menu_item.name
HAVING COUNT(order_id) > (SELECT COUNT(tmp_table.order_id) FROM (SELECT order_id FROM order_items
GROUP BY order_id
HAVING SUM(price*amount)>50000) as tmp_table)/2
\g | cat >> 'dml_ind_res/2.txt'

COMMIT;
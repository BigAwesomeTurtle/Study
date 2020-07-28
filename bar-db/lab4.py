import random
import psycopg2
from datetime import date
import time
import matplotlib.pyplot as plt
import threading
import dicts

connection=psycopg2.connect(dbname='bar_db', user='bar_user', password='barbar', host='127.0.0.1' )
cursor=connection.cursor()
connection.autocommit = True

cursor.execute("SELECT name FROM regular_customer")
names=cursor.fetchall()
cursor.execute("SELECT id FROM bar")
bar_ids=cursor.fetchall()
cursor.execute("SELECT id FROM menu_item")
menu_item_ids=cursor.fetchall()

query_1="SELECT points FROM regular_customer WHERE name = %(name)s;"
query_2="SELECT discount FROM regular_customer WHERE name = %(name)s;"
query_3="SELECT price FROM bar_menu WHERE bar_id = %(bar)s AND menu_item_id = %(menu_item)s;"
query_4="INSERT INTO public.order_history (bar_id, date_and_time, customer_name) VALUES (%(bar)s,%(date)s,%(name)s);"
query_5="INSERT INTO public.regular_customer (name, telephone, discount, points, favourite_bar_id) VALUES (%(name)s,%(telephone)s, 0.05,100,%(f_bar)s);"
query_6="SELECT adult FROM menu_category WHERE name = %(name)s;"
query_7="SELECT position_id, schedule FROM worker WHERE name = %(name)s;"
query_8="SELECT id FROM menu_item WHERE name = %(name)s;"

query_list=(query_1,query_2,query_3,query_5,query_6,query_7,query_8)

curr_res_cosntant_threads=[[]]
curr_res_dinamic_threads=[]

before = True

def main():
	check_explain_analyze()
	constant_threads(1)
	constant_threads(2)
	dinamic_threads(2000)
	optimize()
	check_explain_analyze()
	constant_threads(1)
	constant_threads(2)
	dinamic_threads(2000)	


def constant_threads(num_threads):
	curr_res_cosntant_threads.clear()
	for t in range(num_threads):
		dbt = DBThread_constant_threads(t)
		dbt.start()
	while threading.activeCount() > 1:
		time.sleep(1)
	plot_x=[k for k in range(401,10000,200)]
	plot_y=[]
	for i in range(len(curr_res_cosntant_threads[0])):
		curr_sum=0
		for j in range(num_threads):
			curr_sum+=curr_res_cosntant_threads[j][i]
		plot_y.append(curr_sum/num_threads)
	plt.plot(plot_x,plot_y,linewidth=2.0)
	plt.xlabel('Запросов в секунду')
	plt.ylabel('Время ответа на один запрос, мс')
	if before:
		plt.title("Before. Num threads: {}".format(num_threads))
	else:
		plt.title("After. Num threads: {}".format(num_threads))
	plt.show()

def dinamic_threads(num_querys):
	plot_x=[k for k in range(1,31)]
	plot_y=[]
	for num_threads in range(1,31):
		curr_res_dinamic_threads.clear()
		for t in range(num_threads):
			dbt = DBThread_dinamic_threads(num_querys)
			dbt.start()

		while threading.activeCount() > 1:
			time.sleep(1)

		plot_y.append(sum(curr_res_dinamic_threads)/len(curr_res_dinamic_threads))


	plt.plot(plot_x,plot_y,linewidth=2.0)
	plt.xlabel('Количество потоков')
	plt.ylabel('Время ответа на один запрос, мс')
	if before:
		plt.title("Before. Dinamic thread num")
	else:
		plt.title("After. Dinamic thread num")
	plt.show()

def optimize():
	cursor.execute("CREATE INDEX ON regular_customer(name);")
	cursor.execute("CREATE INDEX ON worker(name);")
	cursor.execute("CREATE INDEX ON menu_item(name);")

	global before
	before = False

def exec_query_before(query,thread_cursor):
	if query==query_1:
		thread_cursor.execute("EXPLAIN ANALYZE " + query_1, {"name":random.choice(names)})
	elif query == query_2:
		thread_cursor.execute("EXPLAIN ANALYZE " + query_2, {"name":random.choice(names)})
	elif query == query_3:
		thread_cursor.execute("EXPLAIN ANALYZE " + query_3, {"bar":random.choice(bar_ids)[0],
			"menu_item":random.choice(menu_item_ids)[0]})
	elif query == query_4:
		thread_cursor.execute("EXPLAIN ANALYZE " + query_4, {"bar":random.choice(bar_ids)[0],
			"date":date.today(),"name":random.choice(names)})
	elif query == query_5:
		thread_cursor.execute("EXPLAIN ANALYZE " + query_5, {"name":random.choice(names),
			"telephone":generate_phone(),"f_bar":random.choice(bar_ids)[0]})
	elif query == query_6:
		thread_cursor.execute("EXPLAIN ANALYZE " + query_6, {"name":random.choice(dicts.category_list)["name"]})
	elif query == query_7:
		thread_cursor.execute("EXPLAIN ANALYZE " + query_7, {"name":random.choice(names)})
	elif query == query_8:
		thread_cursor.execute("EXPLAIN ANALYZE " + query_8, {"name":random.choice(random.choice(dicts.menu_list))["name"]})
	else:
		print("Wrong query!")
	fetch_res=thread_cursor.fetchall()
	try:
		return float(fetch_res[-1][0].split(" ")[2])+float(fetch_res[-2][0].split(" ")[2])
	except ValueError:
		return float(fetch_res[-1][0].split(" ")[2])+float(fetch_res[-2][0].split(" ")[4].split("=")[1])+float(fetch_res[-3][0].split(" ")[2])


def exec_query_after(query,thread_cursor):
	if query==query_1:
		thread_cursor.execute("EXPLAIN ANALYZE EXECUTE query1 (%s);", random.choice(names))
	elif query == query_2:
		thread_cursor.execute("EXPLAIN ANALYZE EXECUTE query2 (%s);", random.choice(names))
	elif query == query_3:
		thread_cursor.execute("EXPLAIN ANALYZE EXECUTE query3 (%s,%s);", (random.choice(bar_ids)[0],
			random.choice(menu_item_ids)[0]))
	elif query == query_4:
		thread_cursor.execute("EXPLAIN ANALYZE EXECUTE query4 (%s,%s,%s);", (random.choice(bar_ids)[0],
			date.today(), random.choice(names)))
	elif query == query_5:
		thread_cursor.execute("EXPLAIN ANALYZE EXECUTE query5 (%s,%s,%s);", (random.choice(names),
			generate_phone(), random.choice(bar_ids)[0]))
	elif query == query_6:
		thread_cursor.execute("EXPLAIN ANALYZE EXECUTE query6 (%s);", random.choice(dicts.category_list)["name"][0])
	elif query == query_7:
		thread_cursor.execute("EXPLAIN ANALYZE EXECUTE query7 (%s);", random.choice(names))
	elif query == query_8:
		thread_cursor.execute("EXPLAIN ANALYZE EXECUTE query8 (%s);", random.choice(random.choice(dicts.menu_list))["name"][0])
	else:
		print("Wrong query!")
	fetch_res=thread_cursor.fetchall()
	try:
		return float(fetch_res[-1][0].split(" ")[2])+float(fetch_res[-2][0].split(" ")[2])
	except ValueError:
		return float(fetch_res[-1][0].split(" ")[2])+float(fetch_res[-2][0].split(" ")[4].split("=")[1])+float(fetch_res[-3][0].split(" ")[2])		

def check_explain_analyze():
	cursor.execute("EXPLAIN ANALYZE " + query_1, {"name":random.choice(names)})
	#cursor.execute("EXPLAIN ANALYZE " + query_4, {"bar":random.choice(bar_ids)[0],
	#		"date":date.today(),"name":random.choice(names)})
	print(cursor.fetchall())


def generate_phone():
	first = str(random.randint(900,999))
	second = str(random.randint(1,888)).zfill(3)

	last = (str(random.randint(1,9998)).zfill(4))
	return '+7{}{}{}'.format(first,second, last)

class DBThread_constant_threads(threading.Thread):
	def __init__(self,curr_thread):

		self.curr_thread=curr_thread
		threading.Thread.__init__(self)
		self.conn = psycopg2.connect(dbname='bar_db', user='bar_user', password='barbar', host='127.0.0.1' )
		self.cur = self.conn.cursor()

		global before

		if not before:
			self.cur.execute("PREPARE query1 (varchar(50)) AS SELECT points FROM regular_customer WHERE name = $1;")
			self.cur.execute("PREPARE query2 (varchar(50)) AS SELECT discount FROM regular_customer WHERE name = $1;")
			self.cur.execute("PREPARE query3 (int, int) AS SELECT price FROM bar_menu WHERE bar_id = $1 AND menu_item_id = $2;")
			self.cur.execute("PREPARE query4 (int, date, varchar(50)) AS INSERT INTO public.order_history (bar_id, date_and_time, customer_name) VALUES ($1,$2,$3);")
			self.cur.execute("PREPARE query5 (varchar(50), varchar(15), int) AS INSERT INTO public.regular_customer (name, telephone, discount, points, favourite_bar_id) VALUES ($1,$2,0.05,100,$3);")
			self.cur.execute("PREPARE query6 (varchar(50)) AS SELECT adult FROM menu_category WHERE name = $1;")
			self.cur.execute("PREPARE query7 (varchar(50)) AS SELECT position_id, schedule FROM worker WHERE name = $1;")
			self.cur.execute("PREPARE query8 (varchar(50)) AS SELECT id FROM menu_item WHERE name = $1;")

	def run(self):
		for i in range(401,10000,200):
			results=[]
			for j in range(0,i):
				print(j)
				random_query=random.choice(query_list)
				if before:
					results.append(exec_query_before(random_query,self.cur))
				else:
					results.append(exec_query_after(random_query,self.cur))
			if len(curr_res_cosntant_threads)<self.curr_thread+1:
				curr_res_cosntant_threads.append([])
			curr_res_cosntant_threads[self.curr_thread].append(sum(results)/len(results))
		self.conn.commit() 

class DBThread_dinamic_threads(threading.Thread):
	def __init__(self,num_querys):

		self.num_querys=num_querys
		threading.Thread.__init__(self)
		self.conn = psycopg2.connect(dbname='bar_db', user='bar_user', password='barbar', host='127.0.0.1' )
		self.cur = self.conn.cursor()

		global before

		if not before:
			self.cur.execute("PREPARE query1 (varchar(50)) AS SELECT points FROM regular_customer WHERE name = $1;")
			self.cur.execute("PREPARE query2 (varchar(50)) AS SELECT discount FROM regular_customer WHERE name = $1;")
			self.cur.execute("PREPARE query3 (int, int) AS SELECT price FROM bar_menu WHERE bar_id = $1 AND menu_item_id = $2;")
			self.cur.execute("PREPARE query4 (int, date, varchar(50)) AS INSERT INTO public.order_history (bar_id, date_and_time, customer_name) VALUES ($1,$2,$3);")
			self.cur.execute("PREPARE query5 (varchar(50), varchar(15), int) AS INSERT INTO public.regular_customer (name, telephone, discount, points, favourite_bar_id) VALUES ($1,$2,0.05,100,$3);")
			self.cur.execute("PREPARE query6 (varchar(50)) AS SELECT adult FROM menu_category WHERE name = $1;")
			self.cur.execute("PREPARE query7 (varchar(50)) AS SELECT position_id, schedule FROM worker WHERE name = $1;")
			self.cur.execute("PREPARE query8 (varchar(50)) AS SELECT id FROM menu_item WHERE name = $1;")

	def run(self):
		results=[]
		for j in range(0,self.num_querys+1):
			print(j)
			random_query=random.choice(query_list)
			if before:
				results.append(exec_query_before(random_query,self.cur))
			else:
				results.append(exec_query_after(random_query,self.cur))
		curr_res_dinamic_threads.append(sum(results)/len(results))
		self.conn.commit() 

if __name__ == "__main__":
	main()
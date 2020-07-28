import random
import psycopg2
from datetime import date, timedelta
import dicts
import argparse
import sys
import math

connection=psycopg2.connect(dbname='bar_db', user='bar_user', password='barbar', host='127.0.0.1' )
cursor=connection.cursor()

addresses=open("resourses/streets.txt","r",encoding='utf-8').readlines()
names=open("resourses/names.txt","r",encoding='utf-8').readlines()

opening_date=dicts.opening_date

def createParser():
	parser = argparse.ArgumentParser()
	parser.add_argument('-w', '--worker', nargs='+', type=str)
	parser.add_argument('-b', '--bar', nargs='+', type=str)
	parser.add_argument('-wh', '--whistory', nargs='+', type=str)
	parser.add_argument('-rc', '--regular', nargs='+', type=str)
	parser.add_argument('-p', '--promo', nargs='+', type=str)
	parser.add_argument('-oh', '--orderhistory', nargs='+', type=str)
	parser.add_argument('-oi', '--orderitems', nargs='+', type=str)

	return parser

def main():

	parser=createParser()
	args=parser.parse_args(sys.argv[1:])

	if(len(sys.argv)==1):
		cursor.execute('DELETE FROM order_items')

		cursor.execute('DELETE FROM order_history')
		cursor.execute('ALTER SEQUENCE order_history_id_seq RESTART')

		cursor.execute('DELETE FROM promotion')
		cursor.execute('ALTER SEQUENCE promotion_id_seq RESTART')

		cursor.execute('DELETE FROM bar_menu')

		cursor.execute('DELETE FROM menu_item')
		cursor.execute('ALTER SEQUENCE menu_item_id_seq RESTART')

		cursor.execute('DELETE FROM menu_category')
		cursor.execute('ALTER SEQUENCE menu_category_id_seq RESTART')

		cursor.execute('DELETE FROM regular_customer')
		cursor.execute('ALTER SEQUENCE regular_customer_id_seq RESTART')

		cursor.execute('DELETE FROM worker_history')
		cursor.execute('ALTER SEQUENCE worker_history_id_seq RESTART')

		cursor.execute('DELETE FROM worker')
		cursor.execute('ALTER SEQUENCE worker_id_seq RESTART')

		cursor.execute('DELETE FROM position')
		cursor.execute('ALTER SEQUENCE position_id_seq RESTART')

		cursor.execute('DELETE FROM bar')
		cursor.execute('ALTER SEQUENCE bar_id_seq RESTART')

		bar_generation(20)

		position_generation()

		worker_generation(False)

		worker_history_generation(False)

		regular_customer_generation(500)

		menu_category_generation()

		menu_item_generation()

		bar_menu_generation(False)

		promotion_generation(100, 5)

		order_history_generation(5000)

		order_items_generation(False)

	else:
		if args.worker:
			length=len(args.worker)
			if(length>3):
				print("More than 3 arguments after worker")
				sys.exit(1)
			else:
				worker_generation(True, *args.worker)			

		if args.bar:
			length=len(args.bar)
			if(length!=1):
				print("Illegal argument size after -b. Need 1 Argument")
				sys.exit(1)
			else:
				cursor.execute("SELECT count(id) FROM bar")
				bar_amount=cursor.fetchone()[0]
				bar_generation(int(args.bar[0]))
				connection.commit()
				for i in range(1, int(args.bar[0])+1):
					worker_generation(True, 1, str(bar_amount+i),'1')
					bar_menu_generation(True, bar_amount+i)	

		if args.whistory:
			length=len(args.whistory)
			if(length>4):
				print("More than 4 arguments after history")
				sys.exit(1)
			else:
				worker_history_generation(True, *args.whistory)

		if args.regular:
			length=len(args.regular)
			if(length!=1 and length!=2):
				print("Illegal argument size after -rc. Need 1 or 2 Arguments")
				sys.exit(1)
			else:
				regular_customer_generation(*args.regular)
		
		if args.promo:
			length=len(args.promo)
			if(length!=2 and length!=3 and length!=4):
				print("Illegal argument size after -p. Need 2, 3 or 4 Arguments")
				sys.exit(1)
			else:
				promotion_generation(*args.promo)

		if args.orderhistory:
			length=len(args.orderhistory)
			if(length!=1 and length!=2):
				print("Illegal argument size after -oh. Need 1 or 2 Arguments")
				sys.exit(1)
			else:
				order_history_generation(*args.orderhistory)	

		if args.orderitems:
			length=len(args.orderitems)
			if(length!=1):
				print("Illegal argument size after -oi. Need 1 Argument")
				sys.exit(1)
			else:
				order_items_generation(True, args.orderitems[0])


	connection.commit()
	connection.close()


def bar_generation(amount):
	insert_addresses=""
	addr=[]
	for i in range(amount):
		address=str(random.choice(addresses))
		address=address[0: len(address)-1] + ", д." + str(random.randint(1,99))
		addr.append(address)
		insert_addresses=insert_addresses + "(\'"+address+"\'),"
	exec_str='INSERT INTO public.bar (address) VALUES {}'.format(insert_addresses)
	cursor.execute(exec_str[:len(exec_str)-1])

	for i in range(amount):
		has_teleph=random.randint(1,5) != 1
		has_profit=random.randint(1,5) != 1

		if(has_teleph):
			cursor.execute('UPDATE public.bar SET telephone=%s WHERE address=%s',(generate_phone(),addr[i]))
		if(has_profit):
			cursor.execute('UPDATE public.bar SET profit=%s WHERE address=%s',
				(str(random.randint(-1000000, 1000000)), addr[i]))

def position_generation():
	position_list = dicts.position_list
	insert_position=""
	for elem in position_list:
		insert_position=insert_position + "( "+"\'"+elem["name"]+"\'"+", "+str(elem["salary"])+", "+"\'"+elem["respons"]+"\'"+", "+str(elem["discount"])+"),"
	exec_str='INSERT INTO public.position (name, salary, responsibilities, discount) VALUES {}'.format(insert_position)
	cursor.execute(exec_str[:len(exec_str)-1])

def worker_generation(adding_flag, worker_amount=0, bar_args=0, position_args=0):
	
	cursor.execute("SELECT count(id) FROM bar")
	bar_amount=cursor.fetchone()[0]

	cursor.execute("SELECT count(id) FROM position")
	position_amount=cursor.fetchone()[0]

	insert_worker=""
	if not adding_flag:

		for i in range(1, bar_amount+1):
			
			bar_size=random.randint(0,2)

			insert_worker+=worker_sql("Администратор",i)
	
			waiter_size=dicts.bar_personal_size[bar_size]["waiter_size"]
			cook_size=dicts.bar_personal_size[bar_size]["cook_size"]
			barmen_size=dicts.bar_personal_size[bar_size]["barmen_size"]

			insert_worker+=worker_sql("Помощник бармена",i)

			for j in range(0,waiter_size):
				insert_worker+= worker_sql("Официант",i)

			for j in range(0,cook_size):
				insert_worker+= worker_sql("Повар",i)
				insert_worker+= worker_sql("Уборщик",i)

			for j in range(0,barmen_size):
				insert_worker+= worker_sql("Бармен",i)		
			insert_worker+= worker_sql("Директор",i)

	else:
		bar_list=()
		position_list=()
		if(bar_args!=0):
			bar_list=bar_args.split(',')
		else:
			bar_list = list(range(1,bar_amount+1))
		if(position_args!=0):
			position_list=position_args.split(',')
		else:
			position_list=list(range(2,position_amount+1))
		for i in range(0,int(worker_amount)):
			insert_worker+= worker_sql(int(random.choice(position_list)),int(random.choice(bar_list)))
	exec_str='INSERT INTO public.worker (name, bar_id, telephone, bonus, entry_date, position_id, address, schedule) VALUES {}'.format(insert_worker)
	cursor.execute(exec_str[:len(exec_str)-1])

	update_admin()

def worker_history_generation(adding_flag, history_amount=0, worker_args=0, bar_args=0, position_args=0):

	if worker_args==0:
		cursor.execute("SELECT count(id) FROM worker")
		amount=cursor.fetchone()[0]
	else:
		worker_args=worker_args.split(',')
		amount=len(worker_args)

	cursor.execute("SELECT count(id) FROM bar")
	bar_amount=cursor.fetchone()[0]

	cursor.execute("SELECT count(id) FROM position")
	position_amount=cursor.fetchone()[0]

	insert_history=""
	for i in range(1,amount+1):

		if not adding_flag:
			number_of_works=random.randint(0,4)
		else:
			number_of_works=math.floor(int(history_amount)/amount)
			reminder=int(int(history_amount)%amount)
			if i>(amount-reminder):
				number_of_works+=1
		date_begin = opening_date
		curr_id=-1;
		if(worker_args==0):
			curr_id=i
		else:
			curr_id=worker_args[i-1]
		cursor.execute("SELECT entry_date, bar_id, position_id FROM public.worker WHERE id=%s",(curr_id,))
		curr_job=cursor.fetchone()
		for j in range(0, number_of_works):

			date_from = get_random_date(date_begin, curr_job[0] - timedelta(days=1))
			date_to=get_random_date(date_begin, curr_job[0] - timedelta(days=1))

			while(date.toordinal(date_from)>date.toordinal(date_to)):
				date_from = get_random_date(date_begin, curr_job[0] - timedelta(days=1))
				date_to=get_random_date(date_begin, curr_job[0] - timedelta(days=1))
			
			if(position_args==0):
				random_position=random.randint(1, position_amount)
			else:
				position_args_=position_args.split(',')
				random_position=random.choice(position_args_)

			if(bar_args==0):
				random_bar=random.randint(1, bar_amount)
			else:
				bar_args_=bar_args.split(',')
				random_bar=random.choice(bar_args_)

			cursor.execute("SELECT salary FROM public.position WHERE id=%s",(random_position,))
			salary=cursor.fetchone()[0] + random.choice(dicts.bonus_list)

			insert_history=insert_history + "( "+str(curr_id)+", "+"\'"+str(date_from)+"\'"+", "+"\'"+str(date_to)+"\'"+", "+str(random_bar)+", "+"\'"+str(random_position)+"\'"+", "+str(salary)+"),"

			date_begin=date_to
		if not adding_flag:
			cursor.execute("SELECT salary FROM public.position WHERE id=%s",(curr_job[2],))
			salary=cursor.fetchone()[0]
			cursor.execute("SELECT bonus FROM public.worker WHERE id=%s",(i,))
			salary+=cursor.fetchone()[0]
			cursor.execute('INSERT INTO public.worker_history (worker_id, date_from, bar_id, position_id, salary) VALUES (%s,%s,%s,%s,%s)',
				(curr_id, str(curr_job[0]), curr_job[1],curr_job[2],salary))

	exec_str='INSERT INTO public.worker_history (worker_id, date_from, date_to, bar_id, position_id, salary) VALUES {}'.format(insert_history)
	cursor.execute(exec_str[:len(exec_str)-1])

def regular_customer_generation(customer_amount, bar_args=0):
	
	if(bar_args==0):
		cursor.execute("SELECT count(id) FROM bar")
		bar_amount=cursor.fetchone()[0]
	else:
		bar_args=bar_args.split(',')
		random_bar=random.choice(bar_args)
	insert_regular=""
	for i in range(0, int(customer_amount)):
		if(bar_args==0):
			random_bar=random.randint(1, bar_amount)
		else:
			random_bar=random.choice(bar_args)
		insert_regular=insert_regular + "( "+"\'"+random.choice(names)+"\'"+", "+ str(i)+", "+"\'"+"c785674868f9bb555c26d09e02509056f28a8bfd1b8dc296978d4db523778d2842c811f3722633520abdc8d6d25989d8536d168b666acce6cda38f759a8a1949"+"\'"+", "+generate_phone()+", "+str(random.randint(1,20)/100)+", "+str(random.randint(1,10000))+", "+str(random_bar)+"),"

	exec_str='INSERT INTO public.regular_customer (name, login, password, telephone, discount, points, favourite_bar_id) VALUES {}'.format(insert_regular)
	cursor.execute(exec_str[:len(exec_str)-1])

def menu_category_generation():
	category_list = dicts.category_list
	insert_category=""
	for elem in category_list:
		insert_category=insert_category + "( "+"\'"+elem["name"]+"\'"+", "+str(elem["adult"])+"),"
	exec_str='INSERT INTO public.menu_category (name, adult) VALUES {}'.format(insert_category)
	cursor.execute(exec_str[:len(exec_str)-1])

def menu_item_generation():
	menu_list=dicts.menu_list
	insert_item=""
	for i in range(0,len(menu_list)):
		for elem in menu_list[i]:
			insert_item=insert_item + "( "+"\'"+elem["name"]+"\'"+", "+"\'"+elem["description"]+"\'"+", "+ "\'"+str(elem["weight"])+"\'"+", "+str(i+1)+", "+str(elem["recomended_price"])+"),"
	exec_str='INSERT INTO public.menu_item ( name, description, weight, category_id, recomended_price) VALUES {}'.format(insert_item)
	cursor.execute(exec_str[:len(exec_str)-1])

def bar_menu_generation(adding_flag, bar_args=0):

	cursor.execute("SELECT count(id) FROM menu_item")
	item_amount=cursor.fetchone()[0]

	if not adding_flag:
		cursor.execute("SELECT count(id) FROM bar")
		bar_amount=cursor.fetchone()[0]
	else:
		bar_amount=1
	
	insert_bar_menu=""
	for i in range(1, bar_amount+1):
		if not adding_flag:
			curr_bar=i
		else:
			curr_bar=bar_args
		for j in range(1, item_amount+1):
			add_item=random.randint(0,9)
			if(add_item!=0):
				cursor.execute("SELECT recomended_price FROM public.menu_item WHERE id=%s",(j,))
				price=cursor.fetchone()[0]
				price=int(price*random.uniform(0.6,1.5))		
				insert_bar_menu=insert_bar_menu + "( "+str(curr_bar)+", "+str(j)+", "+ str(price)+"),"

	exec_str='INSERT INTO public.bar_menu ( bar_id, menu_item_id, price) VALUES {}'.format(insert_bar_menu)
	cursor.execute(exec_str[:len(exec_str)-1])

def promotion_generation(current_amount, old_amount, category_args=0, menu_item_args=0):
	
	if(category_args==0):
		cursor.execute("SELECT count(id) FROM menu_category")
		category_amount=cursor.fetchone()[0]
	else:
		category_args=category_args.split(',')

	if(menu_item_args==0):	
		cursor.execute("SELECT count(id) FROM menu_item")
		item_amount=cursor.fetchone()[0]
	else:
		menu_item_args=menu_item_args.split(',')

	insert_promo=""
	for i in range(0, int(old_amount)):
		date_from = get_random_date(opening_date, date.today()-timedelta(days=1))
		date_to=get_random_date(opening_date, date.today()-timedelta(days=1))

		while(date.toordinal(date_from)>=date.toordinal(date_to)):
			date_from = get_random_date(opening_date, date.today()-timedelta(days=1))
			date_to=get_random_date(opening_date, date.today()-timedelta(days=1))
		date_to_str= "\'"+str(date_to)+"\'"
		if(category_args==0):
			category_id,menu_item_id = category_or_item(category_amount,item_amount)
		elif menu_item_args==0:
			category_id=int(random.choice(category_args))
			menu_item_id="NULL"
		else:
			category_id= int(random.choice(category_args))
			menu_item_id= int(random.choice(menu_item_args))
		date_to_str= "\'"+str(date_to)+"\'"
		insert_promo=insert_promo + "( "+"\'"+str(date_from)+"\'"+", "+date_to_str+", "+ str(category_id)+", "+str(menu_item_id)+", "+"\'No Terms\'"+", "+str(random.randint(1,50)/100)+"),"

	for i in range(0, int(current_amount)):
		date_from = get_random_date(opening_date, date.today())
		date_to_check=random.randint(0,9)

		if(date_to_check!=0):
			date_to=get_random_date(date.today(), date.today()+timedelta(days=365))
			date_to_str= "\'"+str(date_to)+"\'"
		else:
			date_to="NULL"

		if(category_args==0):
			category_id,menu_item_id = category_or_item(category_amount,item_amount)
		elif menu_item_args==0:
			category_id=int(random.choice(category_args))
			menu_item_id="NULL"
		else:
			category_id= int(random.choice(category_args))
			menu_item_id= int(random.choice(menu_item_args))
		insert_promo=insert_promo + "( "+"\'"+str(date_from)+"\'"+", "+date_to_str+", "+ str(category_id)+", "+str(menu_item_id)+", "+"\'No Terms\'"+", "+str(random.randint(1,50)/100)+"),"

	exec_str='INSERT INTO public.promotion (date_from, date_to, category_id, menu_item_id, terms, discount) VALUES {}'.format(insert_promo)
	cursor.execute(exec_str[:len(exec_str)-1])

def order_history_generation(amount, bar_args=0):

	if(bar_args==0):
		cursor.execute("SELECT count(id) FROM bar")
		bar_amount=cursor.fetchone()[0]
	else:
		bar_args=bar_args.split(',')

	insert_order_hist=""
	for i in range(0, int(amount)):
		if(bar_args==0):
			random_bar=random.randint(1,bar_amount)
		else:
			random_bar=int(random.choice(bar_args))

		insert_order_hist=insert_order_hist + "( "+str(random_bar)+", "+"\'"+str(random_date_and_time(date.today().replace(day=1, month=1,
			year=2010),date.today()))+ " " + str(random.randint(0,23)) + ":" + str(random.randint(0,59)) + ":" + str(random.randint(0,59))+"\'"+", "+"\'"+ random.choice(names)+"\'"+"),"

	exec_str='INSERT INTO public.order_history (bar_id, date_and_time, customer_name) VALUES {}'.format(insert_order_hist)
	cursor.execute(exec_str[:len(exec_str)-1])


def order_items_generation(adding_flag, order_args=0):

	cursor.execute("SELECT count(id) FROM menu_item")
	item_amount=cursor.fetchone()[0]

	if not adding_flag:
		cursor.execute("SELECT count(id) FROM order_history")
		order_amount=cursor.fetchone()[0]
	else:
		order_args=order_args.split(',')
		order_amount=len(order_args)

	insert_order_items=""
	for i in range(1, order_amount+1):

		if not adding_flag:
			curr_order=i
		else:
			curr_order=int(order_args[i-1])

		already_ordered=[]
		position_amount=random.randint(1,20)
		for j in range(0,position_amount):
			curr_item=random.randint(1,item_amount)
			while curr_item in already_ordered:
				curr_item=random.randint(1,item_amount)

			already_ordered.append(curr_item)

			curr_item_amount=random.randint(1,10)

			cursor.execute("SELECT bar_id FROM order_history WHERE id=%s",(curr_order,))
			curr_bar=cursor.fetchone()[0]
			cursor.execute("SELECT price FROM bar_menu WHERE bar_id=%s AND menu_item_id=%s",(curr_bar, curr_item))
			price=cursor.fetchone()

			if(price==None):
				continue
			else:
				price=price[0]
			insert_order_items=insert_order_items + "( "+str(curr_order)+", "+str(curr_item)+", "+ str(price)+", "+str(curr_item_amount)+"),"
		
	exec_str='INSERT INTO public.order_items (order_id,menu_item_id, price, amount) VALUES {}'.format(insert_order_items)
	cursor.execute(exec_str[:len(exec_str)-1])

def category_or_item(category_amount,item_amount):
	category_promo=random.choice((True,False))
	if category_promo:
		category_id=random.randint(1,category_amount)
		menu_item_id="NULL"
	else:
		menu_item_id=random.randint(1,item_amount)
		cursor.execute("SELECT category_id FROM menu_item WHERE id=%s",(menu_item_id,))
		category_id=cursor.fetchone()[0]
	return category_id,menu_item_id

def worker_sql(pos,id):
	
	pos_index=0
	if type(pos)==type(""):
		for index,elem in enumerate(dicts.position_list):
			if pos == elem["name"]:
				pos_index=index+1
	else:
		pos_index=pos

	address=str(random.choice(addresses))
	address=address[0: len(address)-1] + ", д." + str(random.randint(1,99)) + ", кв " + str(random.randint(1,1000)) 

	insert_worker="( "+"\'"+str(random.choice(names))+"\'"+", "+str(id)+", "+generate_phone()+", "+str(random.choice(dicts.bonus_list))+", "+"\'"+str(get_random_date(opening_date,
	 date.today()))+"\'"+", "+str(pos_index)+", "+"\'"+address+"\'"+", "+"\'"+generate_schedule()+"\'"+"),"
		
	return insert_worker	

def update_admin():
	cursor.execute("SELECT id, bar_id FROM worker WHERE position_id=1")
	needed_updates=cursor.fetchall()
	for elem in needed_updates:
		cursor.execute('UPDATE public.bar SET admin_id=%s WHERE id=%s',(elem[0],elem[1]))	

def generate_schedule():

	week_list=dicts.week_list
	day_from = random.randint(0, len(week_list)-1)
	day_to = random.randint(day_from, len(week_list)-1)

	time_from = random.randint(0,25)
	time_to = random.randint(0,25)

	return "{}-{}, {}:00-{}:00".format(week_list[day_from],week_list[day_to],time_from,time_to)

def generate_phone():
	first = str(random.randint(900,999))
	second = str(random.randint(1,888)).zfill(3)

	last = (str(random.randint(1,9998)).zfill(4))
	return '+7{}{}{}'.format(first,second, last)

def get_random_date(start, end):
	if(start.toordinal() >= end.toordinal()):
		return start
	random_day = date.fromordinal(random.randint(start.toordinal(), end.toordinal()))
	return random_day


def random_date_and_time(start, end):
    return start + timedelta(
        seconds=random.randint(0, int((end - start).total_seconds())),
    )

if __name__ == "__main__":
	main()
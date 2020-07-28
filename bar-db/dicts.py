from datetime import date, timedelta

position_list = ({"name":"Администратор","salary":100000, "respons":"Управление конкретным баром", "discount":0.15},
 {"name":"Официант","salary":30000, "respons":"Обслуживание клиентов", "discount":0.10},
 {"name":"Повар","salary":35000, "respons":"Готовка еды", "discount":0.10},
 {"name":"Бармен","salary":35000, "respons":"Приготовление напитков", "discount":0.10},
 {"name":"Помощник бармена","salary":20000, "respons":"Помощь в приготовлении напитков", "discount":0.05},
 {"name":"Уборщик","salary":30000, "respons":"Уборка помещений", "discount":0.05},
 {"name":"Директор","salary":200000, "respons":"Общий контроль всех заведений", "discount":0.20})

opening_date=date.today().replace(day=1, month=1, year=2010)

bonus_list=(200,300,500,750,1000,1500,3000,5000)

week_list=("ПН","ВТ","СР","ЧТ","ПТ","СБ","ВС")

bar_personal_size=({"waiter_size":5,"cook_size":2,"barmen_size":2},{"waiter_size":10,"cook_size":4,"barmen_size":4},
	{"waiter_size":15,"cook_size":8,"barmen_size":6})

category_list=({"name": "Разливное Пиво", "adult":True},{"name": "Бутылочное пиво", "adult":True},
	{"name": "Безалкогольное пиво", "adult":False},{"name": "Крафтовое пиво", "adult":True},
	{"name": "Горячие закуски", "adult":False},{"name": "Холодные закуски", "adult":False},
	{"name": "Салаты", "adult":False},{"name": "Горячие блюда", "adult":False},
	{"name": "Десерты", "adult":False},{"name": "Кофе", "adult":False},
	{"name": "Чай", "adult":False},{"name": "Вино", "adult":True},
	{"name": "Виски", "adult":True},{"name": "Коньяк", "adult":True},
	{"name": "Текила", "adult":True},{"name": "Ром", "adult":True},
	{"name": "Ликёры", "adult":True},{"name": "Абсент", "adult":True},
	{"name": "Коктейли", "adult":True},{"name": "Вермут", "adult":True})

draft_beer_list=({"name":"Guinness Dark Stout","description":"Гиннес тёмный стаут - 4.2%","weight":0.5,"recomended_price":410},
	{"name":"Kilkenny Red Ale","description":"Килкенни красный эль - 4.3%","weight":0.5,"recomended_price":410},
	{"name":"Heineken","description":"Хайнекен светлый лагер - 5.0%","weight":0.5,"recomended_price":255},
	{"name":"Affligem","description":"Светлый аббатский эль - 6.8%","weight":0.5,"recomended_price":340},
	{"name":"Youngs Double Chocolate Stout","description":"Шоколадный стаут - 5.2%","weight":0.5,"recomended_price":320})

bottled_beer_list=({"name":"Krusovice","description":"Крушовице тёмный лагер - 3.8%","weight":0.5,"recomended_price":270},
	{"name":"Corona Extra","description":"Корона американский лагер - 4.5%","weight":0.355,"recomended_price":380},
	{"name":"Heineken","description":"Хайнекен светлый лагер - 5.0%","weight":0.5,"recomended_price":270},
	{"name":"Rogue Shakespeare Stout","description":"Тёмный американский стаут - 5.8%","weight":0.355,"recomended_price":560},
	{"name":"Anderson Valley Fall Hornin","description":"Тыквенный американский эль - 6.0%","weight":0.355,"recomended_price":590})

nonalcoholic_beer_list= ({"name":"Clausthaler","description":"Клаусталер","weight":0.25,"recomended_price":260},
	{"name":"Maisels Weisse","description":"Майзелс вайс нефильтрованное","weight":0.5,"recomended_price":400})

сraft_beer_list=({"name":"Bumblebee Amber Ale","description":"Мохнатый шмель янтарный эль - 5.0%","weight":0.5,"recomended_price":255},
	{"name":"Irish Stout","description":"Ирландский сухой стаут - 4.1%","weight":0.5,"recomended_price":255},
	{"name":"English Ale","description":"Английский эль - 6.2%","weight":0.5,"recomended_price":255},
	{"name":"Бланш De Mazaй","description":"Белое пшеничное пиво- 5.9%","weight":0.5,"recomended_price":255},
	{"name":"Кеговое с лавинным эффектом","description":"Светлое плотное и ароматное пиво - 5.5%","weight":0.5,"recomended_price":255})

hot_snack_list= ({"name":"Домашние гренки с сыром","description":"--","weight":150,"recomended_price":270},
	{"name":"Куриные палочки","description":"--","weight":150,"recomended_price":350},
	{"name":"Сырные палочки","description":"--","weight":150,"recomended_price":350},
	{"name":"Куриные крылья","description":"--","weight":160,"recomended_price":350},
	{"name":"Фиш & Чипс","description":"--","weight":250,"recomended_price":440})

cold_snack_list= ({"name":"Ассорти солений","description":"--","weight":160,"recomended_price":300},
	{"name":"Сельдь слабой соли","description":"С картофелем","weight":320,"recomended_price":350},
	{"name":"Сырная тарелка","description":"--","weight":160,"recomended_price":330},
	{"name":"Мясная тарелка","description":"--","weight":160,"recomended_price":390})

salads_list= ({"name":"Салат из свежих овощей","description":"--","weight":150,"recomended_price":250},
	{"name":"Салат с куриной печенью и ягодным соусом","description":"--","weight":210,"recomended_price":280},
	{"name":"Цезарь с курицей","description":"--","weight":210,"recomended_price":360},
	{"name":"Салат с ростбифом","description":"--","weight":210,"recomended_price":410},
	{"name":"Цезарь с креветками","description":"--","weight":210,"recomended_price":420})

main_dishes_list= ({"name":"Спагетти карбонара","description":"--","weight":300,"recomended_price":420},
	{"name":"Бургер с мраморной говядиной","description":"--","weight":550,"recomended_price":555},
	{"name":"Бефстроганов с картофельным пюре","description":"--","weight":300,"recomended_price":450},
	{"name":"Стейк из лосося","description":"--","weight":350,"recomended_price":720},
	{"name":"Стейк рибай","description":"--","weight":290,"recomended_price":1190})

dessert_list= ({"name":"Лимонный мусс с лесными ягодами и печеньем","description":"--","weight":180,"recomended_price":280},
	{"name":"Тирамису","description":"--","weight":110,"recomended_price":280},
	{"name":"Яблочный пирог с солёной карамелью","description":"--","weight":180,"recomended_price":280},
	{"name":"Торт \"Три Шоколада\"","description":"--","weight":150,"recomended_price":280},
	{"name":"Мороженое","description":"Фисташковое, Клубничное, Шоколадное, Ванильное","weight":50,"recomended_price":80})

coffee_list= ({"name":"Эспрессо","description":"--","weight":50,"recomended_price":150},
	{"name":"Американо","description":"--","weight":200,"recomended_price":150},
	{"name":"Капуччино","description":"--","weight":200,"recomended_price":180},
	{"name":"Латте","description":"--","weight":200,"recomended_price":210},
	{"name":"Irish coffee","description":"--","weight":200,"recomended_price":520})

tea_list= ({"name":"Цейлон","description":"--","weight":300,"recomended_price":230},
	{"name":"Эрл Грей","description":"--","weight":300,"recomended_price":230},
	{"name":"Молочный улун","description":"--","weight":300,"recomended_price":280},
	{"name":"Ласные ягоды","description":"--","weight":300,"recomended_price":230},
	{"name":"Иван-чай","description":"--","weight":300,"recomended_price":300})

wine_list= ({"name":"Монтефьоре Пино Гриджио","description":"Италия, Белое, Полусухое, Тихое - 12.5%","weight":0.15,"recomended_price":340},
	{"name":"Каса Де Барро Совиньон Блан","description":"Чили, Белое, Сухое, Тихое - 13%","weight":0.15,"recomended_price":350},
	{"name":"Пти Шабли Ле Серен","description":"Франция, Белое, Сухое, Тихое - 12%","weight":0.75,"recomended_price":4500},
	{"name":"Вальполичелла Супериоре Миццоле","description":"Италия, Красное, Полусухое, Тихое - 12%","weight":0.15,"recomended_price":480},
	{"name":"Классик Де Мари Манес","description":"Франция, Красное, Сухое, Тихое - 13%","weight":0.15,"recomended_price":320})

whiskey_list=({"name":"Jameson","description":"Ирландский виски","weight":0.04,"recomended_price":260},
	{"name":"Jameson 12Y Special Reserve","description":"Ирландский виски, 12 лет выдержки","weight":0.04,"recomended_price":410},
	{"name":"Bushmills 10Y","description":"Ирландский виски, 10 лет выдержки","weight":0.04,"recomended_price":330},
	{"name":"Chivas Regal 12Y","description":"Шотландский виски, 12 лет выдержки","weight":0.04,"recomended_price":350},
	{"name":"Johnie Walker Black Label","description":"Шотландский виски","weight":0.04,"recomended_price":370},
	{"name":"Jack Daniels","description":"Американский виски","weight":0.04,"recomended_price":330})

brandy_list=({"name":"Hennessy VSOP","description":"--","weight":0.04,"recomended_price":530},
	{"name":"Hine XO","description":"--","weight":0.04,"recomended_price":1350},
	{"name":"Армянский 5*","description":"--","weight":0.04,"recomended_price":250},
	{"name":"Remi Martin VSOP","description":"--","weight":0.04,"recomended_price":510},
	{"name":"Frapin VS","description":"--","weight":0.04,"recomended_price":350})

tequila_list=({"name":"Olmeca Silver","description":"--","weight":0.04,"recomended_price":280},
	{"name":"Olmeca Gold","description":"--","weight":0.04,"recomended_price":280},
	{"name":"Jose Cuervo Silver","description":"--","weight":0.04,"recomended_price":310},
	{"name":"Jose Cuervo Reposado","description":"--","weight":0.04,"recomended_price":310})

rum_list= ({"name":"Bacardi Blanca","description":"--","weight":0.04,"recomended_price":250},
	{"name":"Bacardi Black","description":"--","weight":0.04,"recomended_price":250},
	{"name":"Havana Club 7Y","description":"--","weight":0.04,"recomended_price":310},
	{"name":"El Dorado 12Y","description":"--","weight":0.04,"recomended_price":470},
	{"name":"Zacapa 23Y","description":"--","weight":0.04,"recomended_price":520})

liqueurs_list=({"name":"Egermeister","description":"--","weight":0.04,"recomended_price":260},
	{"name":"Becherovka","description":"--","weight":0.04,"recomended_price":260},
	{"name":"Kahlua","description":"--","weight":0.04,"recomended_price":240},
	{"name":"Sambuca","description":"--","weight":0.04,"recomended_price":250},
	{"name":"Бальзам Рижский","description":"--","weight":0.04,"recomended_price":190})

absinthe_list=({"name":"Xenta","description":"--","weight":0.04,"recomended_price":290},)

cocktails_list=({"name":"Лонг Айленд Айс Ти","description":"--","weight":0.04,"recomended_price":540},
	{"name":"Б-52","description":"--","weight":0.04,"recomended_price":350},
	{"name":"Дайкири","description":"--","weight":0.04,"recomended_price":320},
	{"name":"Маргарита","description":"--","weight":0.04,"recomended_price":420},
	{"name":"Мохито","description":"--","weight":0.04,"recomended_price":400})

vermouth_list=({"name":"Martini Bianco","description":"--","weight":0.1,"recomended_price":270},
	{"name":"Martini Rosso","description":"--","weight":0.1,"recomended_price":270},
	{"name":"Martini Rose","description":"--","weight":0.1,"recomended_price":270},
	{"name":"Martini Extra Dry","description":"--","weight":0.1,"recomended_price":270})

menu_list=(draft_beer_list,bottled_beer_list,nonalcoholic_beer_list,сraft_beer_list, hot_snack_list,
 cold_snack_list, salads_list,main_dishes_list, dessert_list, coffee_list, tea_list, wine_list,
 whiskey_list,brandy_list,tequila_list, rum_list, liqueurs_list, absinthe_list,cocktails_list, vermouth_list)

if len(category_list)>len(menu_list):
	print("WARNING: Some categories are not presenting in the menu")
elif(len(category_list)<len(menu_list)):
	print("ERROR: Menu list have more categories than category list")


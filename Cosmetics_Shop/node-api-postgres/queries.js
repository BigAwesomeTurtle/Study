const Pool = require('pg').Pool
const pool = new Pool({
    user: 'anya',
    host: 'localhost',
    database: 'cosmetics_shop',
    password: 'anyadb',
    port: 5432,
})

const cosm_shop_get_groups = (request, response) => {

    pool.query(`SELECT group_name FROM public.group`,(error, results) => {
     if (error) {
        response.status(400).json([{'details':'Bad request'}])
     }
     else{
        response.status(200).json(results.rows)
     }
 
})
}

const cosm_shop_get_client = (request, response) => {
    const client_login = encodeURIComponent(request.params.client_login)
    pool.query(`SELECT * FROM client WHERE login='${client_login}'`,(error, results) => {
     if (error) {
        response.status(400).json([{'details':'Bad request'}])
     }
     else{
        response.status(200).json(results.rows)
     }
 
})
}

const cosm_shop_get_client_password = (request, response) => {
    const client_login = encodeURIComponent(request.params.client_login)
    pool.query(`SELECT password FROM client WHERE login='${client_login}'`,(error, results) => {
     if (error) {
        response.status(400).json([{'details':'Bad request'}])
     }
     else{
        response.status(200).json(results.rows)
     }
 
})
}

const cosm_shop_check_client = (request, response) => {
    const client_login = encodeURIComponent(request.params.client_login)
    pool.query(`SELECT client_id FROM client WHERE login='${client_login}'`,(error, results) => {
     if (error) {
        response.status(400).json([{'details':'Bad request'}])
     }
     else if(results.rows.length==0){
        response.status(200).json([{'result':'False'}])
     }
     else{
        response.status(200).json([{'result':'True'}])
     }
 
})
}

const cosm_shop_get_item_by_group = (request, response) => {
    const group_id = parseInt(request.params.group_id)

    pool.query(`SELECT * FROM item WHERE group_id=${group_id}`,(error, results) => {
     if (error) {
        response.status(400).json([{'details':'Bad request'}])
     }
     else{
        response.status(200).json(results.rows)
     }
 
})
}

const cosm_shop_get_item_by_article = (request, response) => {
    const article = parseInt(request.params.article)

    pool.query(`SELECT * FROM item WHERE article=${article}`,(error, results) => {
     if (error) {
        response.status(400).json([{'details':'Bad request'}])
     }
     else{
        response.status(200).json(results.rows)
     }
 
})
}

const cosm_shop_get_client_orders= (request, response) => {
    
    const client = parseInt(request.params.client)

    pool.query(`SELECT * FROM client_order WHERE client=${client}`,(error, results) => {
     if (error) {
        response.status(400).json([{'details':'Bad request'}])
     }
     else{
        response.status(200).json(results.rows)
     }
 
})
}

const cosm_shop_get_client_max_order_id= (request, response) => {
    
    const client = parseInt(request.params.client)

    pool.query(`SELECT MAX(order_id) FROM client_order WHERE client=${client}`,(error, results) => {
     if (error) {
        response.status(400).json([{'details':'Bad request'}])
     }
     else{
        response.status(200).json(results.rows)
     }
 
})
}

const cosm_shop_add_client = (request, response) => {

    const name = encodeURIComponent(request.params.name)
    const surname = encodeURIComponent(request.params.surname)
    const phone = encodeURIComponent(request.params.phone)
    const email = request.params.email
    const login = encodeURIComponent(request.params.login)
    const password = encodeURIComponent(request.params.password)

    pool.query(`INSERT INTO client (name, surname, phone, email ,login, password) VALUES ('${name}','${surname}','${phone}','${email}','${login}','${password}')`,(error, results) => {
     if (error) {
        response.status(400).json([{'details':'Bad request'}])
     }
     else{
        response.status(200).json([{'result':'True'}])
     }
 
})
}

const cosm_shop_add_client_order = (request, response) => {

    const client = encodeURIComponent(request.params.client)
    const order_date = encodeURIComponent(request.params.order_date)
    const seller_id = encodeURIComponent(request.params.seller_id)
    const status = encodeURIComponent(request.params.status)
    const total_sum = encodeURIComponent(request.params.total_sum)

    pool.query(`INSERT INTO client_order (client,order_date,seller_id,status,total_sum) VALUES ('${client}','${order_date}',${seller_id},'${status}',${total_sum})`,(error, results) => {
     if (error) {
        response.status(400).json([{'details':'Bad request'}])
     }
     else{
        response.status(200).json([{'result':'True'}])
     }
 
})
}

const cosm_shop_add_item_list_to_order = (request, response) => {

    const item_id = encodeURIComponent(request.params.item_id)
    const order_id = encodeURIComponent(request.params.order_id)
    const amount = encodeURIComponent(request.params.amount)
    const total_price = encodeURIComponent(request.params.total_price)

    pool.query(`INSERT INTO item_list_to_order (item_id,order_id,amount,total_price) VALUES (${item_id},${order_id},${amount},${total_price})`,(error, results) => {
     if (error) {
        response.status(400).json([{'details':'Bad request'}])
     }
     else{
        response.status(200).json([{'result':'True'}])
     }
 
})
}

const cosm_shop_update_num_in_shop= (request, response) => {

    const article = parseInt(request.params.article)
    const value = parseInt(request.params.value)

    pool.query(`UPDATE item SET num_in_shop=${value} WHERE article=${article}`,(error, results) => {
     if (error) {
        response.status(400).json([{'details':'Bad request'}])
     }
     else{
        response.status(200).json([{'result':'True'}])
     }
 
})
}


module.exports = {
  cosm_shop_get_groups,
  cosm_shop_get_client,
  cosm_shop_get_client_password,
  cosm_shop_check_client,
  cosm_shop_get_item_by_group,
  cosm_shop_get_item_by_article,
  cosm_shop_get_client_orders,   
  cosm_shop_get_client_max_order_id,
  cosm_shop_add_client,
  cosm_shop_add_client_order,
  cosm_shop_add_item_list_to_order,
  cosm_shop_update_num_in_shop,
}
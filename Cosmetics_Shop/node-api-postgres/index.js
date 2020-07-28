const express = require('express')
const bodyParser = require('body-parser')
const app = express()
const port = 8080
const db = require('./queries')

app.use(express.json())
app.use(bodyParser.json())
app.use(
  bodyParser.urlencoded({
    extended: true,
  })
)

app.get('/getGroups',db.cosm_shop_get_groups)
app.get('/getClient/:client_login',db.cosm_shop_get_client)
app.get('/getClientPassword/:client_login',db.cosm_shop_get_client_password)
app.get('/isClientExists/:client_login',db.cosm_shop_check_client)
app.get('/getItemByGroup/:group_id',db.cosm_shop_get_item_by_group)
app.get('/getItemByArticle/:article',db.cosm_shop_get_item_by_article)
app.get('/getClientOrder/:client',db.cosm_shop_get_client_orders)
app.get('/getClientMaxOrderId/:client',db.cosm_shop_get_client_max_order_id)

app.put('/addClient/:name/:surname/:phone/:email/:login/:password',db.cosm_shop_add_client)
app.put('/addClientOrders/:client/:order_date/:seller_id/:status/:total_sum',db.cosm_shop_add_client_order)
app.put('/addItemListToOrder/:item_id/:order_id/:amount/:total_price',db.cosm_shop_add_item_list_to_order)

app.post('/updateNumInShop/:article/:value',db.cosm_shop_update_num_in_shop)

app.listen(port, () => {
  console.log(`App running on port ${port}.`)
})
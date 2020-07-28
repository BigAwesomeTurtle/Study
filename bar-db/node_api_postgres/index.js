const express = require('express')
const bodyParser = require('body-parser')
const app = express()
const port = 3222
const db = require('./queries')
const { body } = require('express-validator');

app.use(express.json())
app.use(bodyParser.json())
app.use(
  bodyParser.urlencoded({
    extended: true,
  })
)


app.post('/get', [
  body('table').trim().escape(),
  body('colons').trim().escape()
	],db.db_get)
app.post('/insert', [
  body('table').trim().escape(),
  body('fields').trim().escape()
	], db.db_insert)
app.post('/delete', [
  body('table').trim().escape(),
  body('field').trim().escape()
	], db.db_delete)
app.post('/update', [
  body('table').trim().escape()
	], db.db_update)

app.listen(port, () => {
  console.log(`App running on port ${port}.`)
})
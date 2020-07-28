const Pool = require('pg').Pool
const pool = new Pool({
  user: 'bar_user',
  host: '192.168.0.101',
  database: 'bar_db',
  password: 'barbar',
  port: 5432,
})

const db_get = (request, response) => {

	const table  = request.body.table
	var colons = request.body.colons
	var where = request.body.where
	var parse_where=""

	if (typeof colons == 'undefined' )
	{
  		parse_cols="*"
	}
	else{
		colons=colons.substring(1, colons.length - 1)
		colons=colons.split(",")
		var parse_cols = ""

		colons.forEach(elem => { 
			parse_cols+=elem
			parse_cols+=","
		});
		parse_cols=parse_cols.substring(0, parse_cols.length - 1);

	}

	if (typeof where != 'undefined' )
	{
  		where=where.substring(1, where.length - 1)
		where=where.split(",")
		if(where.length % 3 == 0){

			for (i=0;i<where.length;i=i+3){
				if(i!=0){
					parse_where=parse_where+ " AND "
				}
				parse_where=parse_where + where[i] + where[i+2] + where[i+1]
			}
			
		}
	}

	if ( parse_where != ""){
  		pool.query(`SELECT ${parse_cols} FROM ${table} WHERE ${parse_where} ORDER BY id ASC`,(error, results) => {
	    if (error) {
	       response.status(400).json([{'details':'Bad request'}])
	    }
	    else{
		response.status(200).json(results.rows)
	}
		})
	}
	else{
		pool.query(`SELECT ${parse_cols} FROM ${table} ORDER BY id ASC`,(error, results) => {
	    if (error) {
	      response.status(400).json([{'details':'Bad request'}])
	    }
	    else{
		response.status(200).json(results.rows)
	}
		})
	}

	
}

const db_insert = (request, response) => {

	const table  = request.body.table
	var fields = request.body.fields
	var values = request.body.values
	var parse_fields="("
	var parse_values="("

	fields=fields.substring(1, fields.length - 1)
	parse_fields=parse_fields+fields+")"

	values=values.substring(1, values.length - 1)
	parse_values=parse_values+values+")"

	pool.query(`INSERT INTO ${table} ${parse_fields} VALUES ${parse_values}`,(error, results) => {
	if (error) {
	  response.status(400).json([{'details':'Bad request'}])
	}else{
		response.status(200).json(results.rows)
	}
	
	})
}

const db_update = (request, response) => {

	const table  = request.body.table
	const field = request.body.field
	const value = request.body.value
	var where = request.body.where
	var parse_where=""
	if (typeof where != 'undefined' )
	{
  		where=where.substring(1, where.length - 1)
		where=where.split(",")
		if(where.length % 3 == 0){

			for (i=0;i<where.length;i=i+3){
				if(i!=0){
					parse_where=parse_where+ " AND "
				}
				parse_where=parse_where + where[i] + where[i+2] + where[i+1]
			}
			
		}
	}
	if ( parse_where != ""){
  		pool.query(`UPDATE ${table} SET ${field}=${value} WHERE ${parse_where}`,(error, results) => {
	    if (error) {
	      response.status(400).json([{'details':'Bad request'}])
	    }
	    else{
		response.status(200).json(results.rows)
	}
		})
	}
}

const db_delete = (request, response) => {

	const table  = request.body.table
	var where = request.body.where
	var parse_where=""
	if (typeof where != 'undefined' )
	{
  		where=where.substring(1, where.length - 1)
		where=where.split(",")
		if(where.length % 3 == 0){

			for (i=0;i<where.length;i=i+3){
				if(i!=0){
					parse_where=parse_where+ " AND "
				}
				parse_where=parse_where + where[i] + where[i+2] + where[i+1]
			}
			
		}
	}
	if ( parse_where != ""){
  		pool.query(`DELETE FROM ${table} WHERE ${parse_where}`,(error, results) => {
	    if (error) {
	      response.status(400).json([{'details':'Bad request'}])
	    }
	    else{
		response.status(200).json(results.rows)
	}
		})
	}
}


module.exports = {
  db_get,
  db_insert,
  db_delete,
  db_update,
}
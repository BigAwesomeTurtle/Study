package com.lyubchenkova.cosmetics_shop.Adapters

import android.app.Activity
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.lyubchenkova.cosmetics_shop.R
import kotlinx.android.synthetic.main.cart_pos_elem.view.*
import org.json.JSONArray
import org.json.JSONObject

class ClientAdapter (private val clientData: List<Pair<Int,Int>>, private val activity:Activity) :
    RecyclerView.Adapter<ClientAdapter.ClientViewHolder>() {

    inner class ClientViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientViewHolder =
        ClientViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cart_pos_elem, parent, false))

    override fun onBindViewHolder(holder: ClientViewHolder, position: Int) {
        holder.view.apply {

            val queue = Volley.newRequestQueue(context)
            val request = StringRequest(
                Request.Method.GET, "HTTP://192.168.0.101:8080/getItemByArticle/"+clientData[position].first,
                Response.Listener { response ->
                    val curr = JSONObject(JSONArray(response)[0].toString())
                    cart_elem_name_text_view.text="%s:".format(curr.getString("item_name"))
                    cart_elem_amount_text_view.text=clientData[position].second.toString()
                    cart_elem_price_text_view.text=curr.getString("cost")
                },
                Response.ErrorListener {
                    val toast = Toast.makeText(
                        context,
                        "Something went wrong while accessing the server",
                        Toast.LENGTH_SHORT
                    )
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                })
            queue.add(request)
        }
    }
    override fun getItemCount() = clientData.size

}
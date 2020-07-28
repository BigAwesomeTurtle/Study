package com.lyubchenkova.cosmetics_shop.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lyubchenkova.cosmetics_shop.R
import kotlinx.android.synthetic.main.order_element.view.*
import org.json.JSONArray
import org.json.JSONObject

class OrdersAdapter (private val ordersData: JSONArray) :
    RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder>() {

    inner class OrdersViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder =
        OrdersViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.order_element, parent, false))

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        holder.view.apply {
            val curr = JSONObject(ordersData[position].toString())
            order_id_text_view.text=curr.getString("order_id")
            order_status_text_view.text=curr.getString("status")
            order_date_text_view.text=curr.getString("order_date").substring(0,10)
            order_cost_text_view.text=curr.getString("total_sum")
        }
    }
    override fun getItemCount() = ordersData.length()
}
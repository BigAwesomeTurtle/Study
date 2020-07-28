package org.BigAwesomeTurtle.bar_db_app.RecycleAdapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.promo_element.view.*
import org.BigAwesomeTurtle.bar_db_app.R
import org.json.JSONArray
import org.json.JSONObject

class PromoAdapter(private val PromoData: JSONArray, private val categoryMap: Map<String,String>, private val menuItemMap: Map<String,String>) :
    RecyclerView.Adapter<PromoAdapter.PromoViewHolder>() {

    inner class PromoViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PromoViewHolder =
       PromoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.promo_element, parent, false))

    override fun onBindViewHolder(holder: PromoViewHolder, position: Int) {
        val curr = JSONObject(PromoData[position].toString())
        val resPromo:String?
        resPromo = if(curr.getString("menu_item_id")!="null"){
            menuItemMap[curr.getString("menu_item_id")]
        } else{
            categoryMap[curr.getString("category_id")]
        }

        holder.view.apply {
            promo_title_text_view.text = "С %s по %s".format(curr.getString("date_from").substring(0,10),curr.getString("date_to").substring(0,10))
            promo_descr_text_view.text=curr.getString("terms")
            promo_discount_text_view.text="-%d%% на %s".format((curr.getString("discount").toDouble()*100).toInt(), resPromo)
        }
    }
    override fun getItemCount() = PromoData.length()
}
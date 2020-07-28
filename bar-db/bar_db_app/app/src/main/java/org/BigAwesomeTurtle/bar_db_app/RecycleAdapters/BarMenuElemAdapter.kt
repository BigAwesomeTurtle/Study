package org.BigAwesomeTurtle.bar_db_app.RecycleAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.bar_menu_elem_element.view.*
import org.BigAwesomeTurtle.bar_db_app.R
import org.json.JSONArray
import org.json.JSONObject

class BarMenuElemAdapter(private val barMenuElemData: JSONArray) :
    RecyclerView.Adapter<BarMenuElemAdapter.BarMenuElemViewHolder>() {

    inner class BarMenuElemViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarMenuElemViewHolder =
        BarMenuElemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.bar_menu_elem_element, parent, false))

    override fun onBindViewHolder(holder: BarMenuElemViewHolder, position: Int) {
        holder.view.apply {
            val curr = JSONObject(barMenuElemData[position].toString())
            pos_name_text_view.text = curr.getString("name")
            description_text_view.text = curr.getString("description")
            weight_text_view.text = curr.getString("weight")
            price_text_view.text = "%s р".format(curr.getString("recomended_price"))

        }
    }
    override fun getItemCount() = barMenuElemData.length()
}
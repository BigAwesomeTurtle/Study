package org.BigAwesomeTurtle.bar_db_app.RecycleAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.bar_menu_element.view.*
import org.BigAwesomeTurtle.bar_db_app.R
import org.json.JSONArray
import org.json.JSONObject


class BarMenuAdapter(private val barMenuData: JSONArray) :
    RecyclerView.Adapter<BarMenuAdapter.BarMenuViewHolder>() {

    inner class BarMenuViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarMenuViewHolder =
        BarMenuViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.bar_menu_element, parent, false))

    override fun onBindViewHolder(holder: BarMenuViewHolder, position: Int) {
        holder.view.apply {
            val curr = JSONObject(barMenuData[position].toString())
            name_text_view.text = curr.getString("name")
            val bundle = bundleOf("category_id" to position+1)
            setOnClickListener { findNavController().navigate(R.id.action_barMenuFragment_to_barMenuElemFragment,bundle) }
        }
    }
    override fun getItemCount() = barMenuData.length()
}
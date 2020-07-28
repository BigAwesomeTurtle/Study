package com.lyubchenkova.cosmetics_shop.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.lyubchenkova.cosmetics_shop.R
import kotlinx.android.synthetic.main.main_menu_element.view.*
import org.json.JSONArray
import org.json.JSONObject

class MainMenuAdapter (private val mainMenuData: JSONArray) :
    RecyclerView.Adapter<MainMenuAdapter.MainMenuViewHolder>() {

    inner class MainMenuViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainMenuViewHolder =
       MainMenuViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.main_menu_element, parent, false))

    override fun onBindViewHolder(holder: MainMenuViewHolder, position: Int) {
        holder.view.apply {
            val curr = JSONObject(mainMenuData[position].toString())
            group_name_text_view.text = curr.getString("group_name")
            val bundle = bundleOf("group_id" to position+1)
            setOnClickListener { findNavController().navigate(R.id.action_mainMenuFragment_to_mainMenuElemFragment,bundle) }
        }
    }
    override fun getItemCount() = mainMenuData.length()
}
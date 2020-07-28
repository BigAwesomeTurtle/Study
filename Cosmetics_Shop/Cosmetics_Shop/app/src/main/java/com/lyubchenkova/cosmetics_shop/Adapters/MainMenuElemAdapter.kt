package com.lyubchenkova.cosmetics_shop.Adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.lyubchenkova.cosmetics_shop.MainActivity
import com.lyubchenkova.cosmetics_shop.R
import kotlinx.android.synthetic.main.main_menu_elem_element.view.*
import org.json.JSONArray
import org.json.JSONObject

class MainMenuElemAdapter (private val MainMenuElemData: JSONArray,private val act: FragmentActivity?) :
    RecyclerView.Adapter<MainMenuElemAdapter.MainMenuElemViewHolder>() {

    inner class MainMenuElemViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainMenuElemViewHolder =
        MainMenuElemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.main_menu_elem_element, parent, false))

    override fun onBindViewHolder(holder: MainMenuElemViewHolder, position: Int) {

        holder.view.apply {
            val curr = JSONObject(MainMenuElemData[position].toString())
            menu_elem_title_text_view.text = curr.getString("item_name")
            menu_elem_in_stock_text_view.text = "In stock: %s".format(curr.getString("num_in_shop"))
            menu_elem_price_text_view.text = "%s rub".format(curr.getString("cost"))

            val currKey=curr.getString("article").toInt()

            menu_elem_plus_text_view.setOnClickListener{
                val curr_amount=menu_elem_amount_text_view.text.toString().toInt()
                if((act as MainActivity).cart.containsKey(currKey)){

                        menu_elem_amount_text_view.text = (curr_amount+1).toString()

                }
                else if(curr_amount<curr.getString("num_in_shop").toInt()){
                    menu_elem_amount_text_view.text = (curr_amount+1).toString()
                }

            }

            menu_elem_minus_text_view.setOnClickListener{
                val curr_amount=menu_elem_amount_text_view.text.toString().toInt()
                if(curr_amount>0){
                    menu_elem_amount_text_view.text = (curr_amount-1).toString()
                }
            }

            menu_elem_btn_to_basket.setOnClickListener {
                if(menu_elem_amount_text_view.text.toString().toInt()!=0){
                    if((act as MainActivity).cart.containsKey(currKey)){
                        act.cart[currKey]=(act.cart[currKey])!!.toInt()+menu_elem_amount_text_view.text.toString().toInt()
                    }
                    else{
                        act.cart[curr.getString("article").toInt()] = menu_elem_amount_text_view.text.toString().toInt()
                    }
                    menu_elem_amount_text_view.text="0"
                }


            }


        }
    }

    override fun getItemCount() = MainMenuElemData.length()

}
package org.BigAwesomeTurtle.bar_db_app

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_promo.*
import org.BigAwesomeTurtle.bar_db_app.RecycleAdapters.PromoAdapter
import org.BigAwesomeTurtle.bar_db_app.model.ServerImpl
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class PromoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_promo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val resCategMap= mutableMapOf<String,String>()
        val resMenuItemMap= mutableMapOf<String,String>()
        super.onViewCreated(view, savedInstanceState)
        val server = ServerImpl(activity)
        val recyclerLayoutManager = LinearLayoutManager(context)
        val dividerItemDecoration = DividerItemDecoration(
            promo_recycler_view.context,
            recyclerLayoutManager.orientation
        )

        server.db_get(
            mapOf(
                Pair("table", "menu_category"),
                Pair("colons", listOf("id","name"))
            )
        ){
            val curr=JSONArray(it)
            for(i in 0 until curr.length()){
                val currObj=JSONObject(curr[i].toString())
                resCategMap[currObj.getString("id")]= currObj.getString("name")
            }
        }

        //Thread.sleep(100)
        server.db_get(
            mapOf(
                Pair("table", "menu_item"),
                Pair("colons", listOf("id","name"))
            )
        ){
            val curr=JSONArray(it)
            for(i in 0 until curr.length()){
                val currObj=JSONObject(curr[i].toString())
                resMenuItemMap[currObj.getString("id")]= currObj.getString("name")
            }
        }

        //Thread.sleep(100)
        server.db_get(
            mapOf(
                Pair("table", "promotion"),
                Pair("colons", listOf("date_from","date_to","category_id","menu_item_id","terms","discount")),
                Pair("where", listOf("date_from", "\'" +Calendar.getInstance().time+ "\'","<=",
                    "date_to", "\'" +Calendar.getInstance().time+ "\'",">"))
            )
        ){
            promo_recycler_view.apply {
                adapter =
                    PromoAdapter(
                        JSONArray(it),resCategMap,resMenuItemMap
                    )
                setHasFixedSize(true)
                layoutManager = recyclerLayoutManager
                addItemDecoration(dividerItemDecoration)
            }
        }
    }
}

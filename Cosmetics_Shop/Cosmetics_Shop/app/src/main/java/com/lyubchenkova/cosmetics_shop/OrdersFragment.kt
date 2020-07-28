package com.lyubchenkova.cosmetics_shop

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.lyubchenkova.cosmetics_shop.Adapters.OrdersAdapter
import kotlinx.android.synthetic.main.fragment_orders.*
import org.json.JSONArray


class OrdersFragment : Fragment() {

    var account_id:Int?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_orders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val queue = Volley.newRequestQueue(context)
        val recyclerLayoutManager = LinearLayoutManager(context)
        val dividerItemDecoration = DividerItemDecoration(
            orders_recycler_view.context,
            recyclerLayoutManager.orientation
        )
        if(account_id==null){
            account_id=arguments?.getString("account_id")?.toInt()
        }

        val request = StringRequest(
            Request.Method.GET, "HTTP://192.168.0.101:8080/getClientOrder/$account_id",
            Response.Listener { response ->
                orders_recycler_view.apply {
                    adapter =
                        OrdersAdapter(
                            JSONArray(response)
                        )
                    setHasFixedSize(true)
                    layoutManager = recyclerLayoutManager
                    addItemDecoration(dividerItemDecoration)

                }
            },
            Response.ErrorListener {
                val toast = Toast.makeText(
                    context,
                    "Something went wrong while accessing the server1",
                    Toast.LENGTH_SHORT
                )
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            })
        queue.add(request)

    }
}

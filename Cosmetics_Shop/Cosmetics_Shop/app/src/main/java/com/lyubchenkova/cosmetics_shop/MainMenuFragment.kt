package com.lyubchenkova.cosmetics_shop

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.lyubchenkova.cosmetics_shop.Adapters.MainMenuAdapter
import kotlinx.android.synthetic.main.fragment_main_menu.*
import org.json.JSONArray


class MainMenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val queue = Volley.newRequestQueue(context)
        val recyclerLayoutManager = LinearLayoutManager(context)
        val dividerItemDecoration = DividerItemDecoration(
            main_menu_recycler_view.context,
            recyclerLayoutManager.orientation
        )

        val request = StringRequest(
            Request.Method.GET, "HTTP://192.168.0.101:8080/getGroups",
            Response.Listener { response ->
                main_menu_recycler_view.apply {
                    adapter =
                        MainMenuAdapter(
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
                    "Something went wrong while accessing the server",
                    Toast.LENGTH_SHORT
                )
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            })
        queue.add(request)

        mainMenuProfileLogo.setOnClickListener {
            if((activity as MainActivity).login==null){
                activity?.onBackPressed()
            }
            else{
                findNavController().navigate(R.id.action_mainMenuFragment_to_accountFragment)
            }
        }
    }
}

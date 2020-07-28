package com.lyubchenkova.cosmetics_shop

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.lyubchenkova.cosmetics_shop.Adapters.MainMenuElemAdapter
import kotlinx.android.synthetic.main.fragment_main_menu.*
import org.json.JSONArray


class MainMenuElemFragment : Fragment() {

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
            Request.Method.GET, "HTTP://192.168.0.101:8080/getItemByGroup/"+arguments?.getInt("group_id"),
            Response.Listener { response ->
                main_menu_recycler_view.apply {
                    adapter =
                        MainMenuElemAdapter(
                            JSONArray(response),activity
                        )
                    setHasFixedSize(true)
                    layoutManager = recyclerLayoutManager
                    addItemDecoration(dividerItemDecoration)
                }
            },
            Response.ErrorListener {

            })
        queue.add(request)

        mainMenuProfileLogo.setOnClickListener {
            if((activity as MainActivity).login==null){
                val intent = Intent(context, LoginActivity::class.java).apply {}
                val extras = Bundle()
                extras.putSerializable("HashMap", (activity as MainActivity).cart)
                intent.putExtras(extras)
                startActivity(intent)
                activity?.finish()
            }
            else{
                findNavController().navigate(R.id.action_mainMenuElemFragment_to_accountFragment)
            }

        }
    }
}

package org.BigAwesomeTurtle.bar_db_app

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_bar_menu_elem.*
import org.BigAwesomeTurtle.bar_db_app.RecycleAdapters.BarMenuElemAdapter
import org.BigAwesomeTurtle.bar_db_app.model.ServerImpl
import org.json.JSONArray

class BarMenuElemFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bar_menu_elem, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val server = ServerImpl(activity)
        val recyclerLayoutManager = LinearLayoutManager(context)
        val dividerItemDecoration = DividerItemDecoration(
            bar_menu_elem_recycler_view.context,
            recyclerLayoutManager.orientation
        )

       server.db_get(mapOf(
            Pair("table","menu_item"),
            Pair("colons", listOf("name","description","weight","recomended_price")),
            Pair("where",listOf("category_id", arguments?.getInt("category_id"),"="))
        )){
            bar_menu_elem_recycler_view.apply {
                adapter =
                    BarMenuElemAdapter(
                        JSONArray(it)
                    )
                setHasFixedSize(true)
                layoutManager = recyclerLayoutManager
                addItemDecoration(dividerItemDecoration)
            }
        }

    }
}

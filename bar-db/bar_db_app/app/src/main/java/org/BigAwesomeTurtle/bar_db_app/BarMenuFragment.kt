package org.BigAwesomeTurtle.bar_db_app

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_bar_menu.*
import org.BigAwesomeTurtle.bar_db_app.RecycleAdapters.BarMenuAdapter
import org.BigAwesomeTurtle.bar_db_app.model.ServerImpl
import org.json.JSONArray


class BarMenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_bar_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val server = ServerImpl(activity)
        val recyclerLayoutManager = LinearLayoutManager(context)
        val dividerItemDecoration = DividerItemDecoration(
            bar_menu_recycler_view.context,
            recyclerLayoutManager.orientation
        )
        server.db_get(mapOf(Pair("table","menu_category"),Pair("colons", listOf("name")))){
            bar_menu_recycler_view.apply {

                    adapter =
                        BarMenuAdapter(
                            JSONArray(it)
                        )
                    setHasFixedSize(true)
                    layoutManager = recyclerLayoutManager
                    addItemDecoration(dividerItemDecoration)

            }
        }

    }

}

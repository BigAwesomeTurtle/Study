package org.bigawesometurtle.lab3_5_final

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

class Fragment2 : Fragment() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.frag_2, container, false)
        view.findViewById<Button>(R.id.from_second_to_first).setOnClickListener {
            navController.navigate(R.id.action_fragment2_to_fragment1)
        }
        view.findViewById<Button>(R.id.from_second_to_third).setOnClickListener {
            navController.navigate(R.id.action_fragment2_to_fragment3)
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        navController = findNavController()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.about, menu)
        ActivityAbout.text = "This is Activity 2"
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.about -> {
                findNavController().navigate(R.id.action_fragment2_to_activityAbout)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

package org.bigawesometurtle.lab3_5_final

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

class Fragment1 : Fragment() {

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.frag_1, container, false)
        view.findViewById<Button>(R.id.from_first_to_second).setOnClickListener {
            navController.navigate(R.id.action_fragment1_to_fragment2)
        }
        view.findViewById<Button>(R.id.to_second_with_no_history).setOnClickListener {
            navController.navigate(R.id.action_fragment1_to_fragment2)
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        ActivityAbout.text = "This is Activity 1"
        navController = findNavController()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.about, menu)
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.about -> {
                findNavController().navigate(R.id.action_fragment1_to_activityAbout)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

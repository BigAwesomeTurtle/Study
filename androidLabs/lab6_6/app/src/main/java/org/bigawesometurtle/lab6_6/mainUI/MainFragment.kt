package org.bigawesometurtle.lab6_6.mainUI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_main.*
import org.bigawesometurtle.lab6_6.R

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var imgUrl: String = "https://namobilu.com/u/img/ib/003/195/195003-1.jpg"

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        image.setOnClickListener {
            Picasso.get().load(imgUrl).into(image)
        }
    }

    override fun onStop() {
        super.onStop()
        Picasso.get().cancelRequest(image)
    }


}

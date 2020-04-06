package org.bigawesometurtle.lab6_4.mainUI

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_main.*
import org.bigawesometurtle.lab6_4.R

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var imgUrl: String = "https://namobilu.com/u/img/ib/003/195/195003-1.jpg"

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        img.setOnClickListener {
            viewModel.download(imgUrl)
        }
        viewModel.imgBitmap.observe(viewLifecycleOwner, Observer {
            it?.let {
                img.setImageBitmap(it)
            }
        })

    }
}

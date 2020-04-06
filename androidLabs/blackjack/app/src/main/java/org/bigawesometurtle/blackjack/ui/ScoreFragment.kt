package org.bigawesometurtle.blackjack.ui

import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_score.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.bigawesometurtle.blackjack.App
import org.bigawesometurtle.blackjack.R
import org.bigawesometurtle.blackjack.database.RoomDao
import org.bigawesometurtle.blackjack.ui.adapter.ScoreAdapter

class ScoreFragment : BaseFragment() {
    override val layoutRes: Int
        get() = R.layout.fragment_score

    private val scoreAdapter: ScoreAdapter = ScoreAdapter()
    private lateinit var database: RoomDao

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        database = (activity?.application as App).database.scoreDao()
        setupRecycler()
    }

    private fun setupRecycler() {
        score_recycler.adapter = scoreAdapter
        val recyclerLayoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration = DividerItemDecoration(
                score_recycler.context,
                recyclerLayoutManager.orientation
        )
        score_recycler.apply {
            setHasFixedSize(true)
            layoutManager = recyclerLayoutManager
            addItemDecoration(dividerItemDecoration)
        }
        GlobalScope.launch {
            val scoreList = async { database.getAll() }
            scoreAdapter.updateScores(*scoreList.await().toTypedArray())
        }
    }
}

package com.example.lab4_3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import name.ank.lab4.BibDatabase
import name.ank.lab4.Keys

class MainActivity : AppCompatActivity() {

    private lateinit var bibAdepter: BibAdapter


    private val bibData = mutableListOf<BibElem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val articles = resources.openRawResource(R.raw.articles)

        val database = BibDatabase(articles.reader())
        val size = database.size
        for (i in 0 until size) {
            val entry = database.getEntry(i)
            bibData.add(
                    BibElem(
                            entry.getField(Keys.AUTHOR) ?: "Not stated", entry.getField(Keys.TITLE)
                            ?: "Not stated",
                            entry.getField(Keys.JOURNAL) ?: "Not stated", entry.getField(Keys.YEAR)
                            ?: "Not stated",
                            entry.getField(Keys.VOLUME) ?: "Not stated"
                    )
            )

        }

        val recyclerLayoutManager = LinearLayoutManager(this)
        bibAdepter = BibAdapter(bibData)
        val dividerItemDecoration = DividerItemDecoration(
                my_recycler_view.context,
                recyclerLayoutManager.orientation
        )
        my_recycler_view.apply {
            adapter = bibAdepter
            setHasFixedSize(true)
            layoutManager = recyclerLayoutManager
            addItemDecoration(dividerItemDecoration)
        }
    }
}
package com.example.lab4_3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.bib_elem.view.*

class BibAdapter(private val bibData: List<BibElem>) :
        RecyclerView.Adapter<BibAdapter.BibViewHolder>() {

    inner class BibViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BibViewHolder =
            BibViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.bib_elem, parent, false))

    override fun onBindViewHolder(holder: BibViewHolder, position: Int) {
        holder.view.apply {
            title_text_view.text = bibData[position % bibData.size].title
            author_text_view.text = bibData[position % bibData.size].author
            jourlan_text_view.text = "%s, %s".format(bibData[position % bibData.size].journal, bibData[position % bibData.size].year)
            volume_text_view.text = bibData[position % bibData.size].volume
        }
    }

    override fun getItemCount() = Int.MAX_VALUE
}
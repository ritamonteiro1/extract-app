package com.example.phi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.phi.R
import com.example.phi.click.listener.OnExtractItemClickListener
import com.example.phi.domains.Extract

class ExtractListAdapter(
    private val extractList: List<Extract>,
    private val onExtractItemClickListener: OnExtractItemClickListener
) : RecyclerView.Adapter<ExtractListAdapter.ExtractListViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExtractListViewHolder {
        return ExtractListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_extract,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ExtractListViewHolder, position: Int) {
        holder.bind(extractList[position], onExtractItemClickListener)
    }

    override fun getItemCount(): Int {
        return extractList.size
    }

    class ExtractListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemExtractBankTransactionTypeTextView: TextView =
            itemView.findViewById(R.id.itemExtractBankTransactionTypeTextView)
        private val itemExtractNameTextView: TextView =
            itemView.findViewById(R.id.itemExtractNameTextView)
        private val itemExtractDateTextView: TextView =
            itemView.findViewById(R.id.itemExtractDateTextView)
        private val itemExtractMoneyTextView: TextView =
            itemView.findViewById(R.id.itemExtractMoneyTextView)

        fun bind(
            extractList: Extract,
            onExtractItemClickListener: OnExtractItemClickListener
        ) {
            itemExtractBankTransactionTypeTextView.text = extractList.description
            itemExtractNameTextView.text = extractList.to
            itemExtractDateTextView.text = extractList.createdAt.toString()
            itemExtractMoneyTextView.text = extractList.amount.toString()
            itemView.setOnClickListener {
                onExtractItemClickListener.onClick(extractList.id)
            }
        }
    }
}
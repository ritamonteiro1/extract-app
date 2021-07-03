package com.example.phi.activity

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.phi.R

class ExtractActivity : AppCompatActivity() {
    private var extractAccountBalanceTextView: TextView? = null
    private var extractRecyclerView: RecyclerView? = null
    private var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extract)
        findViewsById()
    }

    private fun findViewsById() {
        extractAccountBalanceTextView = findViewById(R.id.extractAccountBalanceTextView)
        extractRecyclerView = findViewById(R.id.extractRecyclerView)
    }
}
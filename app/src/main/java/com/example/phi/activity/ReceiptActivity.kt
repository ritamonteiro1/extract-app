package com.example.phi.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.phi.R

class ReceiptActivity : AppCompatActivity() {
    private var receiptToolBar: Toolbar? = null
    private var receiptRecyclerView: RecyclerView? = null
    private var receiptButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receipt)
        findViewsById()
    }

    private fun findViewsById() {
        receiptToolBar = findViewById(R.id.receiptToolBar)
        receiptRecyclerView = findViewById(R.id.receiptRecyclerView)
        receiptButton = findViewById(R.id.receiptButton)
    }
}
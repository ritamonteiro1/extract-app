package com.example.phi.activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
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
        setupToolBar()
    }

    private fun setupToolBar() {
        setSupportActionBar(receiptToolBar)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun findViewsById() {
        receiptToolBar = findViewById(R.id.receiptToolBar)
        receiptRecyclerView = findViewById(R.id.receiptRecyclerView)
        receiptButton = findViewById(R.id.receiptButton)
    }
}
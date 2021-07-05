package com.example.phi.activity

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.phi.R
import com.example.phi.adapter.ExtractListAdapter
import com.example.phi.api.Api
import com.example.phi.api.DataService
import com.example.phi.click.listener.OnExtractItemClickListener
import com.example.phi.constants.Constants
import com.example.phi.domains.amount.Amount
import com.example.phi.domains.amount.AmountResponse
import com.example.phi.domains.extract.Extract
import com.example.phi.domains.extract.list.ExtractListResponse
import com.example.phi.extensions.convertDateToString
import com.example.phi.extensions.convertInToMoney
import com.example.phi.extensions.setAlertDialog
import com.example.phi.extensions.showErrorDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ExtractActivity : AppCompatActivity() {
    private var extractAccountBalanceTextView: TextView? = null
    private var extractRecyclerView: RecyclerView? = null
    private var loadingDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extract)
        findViewsById()
        loadingDialog = this.setAlertDialog()
    }

    override fun onStart() {
        super.onStart()
        loadingDialog?.show()
        showBalance()
        showExtract()
    }

    private fun showExtract() {
        val dataService: DataService = Api.setupRetrofit().create(DataService::class.java)
        val call: Call<ExtractListResponse> = dataService.recoverExtract(DataService.TOKEN_VALUE)
        call.enqueue(object : Callback<ExtractListResponse> {
            override fun onResponse(
                call: Call<ExtractListResponse>,
                response: Response<ExtractListResponse>
            ) {
                showExtractOnResponse(response)
            }

            override fun onFailure(call: Call<ExtractListResponse>, t: Throwable) {
                loadingDialog?.dismiss()
                extractRecyclerView?.visibility = View.GONE
                this@ExtractActivity.showErrorDialog(getString(R.string.error_connection_fail))
            }
        })
    }

    private fun showExtractOnResponse(response: Response<ExtractListResponse>) {
        loadingDialog?.dismiss()
        if (response.isSuccessful && response.body() != null) {
            extractRecyclerView?.visibility = View.VISIBLE
            val extractListResponse = response.body()
            val extractList = mapToExtractList(extractListResponse)
            treatExtractListEmpty(extractList)
            val extractListAdapter = ExtractListAdapter(extractList,
                object : OnExtractItemClickListener {
                    override fun onClick(extractId: String) {
                        val intent =
                            Intent(this@ExtractActivity, ReceiptActivity::class.java)
                        intent.putExtra(Constants.ID_EXTRACT, extractId)
                        startActivity(intent)
                    }
                })
            setupAdapter(extractListAdapter)
        } else {
            extractRecyclerView?.visibility = View.GONE
            this@ExtractActivity.showErrorDialog(getString(R.string.error_balance_field))
        }
    }

    private fun setupAdapter(extractListAdapter: ExtractListAdapter) {
        extractRecyclerView?.adapter = extractListAdapter
        val layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL, false
        )
        extractRecyclerView?.layoutManager = layoutManager
        extractRecyclerView?.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayout.VERTICAL
            )
        )
    }

    private fun treatExtractListEmpty(extractList: List<Extract>) {
        if (extractList.isEmpty()) {
            this.showErrorDialog(getString(R.string.error_balance_field))
        }
    }

    private fun mapToExtractList(extractListResponse: ExtractListResponse?) =
        extractListResponse?.items?.map {
            Extract(
                it.createdAt?.convertDateToString().orEmpty(),
                it.id.orEmpty(),
                it.amount ?: Constants.NULL_AMOUNT_EXTRACT_RESPONSE,
                it.to.orEmpty(),
                it.description.orEmpty(),
                it.tType.orEmpty(),
                it.from.orEmpty(),
                it.bankName.orEmpty()
            )
        } ?: emptyList()

    private fun showBalance() {
        val dataService: DataService = Api.setupRetrofit().create(DataService::class.java)
        val call: Call<AmountResponse> = dataService.recoverAmount(DataService.TOKEN_VALUE)
        call.enqueue(object : Callback<AmountResponse> {
            override fun onResponse(
                call: Call<AmountResponse>,
                response: Response<AmountResponse>
            ) {
                showBalanceOnResponse(response)
            }

            override fun onFailure(call: Call<AmountResponse?>, t: Throwable) {
                extractAccountBalanceTextView?.text =
                    getString(R.string.error_connection_fail)
            }
        })
    }

    private fun showBalanceOnResponse(response: Response<AmountResponse>) =
        when {
            response.isSuccessful && response.body() != null -> {
                val amountResponse = response.body()
                val amount = Amount(amountResponse?.amount ?: Constants.NULL_AMOUNT_RESPONSE)
                if (amount.amount != Constants.NULL_AMOUNT_RESPONSE) {
                    extractAccountBalanceTextView?.text = amount.amount.convertInToMoney()
                } else {
                    extractAccountBalanceTextView?.text =
                        getString(R.string.error_balance_field)
                }
            }
            else -> {
                extractAccountBalanceTextView?.text =
                    getString(R.string.error_balance_field)
            }
        }

    private fun findViewsById() {
        extractAccountBalanceTextView = findViewById(R.id.extractAccountBalanceTextView)
        extractRecyclerView = findViewById(R.id.extractRecyclerView)
    }
}
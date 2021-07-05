package com.example.phi.activity

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.phi.R
import com.example.phi.api.Api
import com.example.phi.api.DataService
import com.example.phi.constants.Constants
import com.example.phi.domains.amount.Amount
import com.example.phi.domains.amount.AmountResponse
import com.example.phi.extensions.convertInToMoney
import com.example.phi.extensions.setAlertDialog
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
//        val call: Call<ExtractListResponse> = dataService.recoverExtract(DataService.TOKEN_VALUE)
//        call.enqueue(object : Callback<ExtractListResponse> {
//            override fun onResponse(
//                call: Call<ExtractListResponse>,
//                response: Response<ExtractListResponse>
//            ) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onFailure(call: Call<ExtractListResponse>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//
//        })
    }

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
                        getString(R.string.extract_error_balance_field)
                }
            }
            else -> {
                extractAccountBalanceTextView?.text =
                    getString(R.string.extract_error_balance_field)
            }
        }

    private fun findViewsById() {
        extractAccountBalanceTextView = findViewById(R.id.extractAccountBalanceTextView)
        extractRecyclerView = findViewById(R.id.extractRecyclerView)
    }
}
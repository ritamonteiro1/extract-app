package com.example.phi.activity

import android.app.Dialog
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.phi.R
import com.example.phi.api.Api
import com.example.phi.api.DataService
import com.example.phi.constants.Constants
import com.example.phi.domains.details.extract.DetailsExtract
import com.example.phi.domains.details.extract.DetailsExtractResponse
import com.example.phi.extensions.convertDateToString
import com.example.phi.extensions.convertInToMoney
import com.example.phi.extensions.setAlertDialog
import com.example.phi.extensions.showErrorDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReceiptActivity : AppCompatActivity() {
    private var receiptToolBar: Toolbar? = null
    private var receiptButton: Button? = null
    private var loadingDialog: Dialog? = null
    private var receiptBankTextView: TextView? = null
    private var receiptAmountTextView: TextView? = null
    private var receiptBankTransactionTypeTextView: TextView? = null
    private var receiptBankTransactionTextView: TextView? = null
    private var receiptTransactionAmountTextView: TextView? = null
    private var receiptReceiverTextView: TextView? = null
    private var receiptReceiverNameTextView: TextView? = null
    private var receiptBankingInstitutionTextView: TextView? = null
    private var receiptDateAndTimeTextView: TextView? = null
    private var receiptDateTextView: TextView? = null
    private var receiptAuthenticationTextView: TextView? = null
    private var receiptAuthenticationNumberTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receipt)
        findViewsById()
        loadingDialog = this.setAlertDialog()
        loadingDialog?.show()
        setupToolBar()
        val idExtract: String? = retrieverIdExtract()
        idExtract?.let {
            showDetailsExtract(it)
        }
    }

    private fun showDetailsExtract(idExtract: String) {
        val dataService: DataService = Api.setupRetrofit().create(DataService::class.java)
        val call: Call<DetailsExtractResponse> =
            dataService.recoverDetailsExtract(idExtract, DataService.TOKEN_VALUE)
        call.enqueue(object : Callback<DetailsExtractResponse> {
            override fun onResponse(
                call: Call<DetailsExtractResponse>,
                response: Response<DetailsExtractResponse>
            ) {
                loadingDialog?.dismiss()
                if (response.isSuccessful && response.body() != null) {
                    val detailsExtractResponse = response.body()
                    val detailsExtract = DetailsExtract(
                        detailsExtractResponse?.amount
                            ?: Constants.NULL_AMOUNT_DETAILS_EXTRACT_RESPONSE,
                        detailsExtractResponse?.id.orEmpty(),
                        detailsExtractResponse?.authentication.orEmpty(),
                        detailsExtractResponse?.tType.orEmpty(),
                        detailsExtractResponse?.createdAt?.convertDateToString().orEmpty(),
                        detailsExtractResponse?.to.orEmpty(),
                        detailsExtractResponse?.description.orEmpty()
                    )
                    showReceipt(detailsExtract)
                    setVisibilityVisibleViews()
                } else {
                    setVisibilityGoneViews()
                    this@ReceiptActivity.showErrorDialog(
                        getString(R.string.error_balance_field)
                    )
                }
            }

            override fun onFailure(call: Call<DetailsExtractResponse>, t: Throwable) {
                setVisibilityGoneViews()
                loadingDialog?.dismiss()
                this@ReceiptActivity.showErrorDialog(getString(R.string.error_connection_fail))
            }
        })
    }

    private fun showReceipt(detailsExtract: DetailsExtract) {
        receiptBankTransactionTextView?.text = detailsExtract.description
        receiptAmountTextView?.text = detailsExtract.amount.convertInToMoney()
        receiptReceiverNameTextView?.text = detailsExtract.to
        receiptDateTextView?.text = detailsExtract.createdAt
        receiptAuthenticationNumberTextView?.text = detailsExtract.authentication
    }

    private fun setVisibilityGoneViews() {
        receiptButton?.visibility = View.GONE
        receiptBankTextView?.visibility = View.GONE
        receiptAmountTextView?.visibility = View.GONE
        receiptBankTransactionTypeTextView?.visibility = View.GONE
        receiptBankTransactionTextView?.visibility = View.GONE
        receiptTransactionAmountTextView?.visibility = View.GONE
        receiptReceiverTextView?.visibility = View.GONE
        receiptReceiverNameTextView?.visibility = View.GONE
        receiptBankingInstitutionTextView?.visibility = View.GONE
        receiptDateAndTimeTextView?.visibility = View.GONE
        receiptDateTextView?.visibility = View.GONE
        receiptAuthenticationTextView?.visibility = View.GONE
        receiptAuthenticationNumberTextView?.visibility = View.GONE
    }

    private fun setVisibilityVisibleViews() {
        receiptReceiverTextView?.visibility = View.VISIBLE
        receiptReceiverNameTextView?.visibility = View.VISIBLE
        receiptButton?.visibility = View.VISIBLE
        receiptBankTextView?.visibility = View.VISIBLE
        receiptAmountTextView?.visibility = View.VISIBLE
        receiptBankTransactionTypeTextView?.visibility = View.VISIBLE
        receiptBankTransactionTextView?.visibility = View.VISIBLE
        receiptTransactionAmountTextView?.visibility = View.VISIBLE
        receiptBankingInstitutionTextView?.visibility = View.VISIBLE
        receiptDateAndTimeTextView?.visibility = View.VISIBLE
        receiptDateTextView?.visibility = View.VISIBLE
        receiptAuthenticationTextView?.visibility = View.VISIBLE
        receiptAuthenticationNumberTextView?.visibility = View.VISIBLE
    }

    private fun retrieverIdExtract() = intent.getStringExtra(Constants.ID_EXTRACT)

    private fun setupToolBar() {
        setSupportActionBar(receiptToolBar)
        supportActionBar?.title = Constants.EMPTY
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
        receiptButton = findViewById(R.id.receiptButton)
        receiptBankTextView = findViewById(R.id.receiptBankTextView)
        receiptAmountTextView = findViewById(R.id.receiptAmountTextView)
        receiptBankTransactionTypeTextView = findViewById(R.id.receiptBankTransactionTypeTextView)
        receiptBankTransactionTextView = findViewById(R.id.receiptBankTransactionTextView)
        receiptTransactionAmountTextView = findViewById(R.id.receiptTransactionAmountTextView)
        receiptReceiverTextView = findViewById(R.id.receiptReceiverTextView)
        receiptReceiverNameTextView = findViewById(R.id.receiptReceiverNameTextView)
        receiptBankingInstitutionTextView = findViewById(R.id.receiptBankingInstitutionTextView)
        receiptDateAndTimeTextView = findViewById(R.id.receiptDateAndTimeTextView)
        receiptDateTextView = findViewById(R.id.receiptDateTextView)
        receiptAuthenticationTextView = findViewById(R.id.receiptAuthenticationTextView)
        receiptAuthenticationNumberTextView = findViewById(R.id.receiptAuthenticationNumberTextView)
    }
}
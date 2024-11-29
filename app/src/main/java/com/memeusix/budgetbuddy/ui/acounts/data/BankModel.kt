package com.memeusix.budgetbuddy.ui.acounts.data

import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.utils.generateIconSlug

data class BankModel(
    val icon: Int?,
    val name: String = "",
    val iconSlug: String,
    var isSelected: Boolean = false,
    var isEnable: Boolean = true,
) {
    companion object {
        fun getBanks(): List<BankModel> {
            return listOf(
                createBankModel("State Bank of India", R.drawable.ic_state_bank_of_india),
                createBankModel("Bank of Baroda", R.drawable.ic_bob_bank),
                createBankModel("HDFC Bank", R.drawable.ic_hdfc_bank),
                createBankModel("ICICI Bank", R.drawable.ic_icici_bank),
                createBankModel("Axis Bank", R.drawable.ic_axis_bank),
                createBankModel("Kotak Mahindra Bank", R.drawable.ic_kotak_mahindra_bank),
                createBankModel("Punjab National Bank", R.drawable.ic_punjab_national_bank),
                createBankModel("Union Bank of India", R.drawable.ic_union_bank),
                createBankModel("Central Bank of India", R.drawable.ic_centeral_bank_of_india),
                createBankModel("YES Bank", R.drawable.ic_yes_bank),
                createBankModel("Citi Bank", R.drawable.ic_citi_bank),
                createBankModel("IDFC FIRST Bank", R.drawable.ic_idfc_bank),
                createBankModel("India Post Payments Bank", R.drawable.ic_post_payments_bank),
                createBankModel("Indian Bank", R.drawable.ic_indian_bank),
                createBankModel("IndusInd Bank", R.drawable.ic_induslnd_bank),
            )
        }

        fun getWallet(): List<BankModel> {
            return listOf(
                createBankModel("Wallet", R.drawable.ic_wallet)
            )
        }

        fun getAccounts() = getBanks() + getWallet()

        private fun createBankModel(
            name: String, icon: Int, isSelected: Boolean = false
        ): BankModel {
            return BankModel(
                icon = icon,
                name = name,
                iconSlug = name.generateIconSlug(),
                isSelected = isSelected
            )
        }
    }
}

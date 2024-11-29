package com.memeusix.budgetbuddy.ui.dashboard.transactions

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.memeusix.budgetbuddy.components.AppBar
import com.memeusix.budgetbuddy.components.FilledButton
import com.memeusix.budgetbuddy.data.model.TextFieldStateModel
import com.memeusix.budgetbuddy.data.model.responseModel.AccountResponseModel
import com.memeusix.budgetbuddy.data.model.responseModel.CategoryResponseModel
import com.memeusix.budgetbuddy.navigation.CreateTransactionScreenRoute
import com.memeusix.budgetbuddy.ui.dashboard.transactions.components.CreateTransactionFromOptions
import com.memeusix.budgetbuddy.ui.dashboard.transactions.viewmodel.TransactionViewModel
import com.memeusix.budgetbuddy.ui.theme.Blue100
import com.memeusix.budgetbuddy.ui.theme.Green100
import com.memeusix.budgetbuddy.ui.theme.Red100
import com.memeusix.budgetbuddy.utils.TransactionType
import com.memeusix.budgetbuddy.utils.formatRupees

@Composable
fun CreateTransactionScreen(
    navController: NavHostController,
    args: CreateTransactionScreenRoute,
    transactionViewModel: TransactionViewModel = hiltViewModel()
) {
    val systemUiController = rememberSystemUiController()
    DisposableEffect(Unit) {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent, darkIcons = false
        )
        onDispose {
            systemUiController.setSystemBarsColor(
                color = Color.Transparent, darkIcons = true
            )
        }
    }

    val transactionType = args.transactionType
    val bgColor = when (transactionType) {
        TransactionType.DEBIT -> Green100
        TransactionType.CREDIT -> Red100
        TransactionType.TRANSFER -> Blue100
    }

    val amountState = remember { mutableStateOf(TextFieldStateModel()) }
    val noteState = remember { mutableStateOf(TextFieldStateModel()) }
    val selectedCategory = remember { mutableStateOf(CategoryResponseModel()) }
    val selectedAccount = remember { mutableStateOf(AccountResponseModel()) }
    val selectedAttachment = remember { mutableStateOf<Uri?>(null) }

    Scaffold(containerColor = bgColor,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        topBar = {
            AppBar(
                heading = "Create Transaction",
                navController = navController,
                elevation = false,
                isLightColor = true,
                bgColor = bgColor
            )
        }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .imePadding()
        ) {
            Column(
                modifier = Modifier
                    .background(bgColor)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(Modifier.weight(1f))
                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 20.dp)
                        .fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        "How much?", style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                        )
                    )
                    Text(
                        "₹${amountState.value.text.ifEmpty { "0" }.toInt().formatRupees()}",
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 50.sp)
                    )
                }
                CreateTransactionFromOptions(
                    amountState = amountState,
                    noteState = noteState,
                    selectedCategory = selectedCategory,
                    selectedAccount = selectedAccount,
                    selectedAttachment = selectedAttachment,
                    transactionViewModel = transactionViewModel,
                    modifier = Modifier
                        .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                        .background(MaterialTheme.colorScheme.background)
                        .padding(top = 30.dp, start = 20.dp, end = 20.dp, bottom = 50.dp)
                )
            }
            FilledButton(
                text = "Add", textModifier = Modifier.padding(vertical = 17.dp), onClick = {

                }, modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(20.dp)
            )
        }
    }
}
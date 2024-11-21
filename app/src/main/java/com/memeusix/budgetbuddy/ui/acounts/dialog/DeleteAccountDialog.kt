package com.memeusix.budgetbuddy.ui.acounts.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.data.model.responseModel.AccountResponseModel
import com.memeusix.budgetbuddy.ui.theme.Light100
import com.memeusix.budgetbuddy.ui.theme.Light20
import com.memeusix.budgetbuddy.ui.theme.Red20
import com.memeusix.budgetbuddy.ui.theme.Red80
import com.memeusix.budgetbuddy.utils.drawTopAndBottomBorders

@Composable
fun DeleteAccountDialog(
    accountDetails: AccountResponseModel,
    onDismiss: (Boolean) -> Unit,
) {
    val textToDelete = remember(accountDetails.name) { "Delete ${accountDetails.name}" }
    var textState by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = { onDismiss(false) }
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        stringResource(R.string.are_you_absolutely_sure),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                    )
                    Image(
                        painterResource(R.drawable.ic_close),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(
                            Light20
                        ),
                        modifier = Modifier
                            .size(16.dp)
                            .clickable { onDismiss(false) }
                    )
                }
                Text(
                    "This action is permanent and cannot be undone!",
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .background(Red20)
                        .drawTopAndBottomBorders(Red80, 1.dp)
                        .padding(vertical = 10.dp, horizontal = 16.dp),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Red80
                    )
                )
                Text(
                    buildAnnotatedString {
                        append("To confirm, type: ")
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.SemiBold,
                            )
                        ) {
                            append(textToDelete)
                        }
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                BasicTextField(modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 16.dp)
                    .fillMaxWidth(),
                    value = textState,
                    onValueChange = {
                        if (it.length <= textToDelete.length)
                            textState = it
                    },
                    textStyle = MaterialTheme.typography.bodyMedium,
                    decorationBox = { innerBox ->
                        Box(
                            modifier = Modifier
                                .border(
                                    1.dp,
                                    MaterialTheme.colorScheme.secondary,
                                    RoundedCornerShape(5.dp)
                                )
                                .padding(vertical = 10.dp, horizontal = 16.dp)
                        ) {
                            if (textState.isEmpty()) {
                                Text(
                                    textToDelete,
                                    color = Light20,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            innerBox()
                        }
                    }
                )
                ConfirmBtn(textState, textToDelete, onDismiss)
            }
        }
    }

}

@Composable
private fun ConfirmBtn(
    textState: String,
    textToDelete: String,
    onDismiss: (Boolean) -> Unit
) {
    Button(
        shape = RoundedCornerShape(5.dp),
        onClick = {
            if (textState == textToDelete) {
                onDismiss(true)
            }
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Red80,
            contentColor = Light100
        ),
        enabled = textState == textToDelete,
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.i_understand_delete_this_account),
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

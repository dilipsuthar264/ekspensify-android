package com.memeusix.budgetbuddy.ui.dashboard.user

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.memeusix.budgetbuddy.ui.theme.Green100
import com.memeusix.budgetbuddy.ui.theme.Light100
import com.memeusix.budgetbuddy.ui.theme.Light20

@Composable
fun UsersScreen(
    navController: NavController,
) {

    var searchQuery by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Spacer(Modifier.height(5.dp))
        Row(
            modifier = Modifier.height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            BasicTextField(value = searchQuery,
                onValueChange = {
                    if (it.length <= 20) {
                        searchQuery = it
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .border(1.dp, Light20, RoundedCornerShape(10.dp))
                    .fillMaxSize()
                    .height(40.dp),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (searchQuery.isEmpty()) {
                            Text("Search users..", style = TextStyle.Default.copy(color = Light20))
                        }
                        innerTextField()
                    }
                })
            Icon(
                Icons.Default.Add,
                contentDescription = null,
                tint = Green100,
                modifier = Modifier
                    .background(Light100)
                    .clip(RoundedCornerShape(10.dp))
                    .border(1.dp, Green100, RoundedCornerShape(10.dp))
                    .aspectRatio(1f)
                    .fillMaxHeight()
                    .padding(10.dp),
            )
        }
    }

}
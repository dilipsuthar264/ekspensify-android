package com.memeusix.budgetbuddy.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.components.FilledButton
import com.memeusix.budgetbuddy.components.VerticalSpace
import com.memeusix.budgetbuddy.data.model.IntroPages
import com.memeusix.budgetbuddy.navigation.LoginScreenRoute
import com.memeusix.budgetbuddy.navigation.RegisterScreenRoute
import com.memeusix.budgetbuddy.utils.singleClick

@Composable
fun IntroScreen(navController: NavController) {
    val introPages = remember { IntroPages.getPages() }
    val pagerState = rememberPagerState(
        pageCount = { introPages.size },
        initialPage = 0,
        initialPageOffsetFraction = 0f
    )
    val textModifier = Modifier.padding(vertical = 17.dp)
    val btnModifier = Modifier.padding(horizontal = 20.dp)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .safeContentPadding()
            .imePadding()
            .padding(vertical = 10.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(Modifier.weight(1f))
        HorizontalPager(
            state = pagerState,
        ) { page ->
            PageView(introPages[page], modifier = Modifier.fillMaxWidth())
        }
        VerticalSpace(30.dp)
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(pagerState.pageCount) {
                PageIndicator(pagerState.currentPage == it)
            }
        }
        VerticalSpace(30.dp)
        FilledButton(
            text = stringResource(R.string.sign_up),
            onClick = singleClick {
                navController.navigate(RegisterScreenRoute)
            },
            textModifier = textModifier,
            modifier = btnModifier
        )
        VerticalSpace(30.dp)
        FilledButton(
            text = stringResource(R.string.login),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary,
            ),
            onClick = singleClick {
                navController.navigate(LoginScreenRoute)
            },
            textModifier = textModifier,
            modifier = btnModifier
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun PageIndicator(isSelected: Boolean) {
    Box(
        Modifier
            .padding(6.dp)
            .size(if (isSelected) 10.dp else 5.dp)
            .clip(CircleShape)
            .background(
                if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.secondary,
            )
    )
}

@Composable
fun PageView(introPages: IntroPages, modifier: Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(id = introPages.image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .aspectRatio(1f)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            introPages.title,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            fontSize = 32.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = introPages.description,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 15.dp)
        )
    }

}
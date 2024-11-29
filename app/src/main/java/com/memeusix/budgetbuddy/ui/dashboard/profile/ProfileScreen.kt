package com.memeusix.budgetbuddy.ui.dashboard.profile

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.components.CustomListItem
import com.memeusix.budgetbuddy.data.ApiResponse
import com.memeusix.budgetbuddy.data.model.responseModel.UserResponseModel
import com.memeusix.budgetbuddy.navigation.AccountScreenRoute
import com.memeusix.budgetbuddy.navigation.CategoriesScreenRoute
import com.memeusix.budgetbuddy.navigation.IntroScreenRoute
import com.memeusix.budgetbuddy.ui.dashboard.profile.componets.ProfileAvatar
import com.memeusix.budgetbuddy.ui.dashboard.profile.model.ProfileOptionProvider
import com.memeusix.budgetbuddy.ui.dashboard.profile.model.ProfileOptions
import com.memeusix.budgetbuddy.ui.theme.Dark10
import com.memeusix.budgetbuddy.ui.theme.Light20
import com.memeusix.budgetbuddy.utils.singleClick
import com.memeusix.budgetbuddy.utils.toastUtils.CustomToast
import com.memeusix.budgetbuddy.utils.toastUtils.CustomToastModel
import com.memeusix.budgetbuddy.utils.toastUtils.ToastType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController, viewmodel: ProfileViewModel = hiltViewModel()
) {
    val getMeState by viewmodel.getMe.collectAsStateWithLifecycle()
    val updateMeState by viewmodel.updateMe.collectAsStateWithLifecycle()
    var toastState by remember { mutableStateOf<CustomToastModel?>(null) }

    var showEditBottomSheet by remember { mutableStateOf(false) }

    val isLoading = getMeState is ApiResponse.Loading || updateMeState is ApiResponse.Loading


    val modifier = Modifier
        .clip(RoundedCornerShape(16.dp))
        .background(MaterialTheme.colorScheme.background)
        .fillMaxWidth()
        .border(
            width = 1.dp, color = Dark10, RoundedCornerShape(16.dp)
        )
        .padding(vertical = 15.dp)
        .animateContentSize()

    LaunchedEffect(getMeState, updateMeState) {
        when (getMeState) {
            is ApiResponse.Success -> {
                getMeState.data?.let {
                }
            }

            is ApiResponse.Failure -> {
                getMeState.errorResponse?.let {
                    toastState = CustomToastModel(
                        message = it.message,
                        isVisible = true,
                        type = ToastType.ERROR
                    )
                }
            }

            else -> Unit
        }
        when (updateMeState) {
            is ApiResponse.Success -> {
                updateMeState.data?.let {
                }
            }

            is ApiResponse.Failure -> {
                updateMeState.errorResponse?.let {
                    toastState = CustomToastModel(
                        message = it.message,
                        isVisible = true,
                        type = ToastType.ERROR
                    )
                }
            }

            else -> Unit
        }
    }

    // Custom Toast
    CustomToast(toastState) {
        toastState = null
    }


    // Edit Profile Bottom Sheet
    if (showEditBottomSheet) {
        EditProfileBottomSheet(user = getMeState.data ?: UserResponseModel()) { userModel ->
            userModel?.let {
                viewmodel.updateMe(it)
            }
            showEditBottomSheet = false
        }
    }


    //Main Screen
    PullToRefreshBox(
        isRefreshing = isLoading,
        onRefresh = viewmodel::getMe,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            getMeState.data?.let { details ->
                ProfileAvatar(details, onEditClick = singleClick {
                    showEditBottomSheet = true
                })
                Text(
                    details.name ?: "",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Text(
                    details.email ?: "",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Light20
                    )
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    stringResource(R.string.general),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 10.dp, start = 15.dp, end = 15.dp)
                )
                ProfileOptionProvider.getGeneralOptions().forEach { profileOptions ->
                    ProfileListItem(
                        profileOptions = profileOptions,
                        onClick = singleClick {
                            handleProfileOptionClick(profileOptions, navController, viewmodel)
                        }
                    )
                    HorizontalDivider(color = Dark10)
                }
                Text(
                    stringResource(R.string.danger),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 15.dp)
                )
                ProfileOptionProvider.getDangerOptions().forEach { profileOptions ->
                    ProfileListItem(
                        profileOptions = profileOptions,
                        onClick = singleClick {
                            handleProfileOptionClick(profileOptions, navController, viewmodel)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ProfileListItem(profileOptions: ProfileOptions, onClick: () -> Unit) {
    CustomListItem(
        title = profileOptions.title,
        icon = profileOptions.icon,
        modifier = Modifier.padding(horizontal = 15.dp, vertical = 9.dp),
        trailingContent = {
            Image(
                painter = painterResource(R.drawable.ic_arrow_right),
                contentDescription = null,
            )
        },
        onClick = onClick
    )
}


private fun handleProfileOptionClick(
    profileOptions: ProfileOptions,
    navController: NavController,
    viewmodel: ProfileViewModel
) {
    when (profileOptions) {
        ProfileOptions.ACCOUNT -> {
            navController.navigate(
                AccountScreenRoute(
                    isFromProfile = true
                )
            )
        }

        ProfileOptions.CATEGORY -> {
            navController.navigate(CategoriesScreenRoute)
        }

        ProfileOptions.EXPORT -> {
            Log.e(TAG, "handleProfileOptionClick: EXPORT CLICK")
        }

        ProfileOptions.SETTING -> {
            Log.e(TAG, "handleProfileOptionClick: SETTING CLICK")
        }

        ProfileOptions.LOGOUT -> {
            viewmodel.logout()
            navController.navigate(IntroScreenRoute) {
                popUpTo(0) { inclusive = true }
            }
        }
    }
}

private val TAG = "PROFILE SCREEN"

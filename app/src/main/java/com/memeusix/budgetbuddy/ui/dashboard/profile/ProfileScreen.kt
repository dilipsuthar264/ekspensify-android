package com.memeusix.budgetbuddy.ui.dashboard.profile

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.memeusix.budgetbuddy.components.AlertDialog
import com.memeusix.budgetbuddy.components.CustomListItem
import com.memeusix.budgetbuddy.components.EmptyView
import com.memeusix.budgetbuddy.components.ListIcon
import com.memeusix.budgetbuddy.components.PullToRefreshLayout
import com.memeusix.budgetbuddy.components.ShowLoader
import com.memeusix.budgetbuddy.components.VerticalSpace
import com.memeusix.budgetbuddy.data.ApiResponse
import com.memeusix.budgetbuddy.data.model.responseModel.UserResponseModel
import com.memeusix.budgetbuddy.navigation.AboutScreenRoute
import com.memeusix.budgetbuddy.navigation.AccountScreenRoute
import com.memeusix.budgetbuddy.navigation.AutoTrackingScreenRoute
import com.memeusix.budgetbuddy.navigation.CategoriesScreenRoute
import com.memeusix.budgetbuddy.navigation.ExportScreenRoute
import com.memeusix.budgetbuddy.navigation.IntroScreenRoute
import com.memeusix.budgetbuddy.ui.dashboard.profile.componets.ProfileAvatar
import com.memeusix.budgetbuddy.ui.dashboard.profile.data.ProfileOptionProvider
import com.memeusix.budgetbuddy.ui.dashboard.profile.data.ProfileOptions
import com.memeusix.budgetbuddy.ui.dashboard.profile.viewModel.ProfileViewModel
import com.memeusix.budgetbuddy.ui.theme.extendedColors
import com.memeusix.budgetbuddy.utils.handleApiResponse
import com.memeusix.budgetbuddy.utils.singleClick
import com.memeusix.budgetbuddy.utils.toastUtils.CustomToast
import com.memeusix.budgetbuddy.utils.toastUtils.CustomToastModel

@Composable
fun ProfileScreen(
    navController: NavController, viewmodel: ProfileViewModel = hiltViewModel()
) {
    val getMeState by viewmodel.getMe.collectAsStateWithLifecycle()
    val updateMeState by viewmodel.updateMe.collectAsStateWithLifecycle()
    val toastState = remember { mutableStateOf<CustomToastModel?>(null) }

    val showEditBottomSheet = remember { mutableStateOf(false) }

    val isLoading = getMeState is ApiResponse.Loading || updateMeState is ApiResponse.Loading


    val isLogoutDialogOpen = remember { mutableStateOf(false) }

    if (isLogoutDialogOpen.value) {
        AlertDialog(
            title = stringResource(R.string.are_you_sure),
            message = stringResource(R.string.you_want_to_logout),
            btnText = stringResource(R.string.logout),
            onDismiss = { isLogoutDialogOpen.value = false },
            onConfirm = {
                isLogoutDialogOpen.value = false
                navController.navigate(IntroScreenRoute) {
                    popUpTo(0) { inclusive = true }
                }
                viewmodel.spUtilsManager.logout()
            }
        )
    }


    val modifier = Modifier
        .clip(RoundedCornerShape(16.dp))
        .background(MaterialTheme.colorScheme.background)
        .fillMaxWidth()
        .border(
            width = 1.dp,
            color = MaterialTheme.extendedColors.primaryBorder,
            RoundedCornerShape(16.dp)
        )
        .padding(vertical = 15.dp)
        .animateContentSize()

    LaunchedEffect(getMeState, updateMeState) {
        handleApiResponse(
            response = getMeState,
            toastState = toastState,
            navController = navController,
            onSuccess = { data -> }
        )
        handleApiResponse(
            response = updateMeState,
            toastState = toastState,
            navController = navController,
            onSuccess = { data -> }
        )
    }

    // Custom Toast
    CustomToast(toastState)


    // Edit Profile Bottom Sheet
    if (showEditBottomSheet.value) {
        EditProfileBottomSheet(user = getMeState.data ?: UserResponseModel()) { userModel ->
            userModel?.let {
                viewmodel.updateMe(it)
            }
            showEditBottomSheet.value = false
        }
    }


    //Main Screen
    PullToRefreshLayout(
        onRefresh = viewmodel::getMe,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .padding(top = 20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            UserInfo(isLoading, getMeState, showEditBottomSheet)
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
                            handleProfileOptionClick(
                                isLogoutDialogOpen,
                                profileOptions,
                                navController,
                                viewmodel
                            )
                        }
                    )
                    HorizontalDivider(color = MaterialTheme.extendedColors.primaryBorder)
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
                            handleProfileOptionClick(
                                isLogoutDialogOpen,
                                profileOptions,
                                navController,
                                viewmodel
                            )
                        }
                    )
                }
            }
            VerticalSpace(20.dp)
        }
        ShowLoader(isLoading)
    }
}

@Composable
private fun ColumnScope.UserInfo(
    isLoading: Boolean,
    getMeState: ApiResponse<UserResponseModel>,
    showEditBottomSheet: MutableState<Boolean>
) {
    getMeState.data?.let { details ->
        ProfileAvatar(details, onEditClick = singleClick {
            showEditBottomSheet.value = true
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
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        )
    } ?: run {
        EmptyView(
            title = stringResource(R.string.profile_fetch_failed),
            description = stringResource(R.string.profile_error_desc),
            image = null,
            modifier = Modifier
        )
    }
}

@Composable
private fun ProfileListItem(profileOptions: ProfileOptions, onClick: () -> Unit) {
    CustomListItem(
        title = profileOptions.title,
        leadingContent = {
            ListIcon(profileOptions.icon)
        },
        modifier = Modifier.padding(horizontal = 15.dp, vertical = 9.dp),
        trailingContent = {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_right),
                tint = MaterialTheme.extendedColors.iconColor,
                contentDescription = null,
            )
        },
        onClick = onClick
    )
}


private fun handleProfileOptionClick(
    isLogoutDialogOpen: MutableState<Boolean>,
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
            navController.navigate(ExportScreenRoute)
        }

        ProfileOptions.AUTO_TRACKING -> {
            navController.navigate(AutoTrackingScreenRoute)
        }

        ProfileOptions.ABOUT -> {
            Log.e(TAG, "handleProfileOptionClick: SETTING CLICK")
            navController.navigate(AboutScreenRoute)
        }

        ProfileOptions.LOGOUT -> {
            isLogoutDialogOpen.value = true
        }
    }
}

private val TAG = "PROFILE SCREEN"

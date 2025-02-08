package com.memeusix.ekspensify.ui.autoTracking

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.memeusix.ekspensify.R
import com.memeusix.ekspensify.components.AppBar
import com.memeusix.ekspensify.components.FilledButton
import com.memeusix.ekspensify.components.VerticalSpace
import com.memeusix.ekspensify.ui.autoTracking.components.PointsBox
import com.memeusix.ekspensify.ui.autoTracking.viewModel.AutoTrackViewModel
import com.memeusix.ekspensify.ui.theme.Red100
import com.memeusix.ekspensify.ui.theme.Red20
import com.memeusix.ekspensify.ui.theme.fonartoFontFamily
import com.memeusix.ekspensify.utils.dynamicImePadding

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AutoTrackingScreen(
    navController: NavHostController,
    autoTrackingViewModel: AutoTrackViewModel = hiltViewModel()
) {

    val smsFeatureState by autoTrackingViewModel.isSmsFeatureEnable.collectAsStateWithLifecycle()

    val permissions = listOf(Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS)
    val permissionState = rememberMultiplePermissionsState(permissions)

    // Permission Dialog
    var showSmsPermissionDialog by remember { mutableStateOf(false) }
    if (showSmsPermissionDialog && !permissionState.allPermissionsGranted) {
        SmsPermissionDialog(
            permissions = permissions,
            onDismiss = {
                showSmsPermissionDialog = false
            }
        )
    }

    LaunchedEffect(permissionState.allPermissionsGranted) {
        if (!permissionState.allPermissionsGranted) {
            autoTrackingViewModel.toggleSmsFeature(false)
        }
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppBar(
                heading = "",
                navController
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .dynamicImePadding(paddingValues)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
//            AnimatedImage(
//                modifier = Modifier
//                    .fillMaxWidth(0.6f)
//                    .aspectRatio(1f)
//            )
            Image(
                rememberAsyncImagePainter(R.drawable.ic_budget_img),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(0.7f)
            )
            Text(
                text = stringResource(R.string.auto_tracking),
                fontFamily = fonartoFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = stringResource(R.string.track_your_expenses_automatically_with_sms_notifications_secure_private_and_effortless),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp
                ),
                textAlign = TextAlign.Center
            )
            BulletPoints()
            VerticalSpace(1.dp)
            Spacer(Modifier.weight(1f))
            SwitchBtn(
                smsFeatureState,
                onClick = {
                    if (permissionState.allPermissionsGranted) {
                        autoTrackingViewModel.toggleSmsFeature(
                            !smsFeatureState
                        )
                    } else {
                        showSmsPermissionDialog = true
                    }
                }
            )
            Text(
                stringResource(R.string.safe_and_secure),
                style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
            )
        }
    }
}

@Composable
private fun SwitchBtn(isEnable: Boolean, onClick: () -> Unit) {
    val text =
        if (isEnable) stringResource(R.string.disable) else stringResource(R.string.enable)
    val (bg, textColor) =
        if (isEnable) Red20 to Red100 else MaterialTheme.colorScheme.secondary to MaterialTheme.colorScheme.primary

    FilledButton(
        text = text,
        textModifier = Modifier.padding(vertical = 17.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = bg,
            contentColor = textColor
        ),
        onClick = onClick
    )
}

@Composable
private fun BulletPoints() {
    PointsBox(
        text = stringResource(R.string.no_otps_or_personal_messages_accessed),
        icon = R.drawable.ic_sms,
        alignment = Alignment.CenterStart,
        isStart = true
    )
    PointsBox(
        text = stringResource(R.string.data_stays_on_your_device_no_servers_involved),
        icon = R.drawable.ic_vault,
        alignment = Alignment.CenterEnd,
        isStart = false
    )
}


@Composable
private fun AnimatedImage(
    modifier: Modifier
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.auto_tracking_animation))
    LottieAnimation(
        composition,
        iterations = LottieConstants.IterateForever,
        modifier = modifier,
    )
}

fun openAppSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", context.packageName, null)
    }
    context.startActivity(intent)
}

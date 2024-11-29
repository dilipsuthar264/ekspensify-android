package com.memeusix.budgetbuddy.imagePicker

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.memeusix.budgetbuddy.R
import com.memeusix.budgetbuddy.ui.theme.Dark10

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun ImagePickerBottomSheet(
    onDismiss: (Uri?) -> Unit
) {

    val TAG = "ImagePickerBottomSheet"

    val context = LocalContext.current

    val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_MEDIA_IMAGES
        )
    } else {
        listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

    val permissionState = rememberMultiplePermissionsState(permissions)
    val checkForPermission = permissionState.allPermissionsGranted

    val selectedImageUri = remember { mutableStateOf<Uri?>(null) }

    val imagePickerLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            Log.e(TAG, "Gallery Picker URI: $uri")
            selectedImageUri.value = uri
            onDismiss(selectedImageUri.value)
        }

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success: Boolean ->
            if (success) {
                Log.e(TAG, "Camera Capture Success: URI -> ${selectedImageUri.value}")
                onDismiss(selectedImageUri.value)
            } else {
                Log.e(TAG, "Camera Capture Failed")
            }
        }


    ModalBottomSheet(
        onDismissRequest = { onDismiss(null) },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painterResource(R.drawable.ic_camera),
                contentDescription = "Camera",
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(Dark10)
                    .clickable {
                        if (checkForPermission) {
                            val photoUri = createImageUri(context)
                            selectedImageUri.value = photoUri
                            if (photoUri != null) {
                                cameraLauncher.launch(photoUri)
                            }
                        } else {
                            permissionState.launchMultiplePermissionRequest()
                        }
                    }
                    .padding(20.dp)
            )
            Icon(
                painterResource(R.drawable.ic_gallery),
                contentDescription = "Gallery",
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(Dark10)
                    .clickable {
                        if (checkForPermission) {
                            imagePickerLauncher.launch("image/*")
                        } else {
                            permissionState.launchMultiplePermissionRequest()
                        }
                    }
                    .padding(20.dp)
            )
        }
    }

}

fun createImageUri(context: Context): Uri? {
    val contentResolver = context.contentResolver
    val contentValues = ContentValues().apply {
        put(
            MediaStore.Images.Media.DISPLAY_NAME,
            "captured_image_${System.currentTimeMillis()}.jpg"
        )
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
    }
    return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
}
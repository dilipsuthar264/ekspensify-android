package com.memeusix.budgetbuddy.utils

import android.content.Context
import android.net.Uri
import androidx.compose.animation.core.AnimationVector
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.VectorizedDecayAnimationSpec
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.DialogWindowProvider
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.memeusix.budgetbuddy.data.ApiResponse
import com.memeusix.budgetbuddy.data.ErrorResponseModel
import com.memeusix.budgetbuddy.utils.toastUtils.CustomToastModel
import com.memeusix.budgetbuddy.utils.toastUtils.ToastType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import kotlin.math.min

const val TAG: String = "GENERAL FUNCTIONS"


private var isClicked = false
fun singleClick(delayMillis: Long = 300, onClick: () -> Unit): () -> Unit {
    return {
        if (!isClicked) {
            isClicked = true
            onClick()

            // Reset `isClicked` to false after the delayMillis duration
            CoroutineScope(Dispatchers.Main).launch {
                delay(delayMillis)
                isClicked = false
            }
        }
    }
}


val gson = Gson()
inline fun <reified T> T?.toJson(): String {
    return gson.toJson(this)
}

inline fun <reified T> String?.fromJson(): T? {
    return gson.fromJson(this, object : TypeToken<T>() {}.type)
}


@Composable
fun keyboardAsState(): State<Boolean> {
    val isImeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    return rememberUpdatedState(isImeVisible)
}


fun <T> handleApiResponse(
    response: ApiResponse<T>,
    toastState: MutableState<CustomToastModel?>,
    onSuccess: (T?) -> Unit,
) {
    when (response) {
        is ApiResponse.Success -> {
            onSuccess(response.data)
        }

        is ApiResponse.Failure -> {
            val errorMessage = response.errorResponse?.message ?: "An error occurred"
            toastState.value = CustomToastModel(
                message = errorMessage,
                isVisible = true,
                type = ToastType.ERROR
            )
        }

        else -> Unit
    }
}

fun <T> handleApiResponseWithError(
    response: ApiResponse<T>,
    onFailure: (ErrorResponseModel?) -> Unit,
    onSuccess: (T?) -> Unit
) {
    when (response) {
        is ApiResponse.Success -> {
            onSuccess(response.data)
        }

        is ApiResponse.Failure -> {
            onFailure(response.errorResponse)
        }

        else -> Unit
    }
}

@Composable
fun SetWindowDim(value: Float = 0f) {
    val window = (LocalView.current.parent as? DialogWindowProvider)?.window
    window?.setDimAmount(value)
}


fun getFileFromUri(context: Context, uri: Uri): File? {
    return try {
        val fileName = "temp_image_${System.currentTimeMillis()}.jpg"
        val tempFile = File(context.cacheDir, fileName)
        context.contentResolver.openInputStream(uri).use { inputStream ->
            tempFile.outputStream().use { outputStream ->
                inputStream?.copyTo(outputStream)
            }
        }
        tempFile
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
fun spacedByWithFooter(space: Dp) = object : Arrangement.Vertical {

    override val spacing = space

    override fun Density.arrange(
        totalSize: Int,
        sizes: IntArray,
        outPositions: IntArray,
    ) {
        if (sizes.isEmpty()) return
        val spacePx = space.roundToPx()

        var occupied = 0
        var lastSpace = 0

        sizes.forEachIndexed { index, size ->

            if (index == sizes.lastIndex) {
                outPositions[index] = totalSize - size
            } else {
                outPositions[index] = min(occupied, totalSize - size)
            }
            lastSpace = min(spacePx, totalSize - outPositions[index] - size)
            occupied = outPositions[index] + size + lastSpace
        }
        occupied -= lastSpace
    }
}

package com.memeusix.budgetbuddy.ui.auth.viewModel

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.memeusix.budgetbuddy.BuildConfig
import com.memeusix.budgetbuddy.data.ApiResponse
import com.memeusix.budgetbuddy.data.model.requestModel.AuthRequestModel
import com.memeusix.budgetbuddy.data.model.responseModel.AuthResponseModel
import com.memeusix.budgetbuddy.data.model.responseModel.UserResponseModel
import com.memeusix.budgetbuddy.data.repository.AuthRepository
import com.memeusix.budgetbuddy.utils.SpUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    val spUtils: SpUtils
) : ViewModel() {

    companion object {
        val TAG = AuthViewModel::class.java.name
    }


    /**
     * Login With Google
     */
    private val _signInWithGoogle =
        MutableStateFlow<ApiResponse<AuthResponseModel>>(ApiResponse.Idle)
    val signInWithGoogle = _signInWithGoogle.asStateFlow()
    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            _signInWithGoogle.value = ApiResponse.Loading()
            _signInWithGoogle.value = authRepository.signInWithGoogle(idToken)
            delay(500)
            _signInWithGoogle.value = ApiResponse.Idle
        }
    }

    /**
     * Register  With Google
     */
    private val _signUpWithGoogle =
        MutableStateFlow<ApiResponse<AuthResponseModel>>(ApiResponse.Idle)
    val signUpWithGoogle = _signUpWithGoogle.asStateFlow()
    fun signUpWithGoogle(idToken: String) {
        viewModelScope.launch {
            _signUpWithGoogle.value = ApiResponse.Loading()
            _signUpWithGoogle.value = authRepository.signUpWithGoogle(idToken)
            delay(500)
            _signUpWithGoogle.value = ApiResponse.Idle
        }
    }

    /**
     *  Login Api Calling
     */
    private val _login = MutableStateFlow<ApiResponse<AuthResponseModel>>(ApiResponse.Idle)
    val login = _login.asStateFlow()
    fun login(authRequestModel: AuthRequestModel) {
        viewModelScope.launch {
            _login.value = ApiResponse.Loading()
            _login.value = authRepository.login(authRequestModel)
            delay(500)
            _login.value = ApiResponse.Idle
        }
    }


    /**
     *  Register Api calling
     */
    private val _register = MutableStateFlow<ApiResponse<UserResponseModel>>(ApiResponse.Idle)
    val register = _register.asStateFlow()
    fun register(authRequestModel: AuthRequestModel) {
        viewModelScope.launch {
            _register.value = ApiResponse.Loading()
            _register.value = authRepository.register(authRequestModel)
            delay(500)
            _register.value = ApiResponse.Idle
        }
    }

    /**
     * Send Otp Api calling
     */
    private val _sendOtp = MutableStateFlow<ApiResponse<Any>>(ApiResponse.Idle)
    val sendOtp = _sendOtp.asStateFlow()
    fun sendOtp(authRequestModel: AuthRequestModel) {
        viewModelScope.launch {
            _sendOtp.value = ApiResponse.Loading()
            _sendOtp.value = authRepository.sendOtp(authRequestModel)
            delay(500)
            _sendOtp.value = ApiResponse.Idle
        }
    }


    /**
     * Google sign in
     */
    fun launchGoogleAuth(
        context: Context,
        coroutines: CoroutineScope,
        onResult: (String) -> Unit
    ) {
        val credentialManager = CredentialManager.create(context)
        val googleIdOptions: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(BuildConfig.CLIENT_ID)
            .setNonce(generateNonce())
            .build()

        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOptions)
            .build()

        coroutines.launch {
            try {
                val result = credentialManager.getCredential(
                    request = request,
                    context = context,
                )
                val credential = result.credential
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val googleIdToken = googleIdTokenCredential.idToken
                // calling a call back for result
                onResult(googleIdToken)
            } catch (e: GetCredentialException) {
                Log.e(TAG, "signInWithGoogle: $e")
            } catch (e: GoogleIdTokenParsingException) {
                Log.e(TAG, "signInWithGoogle: $e")
            }
        }
    }

    private fun generateNonce(): String {
        val rawNonce = UUID.randomUUID().toString()
        val bytes = rawNonce.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        val hashNonce = digest.fold("") { str, it -> str + "%02x".format(it) }
        return hashNonce
    }

}
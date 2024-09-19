package com.memeusix.budgetbuddy.ui.auth.viewModel

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.memeusix.budgetbuddy.data.BaseModel
import com.memeusix.budgetbuddy.data.model.AuthRequestModel
import com.memeusix.budgetbuddy.data.repository.AuthRepository
import com.memeusix.budgetbuddy.utils.GoogleKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    companion object {
        val TAG = AuthViewModel::class.java.name
    }

    /**
     *  Login Api Calling
     */
    private val _login = MutableStateFlow<BaseModel<Any>>(BaseModel())
    val login: StateFlow<BaseModel<Any>> = _login
    fun login(authRequestModel: AuthRequestModel) {
        viewModelScope.launch {
            _login.value = BaseModel.loading()
            _login.value = authRepository.login(authRequestModel)
        }
    }


    /**
     *  Register Api calling
     */
    private val _register = MutableStateFlow<BaseModel<Any>>(BaseModel())
    val register : StateFlow<BaseModel<Any>> = _register

    fun register(authRequestModel: AuthRequestModel){
        viewModelScope.launch {
            _register.value = BaseModel.loading()
            _register.value = authRepository.register(authRequestModel)
        }
    }


    /**
     * Google sign in
     */
    private val _userAccount = mutableStateOf<GoogleSignInAccount?>(null)
    val userAccount: State<GoogleSignInAccount?> = _userAccount
    private lateinit var googleSignInClient: GoogleSignInClient

    fun signInWithGoogle(context: Context, intentLauncher: (Intent) -> Unit) {
        val googleSignInClient = GoogleSignIn.getClient(
            context, GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(GoogleKeys.CLIENT_ID)
                .build()
        )
        val signInIntent = googleSignInClient.signInIntent
        googleSignInClient.signOut().addOnCompleteListener {
            _userAccount.value = null
        }
        intentLauncher(signInIntent)
    }
    fun handleGoogleSignInResult(intent: Intent) {
        viewModelScope.launch {
            try {
                val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
                val account = task.getResult(ApiException::class.java)
                _userAccount.value = account
                Log.e(TAG, "handleGoogleSignInResult: ${account.email}")
                Log.e(TAG, "handleGoogleSignInResult: ${account.displayName}")
                Log.e(TAG, "handleGoogleSignInResult: ${account.idToken}")
            } catch (e: ApiException) {
                Log.e(TAG, "handleGoogleSignInResult: $e")
                _userAccount.value = null
            }
        }
    }


}
package com.wama.libsignup.utility

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.facebook.*
import com.facebook.login.LoginBehavior
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.wama.libsignup.R
import com.wama.libsignup.databinding.FragmentLoginBinding
import com.wama.libsignup.utility.utility.Utills
import org.json.JSONException
import org.json.JSONObject

class LoginFragment : Fragment(){
    private var forgotPasswordBottomSheet: ForgotPasswordBottomSheet? = null
    private lateinit var binding:FragmentLoginBinding
    var navController: NavController?= null
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private lateinit var callbackManager: CallbackManager


    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        initUI()
    }

    private fun initUI() {

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestServerAuthCode(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(activity, gso)
//        auth = Firebase.auth

        binding.signInBtn.setOnClickListener {
            checkEmailPassword()
        }

        binding.signUpTv.setOnClickListener {
            navController!!.navigate(R.id.action_loginFragment_to_profileFragment)
        }

        binding.googleCard.setOnClickListener {
            googleSignIn()
        }

        binding.fbCard.setOnClickListener {
            facebookLogin()
        }

        binding.signInBtn.setOnClickListener {
            navController!!.navigate(R.id.action_loginFragment_to_profileFragment)
        }

        binding.forgotTv.setOnClickListener {
            forgotPasswordBottomSheet =  ForgotPasswordBottomSheet()
            forgotPasswordBottomSheet!!.show(requireActivity().supportFragmentManager, "ChangePassword")
           // navController!!.navigate(R.id.action_loginFragment_to_forgotPasswordBottomSheet)
        }
    }

    private fun checkEmailPassword() {
        val isValidEmail = binding.emailTet.validateEmail()
        val isValidPassword = binding.passwordEt.validatePassword()
        if (!isValidEmail) {
            return
        } else if (!isValidPassword) {
            return
        } else {
           Toast.makeText(context,"Login Successful",Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val RC_SIGN_IN = 101
    }

    //This function start intent of google signIn
    private fun googleSignIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with backend
                val account = task.getResult(ApiException::class.java)!!
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.idToken)
                /*loginViewModel.callToken(
                    account.serverAuthCode,
                    account.email,
                    account.displayName,
                    Constant.IS_GOOGLE
                )*/

            } catch (e: ApiException) {
                // Google Sign In failed, show Toast message
             //   Utills.showSnackBar(binding.root, "Google Sign In Failed")
                Log.w("TAG", "Google sign in failed", e)
            }
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data)
        }

    }



    /**
     * this function handle facebook login button click
     */
    private fun facebookLogin() {
        callbackManager = CallbackManager.Factory.create()

        LoginManager.getInstance().setLoginBehavior(LoginBehavior.WEB_ONLY)
            .logInWithReadPermissions(this, listOf("email", "public_profile"))
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult?> {


                override fun onCancel() {
                  //  Utills.showSnackBar(binding.root, "Facebook Login Cancel")
                    Log.d("TAG", "onSuccess: cancel")
                }

                override fun onError(exception: FacebookException) {
                   // Utills.showSnackBar(binding.root, "Oops")
                    Log.d("TAG", "onSuccess: $exception")
                }

                override fun onSuccess(result: LoginResult?) {
                    Log.d("TAG", "O: ${result!!.accessToken}")
//                    handleFacebookAccessToken(result!!.accessToken)
                    val newMeRequest =
                        GraphRequest.newMeRequest(result?.accessToken) { json: JSONObject?, response: GraphResponse? ->
                            Log.e("TAG", "onCompleted 354: $json")
                            var email: String? = null
                            try {
                                email = json!!.getString("email")
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                         /*   callLoginApi(
                                Constant.IS_FACEBOOK,
                                email,
                                "",
                                json!!.optString("name"),
                                result.accessToken.token
                            )*/
                        }

                    val parameters = Bundle()
                    parameters.putString("fields", "id,name,email,birthday")
                    newMeRequest.parameters = parameters
                    newMeRequest.executeAsync()
                }
            })
    }

}
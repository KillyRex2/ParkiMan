package ViewModels

import Interface.IonClick
import Library.Networks
import Library.Validate
import Models.BindableString
import android.app.Activity
import android.content.Intent
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.userskotlin.MainActivity
import com.example.userskotlin.R
import com.example.userskotlin.VeificarPassword
import com.example.userskotlin.databinding.VerificarPasswordBinding
import com.example.userskotlin.databinding.VerifyEmailBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.verify_email.view.*

open class LoginViewModels(
    activity: Activity,
    bindingEmail: VerifyEmailBinding?, //? sirve para tambien poder recibir valores nulos en los parametros
    bindingPassword: VerificarPasswordBinding?)
    : ViewModel(), IonClick {
    private var _activity : Activity? = null
    var emailUI = BindableString()
    var passwordUI = BindableString()
    var email:String? = null
    private var mAuth: FirebaseAuth? = null //Firebase
    companion object{
        private var _bindingEmail:VerifyEmailBinding? = null
        private var _bindingPassword:VerificarPasswordBinding?=null
        private var emailData: String ?= null
    }
    init {
        _activity = activity
        _bindingEmail = bindingEmail
        _bindingPassword = bindingPassword
        if (emailData != null){
            emailUI.setValue(emailData!!)
            email = emailData!!
        }
        mAuth = FirebaseAuth.getInstance()
    }

    override fun onClick(view: View) {
       when(view.id){
           R.id.email_sing_in_button -> verificarEmail()
           R.id.password_singin_button -> login()
       }
        Toast.makeText(_activity,emailUI.getValue(), Toast.LENGTH_SHORT).show()
    }
    private fun verificarEmail(){
        var cancel = true
        _bindingEmail!!.emailEditText.error = null
        if (TextUtils.isEmpty(emailUI.getValue())){
            _bindingEmail!!.emailEditText.error = _activity!!
                .getString(R.string.error_field_required)
            _bindingEmail!!.emailEditText.requestFocus()
            cancel = false
        }else if (!Validate.isEmail(emailUI.getValue())){
            _bindingEmail!!.emailEditText.error = _activity!!
                .getString(R.string.error_invalid_email)
            _bindingEmail!!.emailEditText.requestFocus()
            cancel = false
        }
        if(cancel){
            emailData = emailUI.getValue()
            _activity!!.startActivity(Intent(_activity, VeificarPassword::class.java))
        }
    }

    private fun login(){
        var cancel = true
        _bindingPassword!!.passwordEditText.error = null
        if (TextUtils.isEmpty(passwordUI.getValue())){
            _bindingPassword!!.passwordEditText.error =
                _activity!!.getString(R.string.error_field_required)
            cancel = false
        }else if(!isPasswordValid(passwordUI.getValue())){
            _bindingPassword!!.passwordEditText.error =
                _activity!!.getString(R.string.error_invalid_pasw)
            cancel = false
        }
        if (cancel) if(Networks(_activity!!).verificarNetworks()){
            mAuth!!.signInWithEmailAndPassword(email!!,passwordUI.getValue()) // indica que el campo no puede contener un valon null
                .addOnCompleteListener(_activity!!){task ->
                    if (task.isSuccessful){
                        val intent = Intent(_activity, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        _activity!!.startActivity(intent)  // Elimina actividades anteriores para que no esten en la memoria de la app

                    }else
                        Snackbar.make(
                            _bindingPassword!!.passwordEditText,
                            R.string.invalid_credentials, Snackbar.LENGTH_LONG
                        ).show()
                }
        }else{
           Snackbar.make(
               _bindingPassword!!.passwordEditText,
               R.string.networks, Snackbar.LENGTH_LONG
           ).show()
        }
    }
    private fun isPasswordValid(password: String):Boolean{
           return password.length >= 6
    }
}
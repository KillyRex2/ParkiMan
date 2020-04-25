package com.example.userskotlin

import ViewModels.LoginViewModels
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.userskotlin.databinding.VerificarPasswordBinding

class VeificarPassword : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.verificar_password)
        val binding = DataBindingUtil.setContentView<VerificarPasswordBinding>(this,R.layout.verificar_password)
        binding.passwordModel = LoginViewModels(this,null,binding)
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        //window.statusBarColor = this.resources.getColor(R.color.colorBlue,  theme:null)
        window.statusBarColor=this.resources.getColor(R.color.colorBlue,null)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, VerifyEmail::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent) //eliminar act anteriores
    }
}
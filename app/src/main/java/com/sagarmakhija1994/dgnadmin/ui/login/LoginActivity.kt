package com.sagarmakhija1994.dgnadmin.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.decos.saans.util.enums.LoginTypeEnum
import com.sagarmakhija1994.dgnadmin.R
import com.sagarmakhija1994.dgnadmin.databinding.ActivityLoginBinding
import com.sagarmakhija1994.dgnadmin.ui.home.HomeActivity
import com.sagarmakhija1994.dgnadmin.util.AppLevelData

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var userPin = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init(){
        binding.txtPinInfo.text = "Enter 4 Digit Pin To Login"
        binding.btnNumSet.text = "✓"
        binding.btnNumClear.text = "⟳"
        init4DigitButtonClick()
    }

    private fun init4DigitButtonClick(){
        binding.btnNum0.setOnClickListener { addUserPin("0") }
        binding.btnNum1.setOnClickListener { addUserPin("1") }
        binding.btnNum2.setOnClickListener { addUserPin("2") }
        binding.btnNum3.setOnClickListener { addUserPin("3") }
        binding.btnNum4.setOnClickListener { addUserPin("4") }
        binding.btnNum5.setOnClickListener { addUserPin("5") }
        binding.btnNum6.setOnClickListener { addUserPin("6") }
        binding.btnNum7.setOnClickListener { addUserPin("7") }
        binding.btnNum8.setOnClickListener { addUserPin("8") }
        binding.btnNum9.setOnClickListener { addUserPin("9") }
        binding.btnNumClear.setOnClickListener {
            clearUserPin()
        }
        binding.btnNumSet.setOnClickListener {
            if(userPin.length==4){
                if(AppLevelData.adminPin == userPin){
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                }else{
                    Toast.makeText(this,"Invalid Pin, Login Fail.", Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this,"Please Enter 4 Digit Pin", Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun addUserPin(pin:String){
        if(userPin.length<4){
            userPin += pin
        }
        if(userPin.length>=1){
            binding.editPin1.text = "*"
        }
        if(userPin.length>=2){
            binding.editPin2.text = "*"
        }
        if(userPin.length>=3){
            binding.editPin3.text = "*"
        }
        if(userPin.length>=4){
            binding.editPin4.text = "*"
        }

    }

    private fun clearUserPin(){
        userPin = ""
        binding.editPin1.text = ""
        binding.editPin2.text = ""
        binding.editPin3.text = ""
        binding.editPin4.text = ""
    }
}
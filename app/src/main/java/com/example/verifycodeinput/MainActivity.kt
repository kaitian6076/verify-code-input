package com.example.verifycodeinput

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.verifycodeinputlib.VerifyCodeInput
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        verify_code_input.setOnInputListener(object : VerifyCodeInput.OnInputListener{
            override fun onComplete(code: String?) {
                tv_input_text.text = "Your input code is : "+ code
            }

            override fun onInput() {
                tv_input_text.text = ""
            }

        })
    }
}

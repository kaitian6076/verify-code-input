package com.example.verifycodeinputlib

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView

class VerifyCodeInput : RelativeLayout {
    private var tv_code1: TextView? = null
    private var tv_code2: TextView? = null
    private var tv_code3: TextView? = null
    private var tv_code4: TextView? = null
    private var et_code: EditText? = null
    private var inputCodes: ArrayList<String> = ArrayList()

    constructor(context: Context?) : super(context) {
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initView()
    }


    private fun initView() {
        val view =
            LayoutInflater.from(context).inflate(R.layout.code_input_verify, this)
        tv_code1 = view.findViewById(R.id.tv_code1)
        tv_code2 = view.findViewById(R.id.tv_code2)
        tv_code3 = view.findViewById(R.id.tv_code3)
        tv_code4 = view.findViewById(R.id.tv_code4)
        et_code = view.findViewById(R.id.et_code)
        initEvent()
    }


    private fun initEvent() {
        //验证码输入
        et_code?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                if (editable != null && editable.isNotEmpty()) {
                    et_code?.setText("")
                    if (inputCodes.size < 4) {
                        //始终只显示第一个字符
                        inputCodes.add(editable.toString().substring(0, 1))
                        showCode()
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        // 监听验证码删除按键
        et_code?.setOnKeyListener(object : OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, keyEvent: KeyEvent?): Boolean {
                if (keyCode == KeyEvent.KEYCODE_DEL && keyEvent!!.getAction() == KeyEvent.ACTION_DOWN && inputCodes.size > 0) {
                    inputCodes.removeAt(inputCodes.size - 1)
                    showCode()
                    return true
                }
                return false
            }

        })
    }

    // 显示输入的验证码
    private fun showCode() {
        var code1 = ""
        var code2 = ""
        var code3 = ""
        var code4 = ""
        if (inputCodes.size >= 1) {
            code1 = inputCodes[0]
        }
        if (inputCodes.size >= 2) {
            code2 = inputCodes[1]
        }
        if (inputCodes.size >= 3) {
            code3 = inputCodes[2]
        }
        if (inputCodes.size >= 4) {
            code4 = inputCodes[3]
        }
        tv_code1?.text = code1
        tv_code2?.text = code2
        tv_code3?.text = code3
        tv_code4?.text = code4
        callBack() //回调
    }

    // 回调
    private fun callBack() {
        if (onInputListener == null) {
            return
        }
        if (inputCodes.size == 4) {
            onInputListener!!.onComplete(getInputCode())
        } else {
            onInputListener!!.onInput()
        }
    }

    interface OnInputListener {
        fun onComplete(code: String?)
        fun onInput()
    }

    private var onInputListener: OnInputListener? = null
    fun setOnInputListener(onInputListener: OnInputListener?) {
        this.onInputListener = onInputListener
    }

    // 获取输入的验证码
    fun getInputCode(): String? {
        val stringBuilder = StringBuilder()
        for (code in inputCodes) {
            stringBuilder.append(code)
        }
        return stringBuilder.toString()
    }

}

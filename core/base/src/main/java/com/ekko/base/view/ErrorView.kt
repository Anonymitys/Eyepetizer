package com.ekko.base.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.ekko.base.databinding.LayoutErrorViewBinding

class ErrorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: LayoutErrorViewBinding =
        LayoutErrorViewBinding.inflate(LayoutInflater.from(context), this)


    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER
    }


    fun bindErrorMsg(msg: String, retry: () -> Unit) {
        binding.errorMessage.text = msg
        binding.retry.setOnClickListener { retry.invoke() }
    }

}
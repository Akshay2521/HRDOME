package com.app.hrdrec.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup

import com.app.hrdrec.R


class Loader(context: Context) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loader_layout)
        window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
          setBackgroundDrawableResource(R.color.dialog_bg_color)
        }
    }
}
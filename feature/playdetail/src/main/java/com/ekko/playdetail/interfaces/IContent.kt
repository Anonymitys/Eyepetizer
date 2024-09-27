package com.ekko.playdetail.interfaces

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

interface IContent {

    fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View

    fun onViewCreated(view: View, savedInstanceState: Bundle?)
}
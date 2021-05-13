package com.example.myapp.ui

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel>(layout: Int) : Fragment(layout) {

    abstract val binding: VB
    abstract val viewModel: VM
}
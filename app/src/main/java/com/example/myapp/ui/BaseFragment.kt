package com.example.myapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ViewModelOwner
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.koin.getViewModel
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.parameter.emptyParametersHolder
import org.koin.core.parameter.parametersOf
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel>(
    layoutId: Int,
    private val inflate: Inflate<VB>
) : Fragment(layoutId) {

    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    /*lateinit var viewModel: VM
    abstract val viewModelClass: KClass<VM>

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = getViewModel(clazz = viewModelClass)

        super.onCreate(savedInstanceState)
    }*/

    protected open val viewModel: VM by lazy {
        getKoin().getViewModel(
            owner = { ViewModelOwner.from(this, this) },
            clazz = getViewModelKClass(),
            parameters = getParameters()
        )
    }

    @Suppress("UNCHECKED_CAST")
    protected fun getViewModelKClass(): KClass<VM> {
        val actualClass =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>
        return actualClass.kotlin
    }

    open fun getParameters(): ParametersDefinition = {
        emptyParametersHolder()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
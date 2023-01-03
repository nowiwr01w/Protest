package com.nowiwr01p.core_ui.mapper

import androidx.lifecycle.ViewModel

abstract class ViewModelMapper<VM: ViewModel> {
    lateinit var viewModel: VM
}
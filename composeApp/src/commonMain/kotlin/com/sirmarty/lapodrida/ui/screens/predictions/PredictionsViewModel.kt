package com.sirmarty.lapodrida.ui.screens.predictions

import androidx.lifecycle.ViewModel
import com.sirmarty.lapodrida.ui.navigation.Navigator

class PredictionsViewModel(
    private val navigator: Navigator,
) : ViewModel() {

    fun onBackClicked() {
        navigator.goBack()
    }
}

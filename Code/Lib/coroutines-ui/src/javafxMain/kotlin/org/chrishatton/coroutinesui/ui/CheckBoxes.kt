package org.chrishatton.coroutinesui.ui

import javafx.scene.control.CheckBox
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combineLatest

@ExperimentalCoroutinesApi
fun CheckBox.indeterminacy() : ReceiveChannel<Boolean> = indeterminateProperty().values()

@ExperimentalCoroutinesApi
fun CheckBox.isSelecteds() : ReceiveChannel<Boolean> = selectedProperty().values()

enum class CheckedState {
    Indeterminate,
    Selected,
    NotSelected
}

@ExperimentalCoroutinesApi
@FlowPreview
fun CheckBox.checkedStates() : Flow<CheckedState> = TODO()
//    indeterminateProperty().asFlow().combine(selectedProperty().asFlow()) { isIndeterminate:Boolean,isSelected:Boolean ->
//        when {
//            isIndeterminate -> CheckedState.Indeterminate
//            isSelected      -> CheckedState.Selected
//            else            -> CheckedState.NotSelected
//        }
//    }

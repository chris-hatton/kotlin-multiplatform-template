package example.ui

interface PresenterContract<V:ViewContract<Self,V>,Self:PresenterContract<V,Self>> {
    val view : V

    fun onAttach()

    fun onDetach()
}
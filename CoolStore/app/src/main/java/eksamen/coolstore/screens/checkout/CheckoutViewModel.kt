package eksamen.coolstore.screens.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eksamen.coolstore.data.Orders
import eksamen.coolstore.data.ProductRepository
import eksamen.coolstore.data.ShoppingCart
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CheckoutViewModel: ViewModel() {
    private val _selectedOrder = MutableStateFlow<ShoppingCart?>(null)
    val selectedOrder = _selectedOrder.asStateFlow()
}
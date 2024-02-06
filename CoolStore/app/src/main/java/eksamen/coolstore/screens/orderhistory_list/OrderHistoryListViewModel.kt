package eksamen.coolstore.screens.orderhistory_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eksamen.coolstore.data.Orders
import eksamen.coolstore.data.Product
import eksamen.coolstore.data.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OrderHistoryListViewModel : ViewModel() {
    private val _orders = MutableStateFlow<List<Orders>>(emptyList())
    val orders: StateFlow<List<Orders>> = _orders.asStateFlow()

    init {
        loadOrders()
    }

    fun loadOrders() {
        viewModelScope.launch {
            val ordersList = ProductRepository.getAllOrders()
            _orders.value = ordersList
        }
    }
}

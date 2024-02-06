package eksamen.coolstore.product_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eksamen.coolstore.data.Product
import eksamen.coolstore.data.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductListViewModel : ViewModel() {
    private val _products = MutableStateFlow <List<Product>> (emptyList())
    val products = _products.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            _loading.value = true
            _products.value = ProductRepository.getProducts()
            _loading.value = false
        }
    }

    fun clearOrderHistory() {
        viewModelScope.launch {
            ProductRepository.clearOrderHistory()
        }
    }
}
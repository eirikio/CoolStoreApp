// ProductDetailsViewModel

package eksamen.coolstore.product_details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eksamen.coolstore.data.Product
import eksamen.coolstore.data.ProductRepository
import eksamen.coolstore.data.ShoppingCart
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductDetailsViewModel : ViewModel() {
    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct = _selectedProduct.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    fun setSelectedProduct(productId: Int) {
        viewModelScope.launch {
            _selectedProduct.value = ProductRepository.getProductById(productId)
        }
    }

    fun addToCart(productId: Int) {
        viewModelScope.launch {
            val selectedProduct = _selectedProduct.value
            if (selectedProduct != null) {
                ProductRepository.addToCart(selectedProduct)
                Log.d("ProductDetailsViewModel", "Hey")
            }
        }
    }
}
package eksamen.coolstore.screens.shopping_cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eksamen.coolstore.data.ProductRepository
import eksamen.coolstore.data.ShoppingCart
import kotlinx.coroutines.flow.Flow

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ShoppingCartViewModel : ViewModel() {
    private val _cartItems = MutableStateFlow <List<ShoppingCart>> (emptyList())
    val cartItems = _cartItems.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            _loading.value = true
            _cartItems.value = ProductRepository.getAllCartItems()
            _loading.value = false
            Log.d("ShoppingCartViewModel", "Products loaded: $_cartItems")
        }
    }

    fun deleteFromCart(productId: Int) {
        viewModelScope.launch {
            ProductRepository.deleteCartItem(productId)
            loadProducts()
            Log.d("ShoppingView", "Hey from the viewModel and")
        }
    }

    fun completeOrder(cartItems: List<ShoppingCart>) {
        viewModelScope.launch {
            ProductRepository.completeOrder(cartItems)
        }
    }

    fun increaseQuantity(cartItemId: Int) {
        viewModelScope.launch {
            ProductRepository.increaseCartItemQuantity(cartItemId)
            loadProducts()
        }
    }

    fun decreaseQuantity(cartItemId: Int) {
        viewModelScope.launch {
            ProductRepository.decreaseCartItemQuantity(cartItemId)
            loadProducts()
        }
    }
}
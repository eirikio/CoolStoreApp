package eksamen.coolstore.data

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import eksamen.coolstore.data.room.AppDatabase
import eksamen.coolstore.data.room.ShoppingCartDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object ProductRepository {
    private val _httpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()

    private val _retrofit =
        Retrofit.Builder()
            .client(_httpClient)
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val _productService =
        _retrofit.create(ProductService::class.java)

    private lateinit var _appDataBase: AppDatabase

    fun initiateAppDatabase(context: Context) {
        _appDataBase = Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "appdatabase"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    suspend fun getProducts(): List<Product> {
        return try {
            val response = _productService.getAllProducts()

            if (response.isSuccessful) {

                val product = response.body() ?: emptyList()
                _appDataBase.getProductDao().addProduct(product)
                return _appDataBase.getProductDao().getAllProducts()

            } else {
                throw Exception("getProducts response is not successful")
            }
        } catch (e: Exception) {
            Log.e("getProducts", "Failed to get products from the API", e)
            return _appDataBase.getProductDao().getAllProducts()
        }
    }

    suspend fun getProductById(productId: Int): Product? {
        return _appDataBase.getProductDao().getProductById(productId)
    }

    suspend fun getProductsById(ids:List<Int>): List<Product> {
        return _appDataBase.getProductDao().getProductsById(ids)
    }



    // Shopping Cart
    suspend fun getAllCartItems(): List<ShoppingCart> {
        return _appDataBase.getShoppingCartDao().getAllCartItems()
    }

    suspend fun addToCart(product: Product) {
        withContext(Dispatchers.IO) {
            val shoppingCart = ShoppingCart(
                id = product.id,
                title = product.title,
                price = product.price,
                category = product.category,
                quantity = 1,
                imageUrl = product.imageUrl
            )

            try {
                _appDataBase.getShoppingCartDao().addToCart(shoppingCart)
                Log.d("ProductRepository", "Success, added product to cart")
            } catch (e: Exception) {
                Log.e("ProductRepository", "Error adding to cart: ${e.message}")
            }
        }
    }

    suspend fun deleteCartItem(productId: Int) {
        withContext(Dispatchers.IO) {
            try {
                _appDataBase.getShoppingCartDao().deleteCartItem(productId)
                Log.d("ProductRepository", "Success, deleted product from cart")
            } catch (e: Exception) {
                Log.e("ProductRepository", "Error deleting from cart: ${e.message}")
            }
        }
    }

    suspend fun clearCart() {
        _appDataBase.getShoppingCartDao().clearCart()
    }

    suspend fun increaseCartItemQuantity(productId: Int) {
        val cartItem = _appDataBase.getShoppingCartDao().getCartItemById(productId)

        cartItem?.let {
            it.quantity += 1
            _appDataBase.getShoppingCartDao().updateCartItem(it)
        }
    }

    suspend fun decreaseCartItemQuantity(productId: Int) {
        val cartItem = _appDataBase.getShoppingCartDao().getCartItemById(productId)

        cartItem?.let {
            if (it.quantity > 1) {
                it.quantity -= 1
                _appDataBase.getShoppingCartDao().updateCartItem(it)
            }
        }
    }


    // Order History
    suspend fun getAllOrders(): List<Orders> {
        return _appDataBase.getOrdersDao().getAllOrders()
    }

    suspend fun completeOrder(orderItems: List<ShoppingCart>) {
        withContext(Dispatchers.IO) {
            try {
                val orderDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
                val totalAmount = orderItems.sumBy { it.quantity }
                val totalPrice = orderItems.sumByDouble { it.price * it.quantity }

                val productTitles = orderItems.joinToString(separator = "; ") { it.title }

                val order = Orders(
                    id = 0,
                    date = orderDate,
                    title = productTitles,
                    price = totalPrice,
                    quantity = totalAmount
                )

                _appDataBase.getOrdersDao().addOrder(order)
                Log.d("ProductRepository", "Success, completed order")
                clearCart()
            } catch (e: Exception) {
                Log.e("ProductRepository", "Error completing order: ${e.message}")
            }
        }
    }

    suspend fun clearOrderHistory() {
            _appDataBase.getOrdersDao().clearOrderHistory()
        }
}
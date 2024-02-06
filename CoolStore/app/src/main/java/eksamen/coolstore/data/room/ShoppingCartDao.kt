package eksamen.coolstore.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import eksamen.coolstore.data.Product
import eksamen.coolstore.data.ShoppingCart
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingCartDao {

    @Query("SELECT * FROM ShoppingCart")
    suspend fun getAllCartItems(): List<ShoppingCart>

    @Insert
    suspend fun addToCart(shoppingCart: ShoppingCart)

    @Query("DELETE FROM ShoppingCart WHERE id = :productId")
    suspend fun deleteCartItem(productId: Int)

    @Query("DELETE FROM ShoppingCart")
    suspend fun clearCart()

    @Query("SELECT * FROM ShoppingCart WHERE id = :productId")
    suspend fun getCartItemById(productId: Int): ShoppingCart?

    @Update
    suspend fun updateCartItem(shoppingCart: ShoppingCart)
}


/*@Dao
interface ShoppingCartDao {

    @Query("SELECT * FROM ShoppingCart")
    suspend fun getAllCartItems(): List<ShoppingCart>

    @Insert
    suspend fun addToCart(shoppingCart: ShoppingCart)

    @Query("DELETE FROM ShoppingCart WHERE id = :productId")
    suspend fun deleteCartItem(productId: Int)

    @Query("DELETE FROM ShoppingCart")
    suspend fun clearCart()

    @Query("SELECT * FROM ShoppingCart WHERE id = :productId")
    suspend fun getCartItemById(productId: Int): ShoppingCart?

    @Update
    suspend fun updateCartItem(shoppingCart: ShoppingCart)
}*/
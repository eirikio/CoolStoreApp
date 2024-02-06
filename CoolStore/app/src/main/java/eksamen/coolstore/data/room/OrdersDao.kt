package eksamen.coolstore.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import eksamen.coolstore.data.Orders
import eksamen.coolstore.data.Product
import eksamen.coolstore.data.ShoppingCart
import kotlinx.coroutines.flow.Flow

@Dao
interface OrdersDao {
    @Query("SELECT * FROM Orders")
    suspend fun getAllOrders(): List<Orders>

    @Insert
    suspend fun addOrder(order: Orders): Long

    @Insert
    suspend fun addOrders(orders: List<Orders>): List<Long>

    @Query("DELETE FROM Orders")
    suspend fun clearOrderHistory()
}


/*@Dao
interface OrdersDao {
    @Query("SELECT * FROM Orders")
    suspend fun getAllOrders(): List<Orders>

    @Insert
    suspend fun addOrder(order: Orders): Long

    @Insert
    suspend fun addOrders(orders: List<Orders>): List<Long>

    @Query("DELETE FROM Orders")
    suspend fun clearOrderHistory()
}*/
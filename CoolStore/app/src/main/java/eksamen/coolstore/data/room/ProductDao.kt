package eksamen.coolstore.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import eksamen.coolstore.data.Product
import eksamen.coolstore.data.ShoppingCart

@Dao
interface ProductDao {

    @Query("SELECT * FROM Products")
    suspend fun getAllProducts(): List<Product>

    @Query("SELECT * FROM Products WHERE :productId = id")
    suspend fun getProductById(productId: Int): Product?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProduct(products:List<Product>)

    @Query("SELECT * FROM Products WHERE id IN (:ids)")
    suspend fun getProductsById(ids: List<Int>): List<Product>
}
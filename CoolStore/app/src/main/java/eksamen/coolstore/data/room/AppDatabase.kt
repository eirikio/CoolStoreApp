package eksamen.coolstore.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import eksamen.coolstore.data.Orders
import eksamen.coolstore.data.Product
import eksamen.coolstore.data.ShoppingCart

@Database(entities = [Product::class, ShoppingCart::class, Orders::class], version = 11, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getProductDao(): ProductDao

    abstract fun getShoppingCartDao(): ShoppingCartDao

    abstract fun getOrdersDao(): OrdersDao

}
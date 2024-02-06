package eksamen.coolstore.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class ShoppingCart(
    @PrimaryKey
    val id: Int,
    val title: String,
    val price: Double,
    val category: String,
    var quantity: Int,
    @SerializedName("image")
    val imageUrl: String,
)
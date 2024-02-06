package eksamen.coolstore.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity("Products")
data class Product(
    @PrimaryKey
    val id: Int,
    val title: String,
    val price: Double,
    val category: String,
    val description: String,
    @SerializedName("image")
    val imageUrl: String,
    )
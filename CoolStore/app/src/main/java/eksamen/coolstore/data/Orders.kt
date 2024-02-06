package eksamen.coolstore.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Orders(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: String,
    val title: String,
    val price: Double,
    val quantity: Int
    )
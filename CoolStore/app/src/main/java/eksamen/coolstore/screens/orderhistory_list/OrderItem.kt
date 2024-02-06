package eksamen.coolstore.screens.order_history

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import eksamen.coolstore.data.Orders
import eksamen.coolstore.ui.theme.BabyBlue
import eksamen.coolstore.ui.theme.BabyBlue50

@Composable
fun OrderItem(order: Orders) {

    fun qtyString(): String {
        return if (order.quantity < 2) {
            "item"
        } else {
            "items"
        }
    }

        val productTitles = order.title.split("; ")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 20.dp)
            .background(color = BabyBlue)
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(1),
            )
            .border(2.dp, Color.Black, RoundedCornerShape(10)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
        ){
            Text(
                text = "${order.date}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(10.dp))
            for ((index, productTitle) in productTitles.withIndex()) {
                Text(
                    text = "${index + 1}. $productTitle",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(5.dp))
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "€${order.price}")
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = "(${order.quantity} ${qtyString()})")
            }

        }
    }
}
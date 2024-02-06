package eksamen.coolstore.screens.order_history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eksamen.coolstore.data.Orders
import eksamen.coolstore.screens.orderhistory_list.OrderHistoryListViewModel
import eksamen.coolstore.screens.shopping_cart.ShoppingCartViewModel
import eksamen.coolstore.shopping_cart.ShoppingCartItem
import eksamen.coolstore.ui.theme.BabyBlue

@Composable
fun OrderHistoryListScreen(
    viewModel: OrderHistoryListViewModel,
    onHomeButtonClick: () -> Unit = {},
    onNavButtonClick: () -> Unit = {}
) {
    val orders = viewModel.orders.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BabyBlue)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = BabyBlue),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                text = "Order History",
                style = MaterialTheme.typography.titleLarge
            )

            Row(
                modifier = Modifier
                    .weight(1f),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onHomeButtonClick() }) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Home Button"
                    )
                }
                IconButton(onClick = { onNavButtonClick() }) {
                    Icon(
                        imageVector = Icons.Default.List,
                        contentDescription = "Open Navigation Menu"
                    )
                }
                IconButton(onClick = { viewModel.loadOrders() }) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh"
                    )
                }
            }
        }
        Divider(color = Color.Black)

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(orders.value) {order ->
                OrderItem(
                    order = order
                )
            }
        }
    }
}
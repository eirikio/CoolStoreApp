package eksamen.coolstore.screens.shopping_cart

import android.graphics.ColorSpace
import androidx.annotation.ColorRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.Color.Companion.hsl
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eksamen.coolstore.R
import eksamen.coolstore.data.ProductRepository
import eksamen.coolstore.data.ShoppingCart
import eksamen.coolstore.product_list.ProductItem
import eksamen.coolstore.shopping_cart.ShoppingCartItem
import eksamen.coolstore.ui.theme.BabyBlue


@Composable
fun ShoppingCartScreen(
    viewModel: ShoppingCartViewModel,
    onBackButtonClick: () -> Unit = {},
    onCheckoutClick: () -> Unit = {},
    onHomeButtonClick: () -> Unit = {},
    onNavButtonClick: () -> Unit = {}
) {

    fun totalPrice(cartItems: List<ShoppingCart>): Double {
        return cartItems.sumByDouble { it.price * it.quantity }
    }

    val cartItems = viewModel.cartItems.collectAsState()
    val isLoading = viewModel.loading.collectAsState()

    if (isLoading.value) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BabyBlue),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onBackButtonClick() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Arrow back"
                )
            }
            Text(
                modifier = Modifier.padding(12.dp),
                text = "Cart",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
            )
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { onHomeButtonClick() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Home Button"
                    )
                }
                IconButton(
                    onClick = { onNavButtonClick() }
                ) {
                    Icon(
                        imageVector = Icons.Default.List,
                        contentDescription = "Open Navigation Menu"
                    )
                }
                IconButton(onClick = { viewModel.loadProducts() }) {
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
            items(cartItems.value) { shoppingCart ->
                ShoppingCartItem(
                    shoppingCart = shoppingCart,
                    viewModel = viewModel
                )
            }
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            onClick = {
                viewModel.completeOrder(cartItems.value)
                onCheckoutClick()
            }
        ) {
            Text(
                text = "Checkout (€${totalPrice(cartItems.value)})",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}
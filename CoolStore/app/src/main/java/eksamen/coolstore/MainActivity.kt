package eksamen.coolstore

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import eksamen.coolstore.data.ProductRepository
import eksamen.coolstore.product_details.ProductDetailsScreen
import eksamen.coolstore.product_details.ProductDetailsViewModel
import eksamen.coolstore.ui.theme.CoolStoreTheme
import eksamen.coolstore.product_list.ProductListScreen
import eksamen.coolstore.product_list.ProductListViewModel
import eksamen.coolstore.screens.checkout.CheckoutScreen
import eksamen.coolstore.screens.checkout.CheckoutViewModel
import eksamen.coolstore.screens.navigation_screen.NavigationScreen
import eksamen.coolstore.screens.order_history.OrderHistoryListScreen
import eksamen.coolstore.screens.orderhistory_list.OrderHistoryListViewModel
import eksamen.coolstore.screens.shopping_cart.ShoppingCartScreen
import eksamen.coolstore.screens.shopping_cart.ShoppingCartViewModel

class MainActivity : ComponentActivity() {
    private val _productListViewModel: ProductListViewModel by viewModels()
    private val _productDetailsViewModel: ProductDetailsViewModel by viewModels()
    private val _shoppingCartViewModel: ShoppingCartViewModel by viewModels()
    private val _checkoutViewModel: CheckoutViewModel by viewModels()
    private val _orderHistoryListViewModel: OrderHistoryListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ProductRepository.initiateAppDatabase(applicationContext)

        val showToast: (String) -> Unit = { message ->
            val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
            toast.show()
        }

        setContent {
            CoolStoreTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "productListScreen"
                ) {
                    composable(route = "productListScreen") {
                        ProductListScreen(
                            viewModel = _productListViewModel,
                            onProductClick = {productId ->
                                navController.navigate("productDetailsScreen/${productId}")
                            },
                            onShoppingCartButtonClick = {navController.navigate("shoppingCartScreen")},
                            onNavButtonClick = {navController.navigate("navigationScreen")}
                        )
                    }

                    composable(
                        route = "productDetailsScreen/{productId}",
                        arguments = listOf(
                            navArgument(name = "productId") {
                                type = NavType.IntType
                            }
                        )
                    ) {
                            backStackEntry ->
                        val productId = backStackEntry.arguments?.getInt("productId") ?: -1
                        LaunchedEffect(productId) {
                            _productDetailsViewModel.setSelectedProduct(productId)
                        }

                        ProductDetailsScreen(
                            viewModel = _productDetailsViewModel,
                            onBackButtonClick = {navController.popBackStack()},
                            onShoppingCartButtonClick = { navController.navigate("shoppingCartScreen") },
                            onNavButtonClick = {navController.navigate("navigationScreen")},
                            showToast = { message -> showToast(message) }
                        )
                    }


                    composable(route = "shoppingCartScreen") {
                        ShoppingCartScreen(
                            viewModel = _shoppingCartViewModel,
                            onBackButtonClick = {navController.popBackStack()},
                            onCheckoutClick = {navController.navigate("checkoutScreen")},
                            onHomeButtonClick = {navController.navigate("productListScreen")},
                            onNavButtonClick = {navController.navigate("navigationScreen")},
                        )
                    }

                    composable(route = "checkoutScreen") {
                        CheckoutScreen(
                            viewModel = _checkoutViewModel,
                            onHomeButtonClick = {navController.navigate("productListScreen")},
                            onOrderHistoryButtonClick = {navController.navigate("orderHistoryScreen")},
                        )
                    }

                    composable(route = "orderHistoryScreen") {
                        OrderHistoryListScreen(
                            viewModel = _orderHistoryListViewModel,
                            onHomeButtonClick = {navController.navigate("productListScreen")},
                            onNavButtonClick = {navController.navigate("navigationScreen")},
                        )
                    }

                    composable(route = "navigationScreen") {
                        NavigationScreen(
                            onHomeButtonClick = {navController.navigate("productListScreen")},
                            onShoppingCartButtonClick = {navController.navigate("shoppingCartScreen")},
                            onOrderHistoryButtonClick = {navController.navigate("orderHistoryScreen")},
                            onExitButtonClick = {navController.popBackStack()}
                        )
                    }
                }
            }
        }
    }
}

/*
* TODOS:
* [X] Fikse OrderItem til å displaye alt riktig og fint (50%)
* [X] ^Ekstra. Fiks Orders table så den kan displaye hver item i carten med egen ID for hvert enkelt item.
*
* [X] 1/2 Fikse alle knapper slik at man kan navigere rundt i appen (90%)
* [X] 2/2Fikse liste knappen så man kan gå fra forsiden rett til order history hvis man vil
*
* [X] (50%)Fikse slik at ting loader med en gang slik at man kan fjerne refresh knappen. F.eks at det loader automatisk når man fjerner noe fra handlekurv
* [] Fikset at quantity oppdaterer seg, og slett fra handlekurv. Men legg til i handlekurv og checkout oppdaterer ikke neste skjerm automatisk.
*
* [X] Fiks beskjeder om at ting er f.eks. lagt til i handlekurven når man trykker Add
*
* [X] Fiks Increment quantity i handlekurv siden og decrement qantity og oppdater pris etter hvor mange som er i kurven
*
* [X] Order History siden må ha hjemknapp, tilbakeknapp osv.
*
* [X] Etter alt dette test appen skikkelig.
*
* [X] 1/2 Gå gjennom koden og se at alt er ryddig og pent, at det ikke ligger ubrukt kode rundt om kring og at loading sirkel er implementert på alle screens.
* [X] 2/2 Gå gjennom koden og se etter steder man kan legge på try catch og lignende.
*
* [] Gå gjennom alle additional functionality og legg på det du klarer
*
* [] Fjerne alle refresh knapper når alt fungerer.

* * [] Legg til flere TODOS under
* */
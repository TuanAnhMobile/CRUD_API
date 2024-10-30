package android.mobile.crud_api

import android.mobile.crud_api.model.Products
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import android.mobile.crud_api.ui.theme.CRUD_APITheme
import android.mobile.crud_api.viewmodel.ProductsViewModel
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items

import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import androidx.lifecycle.viewmodel.compose.viewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProductScreen()
            //push code sang nhanh master
            

            //soundcloud Tuan Anh Mobile

            //keo code sang nhanh 01
        }
    }
}


@Composable
fun ProductScreen(viewModel: ProductsViewModel = viewModel()) {

    LaunchedEffect(Unit) {
        viewModel.getProducts()
    }

    val arrayProducts = viewModel.listProducts

    var showDialogAdd by remember { mutableStateOf(false) }
    var showDialogDetail by remember { mutableStateOf(false) }

    var selectedProduct by remember { mutableStateOf<Products?>(null) }


    val context = LocalContext.current

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialogAdd = true }) {
                Icon(Icons.Default.Add, contentDescription = "")
            }
        },
        content = { paddingValues ->
            if (
                arrayProducts.isEmpty()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Loading...", fontSize = 20.sp)
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding(),
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .statusBarsPadding()
                            .padding(10.dp),
//                        columns = GridCells.Fixed(2),
//                        horizontalArrangement = Arrangement.spacedBy(16.dp),
//                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {

                        items(arrayProducts) { data ->
                            ProductItem(products = data, onClick = {
                                viewModel.getProductById(data.id) { fetchedProduct ->
                                    selectedProduct = fetchedProduct
                                    showDialogDetail = true
                                }
                            })
                        }
                    }

                }
            }
//Dung de them san pham
            if (showDialogAdd) {
                AddProducts(onDissmiss = { showDialogAdd = false }, onAdd = { newUser ->
                    viewModel.addProducts(newUser)
                    Toast.makeText(context, "Add Success", Toast.LENGTH_SHORT).show()
                    showDialogAdd = false
                })
            }

//Dung de xem chi tiet
            if (showDialogDetail && selectedProduct != null) {
                ProductDetailsDialog(
                    product = selectedProduct!!,
                    onDismiss = { showDialogDetail = false }
                )
            }
        }
    )
}

@Composable
fun ProductItem(products: Products, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .height(100.dp)
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AsyncImage(
                model = products.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(60.dp)
            )

            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = products.name_genre, fontSize = 20.sp)
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = if (products.status) "Con hang" else "Het hang",
                    fontSize = 15.sp,
                    color = if (products.status) Color.Green else Color.Red
                )
            }
        }
    }
}

@Composable
fun Preview_Demo(products: Products,onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .height(300.dp)
                .width(200.dp),
        ) {
            AsyncImage(
                model = products.image,
                contentDescription = "",
                placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                modifier = Modifier
                    .height(225.dp)
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(topEnd = 8.dp, topStart = 8.dp),
                    )
            )
            Spacer(modifier = Modifier.height(10.dp))

            Column(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = products.name_genre)
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = if (products.status) "Con hang " else "Het hang",
                    color = if (products.status) Color.Green else Color.Red
                )

            }

        }

    }

}

@Composable
fun AddProducts(onDissmiss: () -> Unit, onAdd: (Products) -> Unit) {
    var name by remember { mutableStateOf("") }
    var image by remember { mutableStateOf("") }
    var status by remember { mutableStateOf(true) }

    AlertDialog(
        onDismissRequest = { onDissmiss() },
        title = { Text(text = "Add Products") },
        text = {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(text = "Name") },
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = image,
                    onValueChange = { image = it },
                    label = { Text(text = "Name") },
                )
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Switch(checked = status, onCheckedChange = { status = it })
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "Con hang", color = Color.Black)
                }
            }

        },
        confirmButton = {
            TextButton(
                onClick = {
                    val newProduct = Products(
                        id = "",
                        name_genre = name,
                        image = image,
                        status = status
                    )
                    onAdd(newProduct)
                }) {
                Text(text = "Add")
            }
        })
}

@Composable
fun ProductDetailsDialog(product: Products, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Product Details") },
        text = {
            Column {
                Text(text = "Name: ${product.name_genre}")
                Text(text = "Status: ${if (product.status) "Con hang" else "Het hang"}")
                AsyncImage(
                    model = product.image,
                    contentDescription = null,
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(text = "Close")
            }
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    ProductScreen()
}
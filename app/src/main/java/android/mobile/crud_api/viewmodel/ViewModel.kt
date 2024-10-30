package android.mobile.crud_api.viewmodel

import android.mobile.crud_api.model.Products
import android.mobile.crud_api.service.API_CONNECT
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ProductsViewModel : ViewModel() {
    val listProducts = mutableStateListOf<Products>()


    fun getProducts() {
        viewModelScope.launch {
            try {
                val products = API_CONNECT.apiService.getProducts()
                Log.d("ProductsData777", "Fetched products: $products") // Log data fetched from API
                listProducts.clear()
                listProducts.addAll(products)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("ERROR_DATA", "getProducts: " + e.message)
            }

        }
    }

    //Xem chi tiet theo ID
    fun getProductById(productId: String, onSuccess: (Products) -> Unit) {
        viewModelScope.launch {
            try {
                val product = API_CONNECT.apiService.getProductById(productId)
                onSuccess(product)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("ERROR_DATA", "getProductById: ${e.message}")
            }
        }
    }

    fun addProducts(products: Products){
        viewModelScope.launch {
            try {
                val newProducts = API_CONNECT.apiService.addProduct(products)
                listProducts.add(newProducts)
                Log.d("Data_add", "San pham vua duoc them: ${newProducts}")
            }catch (e:Exception){
                e.printStackTrace()
            }
        }

    }
}
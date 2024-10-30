package android.mobile.crud_api.service

import android.mobile.crud_api.model.Products
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface API_METHOD {

    @GET("/products")
    suspend fun getProducts(): List<Products>

    @GET("/products/{id}")
    suspend fun getProductById(@Path("id") productId: String): Products

    @POST("/products")
    suspend fun addProduct(@Body product: Products): Products

}
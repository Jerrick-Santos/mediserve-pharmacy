package com.mediserve.pharma.mediservepharma

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface MediServeApi {

    @GET("login")
    suspend fun login(@Query("username") username: String, @Query("password") password: String): Response<UserPharmacist>

    @GET("prodcatalogue")
    suspend fun getProductCatalogue(@Query("pharmacyID") pharmacyID: Int): Response<List<Product>>

    @GET("inventory")
    suspend fun getInventory(@Query("pharmacyID") pharmacyID: Int): Response<List<InventoryStockGET>>

    @GET("transactions")
    suspend fun getTransactions(@Query("pharmacyID") pharmacyID: Int): Response<List<Transaction>>

    @POST("newstock")
    suspend fun addStock(@Body stock: NewStockPOST): Response<Void>

    @PATCH("editstock")
    suspend fun editStock(@Body stock: EditStockPatch): Response<Void>

    @GET("viewcart")
    suspend fun getCart(@Query("pharmacyID") pharmacyID: Int): Response<List<Order>>

    @POST("addcart")
    suspend fun addCart(@Body cartItem: NewCartItemPOST): Response<Void>

    @POST("scantocart")
    suspend fun scanQR(@Body newItems : ScanQRPOST): Response<Void>

    @PATCH("checkout")
    suspend fun checkoutCart(@Query("pharmacyID") pharmacyID: Int): Response<Void>

    @DELETE("clearcart")
    suspend fun clearCart(@Query("pharmacyID") pharmacyID: Int): Response<Void>
}

data class ProductCatalogueResponse(
    val results: List<Product>
)
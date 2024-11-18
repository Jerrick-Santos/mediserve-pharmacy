package com.mediserve.pharma.mediservepharma

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MediServeApi {

    @GET("login")
    suspend fun login(@Query("username") username: String, @Query("password") password: String): Response<UserPharmacist>

    @GET("prodcatalogue")
    suspend fun getProductCatalogue(@Query("pharmacyID") pharmacyID: Int): Response<List<Product>>

    @GET("inventory")
    suspend fun getInventory(@Query("pharmacyID") pharmacyID: Int): Response<List<InventoryStockGET>>
}

data class ProductCatalogueResponse(
    val results: List<Product>
)
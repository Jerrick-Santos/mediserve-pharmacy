package com.mediserve.pharma.mediservepharma

data class NewStockPOST(
    val pharmacyID: Int,
    val productID: Int,
    val currentAmt: Int
)
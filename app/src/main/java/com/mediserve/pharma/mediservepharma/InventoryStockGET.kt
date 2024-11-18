package com.mediserve.pharma.mediservepharma

data class InventoryStockGET(
    val id: String,
    val brandName: String,
    val genericName: String,
    val manufacturer: String,
    val dosage: String,
    val qty: Int
)
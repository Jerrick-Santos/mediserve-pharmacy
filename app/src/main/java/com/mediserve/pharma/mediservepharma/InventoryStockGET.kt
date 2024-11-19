package com.mediserve.pharma.mediservepharma

data class InventoryStockGET(

    val product_id: String,
    val stock_id: Int,
    val brandName: String,
    val genericName: String,
    val manufacturer: String,
    val dosage: String,
    val qty: Int
)
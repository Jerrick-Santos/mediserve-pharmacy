package com.mediserve.pharma.mediservepharma

data class Transaction(
    val transac_id: String,
    val product_name: String,
    val change_type: String,
    val qty: Int
)
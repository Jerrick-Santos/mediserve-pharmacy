package com.mediserve.pharma.mediservepharma

data class NewCartItemPOST(
    val pharmacyID: Int,
    val stockID: Int,
    val qty: Int
)
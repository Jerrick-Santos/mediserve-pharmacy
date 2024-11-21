package com.mediserve.pharma.mediservepharma

data class EditStockPatch(
    val stockID: Int,
    val changeType: String,
    val amt: Int
)
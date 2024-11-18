package com.mediserve.pharma.mediservepharma

class Transaction (inventoryStock: InventoryStock, qty: Int, isAdd: Boolean){

    var inventoryStock = inventoryStock
        private set

    var qty = qty
        private set

    var isAdd = isAdd
        private set
}
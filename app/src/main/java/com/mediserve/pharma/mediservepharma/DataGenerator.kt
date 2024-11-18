package com.mediserve.pharma.mediservepharma

class DataGenerator {

    companion object {

        // Generates a list of Products (Master list of products)
        fun generateProductList(): ArrayList<Product> {
            val productList = ArrayList<Product>()

            productList.add(Product("HC001293", "BIOGESIC", "Paracetamol", "Uneliever", "500mg"))
            productList.add(Product("HC001294", "Advil", "Ibuprofen", "Pfizer", "200mg"))
            productList.add(Product("HC001295", "Alaxan", "Ibuprofen + Paracetamol", "Unilab", "500mg"))
            productList.add(Product("HC001296", "Decolgen", "Phenylephrine + Paracetamol", "Unilab", "500mg"))
            productList.add(Product("HC001297", "Neozep", "Phenylephrine HCl", "Unilab", "500mg"))
            productList.add(Product("HC001298", "Cortal", "Aspirin", "Bayer", "500mg"))
            productList.add(Product("HC001299", "Diatabs", "Loperamide", "Johnson & Johnson", "2mg"))
            productList.add(Product("HC001300", "Kremil-S", "Magnesium Hydroxide + Aluminum Hydroxide", "United Lab", "500mg"))
            productList.add(Product("HC001301", "Tuseran", "Phenylephrine + Dextromethorphan", "Unilab", "500mg"))
            productList.add(Product("HC001302", "Solmux", "Carbocisteine", "Pascual", "500mg"))
            productList.add(Product("HC001303", "Ventolin", "Salbutamol", "GlaxoSmithKline", "100mcg"))
            productList.add(Product("HC001304", "Ceelin", "Vitamin C (Ascorbic Acid)", "Unilab", "100mg"))
            productList.add(Product("HC001305", "Enervon", "Vitamin B + C", "Unilab", "500mg"))
            productList.add(Product("HC001306", "Medicol", "Ibuprofen", "Pfizer", "200mg"))
            productList.add(Product("HC001307", "Dolfenal", "Mefenamic Acid", "Unilab", "500mg"))
            productList.add(Product("HC001308", "Zithromax", "Azithromycin", "Pfizer", "250mg"))
            productList.add(Product("HC001309", "Amoxicillin", "Amoxicillin", "GlaxoSmithKline", "500mg"))
            productList.add(Product("HC001310", "Cetirizine", "Cetirizine Hydrochloride", "Pfizer", "10mg"))
            productList.add(Product("HC001311", "Biogesic for Kids", "Paracetamol", "Unilab", "120mg"))

            // You can add more products if needed

            return productList
        }

        // Generates a list of InventoryStock (stock in stores)
        fun generateInventoryStockList(): ArrayList<InventoryStock> {
            val productList = generateProductList() // Get the product list
            val inventoryStockList = ArrayList<InventoryStock>()

            for (i in productList.indices) {
                // Generate different quantities for each product stock
                inventoryStockList.add(InventoryStock(productList[i], (10..100).random()))
            }

            return inventoryStockList
        }

        // Generates a list of Transactions (adding or subtracting stock)
        fun generateTransactionList(): ArrayList<Transaction> {
            val inventoryStockList = generateInventoryStockList()
            val transactionList = ArrayList<Transaction>()

            for (i in inventoryStockList.indices) {
                // Randomly decide whether it's an addition or subtraction transaction
                val isAdd = listOf(true, false).random()
                val qty = (1..inventoryStockList[i].qty).random() // Quantity involved in the transaction
                transactionList.add(Transaction(inventoryStockList[i], qty, isAdd))
            }

            return transactionList
        }

        // Generates a list of Orders (each containing multiple transactions)
        fun generateOrderList(): ArrayList<Order> {
            val transactionList = generateTransactionList()
            val orderList = ArrayList<Order>()

            for (i in transactionList.indices) {
                // Randomly generate total item amount based on transaction quantity
                val totalItemAmt = transactionList[i].qty
                orderList.add(Order(transactionList[i], totalItemAmt))
            }

            return orderList
        }
    }
}

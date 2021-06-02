package com.example.fakeonliner.repos

import com.example.fakeonliner.models.ProductSimplified
import kotlinx.coroutines.delay

class ProductRepo {

    suspend fun getProducts(categoryId: String): List<ProductSimplified> {
        val data: Map<String, List<ProductSimplified>> = mapOf(
            "Электроника" to listOf(
                ProductSimplified(
                    "https://i.dell.com/is/image/DellContent//content/dam/global-site-design/product_images/dell_client_products/desktops/inspiron_desktops/27_7790/general/new-category-page-desktop-inspiron-27-7790-3880-800x620.png?fmt=png-alpha&amp;wid=800&amp;hei=620",
                    "Title 1",
                    "Description 1"
                ),
                ProductSimplified(
                    "https://meetingtomorrow.com/wp-content/uploads/2019/09/8BF79EAA-C291-890F-3B4303804F13173B.jpg",
                    "Title 2",
                    "Description 2"
                ),
            ),
            "Компьютеры и сети" to listOf(
                ProductSimplified(
                    "https://i2.wp.com/www.un.org/pga/74/wp-content/uploads/sites/99/2020/08/240-2407402_laptop-personal-computer-clip-art-transparent-background-laptop.png?fit=300%2C204&ssl=1",
                    "Title 3",
                    "Description 3"
                ),
                ProductSimplified(
                    "https://images.indianexpress.com/2021/05/Asus-AIO-1.jpg",
                    "Title 4",
                    "Description 4"
                ),
                ProductSimplified(
                    "https://cdn.cnn.com/cnnnext/dam/assets/200630134005-build-two-1.jpg",
                    "Title 5",
                    "Description 5"
                )
            )
        )

        delay(1000)

        return data[categoryId] ?: emptyList()
    }
}
package com.example.fakeonliner.service.response

data class GetProductsResponse(
    val page: Page,
    val products: List<Product>,
    val total: Int,
    val total_ungrouped: Int
) {
    data class Page(
        val current: Int,
        val items: Int,
        val last: Int,
        val limit: Int
    )

    data class Product(
        val advertise: Any?,
        val color_code: Any?,
        val description: String,
        val extended_name: String,
        val forum: Forum,
        val full_name: String,
        val html_url: String,
        val id: Int,
        val image_size: List<Any>,
        val images: Images,
        val key: String,
        val max_cobrand_cashback: Any?,
        val max_installment_terms: Any?,
        val micro_description: String,
        val name: String,
        val name_prefix: String,
        val prices: Prices?,
        val review_url: Any?,
        val reviews: Reviews,
        val sale: Sale,
        val second: Second,
        val status: String,
        val stickers: Any?,
        val url: String
    ) {
        data class Forum(
            val post_url: String,
            val replies_count: Any?,
            val topic_id: Any?,
            val topic_url: Any?
        )

        data class Images(
            val header: String,
            val icon: Any?
        )

        data class Prices(
            val currency_sign: Any?,
            val html_url: String,
            val max: Any?,
            val min: Any?,
            val offers: Offers,
            val price_max: PriceMax,
            val price_min: PriceMin,
            val url: String
        ) {
            data class Offers(
                val count: Int
            )

            data class PriceMax(
                val amount: Float,
                val converted: Converted,
                val currency: String
            ) {
                data class Converted(
                    val BYN: BYNValue,
                    val BYR: BYRValue
                ) {
                    data class BYNValue(
                        val amount: String,
                        val currency: String
                    )

                    data class BYRValue(
                        val amount: String,
                        val currency: String
                    )
                }
            }

            data class PriceMin(
                val amount: Float,
                val converted: Converted,
                val currency: String
            ) {
                data class Converted(
                    val BYN: BYNValue,
                    val BYR: BYRValue
                ) {
                    data class BYNValue(
                        val amount: String,
                        val currency: String
                    )

                    data class BYRValue(
                        val amount: String,
                        val currency: String
                    )
                }
            }
        }

        data class Reviews(
            val count: Int,
            val html_url: String,
            val rating: Int,
            val url: String
        )

        data class Sale(
            val discount: Int,
            val is_on_sale: Boolean,
            val min_prices_median: MinPricesMedian
        ) {
            data class MinPricesMedian(
                val amount: String,
                val currency: String
            )
        }

        data class Second(
            val min_price: Any?,
            val offers_count: Int
        )
    }
}
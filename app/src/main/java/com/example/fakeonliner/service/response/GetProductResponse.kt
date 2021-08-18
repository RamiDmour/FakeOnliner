package com.example.fakeonliner.service.response

data class GetProductResponse(
    val certification_required: Boolean,
    val color_code: String,
    val description: String,
    val extended_name: String,
    val forum: Forum,
    val full_name: String,
    val html_url: String,
    val id: Int,
    val image_size: List<Any>,
    val images: Images,
    val key: String,
    val manufacturer: Manufacturer,
    val max_cobrand_cashback: MaxCobrandCashback,
    val micro_description: String,
    val name: String,
    val name_prefix: String,
    val parent_key: String,
    val prices: Prices,
    val review_url: String,
    val reviews: Reviews,
    val sale: Sale,
    val second: Second,
    val status: String,
    val stickers: List<Sticker>,
    val url: String
) {
    data class Forum(
        val post_url: String,
        val topic_id: Int,
        val topic_url: Any?
    )

    data class Images(
        val header: String,
        val icon: Any?
    )

    data class Manufacturer(
        val key: String,
        val legal_address: String,
        val legal_name: String,
        val name: String
    )

    data class MaxCobrandCashback(
        val label: String,
        val percentage: Int
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
            val amount: String,
            val converted: Converted,
            val currency: String
        ) {
            data class Converted(
                val byn: BYN,
                val byr: BYR
            ) {
                data class BYN(
                    val amount: String,
                    val currency: String
                )

                data class BYR(
                    val amount: String,
                    val currency: String
                )
            }
        }

        data class PriceMin(
            val amount: String,
            val converted: Converted,
            val currency: String
        ) {
            data class Converted(
                val byn: BYN,
                val byr: BYR
            ) {
                data class BYN(
                    val amount: String,
                    val currency: String
                )

                data class BYR(
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
        val can_be_subscribed: Boolean,
        val discount: Int,
        val is_on_sale: Boolean,
        val min_prices_median: MinPricesMedian,
        val subscribed: Boolean
    ) {
        data class MinPricesMedian(
            val amount: String,
            val currency: String
        )
    }

    data class Second(
        val max_price: Any?,
        val min_price: Any?,
        val offers_count: Int
    )

    data class Sticker(
        val bg_color: String,
        val color: String,
        val label: String,
        val text: String,
        val type: String
    )
}
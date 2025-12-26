package ru.dev.sergey

object OffersData {

    var textButtonDefault = "Перейти"

    fun getOffers(): List<Offer> = listOf(
        Offer(
            title = "Binance",
            description = "Бонус до $600 за регистрацию и первый депозит",
            textButton = textButtonDefault,
            url = "https://www.binance.com/"
        ),
        Offer(
            title = "ByBit",
            description = "Бонус $20 + скидка на комиссии",
            textButton = textButtonDefault,
            url = "https://bybit.com/"
        ),
        Offer(
            title = "AliExpress",
            description = "Купон до 20% + кэшбэк",
            textButton = textButtonDefault,
            url = "https://aliexpress.com/"
        ),
        Offer(
            title = "OKX",
            description = "До $10 000 бонусов за регистрацию",
            textButton = textButtonDefault,
            url = "https://www.okx.com/"
        ),
        Offer(
            title = "Wildberries",
            description = "Купон 500₽ на первую покупку",
            textButton = textButtonDefault,
            url = "https://www.wildberries.ru/"
        )
        // Добавляй сюда новые офферы сколько угодно
        // Можно даже сортировать по категориям или добавлять поле category позже
    )
}
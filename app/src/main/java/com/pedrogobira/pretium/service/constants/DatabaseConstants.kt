package com.pedrogobira.pretium.service.constants

/**
 * Todas as constantes utilizadas no banco de dados - Tabelas e colunas
 */
class DatabaseConstants private constructor() {

    /**
     * Tabelas disponíveis no banco de dados com suas colunas
     */
    object SERVICE {
        const val ID = "serviceid"
        const val TABLE_NAME = "services"

        object COLUMNS {
            const val ID = "id"
            const val PRICE_PER_HOUR = "price_per_hour"
            const val HOURS = "hours"
            const val MINUTES = "minutes"
            const val DAY = "day"
            const val MONTH = "month"
            const val YEAR = "year"
            const val DESCRIPTION = "description"
        }
    }
}
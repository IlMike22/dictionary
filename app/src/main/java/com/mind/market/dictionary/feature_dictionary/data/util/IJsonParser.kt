package com.mind.market.dictionary.feature_dictionary.data.util

import java.lang.reflect.Type

interface IJsonParser {
    fun <T> fromJson(json: String, type: Type): T?
    fun <T> toJson(obj: T, type: Type): String?
}
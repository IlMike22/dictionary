package com.mind.market.dictionary.feature_dictionary.data.util

import com.google.gson.Gson
import java.lang.reflect.Type

class GsonParser(
    private val gson: Gson
) : IJsonParser {
    override fun <T> fromJson(json: String, type: Type): T? {
        return gson.fromJson(json, type)
    }

    override fun <T> toJson(obj: T, type: Type): String? {
        return gson.toJson(obj, type)
    }
}
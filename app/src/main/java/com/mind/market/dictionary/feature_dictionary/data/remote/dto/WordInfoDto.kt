package com.mind.market.dictionary.feature_dictionary.data.remote.dto

import com.mind.market.dictionary.feature_dictionary.data.local.entity.WordInfoEntity

data class WordInfoDto(
    val meanings: List<MeaningDto>,
    val origin: String,
    val phonetic: String,
    val phonetics: List<PhoneticDto>,
    val word: String
) {
    fun toWordInfoEntity(): WordInfoEntity =
        WordInfoEntity(
            meanings = meanings.map { meaningDto -> meaningDto.toMeaning() },
            origin = origin,
            phonetic = phonetic,
            word = word
        )
}
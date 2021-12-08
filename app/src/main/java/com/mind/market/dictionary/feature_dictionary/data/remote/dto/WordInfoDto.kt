package com.mind.market.dictionary.feature_dictionary.data.remote.dto

import com.mind.market.dictionary.feature_dictionary.domain.model.WordInfo

data class WordInfoDto(
    val meanings: List<MeaningDto>,
    val origin: String,
    val phonetic: String,
    val phonetics: List<PhoneticDto>,
    val word: String
) {
    fun toWordInfo(): WordInfo =
        WordInfo(
            meanings = meanings.map { meaningDto -> meaningDto.toMeaning() },
            origin = origin,
            phonetic = phonetic,
            word = word
        )
}
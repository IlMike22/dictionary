package com.mind.market.dictionary.feature_dictionary.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mind.market.dictionary.feature_dictionary.domain.model.Meaning
import com.mind.market.dictionary.feature_dictionary.domain.model.WordInfo

@Entity
data class WordInfoEntity(
    val word: String? = null,
    val phonetic: String? = null,
    val origin: String? = null,
    val meanings: List<Meaning>? = null,
    @PrimaryKey val id: Int? = null
) {
    fun toWordInfo(): WordInfo =
        WordInfo(
            meanings = meanings ?: emptyList(),
            origin = origin ?: "",
            phonetic = phonetic ?: "",
            word = word ?: ""
        )
}

package com.mind.market.dictionary.feature_dictionary.domain.repository

import com.mind.market.dictionary.core.util.Resource
import com.mind.market.dictionary.feature_dictionary.domain.model.WordInfo
import kotlinx.coroutines.flow.Flow

interface IWordInfoRepository {
    fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>>
}
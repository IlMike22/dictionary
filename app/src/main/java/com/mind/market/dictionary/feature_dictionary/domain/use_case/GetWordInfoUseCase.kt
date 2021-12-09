package com.mind.market.dictionary.feature_dictionary.domain.use_case

import com.mind.market.dictionary.core.util.Resource
import com.mind.market.dictionary.feature_dictionary.domain.model.WordInfo
import com.mind.market.dictionary.feature_dictionary.domain.repository.IWordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetWordInfoUseCase(
    private val repository: IWordInfoRepository
) {
    operator fun invoke(word: String): Flow<Resource<List<WordInfo>>> {
        if (word.isEmpty()) {
            return flow {}
        }
        return repository.getWordInfo(word)
    }
}
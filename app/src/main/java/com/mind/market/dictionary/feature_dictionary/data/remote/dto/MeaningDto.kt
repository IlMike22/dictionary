package com.mind.market.dictionary.feature_dictionary.data.remote.dto

import com.mind.market.dictionary.feature_dictionary.domain.model.Meaning

data class MeaningDto(
    val definitions: List<DefinitionDto>,
    val partOfSpeech: String
) {
    fun toMeaning(): Meaning =
        Meaning(
            definitions = definitions.map { definitionDto ->
                definitionDto.toDefinition()
            },
            partOfSpeech = partOfSpeech
        )
}
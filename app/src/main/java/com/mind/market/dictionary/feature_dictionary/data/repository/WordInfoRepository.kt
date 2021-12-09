package com.mind.market.dictionary.feature_dictionary.data.repository

import com.mind.market.dictionary.core.util.Resource
import com.mind.market.dictionary.feature_dictionary.data.local.IWordInfoDao
import com.mind.market.dictionary.feature_dictionary.data.remote.IDictionaryApi
import com.mind.market.dictionary.feature_dictionary.domain.model.WordInfo
import com.mind.market.dictionary.feature_dictionary.domain.repository.IWordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class WordInfoRepository(
    private val api: IDictionaryApi,
    private val dao: IWordInfoDao
) : IWordInfoRepository {
    override fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>> = flow {
        emit(Resource.Loading())

        val wordInfos = dao.getWordInfos(word).map { wordInfoEntity ->
            wordInfoEntity.toWordInfo()
        }

        emit(Resource.Loading(data = wordInfos))

        try {
            val remoteWordInfos = api.getWordInfo(word)

            dao.deleteWordInfos(remoteWordInfos.map { wordInfoDto ->
                wordInfoDto.word
            })

            dao.insertWordInfos(remoteWordInfos.map { wordInfoDto ->
                wordInfoDto.toWordInfoEntity()
            })
        } catch (exception: HttpException) {
            emit(
                Resource.Error(
                    message = "Ups something went wrong here.",
                    data = wordInfos
                )
            )
        } catch (exception: IOException) {
            emit(
                Resource.Error(
                    message = "Cannot reach server. Check your internet.",
                    data = wordInfos
                )
            )
        }

        val newWordInfos = dao.getWordInfos(word).map { wordInfoEntity ->
            wordInfoEntity.toWordInfo()
        }

        emit(Resource.Success(newWordInfos))
    }
}
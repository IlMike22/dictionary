package com.mind.market.dictionary.feature_dictionary.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.mind.market.dictionary.feature_dictionary.data.local.Converters
import com.mind.market.dictionary.feature_dictionary.data.local.WordInfoDatabase
import com.mind.market.dictionary.feature_dictionary.data.remote.IDictionaryApi
import com.mind.market.dictionary.feature_dictionary.data.remote.IDictionaryApi.Companion.BASE_URL
import com.mind.market.dictionary.feature_dictionary.data.repository.WordInfoRepository
import com.mind.market.dictionary.feature_dictionary.data.util.GsonParser
import com.mind.market.dictionary.feature_dictionary.domain.repository.IWordInfoRepository
import com.mind.market.dictionary.feature_dictionary.domain.use_case.GetWordInfoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WordInfoModule {
    // provide use case
    @Provides
    @Singleton
    fun provideGetWordInfoUseCase(repository: IWordInfoRepository): GetWordInfoUseCase {
        return GetWordInfoUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideWordInfoRepository(
        api: IDictionaryApi,
        database: WordInfoDatabase
    ): IWordInfoRepository {
        return WordInfoRepository(
            api = api,
            dao = database.dao
        )
    }

    @Provides
    @Singleton
    fun provideWordInfoDatabase(app: Application): WordInfoDatabase {
        return Room.databaseBuilder(
            app,
            WordInfoDatabase::class.java,
            "word_info_db"
        ).addTypeConverter(Converters(GsonParser(Gson())))
            .build()
    }

    fun provideApi(): IDictionaryApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IDictionaryApi::class.java)
    }
}
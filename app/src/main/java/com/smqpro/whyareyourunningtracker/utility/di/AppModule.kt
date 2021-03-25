package com.smqpro.whyareyourunningtracker.utility.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import com.smqpro.whyareyourunningtracker.model.db.RunningDatabase
import com.smqpro.whyareyourunningtracker.utility.Constants
import com.smqpro.whyareyourunningtracker.utility.Constants.KEY_FIRST_TIME_TOGGLE
import com.smqpro.whyareyourunningtracker.utility.Constants.KEY_NAME
import com.smqpro.whyareyourunningtracker.utility.Constants.KEY_WEIGHT
import com.smqpro.whyareyourunningtracker.utility.Constants.RUNNING_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRunningDatabase(
        @ApplicationContext
        app: Context
    ) = Room.databaseBuilder(
        app,
        RunningDatabase::class.java,
        RUNNING_DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideRunDao(db: RunningDatabase) = db.getRunDao()

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext app: Context) =
        app.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideName(sp: SharedPreferences) = sp.getString(KEY_NAME, "") ?: ""

    @Singleton
    @Provides
    fun provideWeight(sp: SharedPreferences) = sp.getFloat(KEY_WEIGHT, 80F)

    @Singleton
    @Provides
    fun provideFirstTimeToggle(sp: SharedPreferences) = sp.getBoolean(KEY_FIRST_TIME_TOGGLE, true)

}
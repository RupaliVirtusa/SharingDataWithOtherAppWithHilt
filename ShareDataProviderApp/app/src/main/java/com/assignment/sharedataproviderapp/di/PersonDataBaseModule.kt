package com.assignment.sharedataproviderapp.di

import android.content.Context
import androidx.room.Room
import com.assignment.sharedataproviderapp.db.PersonDataBase
import com.assignment.sharedataproviderapp.db.dao.PersonDao
import com.assignment.sharedataproviderapp.repository.PersonRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersonDataBaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): PersonDataBase {
        return Room.databaseBuilder(appContext, PersonDataBase::class.java, "PersonDatabase")
            .fallbackToDestructiveMigration().build()
    }
}
package com.assignment.sharedataproviderapp.di

import com.assignment.sharedataproviderapp.db.PersonDataBase
import com.assignment.sharedataproviderapp.db.dao.PersonDao
import com.assignment.sharedataproviderapp.repository.PersonRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersonRepoModule {

    @Provides
    @Singleton
    fun providePersonRepo(personDataBase: PersonDataBase): PersonRepoImpl {
        return PersonRepoImpl(personDataBase)
    }
}
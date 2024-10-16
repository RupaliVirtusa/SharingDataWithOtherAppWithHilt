package com.assignment.sharedataproviderapp.repository

import com.assignment.sharedataproviderapp.db.PersonDataBase
import com.assignment.sharedataproviderapp.db.dao.PersonDao
import com.assignment.sharedataproviderapp.db.entities.PersonEntity
import javax.inject.Inject

interface PersonRepo {
    suspend fun insertPerson(entity: PersonEntity)
    suspend fun getPersons(): List<PersonEntity>
}

class PersonRepoImpl @Inject constructor(private val personDb: PersonDataBase) : PersonRepo {
    override suspend fun insertPerson(entity: PersonEntity) {
        personDb.personDao().insert(entity)
    }

    override suspend fun getPersons(): List<PersonEntity> {
        return personDb.personDao().fetch()
    }

}
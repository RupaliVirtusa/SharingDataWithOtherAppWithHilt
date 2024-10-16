package com.assignment.sharedataproviderapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.assignment.sharedataproviderapp.db.dao.PersonDao
import com.assignment.sharedataproviderapp.db.entities.PersonEntity

@Database(entities = [PersonEntity::class], version = 1)
abstract class PersonDataBase : RoomDatabase() {

    abstract fun personDao(): PersonDao
}
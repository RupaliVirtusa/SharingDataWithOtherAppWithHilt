package com.assignment.sharedataproviderapp.db.dao

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.assignment.sharedataproviderapp.db.entities.PersonEntity

@Dao
interface PersonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: PersonEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFromContentProvider(entity: PersonEntity): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateFromContentProvider(person: PersonEntity): Int?

    @Query("SELECT * FROM person_entity")
    suspend fun fetch(): List<PersonEntity>

    @Query("SELECT * FROM person_entity")
    fun fetchCursor(): Cursor

    @Query("DELETE  FROM person_entity WHERE id=:id")
    fun deletePerson(id: String)

    //TODO it will delete the exact object which is stored in the db with the same values.
    @Delete
    fun delete(entity: PersonEntity)

}
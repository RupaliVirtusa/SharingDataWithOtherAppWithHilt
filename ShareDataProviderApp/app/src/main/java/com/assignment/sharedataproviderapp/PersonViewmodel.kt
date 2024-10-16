package com.assignment.sharedataproviderapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.sharedataproviderapp.db.entities.PersonEntity
import com.assignment.sharedataproviderapp.repository.PersonRepo
import com.assignment.sharedataproviderapp.repository.PersonRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(private val repo: PersonRepoImpl) : ViewModel() {

    val personState = MutableStateFlow<List<PersonEntity>>(emptyList())
    fun insertPerson(entity: PersonEntity) {
        viewModelScope.launch {
            repo.insertPerson(entity)
        }
    }

    fun fetchPersons() {
        viewModelScope.launch {
            personState.value = repo.getPersons()
        }
    }
}
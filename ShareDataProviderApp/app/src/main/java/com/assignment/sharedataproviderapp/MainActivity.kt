package com.assignment.sharedataproviderapp

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.assignment.sharedataproviderapp.db.entities.PersonEntity
import com.assignment.sharedataproviderapp.db.entities.fromContentValues
import com.assignment.sharedataproviderapp.ui.theme.ShareDataProviderAppTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    var uri = Uri.parse("content://com.assignment.sharedataproviderapp.myProvider/person_entity")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShareDataProviderAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Greeting("Android", viewModel = hiltViewModel<PersonViewModel>())
                    PersonScreen(hiltViewModel<PersonViewModel>())
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun PersonScreen(viewModel: PersonViewModel) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                var userName by remember { mutableStateOf(TextFieldValue("")) }
                TextField(value = userName, placeholder = {
                    Text(text = "Username")
                }, onValueChange = {
                    userName = it
                    Log.d("testing", "Username: $it")
                })
                var age by remember { mutableStateOf(TextFieldValue("")) }
                TextField(value = age, placeholder = {
                    Text(text = "Age")
                }, onValueChange = {
                    age = it
                    Log.d("testing", "Age: $it")
                }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal))
                var gender by remember { mutableStateOf("") }
                TextField(value = gender, placeholder = {
                    Text(text = "Gender")
                }, onValueChange = {
                    gender = it
                    Log.d("testing", "Age: $it")
                })
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = {
                    viewModel.insertPerson(
                        PersonEntity(
                            name = userName.text,
                            age = age.text,
                            gender = gender
                        )
                    )
                }) {
                    Text(text = "Insert Person")
                }
                Button(onClick = {
                    viewModel.fetchPersons()
                }) {
                    Text(text = "Show Person")
                    val personState by viewModel.personState.collectAsState()

                    personState.apply {
                        for (person in this) {
                            person.age?.let { Log.v("person age", it) }
                            person.name?.let { Log.v("person name", it) }
                            person.gender?.let { Log.v("person gender", it) }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, viewModel: PersonViewModel) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
    repeat(5) { i ->
        var age = 10 + i
        viewModel.insertPerson(PersonEntity(name = "Test $i", age = "" + age, gender = "F"))
    }
    LaunchedEffect(Unit, block = {
        viewModel.fetchPersons()
    })

    val personState by viewModel.personState.collectAsState()

    personState.apply {
        for (person in this) {
            person.age?.let { Log.v("person age", it) }
            person.name?.let { Log.v("person name", it) }
            person.gender?.let { Log.v("person gender", it) }
        }
    }
}


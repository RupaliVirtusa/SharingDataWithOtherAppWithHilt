package com.assignment.sharedataconsumerapp

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.assignment.sharedataconsumerapp.ui.theme.ShareDataConsumerAppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    var uri = Uri.parse("content://com.assignment.sharedataproviderapp.myProvider/person_entity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var text by remember { mutableStateOf("Click a button") }
            ShareDataConsumerAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(text)
                    Button(onClick = {
                        text = ""
                        lifecycleScope.launch {
                            val en = ContentResolverHelper(applicationContext).allCompanyTMRecords
                            Log.v("person ", en[0].name.toString())
                        }
                    }) {
                        Text(text = "Get Data")
                    }
                }
            }
        }
    }


    @SuppressLint("Range")
    private fun getData(): String {
        var cursor = contentResolver.query(Constants.CONTENT_URI, null, null, null, "id")
        if (cursor!!.moveToFirst()) {
            val strBuild = StringBuilder()
            while (!cursor.isAfterLast) {
                strBuild.append(
                    """${cursor.getString(cursor.getColumnIndex("id"))}-${
                        cursor.getString(
                            cursor.getColumnIndex("name")
                        )
                    }""".trimIndent()
                )
                cursor.moveToNext()
            }
            Log.d("", "$strBuild")
            return strBuild.toString()
        } else {
            Log.d("", "No Records Found")
        }
        return "nothing"
    }

}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ShareDataConsumerAppTheme {
        Greeting("Android")
    }
}
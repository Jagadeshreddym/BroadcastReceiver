package com.example.broadcastreceiverexample

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.broadcastreceiverexample.ui.theme.BroadCastReceiverExampleTheme

class MainActivity : ComponentActivity() {

    lateinit var receiver: MyReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BroadCastReceiverExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android", context = LocalContext.current)
                    receiver = MyReceiver()

                    // Intent Filter is useful to determine which apps wants to receive
                    // which intents,since here we want to respond to change of
                    // airplane mode
                    IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED).also {
                        // registering the receiver
                        // it parameter which is passed in  registerReceiver() function
                        // is the intent filter that we have just created
                        registerReceiver(receiver, it)
                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiver)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, context : Context) {
    var text by remember { mutableStateOf("") }

    var text1 by remember { mutableStateOf("") }
    val sh = context.getSharedPreferences("MySharedPref", MODE_PRIVATE)
    val name = sh.getString("name", "")
    val age = sh.getString("age", "")

    Column(
        // we are using column to align our
        // imageview to center of the screen.
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),

        // below line is used for specifying
        // vertical arrangement.
        verticalArrangement = Arrangement.Center,

        // below line is used for specifying
        // horizontal arrangement.
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {
        if (name != null) {
            Text(

                text = name,
                modifier = modifier
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        if (age != null) {
            Text(
                text = age,
                modifier = modifier
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = text,
            onValueChange = {
                text = it

            },

        )
        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = text1,
            onValueChange = {
                text1 = it

            },

            )
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { /*TODO*/

            val sharedPreferences = context.getSharedPreferences("MySharedPref", MODE_PRIVATE)
            val myEdit = sharedPreferences.edit()

            // write all the data entered by the user in SharedPreference and apply
            myEdit.putString("name", text)
            myEdit.putString("age", text1)
            myEdit.apply()

        } ) {
            Text(
                // on below line adding a text,
                // padding, color and font size.
                text = "Submit",
                modifier = Modifier.padding(10.dp),
                color = Color.White,
                fontSize = 20.sp
            )
        }
        
    }
    
}


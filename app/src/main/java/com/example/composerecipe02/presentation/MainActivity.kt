package com.example.composerecipe02.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.composerecipe02.R
import com.example.composerecipe02.network.RecipeService
import com.google.gson.GsonBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object{
        const val BASE_URL="https://food2fork.ca/api/recipe/"
        const val TOKEN="Token 9c8b06d329136da358c2d00e76946b0111ce2c48"
    }

    val TAG="MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

/*
        val service= Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(RecipeService::class.java)

        CoroutineScope(IO).launch {
            val response=service.get(
                token = TOKEN,
                id = 583
            )
            Log.i(TAG, "onCreate: ${response.title}")
        }

*/


       /* setContent {

            //deprecated ScrollableColumn
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(0xFFF2F2F2))
            ) {
                Image(
                    painterResource(id = R.drawable.happy_meal_med),
                    contentDescription = "happy_meal",
                    modifier = Modifier.height(300.dp),
                    contentScale = ContentScale.Crop,
                )
                /* Image(
                     bitmap = imageFromResource(res = resources, resId = R.drawable.happy_meal),
                     modifier = Modifier.height(300.dp),
                     contentScale = ContentScale.Crop,
                 )*/

                Column(modifier = Modifier.padding(16.dp)) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Simple Meal", fontSize = 26.sp)
                        Text(text = "$5.99", fontSize = 17.sp, color = Color(0XFF85bb65),
                        modifier = Modifier.align(Alignment.CenterVertically))
                    }

                    Spacer(modifier = Modifier.padding(top = 10.dp))
                    Text(text = "800 Calories", fontSize = 17.sp)
                    Spacer(modifier = Modifier.padding(top = 10.dp))
                    
                    Button(onClick = { /*TODO*/ },modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        Text(text = "ORDER NOW")
                    }

                }


            }

        }*/


/*
        setContent{
            Column() {

                Column(modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .border(border = BorderStroke(width = 1.dp, color = Color.Black)),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "ITEM1",modifier = Modifier.align(Alignment.CenterHorizontally))
                }

                Spacer(modifier = Modifier.padding(20.dp))

                Row(modifier = Modifier
                    .width(200.dp)
                    .height(200.dp)
                    .border(border = BorderStroke(width = 1.dp, color = Color.Black)),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "ITEM1",modifier = Modifier.align(Alignment.CenterVertically))
                }

            }
        }

        */


    }
}

/*
@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeRecipe02Theme {
        Greeting("Android")
    }
}

*/

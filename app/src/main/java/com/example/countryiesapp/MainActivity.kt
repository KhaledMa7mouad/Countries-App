package com.example.countryiesapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType.Companion.Uri
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.core.net.toUri
import com.example.countryiesapp.data.DataSource
import com.example.countryiesapp.model.Country
import com.example.countryiesapp.ui.theme.CountryiesAppTheme
import androidx.compose.ui.res.stringResource as stringResource1

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CountryiesAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CountriesList(
                        countries = DataSource().getCountries(),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun CountriesList(countries: List<Country>, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LazyColumn(
            modifier = modifier
        ) {

            items(countries) { country ->
                CountryListItem(country = country)
            }
        }

        val annotatedString = buildAnnotatedString {
            append("Check the countries population from ")
            withStyle(
                style = SpanStyle(
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Blue
                )
            ) {
                pushStringAnnotation(
                    tag = "here_tag",
                    annotation = "https://www.worldometers.info/world-population/population-by-country/"
                )
                append("here")
                pop()

            }

        }
        ClickableText(text = annotatedString) { offset ->
            annotatedString.getStringAnnotations(
                tag = "here_tag",
                start = offset,
                end = offset
            ).firstOrNull()?.let {

                val i = Intent(Intent.ACTION_VIEW, it.item.toUri())
                context.startActivity(i)
            }

        }


    }


}


@Composable
fun CountryListItem(country: Country, modifier: Modifier = Modifier) {

    val context = LocalContext.current

    Card(
        elevation = CardDefaults.cardElevation(12.dp),
        border = BorderStroke(1.dp, Color.Black),
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = country.pic),
                contentDescription = stringResource1(id = country.name),
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(80.dp)
            )
            Text(
                text = stringResource1(id = country.name),
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 16.dp)
            )
            Spacer(modifier = Modifier.weight(1f)) // Add space before the icon
            IconButton(
                onClick = {

                    // val gmmIntentUri = ("google.streetview:cbll=").toUri()  ->  (google street view)
                    //you can add ?z for zoom level
                    // q for query to serach for a lo cation like (hotels) -> val gmmIntentUri = "geo:${country.lat},${country.long}?z=5&q=hotels".toUri()
                    // using q you can use a spacific search like (YAT Learning Centers - Maadi)

                    // for driving mode for a specific location
                    //   -> val gmmIntentUri =
//                        "google.navigation:q=YAT Learning Centers - Maadi".toUri()
//                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
//                    mapIntent.setPackage("com.google.android.apps.maps")
//                  context.startActivity(mapIntent)

                    val gmmIntentUri =
                        "geo:0,0?q=${country.lat},${country.long}(${context.getString(country.name)})?z=7".toUri()
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")
                    context.startActivity(mapIntent)


                },
                modifier = Modifier.wrapContentWidth(align = Alignment.End)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_location),
                    contentDescription = "Location",
                    tint = Color.Unspecified
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun CountryListItemPreview() {
    CountryListItem(country = DataSource().getCountries()[1])
}




package com.example.travelcompanion

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.travelcompanion.ui.theme.TravelCompanionTheme
import com.example.travelcompanion.viewmodel.EventViewModel
import com.example.travelcompanion.viewmodel.NewsViewModel
import com.example.travelcompanion.viewmodel.WeatherViewModel
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TravelCompanionTheme {
                val weatherViewModel: WeatherViewModel by viewModels()
                val newsViewModel: NewsViewModel by viewModels()
                val eventViewModel: EventViewModel by viewModels()
                MainScreen(weatherViewModel, newsViewModel, eventViewModel, applicationContext)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    weatherViewModel: WeatherViewModel,
    newsViewModel: NewsViewModel,
    eventViewModel: EventViewModel,
    context: Context
) {
    val tabs = listOf("Weather", "News", "Events")
    var selectedTabIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        eventViewModel.fetchEvents()
        weatherViewModel.fetchWeatherByLocation(context)
        newsViewModel.fetchTopHeadlines()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text("Travel Companion") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title) }
                    )
                }
            }
            when (selectedTabIndex) {
                0 -> WeatherScreen(weatherViewModel, Modifier.padding(16.dp))
                1 -> NewsScreen(newsViewModel, Modifier.padding(16.dp))
                2 -> EventScreen(eventViewModel, Modifier.padding(16.dp))
            }
        }
    }
}


@Composable
fun WeatherScreen(viewModel: WeatherViewModel, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val weatherState by viewModel.weather.collectAsState()
    val errorState by viewModel.error.collectAsState()

    var locationDropdownExpanded by remember { mutableStateOf(false) }
    var countryDropdownExpanded by remember { mutableStateOf(false) }

    var selectedOption by remember { mutableStateOf("Use Current Location") }
    val options = listOf("Use Current Location", "Select Country")

    var selectedCountry by remember { mutableStateOf("") }
    val countryOptions = listOf("France", "Germany", "United States", "Canada")

    Column(modifier = modifier.padding(16.dp)) {
        Box(modifier = Modifier.fillMaxWidth()) {
            TextButton(onClick = { locationDropdownExpanded = !locationDropdownExpanded }) {
                Text(text = selectedOption)
            }
            DropdownMenu(expanded = locationDropdownExpanded, onDismissRequest = { locationDropdownExpanded = false }) {
                options.forEach { option ->
                    DropdownMenuItem(
                        onClick = {
                            selectedOption = option
                            locationDropdownExpanded = false
                            if (option == "Use Current Location") {
                                viewModel.fetchWeatherByLocation(context)
                            }
                        },
                        text = { Text(text = option) }
                    )
                }
            }
        }

        if (selectedOption == "Select Country") {
            Spacer(modifier = Modifier.height(8.dp))
            Box(modifier = Modifier.fillMaxWidth()) {
                TextButton(onClick = { countryDropdownExpanded = !countryDropdownExpanded }) {
                    Text(text = if (selectedCountry.isEmpty()) "Select Country" else selectedCountry)
                }
                DropdownMenu(expanded = countryDropdownExpanded, onDismissRequest = { countryDropdownExpanded = false }) {
                    countryOptions.forEach { country ->
                        DropdownMenuItem(
                            onClick = {
                                selectedCountry = country
                                countryDropdownExpanded = false
                                viewModel.fetchWeatherByCity(country)
                            },
                            text = { Text(text = country) }
                        )
                    }
                }
            }
        }

        errorState?.let { error ->
            Text(
                text = "Error: $error",
                modifier = modifier.padding(8.dp),
                color = MaterialTheme.colorScheme.error
            )
        }

        weatherState?.let { weather ->
            Column(modifier = modifier.padding(top = 16.dp)) {
                Text(text = "Weather in ${weather.name}", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Temperature: ${weather.main.temp}°C")
                Text(text = "Feels like: ${weather.main.feels_like}°C")
                Text(text = "Humidity: ${weather.main.humidity}%")
                Text(text = "Wind Speed: ${weather.wind.speed} m/s")

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    val shareText = """
                        Weather in ${weather.name}:
                        Temperature: ${weather.main.temp}°C
                        Feels like: ${weather.main.feels_like}°C
                        Humidity: ${weather.main.humidity}%
                        Wind Speed: ${weather.wind.speed} m/s
                    """.trimIndent()

                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, shareText)
                    }
                    context.startActivity(Intent.createChooser(intent, "Share Weather Info"))
                }) {
                    Text("Share Weather")
                }
            }
        }
    }
}




@Composable
fun NewsScreen(viewModel: NewsViewModel, modifier: Modifier = Modifier) {
    val newsState by viewModel.news.collectAsState()
    val errorState by viewModel.error.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Top Headlines") }
    val options = listOf("Top Headlines", "Latest News")

    val context = LocalContext.current

    Column(modifier = modifier) {
        Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            TextButton(onClick = { expanded = !expanded }) {
                Text(text = selectedOption)
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                options.forEach { option ->
                    DropdownMenuItem(
                        onClick = {
                            selectedOption = option
                            expanded = false
                            when (option) {
                                "Top Headlines" -> viewModel.fetchTopHeadlines()
                                "Latest News" -> viewModel.fetchNews("France")
                            }
                        },
                        text = { Text(text = option) }
                    )
                }
            }
        }

        errorState?.let { error ->
            Text(
                text = "Error: $error",
                modifier = modifier.padding(8.dp),
                color = MaterialTheme.colorScheme.error
            )
        }

        newsState?.let { news ->
            LazyColumn(modifier = modifier) {
                items(news.articles) { article ->
                    Column(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .clickable {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                                context.startActivity(Intent.createChooser(intent, "Open with"))
                            }
                    ) {
                        Text(text = article.title, style = MaterialTheme.typography.titleMedium)
                        Text(
                            text = "Author: ${article.author ?: "Unknown"}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        article.description?.let { Text(text = it, style = MaterialTheme.typography.bodySmall) }
                        Text(text = "Published at: ${article.publishedAt}", style = MaterialTheme.typography.bodySmall)
                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}

@Composable
fun EventScreen(viewModel: EventViewModel, modifier: Modifier = Modifier) {
    val eventState by viewModel.events.collectAsState()
    val errorState by viewModel.error.collectAsState()

    errorState?.let { error ->
        Text(text = "Error: $error", modifier = modifier.padding(8.dp), color = MaterialTheme.colorScheme.error)
    }

    eventState?.let { events ->
        LazyColumn(modifier = modifier) {
            items(events) { event ->
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    Text(text = event.name, style = MaterialTheme.typography.headlineMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Address: ${event.address?.streetAddress}, ${event.address?.addressLocality}")
                    Text(text = "Categories: ${event.categories?.joinToString(", ")}")
                    event.description?.let { Text(text = it) }
                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalDivider()
                }
            }
        }
    }
}

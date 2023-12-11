package com.example.flightsearch.ui.favorites

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearch.R
import com.example.flightsearch.model.Airport
import com.example.flightsearch.ui.theme.FlightSearchTheme

@Composable
fun FavoritesScreen() {
    val viewModel: FavoriteScreenViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    Column {
        SearchBar(
            searchText = uiState.searchText,
            onSearchBarClicked = { viewModel.onSearchBarClicked() },
            onSearchTextIntroduced = { viewModel.searchAirport(it) },
            onClearClicked = { viewModel.clearSearch() },
            isSearch = uiState.isSearch,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        AnimatedVisibility(visible = uiState.isSearch) {
            searchList(
                airports = uiState.airports,
                onAirportClicked = { viewModel.updateAirportDetails(it)})
        }
    }
}

@Composable
fun searchList(airports: List<Airport>, onAirportClicked: (Airport) -> Unit, modifier: Modifier = Modifier) {
    LazyColumn(modifier) {
        items(airports) { airport ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp).clickable { onAirportClicked(airport) },
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(text = airport.iataCode, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(
                    text = airport.name,
                    modifier = Modifier.weight(1f),
                    fontSize = 16.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
fun DetailsSection(modifier: Modifier = Modifier) {
    LazyColumn(modifier) {
        items(5) {
            DepartureCard()
        }
    }
}

@Composable
fun DepartureCard(modifier: Modifier = Modifier) {

    var favorite by remember { mutableStateOf(false) }

    Card(modifier.fillMaxWidth()) {
        Row(
            modifier = modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FlightInformation(
                departIata = "FCO",
                arriveIata = "SVO",
                departName = "Leonardo da Vinci International",
                arriveName = "Marco Antonio Solís Airport",
                modifier = Modifier.weight(1f)
            )

            IconButton(onClick = { favorite = !favorite }) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Favorite",
                    tint = if (favorite) Color(0xFFFFC107) else LocalContentColor.current
                )
            }
        }
    }
}

@Composable
fun FlightInformation(
    departIata: String,
    arriveIata: String,
    departName: String,
    arriveName: String,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        FlightInformationParts(label = departIata, airportName = departName)
        FlightInformationParts(label = arriveIata, airportName = arriveName)
    }
}

@Composable
fun FlightInformationParts(label: String, airportName: String, modifier: Modifier = Modifier) {
    Column(modifier) {
        Text(text = stringResource(R.string.depart), modifier = Modifier.padding(bottom = 4.dp))
        Row(
            modifier = Modifier.padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = label)
            Text(
                text = airportName,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    searchText: String,
    onSearchBarClicked: () -> Unit,
    onSearchTextIntroduced: (String) -> Unit,
    onClearClicked: () -> Unit,
    isSearch: Boolean,
    modifier: Modifier = Modifier,
) {

    val radius = if (!isSearch) 28.dp else 0.dp
    val padding = if (!isSearch) 8.dp else 0.dp
    val searchBarPadding by animateDpAsState(targetValue = padding, label = "Searchbar padding")
    val searchBarShape by animateDpAsState(targetValue = radius, label = "Searchbar shape")

    OutlinedTextField(
        value = searchText,
        onValueChange = { onSearchTextIntroduced(it) },
        modifier = modifier
            .clickable(onClick = onSearchBarClicked)
            .animateContentSize()
            .padding(searchBarPadding),
        enabled = isSearch,
        placeholder = {
            Text(text = stringResource(R.string.searchbar_placeholder))
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = stringResource(R.string.searchbar_placeholder)
            )
        },
        trailingIcon = {
            if (isSearch) {
                IconButton(onClick = onClearClicked, enabled = isSearch) {
                    Icon(imageVector = Icons.Filled.Clear, contentDescription = null)
                }
            }
        },
        shape = RoundedCornerShape(searchBarShape),
        colors = TextFieldDefaults.textFieldColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    )


}

@Preview(showSystemUi = false)
@Composable
fun DepartureCardPreview() {
    FlightSearchTheme {
        DepartureCard()
    }
}
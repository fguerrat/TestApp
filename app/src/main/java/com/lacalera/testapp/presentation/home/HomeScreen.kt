package com.lacalera.testapp.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.lacalera.testapp.domain.model.Character
import com.lacalera.testapp.R
import kotlin.collections.listOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeRoot(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.handleIntent(HomeIntent.LoadCharacters)
    }

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is HomeEffect.NavigateToCharacterDetail -> {}
            }
        }
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        modifier = modifier,
        uiState = uiState,
        onIntent = viewModel::handleIntent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeState,
    onIntent: (HomeIntent) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Rick and Morty Characters") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->

        val modifierContent = modifier
            .padding(paddingValues)

        when (uiState) {
            is HomeState.Error -> HomeScreenError(
                modifier = modifierContent,
                message = uiState.message
            ) { }

            is HomeState.Loading -> HomeScreenLoading(
                modifier = modifierContent,
            )

            is HomeState.Success -> HomeScreenSuccess(
                modifier = modifierContent,
                listCharacters = uiState.listCharacters,
                onIntent = onIntent
            )
        }

    }
}

@Composable
fun HomeScreenLoading(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun HomeScreenError(
    modifier: Modifier = Modifier,
    message: String,
    onIntent: (HomeIntent) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = message,
                color = Color.Red,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { onIntent(HomeIntent.LoadCharacters) }) {
                Text(stringResource(R.string.retry))
            }
        }
    }
}

@Composable
fun HomeScreenSuccess(
    modifier: Modifier = Modifier,
    listCharacters: List<Character>,
    onIntent: (HomeIntent) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(items = listCharacters, key = { it.id }) { character ->
            CharacterCard(
                character = character,
                onClick = { onIntent(HomeIntent.ClickCharacter(character.id)) }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


@Composable
fun CharacterCard(
    modifier: Modifier = Modifier,
    character: Character,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Character Image
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(character.image)
                        .crossfade(true)
                        .build()
                ),
                contentDescription = "${character.name}'s image",
                modifier = Modifier
                    .size(100.dp)
                    .padding(4.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Character Details
            Column {
                Text(
                    text = character.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text("Status: ${character.status}")
                Text("Species: ${character.species}")
                Text("Gender: ${character.gender}")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CharacterCardPreview() {
    CharacterCard(
        character = Character(
            id = 1,
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            url = "https://rickandmortyapi.com/api/character/1",
            created = "2017-11-04T18:48:46.250Z"
        )
    ) { }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenLoadingPreview() {
    HomeScreenLoading()
}

@Preview(showBackground = true)
@Composable
fun HomeScreenErrorPreview() {
    HomeScreenError(
        message = "An error occurred while fetching characters."
    ) { }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenSuccessPreview() {
    HomeScreenSuccess(
        listCharacters = listOf(
            Character(
                id = 1,
                name = "Rick Sanchez",
                status = "Alive",
                species = "Human",
                type = "",
                gender = "Male",
                image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                url = "https://rickandmortyapi.com/api/character/1",
                created = "2017-11-04T18:48:46.250Z"
            ),
            Character(
                id = 2,
                name = "Morty Smith",
                status = "Alive",
                species = "Human",
                type = "",
                gender = "Male",
                image = "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
                url = "https://rickandmortyapi.com/api/character/2",
                created = "2017-11-04T18:50:21.651Z"
            ),
            Character(
                id = 3,
                name = "Summer Smith",
                status = "Alive",
                species = "Human",
                type = "",
                gender = "Female",
                image = "https://rickandmortyapi.com/api/character/avatar/3.jpeg",
                url = "https://rickandmortyapi.com/api/character/3",
                created = "2017-11-04T19:09:56.428Z"
            )
        )
    ) { }
}
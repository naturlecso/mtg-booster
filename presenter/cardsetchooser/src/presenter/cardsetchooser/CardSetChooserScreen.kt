package presenter.cardsetchooser

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import theme.shapes.TypeLineShape

@Composable
fun CardSetChooserScreen(
    presenter: CardSetChooserPresenter
) {
    val viewState by presenter.viewState.subscribeAsState()

    Scaffold { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(viewState.cardSetList) { cardSet ->
                CardSetListItem(
                    title = cardSet.name,
                    iconUrl = cardSet.iconUri,
                    releaseDate = cardSet.releasedAt,
                    onClick = { presenter.onSelectCardSet(cardSet.code) }
                )
            }
        }
    }
}

@Composable
private fun CardSetListItem(
    title: String,
    iconUrl: String,
    releaseDate: String,
    onClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Text(
            text = title,
            maxLines = 1,
            style = MaterialTheme.typography.bodyLarge.copy(
                brush = copperBorderBrush
            ),
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )

        TypeLineBar(
            text = releaseDate.getYear(),
            modifier = Modifier
                .align(Alignment.CenterVertically)
        )

        AsyncImage(
            model = iconUrl,
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
        )
    }

    GradientDivider()
}

private fun String.getYear() = this.split('-')[0]

@Composable
private fun GradientDivider() = Box(
    modifier = Modifier
        .fillMaxWidth()
        .height(1.dp)
        .padding(horizontal = 16.dp)
        .background(brush = goldBorderBrush)
)

@Composable
private fun TypeLineBar(
    text: String,
    modifier: Modifier = Modifier
) = Box(
    modifier = modifier
        .background(
            brush = silverBackgroundBrush,
            shape = TypeLineShape
        )
        .border(
            width = 2.dp,
            brush = silverBorderBrush,
            shape = TypeLineShape
        )
        .padding(8.dp)
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier.align(Alignment.Center)
    )
}

private val goldBorderBrush = Brush.linearGradient(
    colors = listOf(
        Color(0xFFD4AF37), // gold top
        Color(0xFF8B7500)  // deep bronze bottom
    )
)

private val silverBorderBrush = Brush.linearGradient(
    colors = listOf(
        Color(0xFFE0E0E0), // light silver top
        Color(0xFF7D7D7D)  // deep metallic bottom
    )
)

private val copperBorderBrush = Brush.linearGradient(
    colors = listOf(
        Color(0xFFB87333), // warm copper top
        Color(0xFF5A2D0C)  // deep rustic bronze bottom
    )
)

private val silverBackgroundBrush = Brush.verticalGradient(
    colors = listOf(
        Color(0xFFF0F0F0), // very light silver
        Color(0xFFD6D6D6)  // slightly darker bottom
    )
)

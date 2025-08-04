package presenter.cardsetchooser

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState

@Composable
fun CardSetChooserScreen(
    presenter: CardSetChooserPresenter
) {
    val viewState by presenter.viewState.subscribeAsState()

    with(viewState) {
        Scaffold { paddingValues ->
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(viewState.cardSetList) { cardSet ->
                    Text(
                        text = cardSet.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                onClick = { presenter.onSelectCardSet(cardSet.code) }
                            )
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}

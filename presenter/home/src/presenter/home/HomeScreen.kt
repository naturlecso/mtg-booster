package presenter.home

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter.State.Loading
import coil3.compose.AsyncImagePainter.State.Success
import coil3.compose.rememberAsyncImagePainter
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.composables.core.Icon
import com.composeunstyled.Text
import com.naturlecso.mtgbooster.theme.Res
import com.naturlecso.mtgbooster.theme.home_edit_description
import com.naturlecso.mtgbooster.theme.home_generate_button
import com.naturlecso.mtgbooster.theme.home_refresh_description
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.defaultShimmerTheme
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import com.valentinilk.shimmer.shimmerSpec
import domain.model.SelectedCardSet
import org.jetbrains.compose.resources.stringResource
import theme.shapes.HomeTopShape
import kotlin.math.PI
import kotlin.math.atan2

@Composable
fun HomeScreen(
    presenter: HomePresenter
) {
    val viewState by presenter.viewState.subscribeAsState()

    with(viewState) {
        Scaffold {
            Box(modifier = Modifier.fillMaxSize()) {
                CardSetDisplay(
                    selectedCardSet = selectedCardSet,
                    loading = loading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.6f)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.4f)
                ) {
                    CardSetActions(
                        onChangeImage = presenter::onChangeSetImage,
                        onChooseSet = presenter::onChooseSet,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                    )
                }

                GenerateBoosterButton(
                    enabled = !loading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.3f)
                        .align(Alignment.BottomCenter)
                )
            }
        }
    }
}

@Composable
private fun CardSetDisplay(
    selectedCardSet: SelectedCardSet?,
    loading: Boolean,
    modifier: Modifier = Modifier
) {
    var boxSize by remember { mutableStateOf(IntSize.Zero) }

    val deltaY = boxSize.height * 0.3f
    val deltaX = boxSize.width.toFloat()

    val angleRadians = atan2(deltaY, deltaX)
    val angleDegrees = -(angleRadians * (180f / PI.toFloat()))

    if (selectedCardSet != null) {
        Box(
            modifier = modifier
                .onGloballyPositioned { coordinates ->
                    boxSize = coordinates.size
                }
                .shadow(
                    elevation = 8.dp,
                    shape = HomeTopShape(),
                    clip = false
                )
                .clip(HomeTopShape())
        ) {
            PersistentAsyncImage(
                model = selectedCardSet.imageUrl,
                loading = loading,
                modifier = Modifier.matchParentSize()
            )

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.Black.copy(alpha = 0.5f))
            )

            Text(
                text = selectedCardSet.name,
                color = MaterialTheme.colorScheme.inverseOnSurface,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(horizontal = 16.dp)
                    .graphicsLayer {
                        rotationZ = angleDegrees
                        transformOrigin = TransformOrigin(0f, 0.5f)
                    }
            )
        }
    }
}

@Composable
private fun CardSetActions(
    onChangeImage: () -> Unit,
    onChooseSet: () -> Unit,
    modifier: Modifier = Modifier
) = Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(16.dp)
) {
    IconButton(
        onClick = onChangeImage
    ) {
        Icon(
            imageVector = Icons.Filled.Refresh,
            contentDescription = stringResource(Res.string.home_refresh_description),
            tint = MaterialTheme.colorScheme.inverseOnSurface
        )
    }

    IconButton(
        onClick = onChooseSet
    ) {
        Icon(
            imageVector = Icons.Filled.Edit,
            contentDescription = stringResource(Res.string.home_edit_description),
            tint = MaterialTheme.colorScheme.inverseOnSurface
        )
    }
}

@Composable
private fun GenerateBoosterButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) = Text(
    text = stringResource(Res.string.home_generate_button),
    style = MaterialTheme.typography.displayLarge,
    textAlign = TextAlign.Center,
    modifier = modifier
)

@Composable
private fun CardSetDisplayLoadingOverlay(
    modifier: Modifier = Modifier,
) {
    val shimmerInstance = rememberShimmer(
        shimmerBounds = ShimmerBounds.View,
        theme = defaultShimmerTheme.copy(
            animationSpec = infiniteRepeatable(
                animation = shimmerSpec(
                    durationMillis = 400,
                    easing = LinearEasing,
                    delayMillis = 400
                ),
                repeatMode = RepeatMode.Restart
            )
        )
    )

    Box(
        modifier = modifier
            .clip(HomeTopShape())
            .shimmer(shimmerInstance)
            .background(Color.White)
    )
}

@Composable
fun PersistentAsyncImage(
    model: String?,
    loading: Boolean,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop
) {
    var lastSuccessfulModel by remember { mutableStateOf<String?>(null) }
    var isImageLoading by remember { mutableStateOf(false) }

    val painter = rememberAsyncImagePainter(
        model = model,
        onState = { state ->
            when (state) {
                is Success -> {
                    lastSuccessfulModel = model
                    isImageLoading = false
                }
                is Loading -> { isImageLoading = true }
                else -> { isImageLoading = false }
            }
        }
    )

    val displayPainter = if (lastSuccessfulModel != null && model != lastSuccessfulModel) {
        rememberAsyncImagePainter(lastSuccessfulModel)
    } else {
        painter
    }

    Image(
        painter = displayPainter,
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier
    )

    if (loading || isImageLoading) {
        CardSetDisplayLoadingOverlay(
            modifier = modifier
        )
    }
}

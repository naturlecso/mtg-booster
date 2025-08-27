package theme.shapes

import androidx.compose.foundation.shape.GenericShape
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class HomeTopShape : Shape {
    private val slopeConstant = .7f

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            moveTo(0f, 0f)
            lineTo(size.width, 0f)
            lineTo(size.width, size.height * slopeConstant)
            lineTo(0f, size.height)
            close()
        }
        return Outline.Generic(path)
    }
}

val TypeLineShape = GenericShape { size, _ ->
    val w = size.width
    val h = size.height
    val inset = h * 0.3f // how much the ends are indented

    moveTo(inset, 0f)
    lineTo(w - inset, 0f)
    quadraticTo(w, 0f, w, h / 2f) // right curve
    quadraticTo(w, h, w - inset, h) // right bottom inward
    lineTo(inset, h)
    quadraticTo(0f, h, 0f, h / 2f) // left curve
    quadraticTo(0f, 0f, inset, 0f) // left top inward
    close()
}

package com.zen.cendakala

import android.graphics.RectF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.vector.VectorProperty
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.PathParser

@Composable
fun GelombangLong() {

    Box(


        modifier = Modifier

            .width(1080.dp)
            .height(386.3838806152344.dp)
            .clip(RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 0.dp, bottomEnd = 0.dp))
            .background(Color.Transparent)

            .padding(start = 0.dp, top = 0.dp, end = 0.dp, bottom = 0.dp)

            .alpha(1f)


    ) {
        Canvas(
            modifier = Modifier
                .width(1080.dp)
                .height(964.dp)
                .fillMaxSize()
                .aspectRatio(1f)
                .align(Alignment.Center)
        ) {
            val fillPath = PathParser.createPathFromPathData("M 0 0 L 360 0 L 360 308.4210205078125 C 360 308.4210205078125 318 359.4736633300781 179.5 258.9473571777344 C 41 158.42105102539062 0 308.4210205078125 0 308.4210205078125 L 0 0 Z ")
            //fillPath.fillType = Path.FillType.EVEN_ODD
            val rectF = RectF()
            fillPath.computeBounds(rectF, true)
            val matrix = Matrix()
            val scale = minOf( size.width / rectF.width(), size.height / rectF.height() )
            matrix.scale(scale, scale)
            val composePathFill = fillPath.asComposePath()

            drawPath(path = composePathFill, color = Color(red = 0.49803921580314636f, green = 0.364705890417099f, blue = 0f, alpha = 1f), style = Fill)
        }

        Text(
            text = "SIGN IN",
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            textDecoration = TextDecoration.None,
            letterSpacing = 4.sp,

            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .align(Alignment.Center)
                .width(173.dp)

                //.height(33.07762908935547.dp)

                .alpha(1f),
            color = Color(red = 1f, green = 1f, blue = 1f, alpha = 1f),
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal,
        )
    }
}

@Preview
@Composable
fun GelombangLongPreview() {
    GelombangLong()
}
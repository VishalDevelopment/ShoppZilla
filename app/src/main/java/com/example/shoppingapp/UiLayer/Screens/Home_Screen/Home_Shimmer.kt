package com.example.shoppingapp.UiLayer.Screens.Home_Screen

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
@Preview(showBackground = true, heightDp = 500, widthDp = 300)
fun Home_Shimmer() {

    val color = Color.LightGray
    val shimmerColor = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f)
    )
    val transition = rememberInfiniteTransition()
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
        )
    )

    val brush = Brush.linearGradient(colors = shimmerColor, start = Offset.Zero, end = Offset(x=translateAnim.value, y = translateAnim.value))
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 6.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(.90f)
                        .height(60.dp)
                        .padding(vertical = 5.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(brush)
                )
                Spacer(modifier = Modifier.padding(horizontal = 3.dp))
                Box(
                    modifier = Modifier
                        .size(35.dp)
                        .clip(CircleShape)
                        .background(brush)
                )
            }
//            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            TextShimerEffect( brush)
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            CategoryShimmer( brush)
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            TextShimerEffect(brush)
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            CardShimmer( brush,250.dp,150.dp)
        }
    }
}

@Composable
fun CategoryShimmer(brush: Brush) {
    LazyRow {
        items(4) {
            Spacer(modifier = Modifier.padding(horizontal = 3.dp))
            Box(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(brush)
            )

        }
    }
}

@Composable
fun TextShimerEffect(brush: Brush) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Box(
            modifier = Modifier
                .width(100.dp)
                .height(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(brush)
        )
        Box(
            modifier = Modifier
                .width(100.dp)
                .height(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(brush)
        )
    }
}

@Composable
fun CardShimmer(brush: Brush,height: Dp ,width:Dp) {
    LazyRow {
        items(4){
            Column{
                Box(
                    modifier = Modifier
                        .height(height)
                        .width(width)
                        .padding(vertical = 15.dp, horizontal = 5.dp)
                        .clip(RoundedCornerShape(25.dp))
                        .background(brush)
            )
                Box(
                    modifier = Modifier
                        .height(250.dp)
                        .width(150.dp)
                        .padding(vertical = 1.dp, horizontal = 5.dp)
                        .clip(RoundedCornerShape(25.dp))
                        .background(brush)
                )
            }
        }
    }

}
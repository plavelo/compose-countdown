/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.plavelo.countdown.ui

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Preview
@Composable
fun Indicator() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
            .clip(CircleShape)
    ) {
        CircularProgressIndicator(
            strokeWidth = 48.dp,
        )
    }
}

// Each rotation is 1 and 1/3 seconds, but 1332ms divides more evenly
private const val RotationDuration = 3000

// How far the head and tail should jump forward during one rotation past the base point
private const val JumpRotationAngle = 290f

// The head animates for the first half of a rotation, then is static for the second half
// The tail is static for the first half and then animates for the second half
private const val HeadAndTailAnimationDuration = (RotationDuration * 0.5).toInt()
private const val HeadAndTailDelayDuration = HeadAndTailAnimationDuration

// The easing for the head and tail jump
private val CircularEasing = CubicBezierEasing(0.4f, 0f, 0.2f, 1f)

// CircularProgressIndicator Material specs
// Diameter of the indicator circle
private val IndicatorDiameter = 320.dp

@Composable
fun CircularProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.primary,
    strokeWidth: Dp = ProgressIndicatorDefaults.StrokeWidth
) {
    val stroke = with(LocalDensity.current) {
        Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Square)
    }
    val transition = rememberInfiniteTransition()
    val endAngle by transition.animateFloat(
        0f,
        JumpRotationAngle,
        infiniteRepeatable(
            animation = keyframes {
                durationMillis = HeadAndTailAnimationDuration + HeadAndTailDelayDuration
                0f at 0 with CircularEasing
                JumpRotationAngle at HeadAndTailAnimationDuration
            }
        )
    )
    Canvas(
        modifier
            .progressSemantics()
            .size(IndicatorDiameter)
            .focusable()
    ) {
        drawIndeterminateCircularIndicator(-90f, endAngle, color, stroke)
    }
}

private fun DrawScope.drawIndeterminateCircularIndicator(
    startAngle: Float,
    sweep: Float,
    color: Color,
    stroke: Stroke
) {
    val radius = size.width / 2
    val angleOffset = (180.0 / PI).toFloat() * (stroke.width / radius) / 2f
    val adjustedStartAngle = startAngle + angleOffset
    val endRadius = stroke.width / 2 * 0.95f
    val arcDimen = size.width - 2 * endRadius
    drawArc(
        color = color,
        startAngle = adjustedStartAngle,
        sweepAngle = sweep,
        useCenter = false,
        topLeft = Offset(endRadius, endRadius),
        size = Size(arcDimen, arcDimen),
        style = stroke
    )
    drawCircle(
        color = color,
        radius = endRadius,
        center = Offset(
            x = cos(PI / 180.0 * (adjustedStartAngle + sweep + angleOffset)).toFloat() * (radius - endRadius) + radius,
            y = sin(PI / 180.0 * (adjustedStartAngle + sweep + angleOffset)).toFloat() * (radius - endRadius) + radius
        )
    )
    drawCircle(
        color = color,
        radius = endRadius,
        center = Offset(
            x = cos(PI / 180.0 * startAngle).toFloat() * (radius - endRadius) + radius,
            y = sin(PI / 180.0 * startAngle).toFloat() * (radius - endRadius) + radius
        )
    )
}

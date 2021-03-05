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

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

enum class Motion {
    Stay,
    Jump,
    Vibe,
}

@Preview
@Composable
fun Cat(motion: Motion = Motion.Jump) {
    when (motion) {
        Motion.Stay -> {
            Text(
                "\uD83D\uDC31",
                style = MaterialTheme.typography.h1,
            )
        }
        Motion.Jump -> {
            val transition = rememberInfiniteTransition()
            val offset by transition.animateFloat(
                0f,
                1f,
                infiniteRepeatable(
                    animation = tween(500, easing = FastOutLinearInEasing),
                    repeatMode = RepeatMode.Reverse,
                )
            )
            Text(
                "\uD83D\uDC31",
                style = MaterialTheme.typography.h1,
                modifier = Modifier
                    .offset(y = 48.dp * offset)
                    .padding(bottom = 48.dp)
            )
        }
        Motion.Vibe -> {
            val transition = rememberInfiniteTransition()
            val offset by transition.animateFloat(
                -0.5f,
                0.5f,
                infiniteRepeatable(
                    animation = tween(100, easing = FastOutLinearInEasing),
                    repeatMode = RepeatMode.Reverse,
                )
            )
            Text(
                "\uD83D\uDC31",
                style = MaterialTheme.typography.h1,
                modifier = Modifier
                    .offset(x = 16.dp * offset)
            )
        }
    }
}

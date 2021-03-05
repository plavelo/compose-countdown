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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.plavelo.countdown.R

@Preview
@Composable
fun Control(
    modifier: Modifier = Modifier,
    onTapFab: () -> Unit = {},
    onReset: () -> Unit = {},
    isStarted: Boolean = false,
    resetButtonColors: ButtonColors = ButtonDefaults.textButtonColors(),
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(72.dp)) {
            TextButton(
                onClick = onReset,
                colors = resetButtonColors,
            ) {
                Text(stringResource(R.string.reset))
            }
        }
        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(72.dp)) {
            FloatingActionButton(
                onClick = onTapFab,
            ) {
                if (isStarted) {
                    Icon(
                        Icons.Default.Pause,
                        contentDescription = stringResource(R.string.icon_play)
                    )
                } else {
                    Icon(
                        Icons.Default.PlayArrow,
                        contentDescription = stringResource(R.string.icon_play)
                    )
                }
            }
        }
        Box(modifier = Modifier.size(72.dp))
    }
}

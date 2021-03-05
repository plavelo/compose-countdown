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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.plavelo.countdown.R

@Preview
@Composable
fun Keypad(
    enabled: Boolean = true,
    onTap: (Int) -> Unit = {},
    onBackspace: () -> Unit = {},
) {
    Column(
        modifier = Modifier.padding(horizontal = 32.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Key(1, enabled, onTap = onTap)
            Key(2, enabled, onTap = onTap)
            Key(3, enabled, onTap = onTap)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Key(4, enabled, onTap = onTap)
            Key(5, enabled, onTap = onTap)
            Key(6, enabled, onTap = onTap)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Key(7, enabled, onTap = onTap)
            Key(8, enabled, onTap = onTap)
            Key(9, enabled, onTap = onTap)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(96.dp))
            Key(0, enabled, onTap = onTap)
            Icon(
                Icons.Default.Backspace,
                contentDescription = stringResource(R.string.icon_backspace),
                modifier = Modifier
                    .width(96.dp)
                    .padding(vertical = 16.dp)
                    .clickable(
                        onClick = onBackspace,
                        enabled = enabled,
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = false, radius = 64.dp),
                    ),
            )
        }
    }
}

@Composable
private fun Key(
    number: Int,
    enabled: Boolean,
    modifier: Modifier = Modifier,
    onTap: (Int) -> Unit = {},
) {
    Box(
        modifier = modifier
            .width(80.dp)
            .clickable(
                onClick = { onTap(number) },
                enabled = enabled,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = false, radius = 64.dp),
            )
            .padding(vertical = 4.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            number.toString(),
            style = MaterialTheme.typography.h3,
        )
    }
}

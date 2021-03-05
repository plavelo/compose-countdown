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
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.plavelo.countdown.R

@Preview
@Composable
fun Keypad(onTap: (Int) -> Unit = {}, onBackspace: () -> Unit = {}) {
    Column(
        modifier = Modifier.padding(top = 32.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Text(
                "1",
                style = MaterialTheme.typography.h3,
                modifier = Modifier.clickable(onClick = { onTap(1) }),
            )
            Text(
                "2", style = MaterialTheme.typography.h3,
                modifier = Modifier.clickable(onClick = { onTap(2) }),
            )
            Text(
                "3", style = MaterialTheme.typography.h3,
                modifier = Modifier.clickable(onClick = { onTap(3) }),
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Text(
                "4", style = MaterialTheme.typography.h3,
                modifier = Modifier.clickable(onClick = { onTap(4) }),
            )
            Text(
                "5", style = MaterialTheme.typography.h3,
                modifier = Modifier.clickable(onClick = { onTap(5) }),
            )
            Text(
                "6", style = MaterialTheme.typography.h3,
                modifier = Modifier.clickable(onClick = { onTap(6) }),
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Text(
                "7", style = MaterialTheme.typography.h3,
                modifier = Modifier.clickable(onClick = { onTap(7) }),
            )
            Text(
                "8", style = MaterialTheme.typography.h3,
                modifier = Modifier.clickable(onClick = { onTap(8) }),
            )
            Text(
                "9", style = MaterialTheme.typography.h3,
                modifier = Modifier.clickable(onClick = { onTap(9) }),
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(32.dp))
            Text(
                "0", style = MaterialTheme.typography.h3,
                modifier = Modifier
                    .clickable(onClick = { onTap(0) })
                    .width(32.dp),
            )
            Icon(
                Icons.Default.Backspace,
                contentDescription = stringResource(R.string.icon_backspace),
                modifier = Modifier
                    .clickable(onClick = onBackspace)
                    .width(32.dp)
                    .padding(top = 8.dp),
            )
        }
    }
}

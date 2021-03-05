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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val TIMER_LABEL_HEIGHT = 144.dp

@Preview
@Composable
fun Display() {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .height(128.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        Cell("h")
        Cell("m")
        Cell("s")
    }
}

@Composable
fun Cell(unit: String) {
    Row(
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier.padding(horizontal = 8.dp),
    ) {
        Text("00", style = MaterialTheme.typography.h2, modifier = Modifier.alignByBaseline())
        Text(
            unit,
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .alignByBaseline()
        )
    }
}

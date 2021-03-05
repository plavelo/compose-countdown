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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropScaffoldDefaults
import androidx.compose.material.BackdropValue
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.plavelo.countdown.R

@ExperimentalMaterialApi
@Composable
fun MyApp() {
    Surface(color = MaterialTheme.colors.background) {
        val scope = rememberCoroutineScope()
        val scaffoldState = rememberBackdropScaffoldState(BackdropValue.Concealed)
        BackdropScaffold(
            scaffoldState = scaffoldState,
            peekHeight = BackdropScaffoldDefaults.PeekHeight + TIMER_LABEL_HEIGHT,
            appBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.app_name)) },
                    navigationIcon = {
                        if (scaffoldState.isConcealed) {
                            IconButton(onClick = { scope.launch { scaffoldState.reveal() } }) {
                                Icon(
                                    Icons.Default.Menu,
                                    contentDescription = "Localized description"
                                )
                            }
                        } else {
                            IconButton(onClick = { scope.launch { scaffoldState.conceal() } }) {
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = "Localized description"
                                )
                            }
                        }
                    },
                    actions = {
                        val message = stringResource(R.string.about)
                        IconButton(
                            onClick = {
                                scope.launch {
                                    scaffoldState.snackbarHostState
                                        .showSnackbar(message)
                                }
                            }
                        ) {
                            Icon(
                                Icons.Default.Info,
                                contentDescription = "Localized description"
                            )
                        }
                    },
                    elevation = 0.dp,
                    backgroundColor = Color.Transparent
                )
            },
            backLayerContent = {
                Column {
                    Display()
                    Divider()
                    Keypad()
                    Control(resetButtonColors = ButtonDefaults.buttonColors())
                }
            },
            frontLayerContent = {
                Column {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.padding(top = 72.dp, bottom = 16.dp)
                    ) {
                        Indicator()
                        Cat(motion = Motion.Jump)
                    }
                    Control()
                }
            }
        )
    }
}
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

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import org.plavelo.countdown.R

@ExperimentalMaterialApi
@Composable
fun MyApp() {
    Surface(color = MaterialTheme.colors.background) {
        val scope = rememberCoroutineScope()
        val scaffoldState = rememberBackdropScaffoldState(BackdropValue.Revealed)
        val viewModel: MyViewModel = viewModel()
        BackdropScaffold(
            scaffoldState = scaffoldState,
            peekHeight = BackdropScaffoldDefaults.PeekHeight + TIMER_LABEL_HEIGHT,
            appBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.app_name)) },
                    navigationIcon = {
                        Crossfade(targetState = scaffoldState.isConcealed) { isConcealed ->
                            if (isConcealed) {
                                IconButton(onClick = { scope.launch { scaffoldState.reveal() } }) {
                                    Icon(
                                        Icons.Default.Menu,
                                        contentDescription = stringResource(R.string.icon_menu),
                                    )
                                }
                            } else {
                                IconButton(onClick = { scope.launch { scaffoldState.conceal() } }) {
                                    Icon(
                                        Icons.Default.Close,
                                        contentDescription = stringResource(R.string.icon_close),
                                    )
                                }
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
                                contentDescription = stringResource(R.string.about),
                            )
                        }
                    },
                    elevation = 0.dp,
                    backgroundColor = Color.Transparent,
                )
            },
            backLayerContent = {
                val hours: Int by viewModel.hours.observeAsState(0)
                val minutes: Int by viewModel.minutes.observeAsState(0)
                val seconds: Int by viewModel.seconds.observeAsState(0)
                Column {
                    Display(
                        hours = hours,
                        minutes = minutes,
                        seconds = seconds,
                    )
                    Divider(modifier = Modifier.padding(horizontal = 16.dp))
                    Keypad(
                        enabled = viewModel.isKeypadEnabled(),
                        onTap = { number ->
                            viewModel.input(number)
                        },
                        onBackspace = {
                            viewModel.backspace()
                        },
                    )
                    Control(
                        modifier = Modifier.padding(top = 16.dp, bottom = 32.dp),
                        onTapFab = {
                            viewModel.toggle()
                            scope.launch { scaffoldState.conceal() }
                        },
                        onReset = {
                            viewModel.reset()
                        },
                        isStarted = viewModel.isStarted(),
                        resetButtonColors = ButtonDefaults.buttonColors(),
                    )
                }
            },
            frontLayerContent = {
                val state: State by viewModel.state.observeAsState(State.Stopped)
                val angle: Float by viewModel.angle.observeAsState(0f)
                Column {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.padding(top = 48.dp),
                    ) {
                        Indicator(angle = angle)
                        when (state) {
                            State.Started -> {
                                Cat(motion = Motion.Jump)
                            }
                            State.Stopped, State.Pause -> {
                                Cat(motion = Motion.Stay)
                            }
                            State.Finished -> {
                                Cat(motion = Motion.Vibe)
                                Text(
                                    stringResource(R.string.finished),
                                    style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold),
                                    modifier = Modifier.offset(y = 112.dp),
                                )
                            }
                        }
                    }
                    Control(
                        modifier = Modifier.padding(top = 56.dp),
                        onTapFab = {
                            viewModel.toggle()
                        },
                        onReset = {
                            viewModel.reset()
                            scope.launch { scaffoldState.reveal() }
                        },
                        isStarted = viewModel.isStarted(),
                    )
                }
            }
        )
    }
}

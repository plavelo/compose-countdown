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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.concurrent.timer
import kotlin.math.ceil

enum class State {
    Started,
    Stopped,
    Pause,
    Finished,
}

class MyViewModel : ViewModel() {
    private var _buffer = ArrayDeque(listOf(0, 0, 0, 0, 0, 0))

    private val _hours = MutableLiveData(0)
    val hours: LiveData<Int> = _hours

    private val _minutes = MutableLiveData(0)
    val minutes: LiveData<Int> = _minutes

    private val _seconds = MutableLiveData(0)
    val seconds: LiveData<Int> = _seconds

    private val _state = MutableLiveData(State.Stopped)
    val state: LiveData<State> = _state

    private val _angle = MutableLiveData(0f)
    val angle: LiveData<Float> = _angle

    private var _timer = timer(period = 10) {}

    private var _startTime: Long = 0
    private var _totalTime: Long = 0
    private var _elapsedTime: Long = 0

    fun input(number: Int) {
        _buffer.removeFirst()
        _buffer.addLast(number)
        applyTime()
        stopTimer()
    }

    fun backspace() {
        _buffer.removeLast()
        _buffer.addFirst(0)
        applyTime()
        stopTimer()
    }

    fun reset() {
        applyBuffer(0)
        applyAngle(0)
        applyTime()
        stopTimer()
    }

    fun toggle() {
        when (_state.value) {
            State.Started -> {
                pauseTimer()
                _timer.cancel()
            }
            State.Pause -> {
                if (_buffer.sum() != 0) {
                    restartTimer()
                    generateTimer()
                }
            }
            State.Stopped -> {
                if (_buffer.sum() != 0) {
                    startTimer()
                    generateTimer()
                }
            }
            State.Finished -> {
                stopTimer()
            }
        }
    }

    fun isKeypadEnabled(): Boolean {
        return _state.value != State.Started
    }

    fun isStarted(): Boolean {
        return _state.value == State.Started
    }

    private fun restartTimer() {
        _state.postValue(State.Started)
        _startTime = System.currentTimeMillis() - _elapsedTime
    }

    private fun startTimer() {
        _state.postValue(State.Started)
        _startTime = System.currentTimeMillis()
        _totalTime = calcBuffer()
        applyBuffer(_totalTime)
    }

    private fun stopTimer() {
        _totalTime = calcBuffer()
        _state.postValue(State.Stopped)
    }

    private fun pauseTimer() {
        _state.postValue(State.Pause)
    }

    private fun finishTimer() {
        _state.postValue(State.Finished)
    }

    private fun generateTimer() {
        _timer = timer(period = 10) {
            _elapsedTime = System.currentTimeMillis() - _startTime
            val remainingTime = _totalTime - _elapsedTime
            if (remainingTime > 0) {
                applyBuffer(remainingTime)
                applyTime()
                applyAngle(remainingTime)
            } else {
                _timer.cancel()
                applyBuffer(0)
                applyTime()
                applyAngle(0)
                finishTimer()
            }
        }
    }

    private fun applyBuffer(remainingTime: Long) {
        val h = (remainingTime / 1000 / 60 / 60).toInt()
        val m = ((remainingTime / 1000 / 60) % 60).toInt()
        val s = (ceil(remainingTime.toFloat() / 1000) % 60).toInt()
        _buffer = ArrayDeque(listOf(h / 10, h % 10, m / 10, m % 10, s / 10, s % 10))
    }

    private fun calcBuffer(): Long {
        val h = (_buffer[0] * 10 + _buffer[1]).toLong()
        val m = (_buffer[2] * 10 + _buffer[3]).toLong()
        val s = (_buffer[4] * 10 + _buffer[5]).toLong()
        return 1000 * 60 * 60 * h + 1000 * 60 * m + 1000 * s
    }

    private fun applyTime() {
        _hours.postValue(_buffer[0] * 10 + _buffer[1])
        _minutes.postValue(_buffer[2] * 10 + _buffer[3])
        _seconds.postValue(_buffer[4] * 10 + _buffer[5])
    }

    private fun applyAngle(remainingTime: Long) {
        _angle.postValue(360f * remainingTime.toFloat() / _totalTime.toFloat())
    }
}

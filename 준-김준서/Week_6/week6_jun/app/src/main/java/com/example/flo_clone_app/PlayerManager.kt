package com.example.flo_clone_app

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

// PlayerManager.kt
object PlayerManager {
    private var mp: MediaPlayer? = null
    private var progressJob: Job? = null

    data class State(val position: Int, val duration: Int, val isPlaying: Boolean)
    private val listeners = mutableSetOf<(State) -> Unit>()

    fun initAndPlay(ctx: Context, uri: Uri) {
        if (mp == null) {
            mp = MediaPlayer().apply {
                setDataSource(ctx, uri)
                setOnPreparedListener { start(); startTicker() }
                setOnCompletionListener { notifyAllState() }
                prepareAsync()
            }
        } else {
            mp?.reset()
            mp?.setDataSource(ctx, uri)
            mp?.setOnPreparedListener { mp?.start(); startTicker() }
            mp?.prepareAsync()
        }
    }

    fun playPause() {
        mp?.let {
            if (it.isPlaying) it.pause() else it.start()
            notifyAllState()
        }
    }

    fun seekTo(ms: Int) { mp?.seekTo(ms); notifyAllState() }

    fun attach(listener: (State) -> Unit) { listeners += listener; notifyTo(listener) }
    fun detach(listener: (State) -> Unit) { listeners -= listener }

    private fun startTicker() {
        progressJob?.cancel()
        progressJob = CoroutineScope(Dispatchers.Main).launch {
            while (isActive) { notifyAllState(); delay(500) }
        }
    }

    private fun notifyAllState() {
        val d = mp?.duration ?: 0
        val p = mp?.currentPosition ?: 0
        val s = mp?.isPlaying ?: false
        val state = State(p, d, s)
        listeners.forEach { it(state) }
    }

    private fun notifyTo(l: (State)->Unit) = l(
        State(mp?.currentPosition ?: 0, mp?.duration ?: 0, mp?.isPlaying ?: false)
    )
}
package com.github.zawadz88.exoplayeraudiosample.service

import android.content.Context
import android.content.Intent
import com.google.android.exoplayer2.util.Util
import timber.log.Timber

class AudioPlayerServiceConnector(private val context: Context) {

    private var serviceConnection: AudioPlayerServiceConnection? = null

    fun connect(updatePlaybackStateCallback: (playWhenReady: Boolean) -> Unit) {
        serviceConnection = AudioPlayerServiceConnection(updatePlaybackStateCallback)

        val intent = Intent(context, AudioPlayerService::class.java)
        Util.startForegroundService(context, intent)
        context.bindService(intent, serviceConnection!!, Context.BIND_AUTO_CREATE)
    }

    fun disconnect() {
        serviceConnection?.let {
            it.onUnbind()
            context.unbindService(it)
            return
        }

        Timber.w("Service was not connected when trying to disconnect!")
    }

    fun changePlayback(shouldPlay: Boolean) {
        val intent = Intent(context, AudioPlayerService::class.java).apply {
            putExtra(AudioPlayerService.INTENT_KEY_SHOULD_PLAY, shouldPlay)
        }
        context.startService(intent)
    }
}

package com.github.zawadz88.exoplayeraudiosample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.zawadz88.exoplayeraudiosample.service.AudioPlayerServiceConnector
import kotlinx.android.synthetic.main.activity_main.nextButton
import kotlinx.android.synthetic.main.activity_main.previousButton
import kotlinx.android.synthetic.main.activity_main.togglePlaybackButton
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val serviceConnector = AudioPlayerServiceConnector(this)

    private var currentlyPlaying: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startPlayback()
        initializeViews()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceConnector.disconnect()
    }

    private fun initializeViews() {
        togglePlaybackButton.setOnClickListener {
            serviceConnector.changePlayback(!currentlyPlaying)
        }
        nextButton.setOnClickListener { Timber.i("Next clicked") }
        previousButton.setOnClickListener { Timber.i("Previous clicked") }
    }

    private fun startPlayback() {
        serviceConnector.connect(::updatePlaybackState)
    }

    private fun updatePlaybackState(playWhenReady: Boolean) {
        currentlyPlaying = playWhenReady
        val imageResource = if (playWhenReady) R.drawable.exo_controls_pause else R.drawable.exo_controls_play
        togglePlaybackButton.setImageResource(imageResource)
    }
}

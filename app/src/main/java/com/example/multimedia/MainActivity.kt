package com.example.multimedia

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.SeekBar
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private  var mp: MediaPlayer? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val play = findViewById<Button>(R.id.play)
        val pausa = findViewById<Button>(R.id.pausa)
        val parar = findViewById<Button>(R.id.parar)
        val seekbar = findViewById<SeekBar>(R.id.seekBar)

        play.setOnClickListener {
            if (mp == null) {
                mp = MediaPlayer.create(this, R.raw.sound)
                Log.d("MainActivity", "ID: ${mp!!.audioSessionId}")

                initialLiseSeekbar()
            }
            mp?.start()
            Log.d("MainActivity", "Duración: ${mp!!.duration / 1000} seconds")
        }
        pausa.setOnClickListener {
            if(mp !== null)mp?.pause()
            Log.d("MainActivity", "Pausado: ${mp!!.currentPosition / 1000} seconds")
        }
        parar.setOnClickListener {
            if(mp!==null){
                mp?.stop()
                mp?.reset()
                mp?.release()
                mp = null
            }
        }
        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) mp?.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

    }

    private fun initialLiseSeekbar() {
        val seekbar = findViewById<SeekBar>(R.id.seekBar)
        seekbar.max=mp!!.duration

        val handler = Handler(Looper.getMainLooper()).postDelayed(object : Runnable{
            override fun run() {
                try {
                    seekbar.progress = mp!!.currentPosition
                    Handler(Looper.getMainLooper()).postDelayed(this, 1000)
                }catch (e:Exception){
                    seekbar.progress=0
                }
            }

        },0)
    }
}
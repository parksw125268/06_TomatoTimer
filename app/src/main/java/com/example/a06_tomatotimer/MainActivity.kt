package com.example.a06_tomatotimer

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.SeekBar
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private val remainMinutesTextView : TextView by lazy{
        findViewById<TextView>(R.id.remainMinuteTextView)
    }
    private val remainSecondsTextView : TextView by lazy{
        findViewById<TextView>(R.id.remainSecondsTextView)
    }
    private val seekBar : SeekBar by lazy {
        findViewById<SeekBar>(R.id.seekBar)
    }
    private var currenCountDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindView()
    }

    private fun bindView() {
        seekBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if(fromUser){
                    updateRemainTimes(progress*60*1000L)}
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    currenCountDownTimer?.cancel()
                    currenCountDownTimer = null
                }

                override fun onStopTrackingTouch(seekBar:  SeekBar?){
                    seekBar ?:return
                    currenCountDownTimer = createCountDownTimer(seekBar.progress *60 *1000L)
                    currenCountDownTimer?.start()
                }
            }
        )
    }
    private fun createCountDownTimer(initialMillis:Long) =
        object :CountDownTimer(initialMillis,1000L)  {
            override fun onFinish() {
                updateRemainTimes(0)
                updateSeekBar(0)
            }

            override fun onTick(millisUntilFinished: Long) {
                updateRemainTimes(millisUntilFinished)
                updateSeekBar(millisUntilFinished)
            }
        }
    @SuppressLint("SetTextI18n")
    private fun updateRemainTimes(remainMillis: Long){
        val remainSeconds = remainMillis/1000
        remainMinutesTextView.text = "%02d".format(remainSeconds/60)
        remainSecondsTextView.text = "%02d".format(remainSeconds%60)
    }
    private fun updateSeekBar(remainMillis: Long){
        seekBar.progress = (remainMillis/1000/60).toInt()

    }

}
package io.github.karadkar.sample.threading

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import io.github.karadkar.sample.R
import io.github.karadkar.sample.databinding.ActivityThreadingBinding
import io.github.karadkar.sample.log

class ThreadingActivity : AppCompatActivity() {

    lateinit var binding: ActivityThreadingBinding
    private var shouldStop = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_threading)
        binding.buttonStart.setOnClickListener(this::onClickStart)
        binding.buttonStop.setOnClickListener(this::onClickStop)

    }

    private fun onClickStart(view: View?) {
        shouldStop = false

        Thread(
            ExampleRunnable(uiTask = this::uiTask)
        ).start()

    }

    private fun uiTask(message: String) {
        binding.tvResult.text = message
    }


    private fun onClickStop(view: View?) {
        shouldStop = true
    }

    inner class ExampleRunnable(val uiTask: (message: String) -> Unit) : Runnable {
        override fun run() {
            for (i in 0..9) {
                if (shouldStop) return
                val message = "${Thread.currentThread().id} sleeping $i"

                log(message = message)

                Handler(Looper.getMainLooper()).post {
                    uiTask.invoke(message)
                }

                Thread.sleep((0..1000L).random())
            }
        }
    }
}

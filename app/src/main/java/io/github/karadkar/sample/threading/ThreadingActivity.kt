package io.github.karadkar.sample.threading

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import io.github.karadkar.sample.R
import io.github.karadkar.sample.databinding.ActivityThreadingBinding
import io.github.karadkar.sample.logMessage

class ThreadingActivity : AppCompatActivity() {

    lateinit var binding: ActivityThreadingBinding
    private var shouldStop = false
    private lateinit var looperThread: ExampleLooperThread
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_threading)
        binding.buttonStart.setOnClickListener(this::onClickStart)
        binding.buttonStop.setOnClickListener(this::onClickStop)
        binding.buttonAddTask.setOnClickListener(this::onClickAddTask)

    }

    private fun onClickStart(view: View?) {
        looperThread = ExampleLooperThread()
        looperThread.start()
    }

    private fun uiTask(message: String) {
        binding.tvResult.text = message
    }


    private fun onClickStop(view: View?) {
        looperThread.stopLoop()
    }

    private var taskNo = 0
    private fun onClickAddTask(view: View?) {
        val message = Message.obtain()
        message.what = taskNo++
        looperThread.sendMessage(message)
    }

    inner class ExampleRunnable(val uiTask: (message: String) -> Unit) : Runnable {
        override fun run() {
            for (i in 0..9) {
                if (shouldStop) return
                val message = "${Thread.currentThread().id} sleeping $i"

                logMessage(message = message)

                Handler(Looper.getMainLooper()).post {
                    uiTask.invoke(message)
                }

                Thread.sleep((0..1000L).random())
            }
        }
    }
}

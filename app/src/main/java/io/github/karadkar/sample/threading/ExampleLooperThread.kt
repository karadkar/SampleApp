package io.github.karadkar.sample.threading

import android.os.Handler
import android.os.Looper
import io.github.karadkar.sample.logMessage

class ExampleLooperThread : Thread() {
    private lateinit var looper: Looper
    private lateinit var handler: Handler

    override fun run() {
        super.run()

        logMessage("start of thread ${this.id}")

        // initialise this thread as looper, which gives us chance to create handlers before loop
        Looper.prepare()

        // returns looper associated with current thread
        looper = Looper.myLooper()!!

        // this handles aur messages (tasks)
        handler = ExampleHandler()

        // prevents thread from terminating
        Looper.loop()

        logMessage("end of thread ${this.id}")
    }

    fun stopLoop() {
        looper.quit()
    }

    fun sendMessage(msg: android.os.Message) {
        handler.sendMessage(msg)
    }
}
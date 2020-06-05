package io.github.karadkar.sample.threading

import android.os.Handler
import android.os.Message
import io.github.karadkar.sample.logMessage

class ExampleHandler(uiTask: (message: String) -> Unit = {}) : Handler() {
    override fun handleMessage(msg: Message) {
        val message = "Task ${msg.what} executed"
        logMessage(message = message)
    }
}
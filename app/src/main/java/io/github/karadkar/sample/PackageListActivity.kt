package io.github.karadkar.sample

import android.app.Activity
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.net.Uri
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.widget.NestedScrollView

class PackageListActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val scroll = NestedScrollView(this)

        val container = LinearLayout(this).also { it.orientation = LinearLayout.VERTICAL }
        scroll.addView(container)

        val packages: MutableList<ApplicationInfo> = packageManager.getInstalledApplications(0)
        packages.filter { true }
            .forEach { info ->
                container.addView(TextView(this).apply {
                    setCompoundDrawablesWithIntrinsicBounds(info.loadIcon(packageManager), null, null, null)
                    text = info.loadLabel(packageManager)

                    setOnClickListener {
                        openApp(info.packageName)
                    }

                    setOnLongClickListener {
                        // launch app detail settings
                        openAppDetailSetting(info.packageName)
                        return@setOnLongClickListener true
                    }
                })
            }

        setContentView(scroll)
    }

    private fun openApp(packageName: String) {
        // launch app
        try {
            startActivity(packageManager.getLaunchIntentForPackage(packageName))
        } catch (e: Throwable) {
            e.message?.makeToast(this)
            e.printStackTrace()
        }
    }

    private fun openAppDetailSetting(packageName: String) {
        try {
            startActivity(
                Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS).also {
                    it.data = Uri.parse("package:${packageName}")
                }
            )
        } catch (t: Throwable) {
            t.message?.makeToast(this)
            t.printStackTrace()
        }
    }

    private fun isUserApp(flags: Int): Boolean {
        return flags and ApplicationInfo.FLAG_SYSTEM == 0
    }
}
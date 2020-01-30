package io.github.karadkar.sample

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.net.Uri
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import io.github.karadkar.sample.databinding.PackgeListingActivityBinding

class PackageListActivity : FragmentActivity() {
    lateinit var binding: PackgeListingActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.packge_listing_activity)

        binding.rvPackageListing.layoutManager = GridLayoutManager(this, 5, GridLayoutManager.VERTICAL, false)


        val data: MutableList<ApplicationInfo> = packageManager.getInstalledApplications(0)
        val listItems = data.filter { true }
            .mapTo(mutableListOf(), { ai ->
                return@mapTo PackageInfo(
                    label = ai.loadLabel(packageManager).toString(),
                    packageName = ai.packageName,
                    iconDrawable = ai.loadIcon(packageManager)
                )
            })

        binding.rvPackageListing.adapter = PackageListAdapter(this, listItems, onClickItem = {
            openAppDetailSetting(it.packageName)
        })
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
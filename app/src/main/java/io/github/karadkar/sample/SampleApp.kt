package io.github.karadkar.sample

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class SampleApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initRealm()
    }

    private fun initRealm() {
        Realm.init(this)
        val realmConfig = RealmConfiguration.Builder()
            .schemaVersion(BuildConfig.VERSION_CODE.toLong())
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(realmConfig)
    }
}
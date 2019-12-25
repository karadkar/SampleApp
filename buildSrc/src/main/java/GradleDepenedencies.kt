/**
 * Kotlin gradle dependecy management
 * check https://handstandsam.com/2018/02/11/kotlin-buildsrc-for-better-gradle-dependency-management/
 */
object Versions {
    const val gradlePlugin = "3.1.4"
    const val archExtensions = "2.1.0"
    const val roomDb = "2.2.3"
}

object Libraries {
    const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:2.1.0" // live-data view-model
    const val lifecycleReactiveStreamExtension = "android.arch.lifecycle:reactivestreams:1.1.1" // rx to live-data

    const val roomDbRuntime = "androidx.room:room-runtime:${Versions.roomDb}"
    const val roomDbCompiler = "androidx.room:room-compiler:${Versions.roomDb}"

    const val kotlinStdlibJdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7"
    const val appcompat = "androidx.appcompat:appcompat:1.1.0"
    const val coreKtx = "androidx.core:core-ktx:1.1.0"

    const val recyclerView = "androidx.recyclerview:recyclerview:1.0.0"
    const val materialComponents = "com.google.android.material:material:1.0.0"
    const val swipeRefreashLayout = "androidx.swiperefreshlayout:swiperefreshlayout:1.0.0"
    const val picasso = "com.squareup.picasso:picasso:2.71828"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:1.1.3"

    const val rxJava2 = "io.reactivex.rxjava2:rxjava:2.2.10"
    const val rxAndroid2 = "io.reactivex.rxjava2:rxandroid:2.1.1"

    const val retrofit = "com.squareup.retrofit2:retrofit:2.6.0"
    const val retrofitJacksonConverter = "com.squareup.retrofit2:converter-jackson:2.6.0"
    const val retrofitRxJava2Adapter = "com.squareup.retrofit2:adapter-rxjava2:2.5.0"

    const val kaptRealmFieldHelper = "dk.ilios:realmfieldnameshelper:1.1.1"
    const val kaptDatabindingCompiler = "com.android.databinding:compiler"

    const val junit = "junit:junit:4.12"
    const val testRunner = "androidx.test:runner:1.2.0"
    const val espressoCore = "androidx.test.espresso:espresso-core:3.2.0"
}
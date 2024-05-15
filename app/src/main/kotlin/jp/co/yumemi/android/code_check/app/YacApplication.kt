package jp.co.yumemi.android.code_check.app

import android.app.Application
import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.request.crossfade
import jp.co.yumemi.android.code_check.app.di.applyModules
import kotlinx.coroutines.IO_PARALLELISM_PROPERTY_NAME
import okio.Path.Companion.toOkioPath
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class YacApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@YacApplication)
            androidLogger()
            applyModules()
        }

        System.setProperty(IO_PARALLELISM_PROPERTY_NAME, "10")

        setupAdMob()
        setupCoil()
    }

    private fun setupAdMob() {
        // MobileAds.initialize(this)
        /*MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder()
                .setTestDeviceIds(listOf("5BF0B07F227A5817A04A51CEED4B4608"))
                .build()
        )*/
    }

    private fun setupCoil() {
        SingletonImageLoader.setSafe {
            ImageLoader.Builder(it)
                .memoryCache {
                    MemoryCache.Builder()
                        .maxSizePercent(it, 0.25)
                        .build()
                }
                .diskCache {
                    DiskCache.Builder()
                        .directory(this.cacheDir.resolve("image_cache").toOkioPath())
                        .maxSizePercent(0.02)
                        .build()
                }
                .crossfade(true)
                .build()
        }
    }
}

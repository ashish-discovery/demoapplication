package ashish.be.gupta.firstapplication

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            androidLogger()
            modules(
                listOf(
                    networkModule,
                    loginLogoutModule,
                    customerModule,
                    buildingModule,
                    roomModule
                )
            )
        }
    }
}
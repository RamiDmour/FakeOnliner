package com.example.fakeonliner

import android.app.Application
import com.example.fakeonliner.modules.appModule
import com.example.fakeonliner.modules.categoryModule
import com.example.fakeonliner.modules.productModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        GlobalContext.startKoin {
            androidLogger()
            androidContext(
                this@MainApplication
            )
            modules(appModule, categoryModule, productModule)
        }
    }

}
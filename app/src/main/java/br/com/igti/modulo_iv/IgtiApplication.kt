package br.com.igti.modulo_iv

import android.app.Application
import br.com.igti.modulo_iv.di.apiModule
import br.com.igti.modulo_iv.di.networkModule
import br.com.igti.modulo_iv.di.repositoryModule
import br.com.igti.modulo_iv.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class IgtiApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin {
            androidLogger()
            androidContext(this@IgtiApplication)
            modules(
                modules = listOf(
                    networkModule,
                    apiModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }
    }
}

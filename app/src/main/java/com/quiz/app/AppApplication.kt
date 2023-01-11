package com.quiz.app

import android.app.Application
import com.quiz.app.repository.HomeRepository
import com.quiz.app.ui.home.viewModel.UserVMF
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class AppApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@AppApplication))

        bind() from singleton { HomeRepository() }
        bind() from provider { UserVMF(instance()) }

    }


}
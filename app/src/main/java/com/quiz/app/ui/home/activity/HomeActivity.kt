package com.quiz.app.ui.home.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.quiz.app.AppPreferences
import com.quiz.app.R
import com.quiz.app.databinding.ActivityHomeBinding
import com.quiz.app.ui.home.viewModel.UserVM
import com.quiz.app.ui.home.viewModel.UserVMF
import com.quiz.app.utils.log
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class HomeActivity : AppCompatActivity(), KodeinAware {

    override val kodein: Kodein by kodein { applicationContext }

    private val factory: UserVMF by instance()

    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: UserVM

    private lateinit var appPreferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, factory)[UserVM::class.java]

        appPreferences = AppPreferences(this)

        log(appPreferences.getId().toString())
        log(appPreferences.getName().toString())

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        setSupportActionBar(binding.userToolBar)
        setUpActionBarTitle()

    }

    private fun setUpActionBarTitle() {
        viewModel.fragmentTitle.observe(this) {
            when (it) {
                "Home" -> {
                    binding.userToolBar.title = it
                }
                else -> {
                    binding.userToolBar.title = it
                }
            }
        }
    }

}
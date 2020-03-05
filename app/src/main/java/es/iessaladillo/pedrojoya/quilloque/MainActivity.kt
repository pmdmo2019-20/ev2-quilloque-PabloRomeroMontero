package es.iessaladillo.pedrojoya.quilloque

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {


    val appBarConfiguration = AppBarConfiguration(
        setOf(R.id.dialFragmentDestination, R.id.recentFragmentDestination, R.id.contactFragment2Destination))

    private val navController: NavController by lazy {
        findNavController(R.id.navHostFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setupViews()
    }

    private fun setupViews() {
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration)
    }
}


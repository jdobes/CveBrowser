package cz.utb.jdobes.cvebrowser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cz.utb.jdobes.cvebrowser.R

class MainActivity : AppCompatActivity() {

    var currentCveDetail = "CVE-XXXX-XXXX"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        supportActionBar?.title = getString(R.string.app_name)
        return true
    }
}
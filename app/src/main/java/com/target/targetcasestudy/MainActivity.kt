package com.target.targetcasestudy

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.target.targetcasestudy.ui.payment.PaymentDialogFragment

class MainActivity : AppCompatActivity()  {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.credit_card -> {
        PaymentDialogFragment().show(supportFragmentManager, "CreditCardValidation")
        true
      }
      else -> false
    }
  }

  override fun onSupportNavigateUp(): Boolean = Navigation.findNavController(this, R.id.nav_host).navigateUp()


}

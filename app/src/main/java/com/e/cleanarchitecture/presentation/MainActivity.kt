package com.e.cleanarchitecture.presentation

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.e.cleanarchitecture.R
import com.e.cleanarchitecture.data.Shipment
import com.e.cleanarchitecture.databinding.ActivityMainBinding
import com.e.cleanarchitecture.domain.DeliveryHub

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var counter = 1

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(toolbar)
        binding.tvStatus.text = "Click on Floating bar to send shipment\n"
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            val shipment =
                Shipment(counter++, "User A", "User B", "ABCD", (1..12).shuffled().first())
            binding.tvStatus.text = StringBuffer(binding.tvStatus.text.toString()).append(
                String.format(
                    getString(R.string.text_status_sent_format),
                    shipment.id,
                    shipment.sender,
                    shipment.receiver
                )
            )
            DeliveryHub().connectShipment(shipment,
                object : DeliveryHub.Callback {
                    override fun success(shipment: Shipment) {
                        binding.tvStatus.text =
                            StringBuffer(binding.tvStatus.text.toString()).append(
                                String.format(
                                    getString(R.string.text_status_received_format),
                                    shipment.id,
                                    shipment.sender,
                                    shipment.receiver
                                )
                            )
                    }

                    override fun error(shipment: Shipment, throwable: Throwable) {
                        binding.tvStatus.text =
                            StringBuffer(binding.tvStatus.text.toString()).append(
                                String.format(
                                    getString(R.string.text_status_error_format),
                                    shipment.id,
                                    shipment.sender,
                                    shipment.receiver
                                )
                            )
                    }
                })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}

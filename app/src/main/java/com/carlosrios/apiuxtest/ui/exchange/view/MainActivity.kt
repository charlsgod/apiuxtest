package com.carlosrios.apiuxtest.ui.exchange.view

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import com.carlosrios.apiuxtest.Constants
import com.carlosrios.apiuxtest.R
import com.carlosrios.apiuxtest.api.ApiServiceInterface
import com.carlosrios.apiuxtest.models.Rates
import com.carlosrios.apiuxtest.ui.exchange.ExchangeContract
import com.carlosrios.apiuxtest.ui.exchange.presenter.ExchangePresenter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import com.carlosrios.apiuxtest.api.RestApiManager as RestApiManager

class MainActivity : AppCompatActivity(), ExchangeContract.View, SensorEventListener {

    companion object {

        const val X = 0

    }

    lateinit var presenter: ExchangeContract.Presenter

    private lateinit var mAccelerometer: Sensor
    private lateinit var mSensorManager: SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val apiServiceInterface: ApiServiceInterface = provideApiService()
        val restApiManager = RestApiManager(apiServiceInterface)
        presenter = ExchangePresenter(restApiManager)
        presenter.attach(this)

        rg.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId != -1) {
                val radio: RadioButton = findViewById(checkedId)
                presenter.loadData(radio.text.toString())
            }
        }

        rb_eur.isChecked = true

        this.mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            this.mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) as Sensor
        } else {
            showErrorMessage("Disculpe, su dispositivo no cuenta con acelerómetro")
    }

    }

    override fun onResume() {
        super.onResume()
        if(this.mAccelerometer !=null) {
            mSensorManager.registerListener(
                this,
                this.mAccelerometer,
                SensorManager.SENSOR_DELAY_FASTEST
            )
        }
        val base = rg.findViewById<RadioButton>(rg.checkedRadioButtonId).text.toString()
        presenter.loadData(base)
    }

    override fun onPause() {
        super.onPause()
        if(this.mAccelerometer !=null) {
            mSensorManager.unregisterListener(this)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent) {
        val curX = event.values[X]

        if (curX < -2) {
            img_arrow.setImageResource(R.drawable.ic_baseline_arrow_forward_24)
        } else if (curX > 2) {
            img_arrow.setImageResource(R.drawable.ic_baseline_arrow_back_24)
        }
    }

    override fun showProgress(show: Boolean) {
        if (show)
            progress_bar.visibility = View.VISIBLE
        else
            progress_bar.visibility = View.GONE
    }

    override fun showErrorMessage(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    override fun deliverData(rates: Rates) {
        item_cad.findViewById<ImageView>(R.id.img_country_flag).setImageResource(R.drawable.flag_ca)
        item_cad.findViewById<TextView>(R.id.tv_country_exchange).text = rates.cAD.toString()

        item_gbp.findViewById<ImageView>(R.id.img_country_flag).setImageResource(R.drawable.flag_gb)
        item_gbp.findViewById<TextView>(R.id.tv_country_exchange).text = rates.gBP.toString()

        item_mxn.findViewById<ImageView>(R.id.img_country_flag).setImageResource(R.drawable.flag_mx)
        item_mxn.findViewById<TextView>(R.id.tv_country_exchange).text = rates.mXN.toString()
    }

    private fun provideApiService(): ApiServiceInterface {
        val retrofit = retrofit2.Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()

        return retrofit.create(ApiServiceInterface::class.java)
    }
}
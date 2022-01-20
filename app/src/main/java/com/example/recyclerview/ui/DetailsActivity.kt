package com.example.recyclerview.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.recyclerview.R
import com.example.recyclerview.databinding.ActivityDetailsBinding
import com.example.recyclerview.domain.Earthquake
import java.text.SimpleDateFormat
import java.util.*

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle = intent.extras!!
        val earthquake=bundle.getParcelable<Earthquake>(EQ_KEY)!!
        binding.tvMagnitude.text=this.getString(R.string.magnitude_format,earthquake.magnitude)
        binding.tvLongVal.text=earthquake.longitude.toString()
        binding.tvLatVal.text=earthquake.latitude.toString()
        binding.tvPlace.text=earthquake.place
        binding.tvDate.text=formatDateToString(earthquake.time)
    }

    private fun formatDateToString(time:Long):String {
        val simpleDateFormat = SimpleDateFormat("dd/MMM/yyyy H:mm:ss", Locale.forLanguageTag("es_ES"))
        val date = Date(time)
        return simpleDateFormat.format(date)
    }

    companion object{
        const val EQ_KEY="earthquake"
    }
}
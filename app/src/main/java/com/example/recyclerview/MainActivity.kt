package com.example.recyclerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.BoringLayout
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerview.databinding.ActivityMainBinding
import com.example.recyclerview.domain.ApiResponseStatus
import com.example.recyclerview.domain.Earthquake
import com.example.recyclerview.ui.DetailsActivity
import com.example.recyclerview.ui.EqAdapter
import com.example.recyclerview.viewmodel.MainViewModel
import com.example.recyclerview.viewmodel.MainViewModelFactory

private const val SORT_TYPE_KEY = "sort_type"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private val adapter = EqAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainViewModel = ViewModelProvider(
            this, MainViewModelFactory(application, getPreferences())
        )[MainViewModel::class.java]
        //ORDER IS IMPORTANT
        initRecyclerView()
        initObservers()
        initListeners()

        mainViewModel.eqList.observe(this, Observer {
            adapter.submitList(it)
            handleEmptyView(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val sortType = item.itemId == R.id.i_menu_magnitude
        mainViewModel.getDataFromDB(sortType)
        savePreferences(sortType)

        return super.onOptionsItemSelected(item)
    }

    private fun initObservers() {
        mainViewModel.eqList.observe(this, Observer {
            adapter.submitList(it)
            handleEmptyView(it)
        })
        mainViewModel.status.observe(this, {
            when (it) {
                ApiResponseStatus.LOADING ->
                    binding.pbLoading.visibility = View.VISIBLE
                ApiResponseStatus.DONE, ApiResponseStatus.NOT_INTERNET_CONNECTION ->
                    binding.pbLoading.visibility = View.GONE
            }
        })
    }

    private fun initListeners() {
        //Inicializamos o damos la funcionalidad a onItemClicListener
        adapter.onItemClickListener = {
            launchDetailActivity(it)
        }

    }

    private fun initRecyclerView() {
        binding.rvEq.layoutManager = LinearLayoutManager(this)
        binding.rvEq.adapter = adapter
    }

    private fun handleEmptyView(list: MutableList<Earthquake>) {
        if (list.isEmpty()) {
            binding.tvEmptyEq.visibility = View.VISIBLE
        } else {
            binding.tvEmptyEq.visibility = View.GONE
        }
    }

    private fun savePreferences(sortByMagnitude: Boolean) {
        //Mode private no comparte con otras aplicaciones
        // getSharedPreferences es mejor usar cuando se va a usar en varios lugares en la aplicacion
        //val prefs=getSharedPreferences("eq_prefs", MODE_PRIVATE)
        val prefs = getPreferences(MODE_PRIVATE) //Para usar solo en el activity
        val editor = prefs.edit()
        editor.putBoolean(SORT_TYPE_KEY, sortByMagnitude)
        editor.apply()
    }

    private fun getPreferences(): Boolean {
        val prefs = getPreferences(MODE_PRIVATE)
        return prefs.getBoolean(SORT_TYPE_KEY, false)
    }

    private fun launchDetailActivity(eq: Earthquake) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(DetailsActivity.EQ_KEY, eq)
        startActivity(intent)
    }
}
package com.example.recyclerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerview.databinding.ActivityMainBinding
import com.example.recyclerview.domain.ApiResponseStatus
import com.example.recyclerview.domain.Earthquake
import com.example.recyclerview.ui.DetailsActivity
import com.example.recyclerview.ui.EqAdapter
import com.example.recyclerview.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private val adapter = EqAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id=item.itemId
        if (id==R.id.i_menu_magnitude){
            mainViewModel.getDataFromDB(true)
        } else{
            mainViewModel.getDataFromDB(false)
        }
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

    private fun launchDetailActivity(eq:Earthquake){
        val intent= Intent(this,DetailsActivity::class.java)
        intent.putExtra(DetailsActivity.EQ_KEY,eq)
        startActivity(intent)
    }
}
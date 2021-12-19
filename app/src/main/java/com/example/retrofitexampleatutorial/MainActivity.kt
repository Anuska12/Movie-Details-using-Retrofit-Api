package com.example.retrofitexampleatutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val progress_bar : ProgressBar = findViewById(R.id.progress_bar)
        val request = ServiceBuilder.buildService(TmdbEndpoints::class.java)
        val call = request.getMovies("d35f77f7b22d8bf3f18c087841eb1a34")
        val recyclerView : RecyclerView = findViewById(R.id.recyclerView)
        call.enqueue(object : Callback<TmdbData> {
            override fun onResponse(call: Call<TmdbData>, response: Response<TmdbData>) {
                if(response.isSuccessful){
                    progress_bar?.visibility= View.GONE
                    recyclerView.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        adapter = MoviesAdapter(response.body()!!.results)
                    }
                }
            }

            override fun onFailure(call: Call<TmdbData>, t: Throwable) {
           Toast.makeText(this@MainActivity,"${t.message}",Toast.LENGTH_SHORT).show()
            }

        }
        )
    }
}
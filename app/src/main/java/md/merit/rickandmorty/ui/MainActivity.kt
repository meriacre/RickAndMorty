package md.merit.rickandmorty.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.google.gson.GsonBuilder
import com.sample.apollographqlandroid.ResultsQuery
import kotlinx.android.synthetic.main.activity_main.*
import md.merit.rickandmorty.Adapters.MainAdapter
import md.merit.rickandmorty.R
import md.merit.rickandmorty.models.Characters
import md.merit.rickandmorty.models.Data
import md.merit.rickandmorty.models.Model
import md.merit.rickandmorty.models.Result
import okhttp3.OkHttpClient

class MainActivity : AppCompatActivity() {
    val BASE_URL = "https://rickandmortyapi.com/graphql"
   // internal var items: MutableList<Characters> = ArrayList()
    lateinit var adapter:MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val okHttpClient = OkHttpClient()
        okHttpClient.newBuilder().build()

       val apolloClient = ApolloClient.builder()
            .serverUrl(BASE_URL)
            .okHttpClient(okHttpClient)
            .build()

        apolloClient.query(ResultsQuery.builder().build()).enqueue(object: ApolloCall.Callback<ResultsQuery.Data?>(){
            override fun onResponse(response: Response<ResultsQuery.Data?>) {
                Log.d("ApolloData", response.toString())
//                val gson = GsonBuilder().create()
//                val obiectul = gson.fromJson( "{Characters:"+ response.data?.characters()?.results().toString(), Characters::class.java)
//                items =obiectul as MutableList<Characters>

                this@MainActivity.runOnUiThread{
                adapter = response.data?.characters()?.results()?.let { MainAdapter(it, this@MainActivity) }!!
                    rvMain.layoutManager = LinearLayoutManager(this@MainActivity)
                    rvMain.adapter = adapter
                }

            }

            override fun onFailure(e: ApolloException) {
              Log.d("ApolloError",e.toString())
            }

        })

    }

}
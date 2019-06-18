package fr.epita.android.todo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.Toast
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()

        val service = retrofit.create(WSInterface::class.java)

        val wsCallBack = object : Callback<List<ToDoObject>> {
            override fun onFailure(call: Call<List<ToDoObject>>, t: Throwable) {
                Log.e("WSTAG", "Error in web service " + t.toString())
            }

            override fun onResponse(call: Call<List<ToDoObject>>, response: Response<List<ToDoObject>>) {
                Log.d("WSTAG", "Success in web service " )
               if (response.code() == 200) {
                   if (response.body() != null) {
                       val data = response.body()
                       Toast.makeText(this@MainActivity, "Size of data : " + data!!.size, Toast.LENGTH_LONG).show()
                       //val todoListView : ListView = findViewById(R.id.activity_main_list_todo)
                       activity_main_list_todo.adapter = ToDoListAdapter(this@MainActivity, data)
                       activity_main_list_todo.setOnItemClickListener { parent, view, position, id ->
                           data[position].completed = data[position].completed
                           (activity_main_list_todo.adapter as BaseAdapter).notifyDataSetChanged()
                       }

                   }
               }
            }

        }

        service.listToDos().enqueue(wsCallBack)

    }
}

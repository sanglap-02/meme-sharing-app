package com.example.memesharingapp

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    var shareImageURL:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme()

    }
    private fun loadMeme(){
        val progressBar=findViewById<ProgressBar>(R.id.loader)
        progressBar.visibility=View.VISIBLE
        val queue = Volley.newRequestQueue(this)
        shareImageURL = "https://meme-api.herokuapp.com/gimme"


        val jasonObjectRequest = JsonObjectRequest(
            Request.Method.GET, shareImageURL,null,
            Response.Listener { response ->
                val url=response.getString("url")
                var imageView:ImageView=findViewById(R.id.memeImageview)

                Glide.with(this).load(url).listener(object:RequestListener<Drawable>{
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        progressBar.visibility=View.GONE
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        progressBar.visibility=View.GONE
                        return false
                    }

                }).into(imageView)

            },
            {

            })

// Add the request to the RequestQueue.
        queue.add(jasonObjectRequest)
    }


    fun nextMeme(view: View) {
        loadMeme()
    }
    fun shareMeme(view: View) {
        val intent=Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"hey checkout this cool meme from reddit...")
        val chooser=Intent.createChooser(intent, "Share using...")
        startActivity(chooser)
    }
}
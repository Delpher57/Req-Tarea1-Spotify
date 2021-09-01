package com.example.tarea1spotifyr


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Window

import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.example.tarea1spotifyr.Connectors.ApiService
import com.example.tarea1spotifyr.Connectors.SpotifyApiConnector
import com.example.tarea1spotifyr.Connectors.TokenAuth
import com.example.tarea1spotifyr.Objects.Pager
import com.example.tarea1spotifyr.Objects.Playlist
import com.example.tarea1spotifyr.Objects.PlaylistGetter
import com.google.gson.Gson
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import kotlinx.android.synthetic.main.activity_splash.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//se ejecuta al inicio y se encarga de obtener el token de autenticacion para la API
class SplashActivity : AppCompatActivity() {

    private var editor : SharedPreferences.Editor? = null
    private var msharedPreferences : SharedPreferences? = null

    private var queue : RequestQueue? = null


    private val CLIENT_ID : String = "6ecb1909988649768f2839eb68296f3a"
    private val REDIRECT_URI : String = "https://com.example.tarea1spotifyr/callback"
    private val REQUEST_CODE : Int = 1337
    private val SCOPES : Array<String> = arrayOf("playlist-read-private","playlist-modify-private","playlist-modify-public")



    private fun authenticateSpotify() {
        val builder: AuthorizationRequest.Builder = AuthorizationRequest.Builder(CLIENT_ID, AuthorizationResponse.Type.TOKEN, REDIRECT_URI)
        builder.setScopes(SCOPES)
        val  request : AuthorizationRequest = builder.build()
        AuthorizationClient.openLoginActivity(this, REQUEST_CODE, request)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        //requestWindowFeature(Window.FEATURE_NO_TITLE)
        //supportActionBar?.hide()



        authenticateSpotify()


        msharedPreferences = this.getSharedPreferences("SPOTIFY", 0)
        queue = Volley.newRequestQueue(this)



    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE){
            val response : AuthorizationResponse = AuthorizationClient.getResponse(resultCode, data)

            when(response.type) {
                AuthorizationResponse.Type.TOKEN -> {
                    editor = getSharedPreferences("SPOTIFY", 0).edit()
                    editor!!.putString("token", response.accessToken)

                    editor!!.apply()

                    //waitForUserInfo()
                    textoPrueba.text = "el token es: " + response.accessToken
                    TokenAuth.TOKEN = response.accessToken //guardamos el token en el singleton
                    Log.d("STARTING", "GOT AUTH TOKEN")

                    //nos preparamos para cambiar de pagina
                    val changePage = Intent(this, PlaylistActivity::class.java)
                    startActivity(changePage)

                }
                AuthorizationResponse.Type.ERROR -> {
                    textoPrueba.text = "hubo un error!" + response.error.toString()
                }
                else -> {
                    textoPrueba.text = "estamos en ELSE " + response.type.toString() + " " + response.accessToken.toString() + " " + response.state.toString()
                }
            }
        }
    }
}

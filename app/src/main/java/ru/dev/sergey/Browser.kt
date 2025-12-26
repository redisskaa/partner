package ru.dev.sergey

import android.R.attr.description
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Browser : AppCompatActivity() {

    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browser)

        webView = findViewById(R.id.webView)

        val webSettings = webView.settings
        val url = intent.getStringExtra("url")

        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.databaseEnabled = true
        webSettings.cacheMode = WebSettings.LOAD_DEFAULT
        webSettings.mediaPlaybackRequiresUserGesture = false
        webSettings.allowContentAccess = true
        webSettings.allowFileAccess = false

        webSettings.userAgentString = resources.getString(R.string.useragent)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        try {
            loadView(url.toString(), this)
        } catch (e: Exception) {
            Toast.makeText(this, "Не удалось открыть ссылку: ${e.toString()}", Toast.LENGTH_SHORT).show()
            Log.e("ERROR", e.toString())
        }
    }
    fun loadView(url: String, context: Context){

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                view.loadUrl(request.url.toString())
                return false
            }

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {

            }

            override fun onPageFinished(view: WebView, url: String) {

            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)

                when (error?.errorCode) {
                    -2 -> Toast.makeText(context, "Нет подключения к интернету", Toast.LENGTH_LONG).show()
                    -6 -> Toast.makeText(context, "Время ожидания истекло", Toast.LENGTH_LONG).show()
                    else -> Toast.makeText(context, "Ошибка: ${error?.description}", Toast.LENGTH_LONG).show()
                }

                Log.e("WebViewError", error?.toString() ?: "Неизвестная ошибка")
            }
        }
        webView.loadUrl(url)
    }
}
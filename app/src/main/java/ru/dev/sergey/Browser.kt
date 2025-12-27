package ru.dev.sergey

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Browser : BaseActivity() {

    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browser)

        webView = findViewById(R.id.webView)

        val webSettings = webView.settings
        val url = intent.getStringExtra("url")
        setToolbarWebView(url)
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

        if (url != null){

            try {
                loadView(url, this)
            } catch (e: Exception) {
                Toast.makeText(this, "Не удалось открыть ссылку: ${e.toString()}", Toast.LENGTH_SHORT).show()
                Log.e("ERROR", e.toString())
            }

        }else{
            Toast.makeText(this, "Попробуйте еще раз", Toast.LENGTH_SHORT).show()
            finish()
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
                Toast.makeText(context, "Страница загружается", Toast.LENGTH_LONG).show()
            }

            override fun onPageFinished(view: WebView, url: String) {
                Toast.makeText(context, "Страница загружена", Toast.LENGTH_LONG).show()
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)

//                when (error?.errorCode) {
//                    -2 -> Toast.makeText(context, "Нет подключения к интернету", Toast.LENGTH_LONG).show()
//                    -6 -> Toast.makeText(context, "Время ожидания истекло", Toast.LENGTH_LONG).show()
//                    else -> Toast.makeText(context, "Ошибка: ${error?.description}", Toast.LENGTH_LONG).show()
//                }

                Log.e("WebViewError", error?.toString() ?: "Неизвестная ошибка")
            }
        }
        webView.loadUrl(url)
    }
}
package ru.dev.sergey

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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
        webSettings.domStorageEnabled = true      // обязательно для Wildberries
        webSettings.databaseEnabled = true        // иногда тоже нужно
        webSettings.cacheMode = WebSettings.LOAD_DEFAULT

        webSettings.userAgentString = "Mozilla/5.0 (Linux; Android 14; SM-G998B) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Mobile Safari/537.36"

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

        // ← Ключевой момент: перехватываем все переходы внутри WebView
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                view.loadUrl(request.url.toString())
                return false  // false = грузим внутри приложения
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)

                try {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                } catch (e: Exception) {
                    finish()
                    Toast.makeText(context, "Не удалось открыть ссылку: ${e.toString()}", Toast.LENGTH_SHORT).show()
                }

                Toast.makeText(this@Browser, "Не получилось открыть, пробуем другим способом", Toast.LENGTH_LONG).show()
                Log.e("WebViewError", error?.toString() ?: "Неизвестная ошибка")
            }
        }

        webView.loadUrl(url)
    }
}
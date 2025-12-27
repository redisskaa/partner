package ru.dev.sergey

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar

abstract class BaseActivity : AppCompatActivity() {

    protected lateinit var toolbar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Здесь ничего не устанавливаем, только готовим toolbar
    }

    // Вызываем этот метод в onCreate каждой активности после setContentView
    protected fun setupToolbar() {
        val toolbarView = findViewById<MaterialToolbar>(R.id.toolbar)
        if (toolbarView != null) {
            toolbar = toolbarView
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            toolbar.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        } else {
            // Если toolbar не найден — просто ничего не делаем
            Log.w("BaseActivity", "Toolbar not found in layout")
        }
    }

    protected fun setToolbarWebView(titleTool: String?) {
        val toolbarView = findViewById<MaterialToolbar>(R.id.toolbar)
        if (toolbarView != null) {
            toolbar = toolbarView
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = titleTool
            toolbar.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        } else {
            // Если toolbar не найден — просто ничего не делаем
            Log.w("BaseActivity", "Toolbar not found in layout")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
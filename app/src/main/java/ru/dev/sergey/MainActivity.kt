package ru.dev.sergey

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

open class MainActivity : BaseActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Adapter
    //private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализация RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Инициализация Toolbar
        //toolbar = findViewById(R.id.toolbar)
        //setSupportActionBar(toolbar)

        // Настройка заголовка
        supportActionBar?.title = resources.getText(R.string.app_name)

        setupToolbar()

        // Добавляем кнопки в ActionBar
        //toolbar.menu.clear()
        //toolbar.inflateMenu(R.menu.menu_main)

//        toolbar.setNavigationOnClickListener {
//            finish()
//        }

        // Обработчик нажатий на кнопки
//        toolbar.setOnMenuItemClickListener { item ->
//            when (item.itemId) {
//                R.id.action_settings -> {
//                    Log.e("Обработка", "Обработка нажатия на кнопку настроек")
//                    true
//                }
//                R.id.action_search -> {
//                    Log.e("Обработка", "Обработка поиска")
//                    true
//                }
//                else -> false
//            }
//        }

        recyclerView.layoutManager = LinearLayoutManager(this)

        // Создаём и устанавливаем адаптер
        adapter = Adapter(OffersData.getOffers(), baseContext)
        recyclerView.adapter = adapter

        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(divider)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                Log.e("Обработка", "Обработка поиска")
                true
            }
            R.id.action_settings -> {
                Log.e("Обработка", "Обработка нажатия на кнопку настроек")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

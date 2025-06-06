package com.example.mydrawerapp  // <- замените на свой package, если отличается

import android.os.Bundle
import android.view.Menu
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.mydrawerapp.R

import com.example.mydrawerapp.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1) Привязываем Toolbar как ActionBar
        setSupportActionBar(binding.toolbar)

        // 2) Устанавливаем ActionBarDrawerToggle (гамбургер) между Toolbar и DrawerLayout
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // 3) Обрабатываем клик по пункту меню
        val navView: NavigationView = binding.mobileNavigation
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    // По клику на «Главный экран» просто закрываем Drawer и показываем Toast
                    drawerLayout.closeDrawers()
                    Toast.makeText(this, "Вы на главном экране", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }

        // 4) Поместим саму надпись на главный экран: добавим TextView программно или в XML.
        //    Мы сделали content_frame просто пустым FrameLayout, поэтому добавим туда TextView:
        binding.contentFrame.apply {
            // Упростим: создадим TextView «В одну строку» (по условию задачи)
            val tv = android.widget.TextView(this@MainActivity).apply {
                text = "Главный экран"
                textSize = 20f
                setTextColor(resources.getColor(android.R.color.black, theme))
                // Расположим в левом верхнем углу:
                val params = android.widget.FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
                )
                params.marginStart = 16.dpToPx()   // отступ слева 16dp
                params.topMargin = 16.dpToPx()     // отступ сверху 16dp
                layoutParams = params
            }
            addView(tv)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Если вам не нужны дополнительные пункты в тулбаре, можно вообще вернуть false.
        return super.onCreateOptionsMenu(menu)
    }

    // Расширение для конвертации dp → px
    private fun Int.dpToPx(): Int =
        (this * resources.displayMetrics.density).toInt()
}

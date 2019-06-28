package com.yk.silence.customwallpaper.widget

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import com.yk.silence.customwallpaper.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        btn_start.setOnClickListener(this)
    }

    private fun changePaper() {
        val chooseIntent: Intent
        chooseIntent = Intent(Intent.ACTION_SET_WALLPAPER)
        startActivity(Intent.createChooser(chooseIntent, "选择壁纸"))
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_start -> {
                changePaper()
            }
        }
    }
}

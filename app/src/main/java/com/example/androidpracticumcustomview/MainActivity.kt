package com.example.androidpracticumcustomview

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import com.example.androidpracticumcustomview.ui.theme.ComposeCustomViewActivity
import com.example.androidpracticumcustomview.xmlCustomView.XmlCustomViewActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button_xml_screen).setOnClickListener {
            startActivity(Intent(this, XmlCustomViewActivity::class.java))
        }

        findViewById<Button>(R.id.button_compose).setOnClickListener {
            startActivity(Intent(this, ComposeCustomViewActivity::class.java))
        }
    }

}
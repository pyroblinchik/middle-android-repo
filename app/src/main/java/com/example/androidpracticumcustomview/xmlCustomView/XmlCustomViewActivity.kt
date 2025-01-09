package com.example.androidpracticumcustomview.xmlCustomView

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.androidpracticumcustomview.R

class XmlCustomViewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startXmlPracticum()
    }

    private fun startXmlPracticum() {
        val customContainer = CustomContainer(this)
        setContentView(customContainer)

        val firstView = TextView(this).apply {
            text = getString(R.string.first_element)
            textSize = 25f
        }
        val secondView = Button(this).apply {
            text = getString(R.string.second_element)
            textSize = 25f
        }
        val thirdView = TextView(this).apply {
            text = getString(R.string.third_element)
            textSize = 25f
        }

        customContainer.addView(firstView)
        customContainer.addView(secondView)
//        customContainer.addView(thirdView)

        // Добавление второго элемента через некоторое время
//        Handler(Looper.getMainLooper()).postDelayed({
//            customContainer.addView(secondView)
//        }, 2000)
    }
}
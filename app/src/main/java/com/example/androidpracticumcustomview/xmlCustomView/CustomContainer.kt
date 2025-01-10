package com.example.androidpracticumcustomview.xmlCustomView

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import com.example.androidpracticumcustomview.R

class CustomContainer @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, @AttrRes defStyleAttr: Int = 0, @StyleRes defStyleRes: Int = 0
) : FrameLayout(context, attrs) {

    // Длительность анимации прозрачности
    private var alphaAnimationDuration = 0
    // Длительность анимации перемещенияэлементов к краям
    private var translationAnimationDuration = 0

    init {
        setWillNotDraw(false)
        init(attrs, defStyleAttr, defStyleRes)
    }

    private fun init(
        attrs: AttributeSet?,
        @AttrRes defStyleAttr: Int,
        @StyleRes defStyleRes: Int
    ) {
        // Получение кастомных атрибутов для установки длительностей анимаций
        val typedArray: TypedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.CustomContainer,
            defStyleAttr,
            defStyleRes
        )

        alphaAnimationDuration = context.resources.getInteger(R.integer.default_alpha_animation_duration)
        translationAnimationDuration = context.resources.getInteger(R.integer.default_translation_animation_duration)

        try {
            alphaAnimationDuration = typedArray.getInt(
                R.styleable.CustomContainer_twoElements_alphaAnimationDuration,
                alphaAnimationDuration
            )
            translationAnimationDuration = typedArray.getInt(
                R.styleable.CustomContainer_twoElements_translationAnimationDuration,
                translationAnimationDuration
            )
        } finally {
            typedArray.recycle()
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        for (i in 0 until childCount) {
            val child = getChildAt(i)

            val parentWidth = right - left
            val parentHeight = bottom - top

            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight

            val childLeft = (parentWidth - childWidth) / 2
            val childTop = (parentHeight - childHeight) / 2

            child.layout(
                childLeft,
                childTop,
                childLeft + childWidth,
                childTop + childHeight
            )

            // Запуск анимации
            startAnimation(child)
        }
    }


    override fun addView(child: View) {
        if (this.childCount < 2){
            child.apply { alpha = 0f }

            val layoutParams = LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL
            }

            super.addView(child, layoutParams)
        } else{

            // Ошибка если добавленных view уже 2 и пользователь пытается добавить 3-е
            throw IllegalStateException("Can't add more than 2 views in CustomContainer.kt. Current count: ${this.childCount}")
        }
    }

    private fun startAnimation(child: View){
        if (child.alpha != 0f) return

        child.animate()
            .alpha(1f)
            .setDuration(alphaAnimationDuration.toLong())
            .start()

        val screenHeight = resources.displayMetrics.heightPixels
        if(indexOfChild(child) == 0){
            child.animate()
                .translationY(-screenHeight / 2f + child.height)
                .setDuration(translationAnimationDuration.toLong())
                .start()
        } else if (indexOfChild(child) == 1){
            child.animate()
                .translationY(screenHeight / 2f - child.height)
                .setDuration(translationAnimationDuration.toLong())
                .start()
        }
    }
}
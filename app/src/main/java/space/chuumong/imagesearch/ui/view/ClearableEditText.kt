package space.chuumong.imagesearch.ui.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import space.chuumong.imagesearch.R

class ClearEditText : AppCompatEditText, TextWatcher, View.OnTouchListener,
    View.OnFocusChangeListener {

    private lateinit var clearDrawable: Drawable

    private var onFocusListener: OnFocusChangeListener? = null

    private var onTouchListener: OnTouchListener? = null

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs) {
        init()
    }

    private fun init() {
        val drawable = ContextCompat.getDrawable(context, R.drawable.ic_close_black_24dp)
            ?: throw NullPointerException("Can't init ClearEditText, drawable is null")

        clearDrawable = DrawableCompat.wrap(drawable)
        DrawableCompat.setTintList(clearDrawable, hintTextColors)
        clearDrawable.setBounds(0, 0, clearDrawable.intrinsicWidth, clearDrawable.intrinsicHeight)

        setClearIconVisible(false)

        super.setOnTouchListener(this)
        super.setOnFocusChangeListener(this)
        addTextChangedListener(this)
    }

    override fun setOnFocusChangeListener(onFocusChangeListener: OnFocusChangeListener) {
        this.onFocusListener = onFocusChangeListener
    }

    override fun setOnTouchListener(onTouchListener: OnTouchListener) {
        this.onTouchListener = onTouchListener
    }

    override fun onFocusChange(view: View, hasFocus: Boolean) {
        if (hasFocus) {
            setClearIconVisible(text!!.isNotEmpty())
        } else {
            setClearIconVisible(false)
        }

        if (onFocusListener != null) {
            onFocusListener!!.onFocusChange(view, hasFocus)
        }
    }

    override fun onTextChanged(
        text: CharSequence,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        if (isFocused) {
            setClearIconVisible(text.isNotEmpty())
        }
    }

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        val x = event.x.toInt()
        if (clearDrawable.isVisible && x > width - paddingEnd - clearDrawable.intrinsicWidth) {
            if (event.action == MotionEvent.ACTION_UP) {
                error = null
                text = null
            }

            return true
        }

        return if (onTouchListener != null) {
            onTouchListener!!.onTouch(view, event)
        } else {
            false
        }
    }

    private fun setClearIconVisible(isVisible: Boolean) {
        clearDrawable.setVisible(isVisible, false)
        setCompoundDrawables(null, null, if (isVisible) clearDrawable else null, null)
    }

}
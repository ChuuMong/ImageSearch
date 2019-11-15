package space.chuumong.imagesearch.utils

import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.getSystemService

object SoftKeyboardUtils {

    fun showKeyboard(et: EditText) {
        et.postDelayed({
            val imm = et.context.getSystemService<InputMethodManager>() ?: return@postDelayed
            et.requestFocus()
            imm.showSoftInput(et, InputMethodManager.SHOW_FORCED)
            val isShowing = imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT)
            if (!isShowing) {
                showKeyboard(et)
            }
        }, 30)
    }

    fun hideKeyboard(et: EditText) {
        et.postDelayed({
            val imm = et.context.getSystemService<InputMethodManager>() ?: return@postDelayed
            imm.hideSoftInputFromWindow(et.windowToken, 0)
        }, 30)
    }
}
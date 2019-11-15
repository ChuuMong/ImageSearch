package space.chuumong.imagesearch.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.CheckResult
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable

@CheckResult
fun EditText.afterTextChangeEvents(): Observable<String> {
    return EditTextAfterTextChangeEventObservable(this)
}

class EditTextAfterTextChangeEventObservable(
    private val view: TextView
) : Observable<String>() {

    override fun subscribeActual(observer: Observer<in String>) {
        val listener = TextWatcherListener(view, observer)
        observer.onSubscribe(listener)
        view.addTextChangedListener(listener)
    }

    private class TextWatcherListener(
        private val view: TextView,
        private val observer: Observer<in String>
    ) : MainThreadDisposable(), TextWatcher {

        override fun beforeTextChanged(
            charSequence: CharSequence,
            start: Int,
            count: Int,
            after: Int
        ) {
        }

        override fun onTextChanged(
            charSequence: CharSequence,
            start: Int,
            before: Int,
            count: Int
        ) {
        }

        override fun afterTextChanged(s: Editable) {
            observer.onNext(s.toString())
        }

        override fun onDispose() {
            view.removeTextChangedListener(this)
        }
    }
}
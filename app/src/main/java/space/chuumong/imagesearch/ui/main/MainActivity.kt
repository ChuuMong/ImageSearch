package space.chuumong.imagesearch.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.android.viewmodel.ext.android.getViewModel
import space.chuumong.data.Result
import space.chuumong.domain.entities.SearchImageResult
import space.chuumong.imagesearch.ui.BaseActivity
import space.chuumong.imagesearch.R
import space.chuumong.imagesearch.databinding.ActivityMainBinding
import space.chuumong.imagesearch.utils.SoftKeyboardUtils
import space.chuumong.imagesearch.utils.afterTextChangeEvents
import space.chuumong.imagesearch.viewmodel.SearchImageViewModel
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object {
        private const val TAG = "MainActivity"
        private const val DEFAULT_SEARCH_DEBOUNCE_TIME = 1L
    }

    override fun getLayoutId() = R.layout.activity_main

    private val searchImageViewModel: SearchImageViewModel by lazy { getViewModel() as SearchImageViewModel }

    private var isAlreadySearch = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSearchAfterTextChangeEvent()

        binding.etSearch.setOnEditorActionListener { v, actionId, event ->
            if (v.text.isNotEmpty() && actionId == EditorInfo.IME_ACTION_SEARCH) {
                isAlreadySearch = true
                Log.d("text", v.text.toString())
                clearSearchFocus()
                searchImages()

                return@setOnEditorActionListener true
            }

            false
        }
    }

    @SuppressLint("CheckResult")
    private fun setSearchAfterTextChangeEvent() {
        binding.etSearch.afterTextChangeEvents()
            .debounce(DEFAULT_SEARCH_DEBOUNCE_TIME, TimeUnit.SECONDS)
            .filter { it.isNotEmpty() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (isAlreadySearch) {
                    isAlreadySearch = false
                    return@subscribe
                }

                Log.d(TAG, it)
                clearSearchFocus()
                searchImages()
            }, {
                Log.e(TAG, it.message, it)
            })
    }

    private fun searchImages() {
        val query = binding.etSearch.text.toString()

        searchImageViewModel.searchImage(query, object : Result<SearchImageResult> {
            override fun onSuccess(result: SearchImageResult) {

            }

            override fun onFail(t: Throwable) {
                Log.e(TAG, t.message, t)
            }
        })
    }

    private fun clearSearchFocus() {
        binding.etSearch.clearFocus()
        SoftKeyboardUtils.hideKeyboard(binding.etSearch)
    }
}

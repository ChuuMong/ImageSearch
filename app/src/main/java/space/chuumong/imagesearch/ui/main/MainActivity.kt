package space.chuumong.imagesearch.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.android.viewmodel.ext.android.getViewModel
import space.chuumong.data.Result
import space.chuumong.domain.entities.SearchImageResult
import space.chuumong.imagesearch.ui.BaseActivity
import space.chuumong.imagesearch.R
import space.chuumong.imagesearch.databinding.ActivityMainBinding
import space.chuumong.imagesearch.ui.adapter.SearchImageAdapter
import space.chuumong.imagesearch.ui.view.LoadMoreScrollListener
import space.chuumong.imagesearch.ui.view.showNoTitleTwoButtonsDialog
import space.chuumong.imagesearch.utils.SoftKeyboardUtils
import space.chuumong.imagesearch.utils.afterTextChangeEvents
import space.chuumong.imagesearch.viewmodel.SearchImageViewModel
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object {
        private const val TAG = "MainActivity"

        private const val DEFAULT_SEARCH_DEBOUNCE_TIME = 1L

        private const val START_SEARCH_PAGE = 2
    }

    override fun getLayoutId() = R.layout.activity_main

    private val searchImageViewModel: SearchImageViewModel by lazy { getViewModel() as SearchImageViewModel }
    private val searchImageAdapter = SearchImageAdapter()

    private val loadMoreScrollListener = object : LoadMoreScrollListener() {
        override fun loadMore() {
            moreSearchImage()
        }
    }

    private var isAlreadySearch = false

    private var searchPage = START_SEARCH_PAGE
    private var currentSearchItemCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.searchImageViewModel = searchImageViewModel

        binding.rvImage.layoutManager = LinearLayoutManager(this)
        binding.rvImage.adapter = searchImageAdapter

        setSearchAfterTextChangeEvent()

        binding.etSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
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
        if (query.isEmpty()) {
            binding.etSearch.error = getString(R.string.search_image_not_input_query)
            return
        }

        searchImageViewModel.searchImage(query, object : Result<SearchImageResult> {
            override fun onSuccess(result: SearchImageResult) {
                searchImageAdapter.addAll(result.images)

                searchPage = START_SEARCH_PAGE
                currentSearchItemCount = result.images.size

                if (currentSearchItemCount < result.meta.pageableCount) {
                    binding.rvImage.addOnScrollListener(loadMoreScrollListener)
                }
            }

            override fun onFail(t: Throwable) {
                Log.e(TAG, t.message, t)
                showNoTitleTwoButtonsDialog(
                    getString(R.string.network_error_retry),
                    getString(R.string.retry),
                    { searchImages() },
                    getString(android.R.string.cancel),
                    { }
                )
            }
        })
    }

    private fun moreSearchImage() {
        val query = binding.etSearch.text.toString()
        if (query.isEmpty()) {
            binding.etSearch.error = getString(R.string.search_image_not_input_query)
            return
        }

        searchImageViewModel.moreSearchImages(
            query,
            searchPage,
            object : Result<SearchImageResult> {
                override fun onSuccess(result: SearchImageResult) {
                    searchImageAdapter.addMore(result.images)

                    searchPage += 1
                    currentSearchItemCount += result.images.size
                    loadMoreScrollListener.isLoading = false

                    if (currentSearchItemCount >= result.meta.pageableCount) {
                        binding.rvImage.removeOnScrollListener(loadMoreScrollListener)
                    }
                }

                override fun onFail(t: Throwable) {
                    Log.e(TAG, t.message, t)
                    showNoTitleTwoButtonsDialog(
                        getString(R.string.network_error_retry),
                        getString(R.string.retry),
                        { moreSearchImage() },
                        getString(android.R.string.cancel),
                        { }
                    )
                }
            })
    }

    private fun clearSearchFocus() {
        binding.etSearch.clearFocus()
        SoftKeyboardUtils.hideKeyboard(binding.etSearch)
    }
}

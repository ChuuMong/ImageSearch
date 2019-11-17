package space.chuumong.imagesearch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.http.Query
import space.chuumong.data.Result
import space.chuumong.domain.entities.SearchImageResult
import space.chuumong.domain.usecases.MoreSearchImages
import space.chuumong.domain.usecases.SearchImages


class SearchImageViewModel(
    private val searchImages: SearchImages,
    private val moreSearchImages: MoreSearchImages
) : BaseViewModel() {

    private val _isResultEmpty = MutableLiveData<Boolean>().apply { value = false }
    val isResultEmpty: LiveData<Boolean> get() = _isResultEmpty

    fun searchImage(query: String, result: Result<SearchImageResult>) {
        add(searchImages.search(query).subscribe({
            _isResultEmpty.value = it.images.isEmpty()

            result.onSuccess(it)
        }, {
            result.onFail(it)
        }))
    }

    fun moreSearchImages(query: String, page: Int, result: Result<SearchImageResult>) {
        add(moreSearchImages.search(query, page).subscribe({
            result.onSuccess(it)
        }, {
            result.onFail(it)
        }))
    }
}
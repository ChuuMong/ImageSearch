package space.chuumong.imagesearch.viewmodel

import retrofit2.http.Query
import space.chuumong.data.Result
import space.chuumong.domain.entities.SearchImageResult
import space.chuumong.domain.usecases.SearchImages


class SearchImageViewModel(private val searchImages: SearchImages) : BaseViewModel() {

    fun searchImage(query: String, result: Result<SearchImageResult>) {
        add(searchImages.search(query).subscribe({
            result.onSuccess(it)
        }, {
            result.onFail(it)
        }))
    }
}
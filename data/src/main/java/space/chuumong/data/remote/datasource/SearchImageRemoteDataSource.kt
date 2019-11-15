package space.chuumong.data.remote.datasource

import io.reactivex.Single
import retrofit2.http.Query
import space.chuumong.data.remote.api.ApiService
import space.chuumong.data.remote.model.SearchImageResultResponse


/**
 * Created by Home on 2019-11-15.
 */
class SearchImageRemoteDataSource(private val apiService: ApiService) {

    fun searchImage(query: String, page: Int = 1): Single<SearchImageResultResponse> {
        return apiService.searchImages(query, page)
    }
}
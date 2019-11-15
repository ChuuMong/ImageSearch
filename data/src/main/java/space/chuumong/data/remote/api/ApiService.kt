package space.chuumong.data.remote.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import space.chuumong.data.remote.model.SearchImageResultResponse


/**
 * Created by Home on 2019-11-15.
 */
interface ApiService {

    @GET("image")
    fun searchImages(@Query("query") query: String, @Query("page") page: Int, @Query("size") size: Int = 20): Single<SearchImageResultResponse>
}
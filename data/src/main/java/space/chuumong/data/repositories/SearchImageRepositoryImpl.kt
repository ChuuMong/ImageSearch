package space.chuumong.data.repositories

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import space.chuumong.data.mapper.SearchImageMapper
import space.chuumong.data.remote.datasource.SearchImageRemoteDataSource
import space.chuumong.domain.entities.SearchImageResult
import space.chuumong.domain.repisotries.SearchImageRepository

class SearchImageRepositoryImpl(
    private val remoteDataSource: SearchImageRemoteDataSource,
    private val mapper: SearchImageMapper
) : SearchImageRepository {

    override fun searchImages(query: String): Single<SearchImageResult> {
        return remoteDataSource.searchImage(query)
            .map(mapper.toSearchImageResultEntity())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun searchMoreImages(query: String, page: Int): Single<SearchImageResult> {
        return remoteDataSource.searchImage(query, page)
            .map(mapper.toSearchImageResultEntity())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
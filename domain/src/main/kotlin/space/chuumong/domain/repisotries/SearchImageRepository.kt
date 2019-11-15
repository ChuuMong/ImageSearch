package space.chuumong.domain.repisotries

import io.reactivex.Single
import space.chuumong.domain.entities.SearchImageResult
import space.chuumong.domain.usecases.UseCase

interface SearchImageRepository {

    fun searchImages(query: String): Single<SearchImageResult>

    fun searchMoreImages(query: String, page: Int): Single<SearchImageResult>
}
package space.chuumong.domain.usecases

import io.reactivex.Single
import space.chuumong.domain.entities.SearchImageResult
import space.chuumong.domain.repisotries.SearchImageRepository

class MoreSearchImages(private val repository: SearchImageRepository) :
    UseCase<Map<String, Any>, SearchImageResult>() {

    companion object {
        private const val QUERY = "params:query"
        private const val PAGE = "params:page"
    }

    override fun run(params: Map<String, Any>): Single<SearchImageResult> {
        val query = params[QUERY] as? String ?: throw IllegalArgumentException()
        val page = params[PAGE] as? Int ?: throw IllegalArgumentException()

        return repository.searchMoreImages(query, page)
    }

    fun search(query: String, page: Int): Single<SearchImageResult> {
        val params = hashMapOf<String, Any>()
        params[QUERY] = query
        params[PAGE] = page

        return execute(params)
    }
}
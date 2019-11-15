package space.chuumong.domain.usecases

import io.reactivex.Single
import space.chuumong.domain.entities.SearchImageResult
import space.chuumong.domain.repisotries.SearchImageRepository

class SearchImages(private val repository: SearchImageRepository) :
    UseCase<Map<String, String>, SearchImageResult>() {

    companion object {
        private const val QUERY = "params:query"
    }

    override fun run(params: Map<String, String>): Single<SearchImageResult> {
        val query = params[QUERY] ?: throw NullPointerException()

        return repository.searchImages(query)
    }

    fun search(query: String): Single<SearchImageResult> {
        val params = hashMapOf<String, String>()
        params[QUERY] = query

        return execute(params)
    }
}
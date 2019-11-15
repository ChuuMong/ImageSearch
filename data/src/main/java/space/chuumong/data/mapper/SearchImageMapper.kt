package space.chuumong.data.mapper

import space.chuumong.data.remote.model.SearchImageResultResponse
import space.chuumong.domain.entities.SearchImageResult
import io.reactivex.functions.Function
import space.chuumong.domain.entities.SearchImage

class SearchImageMapper {
    fun toSearchImageResultEntity() =
        Function<SearchImageResultResponse, SearchImageResult> { response ->
            val images = mutableListOf<SearchImage>()
            response.images.forEach {
                images.add(it.toEntity())
            }

            SearchImageResult(images, response.meta.toEntity())
        }
}
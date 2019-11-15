package space.chuumong.data.mapper

import space.chuumong.data.remote.model.SearchImageResultResponse
import space.chuumong.domain.entities.SearchImageResult
import io.reactivex.functions.Function

class SearchImageMapper {
    fun toSearchImageResultEntity() = Function<SearchImageResultResponse, SearchImageResult> {
        it.toEntity()
    }
}
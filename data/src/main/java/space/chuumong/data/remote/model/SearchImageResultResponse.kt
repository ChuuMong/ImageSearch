package space.chuumong.data.remote.model

import com.google.gson.annotations.SerializedName
import space.chuumong.data.const.HTTPS_START_WITH
import space.chuumong.domain.entities.SearchImage
import space.chuumong.domain.entities.SearchImageMeta
import space.chuumong.domain.entities.SearchImageResult

data class SearchImageResultResponse(
    @SerializedName("documents")
    val images: List<SearchImageResponse>,
    @SerializedName("meta")
    val meta: SearchImageMetaResponse
)

data class SearchImageResponse(
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("width")
    val width: Int,
    @SerializedName("height")
    val height: Int
) {
    fun toEntity() = SearchImage(imageUrl, width, height)
}

data class SearchImageMetaResponse(
    @SerializedName("pageable_count")
    val pageableCount: Int,
    @SerializedName("is_end")
    val isEnd: Boolean
) {
    fun toEntity() = SearchImageMeta(pageableCount, isEnd)
}
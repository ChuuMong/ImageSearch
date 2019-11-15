package space.chuumong.data.remote.model

import com.google.gson.annotations.SerializedName
import space.chuumong.domain.entities.SearchImage
import space.chuumong.domain.entities.SearchImageMeta
import space.chuumong.domain.entities.SearchImageResult

data class SearchImageResultResponse(
    @SerializedName("documents")
    val images: List<SearchImageResponse>,
    @SerializedName("meta")
    val meta: SearchImageMetaResponse
) {
    fun toEntity(): SearchImageResult {
        val images = mutableListOf<SearchImage>()
        this.images.forEach { images.add(it.toEntity()) }

        return SearchImageResult(images, meta.toEntity())
    }
}

data class SearchImageResponse(
    @SerializedName("image_url")
    val imageUrl: String
) {
    fun toEntity() = SearchImage(imageUrl)
}

data class SearchImageMetaResponse(
    @SerializedName("pageable_count")
    val pageableCount: Int,
    @SerializedName("is_end")
    val isEnd: Boolean
) {
    fun toEntity() = SearchImageMeta(pageableCount, isEnd)
}
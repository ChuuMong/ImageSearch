package space.chuumong.domain.entities

data class SearchImageResult(
    val images: List<SearchImage>,
    val meta: SearchImageMeta
)

data class SearchImage(
    val imageUrl: String
)

data class SearchImageMeta(
    val pageableCount: Int,
    val isEnd: Boolean
)
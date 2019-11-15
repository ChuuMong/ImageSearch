package space.chuumong.imagesearch.ui.adapter

import android.graphics.drawable.Animatable
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.controller.BaseControllerListener
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.image.ImageInfo
import space.chuumong.domain.entities.SearchImage
import space.chuumong.imagesearch.R

class SearchImageAdapter : RecyclerView.Adapter<SearchImageAdapter.SearchImageViewHolder>() {

    private val images = mutableListOf<SearchImage>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchImageViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_search_image, parent, false)
        return SearchImageViewHolder(view)
    }

    override fun getItemCount() = images.size

    override fun onBindViewHolder(holder: SearchImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun getItem(position: Int) = images[position]

    fun addAll(items: List<SearchImage>) {
        images.clear()
        images.addAll(items)
        notifyDataSetChanged()
    }

    inner class SearchImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val ivImage = view.findViewById<SimpleDraweeView>(R.id.iv_image)

        fun bind(item: SearchImage) {
            ivImage.visibility = View.VISIBLE

            ivImage.controller =
                Fresco.newDraweeControllerBuilder()
                    .setControllerListener(object : BaseControllerListener<ImageInfo>() {
                        override fun onFinalImageSet(
                            id: String?,
                            imageInfo: ImageInfo?,
                            animatable: Animatable?
                        ) {
                            imageInfo ?: return
                            ivImage.aspectRatio =
                                imageInfo.width.toFloat() / imageInfo.height.toFloat()
                        }
                    })
                    .setOldController(ivImage.controller)
                    .setUri(Uri.parse(item.imageUrl))
                    .setTapToRetryEnabled(true)
                    .build()
        }
    }
}
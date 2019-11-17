package space.chuumong.imagesearch.ui.view

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


abstract class LoadMoreScrollListener : RecyclerView.OnScrollListener() {

    companion object {
        private const val VISIBLE_THRESHOLD = 2
    }

    var isLoading = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (!isLoading) {
            val layoutManager = recyclerView.layoutManager as? LinearLayoutManager ?: return

            if (layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.itemCount - VISIBLE_THRESHOLD) {
                isLoading = true
                loadMore()
            }
        }
    }

    abstract fun loadMore()
}
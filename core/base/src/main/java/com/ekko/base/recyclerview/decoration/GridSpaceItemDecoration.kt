package com.ekko.base.recyclerview.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class GridSpaceItemDecoration(
    private val spanCount: Int,
    private val space: Int
) : ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (parent.layoutManager is GridLayoutManager) {
            val position = parent.getChildAdapterPosition(view)
            val column = position % spanCount
            outRect.left =
                column * space / spanCount // column * ((1f / spanCount) * spacing)
            outRect.right =
                space - (column + 1) * space / spanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = space // item top
            }
        }
    }
}
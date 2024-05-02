package com.ekko.base.recyclerview.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class LineSpaceItemDecoration(
    private val space: Int,
    private val headAndTail: Boolean = true
) : ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val count = parent.layoutManager?.itemCount ?: return
        if (position in 1 until count - 1) {
            outRect.left = space / 2
            outRect.right = space / 2
        }
        if (headAndTail) {
            if (position == 0) {
                outRect.left = space
                outRect.right = space / 2
            }
            if (position == count - 1) {
                outRect.left = space / 2
                outRect.right = space
            }
        }
    }
}
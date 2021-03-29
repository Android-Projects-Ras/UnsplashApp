package com.example.myapp.adapter

import android.content.Context
import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView


class CustomItemDecoration(
        private val context: Context,
        private val leftOffset: Int,
        private val rightOffset: Int,
        private val topOffset: Int,
        private val bottomOffset: Int

) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
        outRect.left = dpToPx(context, leftOffset).toInt()
        outRect.right = dpToPx(context, rightOffset).toInt()
        outRect.top = dpToPx(context, topOffset).toInt()
        outRect.bottom = dpToPx(context, bottomOffset).toInt()


    }

    private fun dpToPx(context: Context, dp: Int): Float {
        return dp * context.resources.displayMetrics.density

    }
}
package org.markensic.baselibrary.ui.widget.helper.base

import android.graphics.Canvas
import android.support.v7.widget.RecyclerView

abstract class BaseItemSlideTouchHelperAdapterCallBack {
  val defaultAnimationDuration = 100L

  abstract fun onItemMove(fromPosition: Int, toPosition: Int): Boolean

  abstract fun onItemSwiped(holder: RecyclerView.ViewHolder?, toPosition: Int)

  open fun onChildDraw(
    c: Canvas,
    recyclerView: RecyclerView,
    viewHolder: RecyclerView.ViewHolder,
    dX: Float,
    dY: Float,
    actionState: Int,
    isCurrentlyActive: Boolean
  ) {
    viewHolder.itemView.translationX = dX
    viewHolder.itemView.translationY = dY
  }

  open fun getSwipeThreshold() = 0.2f

  open fun getMoveThreshold() = 0.9f

  open fun getSwipeEscapeVelocity() = 0f

  open fun getAnimationDuration(
    recyclerView: RecyclerView,
    animationType: Int,
    animateDx: Float,
    animateDy: Float
  ): Long = defaultAnimationDuration
}

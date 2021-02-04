package org.markensic.baselibrary.ui.widget.helper

import android.graphics.Canvas
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import org.markensic.baselibrary.ui.widget.helper.base.BaseItemSlideTouchHelperAdapterCallBack

class ItemSlideTouchCallBack(
  val adapter: BaseItemSlideTouchHelperAdapterCallBack,
  val effectiveDragFlag: Int = STATE_IDLE_FLAG,
  val effectiveSwipeFlag: Int = STATE_IDLE_FLAG
) : ItemTouchHelper.Callback() {

  val tag = this.javaClass.simpleName

  companion object {
    val UP_FLAG = ItemTouchHelper.UP
    val DOWN_FLAG = ItemTouchHelper.DOWN
    val LEFT_FLAG = ItemTouchHelper.LEFT
    val RIGHT_FLAG = ItemTouchHelper.RIGHT
    val VERTICAL_FLAG = ItemTouchHelper.UP or ItemTouchHelper.DOWN
    val HORIZONTAL_FLAG = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT

    val STATE_IDLE_FLAG = ItemTouchHelper.ACTION_STATE_IDLE
  }

  override fun getMovementFlags(
    recyclerView: RecyclerView,
    viewHolder: RecyclerView.ViewHolder
  ): Int {
    return makeMovementFlags(effectiveDragFlag, effectiveSwipeFlag)
  }

  override fun onMove(
    recyclerView: RecyclerView,
    viewHolder: RecyclerView.ViewHolder,
    target: RecyclerView.ViewHolder
  ): Boolean {
    return adapter.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
  }

  override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    adapter.onItemSwiped(viewHolder, viewHolder.adapterPosition)
  }

  override fun onChildDraw(
    c: Canvas,
    recyclerView: RecyclerView,
    viewHolder: RecyclerView.ViewHolder,
    dX: Float,
    dY: Float,
    actionState: Int,
    isCurrentlyActive: Boolean
  ) {
//        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    adapter.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
  }

  override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
    return adapter.getSwipeThreshold()
  }

  override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
    return if (adapter.getSwipeEscapeVelocity() == 0f) {
      defaultValue
    } else {
      adapter.getSwipeEscapeVelocity()
    }
  }

  override fun getMoveThreshold(viewHolder: RecyclerView.ViewHolder): Float {
    return adapter.getMoveThreshold()
  }

  override fun getAnimationDuration(
    recyclerView: RecyclerView,
    animationType: Int,
    animateDx: Float,
    animateDy: Float
  ): Long {
    return adapter.getAnimationDuration(recyclerView, animationType, animateDx, animateDy)
  }

  override fun isItemViewSwipeEnabled(): Boolean {
    return (effectiveSwipeFlag == LEFT_FLAG
      || effectiveSwipeFlag == RIGHT_FLAG
      || effectiveSwipeFlag == HORIZONTAL_FLAG)
  }

  override fun isLongPressDragEnabled(): Boolean {
    return (effectiveDragFlag == UP_FLAG
      || effectiveDragFlag == DOWN_FLAG
      || effectiveDragFlag == VERTICAL_FLAG)
  }
}

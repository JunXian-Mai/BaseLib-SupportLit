package org.markensic.baselibrary.ui.widget

import android.content.Context
import android.support.v4.app.Fragment
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import org.markensic.baselibrary.R
import org.markensic.baselibrary.framework.ui.BaseUiView

class TabView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) :
  LinearLayout(context, attrs, defStyleAttr), BaseUiView {

  val icon by bindView<ImageView>(R.id.iv_tab_icon)
  val label by bindView<TextView>(R.id.tv_tab_label)

  init {
    LayoutInflater.from(context).inflate(R.layout.item_tab, this, true)
  }

  fun fillView(item: TabItem) {
    icon.setImageResource(item.iconResId)
    label.setText(item.labelResId)
    label.setTextColor(getResources().getColorStateList(item.labelColorResId))
  }

  class TabItem(
    val iconResId: Int,
    val labelResId: Int,
    val labelColorResId: Int,
    val tabFragmentClz: Class<out Fragment>
  )
}



package org.markensic.baselibrary.widget

import android.content.Context
import android.support.v4.app.Fragment
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import org.markensic.baselibrary.R
import org.markensic.baselibrary.impl.ui.BaseUiView
import java.text.FieldPosition

class TabItem(val iconResId: Int, val labelResId: Int, val labelColorResId: Int, val tabFragmentClz: Class<out Fragment>?)

class TabView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0):
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
}

class TabLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0):
    LinearLayout(context, attrs, defStyleAttr), BaseUiView {
    init {
        orientation = LinearLayout.HORIZONTAL
    }

    var tabCounts: Int = 0
    var selectView: View? = null

    fun initTab(tabs: MutableList<TabItem>, firstPos: Int = 0, onTabClick: (TabItem) -> Unit) {
        val params = LayoutParams(0, LayoutParams.MATCH_PARENT)
        params.weight = 1F
        tabCounts = tabs.size
        if (tabCounts > 0) {
            tabs.forEach { item ->
                TabView(context).apply {
                    tag = item
                    fillView(item)
                    setOnClickListener {
                        setCurrentTab(tabs.indexOf(item))
                        onTabClick(it.tag as TabItem)
                    }
                    this@TabLayout.addView(this, params)
                }
            }
        }
        setCurrentTab(firstPos)
    }

    fun setCurrentTab(position: Int) {
        if (position in 0 until tabCounts) {
            getChildAt(position).apply {
                if (this != selectView) {
                    isSelected = true
                    selectView?.isSelected = false
                    selectView = this
                }
            }
        }
    }
}
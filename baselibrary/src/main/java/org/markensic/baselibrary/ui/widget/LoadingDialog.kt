package org.markensic.baselibrary.ui.widget

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import org.markensic.baselibrary.R
import org.markensic.baselibrary.framework.ui.BaseUiView
import org.markensic.baselibrary.global.ActivityManager
import java.lang.ref.WeakReference

class LoadingDialog private constructor(context: Context) : Dialog(context), BaseUiView {
  private val root = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null)
  private val img by root.bindView<ImageView>(R.id.dialog_loading_img)

  init {
    setContentView(root)
    initView()
  }

  private fun initView() {
    val windowManager = window?.windowManager
    val outMetrics = DisplayMetrics()
    windowManager?.defaultDisplay?.getMetrics(outMetrics)
    window?.attributes?.gravity = Gravity.CENTER
    window?.attributes?.alpha = 0.3f
    window?.attributes?.width = outMetrics.widthPixels / 5
    window?.attributes?.height = window?.attributes?.width
    setCancelable(false)
  }

  private fun startLoadingAnimation() {
    img.background.also {
      if (it is AnimationDrawable && !it.isRunning) {
        it.start()
      }
    }
  }

  fun setLoaingDrawable(d: Drawable) {
    img.setImageDrawable(d)
  }

  override fun show() {
//        clearWindowFoucus()
    super.show()
    startLoadingAnimation()
//        hideNavigationBar()
  }

  private fun hideNavigationBar() {
    window?.setFlags(
      WindowManager.LayoutParams.FLAG_FULLSCREEN,
      WindowManager.LayoutParams.FLAG_FULLSCREEN
    );
    window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    window?.decorView?.setOnSystemUiVisibilityChangeListener {
      val uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or  //布局位于状态栏下方
        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or  //全屏
        View.SYSTEM_UI_FLAG_FULLSCREEN or  //隐藏导航栏
        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
      window?.decorView?.systemUiVisibility = uiOptions
    }
    window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
  }

  private fun clearWindowFoucus() {
    window?.setFlags(
      WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
      WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
    );
  }

  companion object {
    private var drawable: Drawable? = null
    private var dialog: LoadingDialog? = null
    private var weaklasterAct: WeakReference<Activity>? = null

    fun setLoaingDrawable(d: Drawable): LoadingDialog.Companion {
      drawable = d
      return this
    }

    fun loading() {
      runOnUiThread {
        cancelLoading()
        if (weaklasterAct?.get() != it) {
          weaklasterAct?.clear()
          weaklasterAct = WeakReference(it)
          dialog = weaklasterAct?.get()?.let { LoadingDialog(it) }
        }
        if (dialog?.isShowing == false) {
          drawable?.also {
            dialog?.setLoaingDrawable(it)
          }
          dialog?.show()
        }
      }
    }

    fun cancelLoading() {
      runOnUiThread {
        if (dialog?.isShowing == true) {
          dialog?.dismiss()
        }
      }
    }

    private fun runOnUiThread(f: (act: Activity) -> Unit) {
      ActivityManager.weakLasterActivity?.get()?.also {
        it.runOnUiThread {
          f(it)
        }
      }
    }
  }
}

package org.markensic.demo.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import org.markensic.baselibrary.global.AppLog
import org.markensic.demo.R

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    private lateinit var rootView: View

    private lateinit var logIoTest: Button
    private lateinit var crashLogTest: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        rootView = inflater.inflate(R.layout.main_fragment, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel

        logIoTest = rootView.findViewById(R.id.log_io_test)
        crashLogTest = rootView.findViewById(R.id.crash_log_test)

        logIoTest.setOnClickListener {
            Thread{
                for (t in 1..10000000000) {
                    AppLog.e("AndroidJUnit4", "Thread1 count $t")
                    val sleepTime = 20 * Math.random() + 1
                    Thread.sleep(sleepTime.toLong())
                }
            }.start()
            Thread{
                for (t in 10000000001..20000000000) {
                    AppLog.e("AndroidJUnit4", "Thread2 count $t")
                    val sleepTime = 20 * Math.random() + 1
                    Thread.sleep(sleepTime.toLong())
                }
            }.start()
            Thread{
                for (t in 20000000001..30000000000) {
                    AppLog.e("AndroidJUnit4", "Thread3 count $t")
                    val sleepTime = 20 * Math.random() + 1
                    Thread.sleep(sleepTime.toLong())
                }
            }.start()
        }

        crashLogTest.setOnClickListener {
            val nullString: String? = null
            Log.e(":test", nullString!!)
        }
    }

}
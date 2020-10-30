package org.markensic.demo.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import org.markensic.baselibrary.api.utils.XmlParserUtils
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
    private lateinit var xmlParserTest: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rootView = inflater.inflate(R.layout.main_fragment, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel

        logIoTest = rootView.findViewById(R.id.log_io_test)
        crashLogTest = rootView.findViewById(R.id.crash_log_test)
        xmlParserTest = rootView.findViewById(R.id.xml_parser_test)


        logIoTest.setOnClickListener {
            Thread {
                for (t in 1..10000000000) {
                    AppLog.e("AndroidJUnit4", "Thread1 count $t")
                    val sleepTime = 20 * Math.random() + 1
                    Thread.sleep(sleepTime.toLong())
                }
            }.start()
            Thread {
                for (t in 10000000001..20000000000) {
                    AppLog.e("AndroidJUnit4", "Thread2 count $t")
                    val sleepTime = 20 * Math.random() + 1
                    Thread.sleep(sleepTime.toLong())
                }
            }.start()
            Thread {
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

        xmlParserTest.setOnClickListener {
            testXmlParser()
        }
    }

    //region xml解析为json
    fun testXmlParser() {
        val xmlDate = """
            <?xml version="1.0" encoding="UTF-8"?>
            <Transaction>
              <Transaction_Header>
                <tran_response>
                  <QJGZH><![CDATA[1010111681599113654421152]]></QJGZH>
                  <ZQLSH><![CDATA[0200003550]]></ZQLSH>
                  <status><![CDATA[FAIL]]></status>
                  <POS_RSP_CODE><![CDATA[96]]></POS_RSP_CODE>
                </tran_response>
                <transaction_id><![CDATA[POSJHZF08]]></transaction_id>
                <SYS_EVT_TRACE_ID><![CDATA[1010111681599113654421152]]></SYS_EVT_TRACE_ID>
              </Transaction_Header>
              <Transaction_Body>
                <response>
                  <trade_state_desc><![CDATA[0200003550,[TBL_JHZF_TRAN]流水记录不存在]]></trade_state_desc>
                  <bank_type/>
                  <MERCHANT_CODE><![CDATA[105000153110676]]></MERCHANT_CODE>
                  <POSB_TERM_NO><![CDATA[10040255]]></POSB_TERM_NO>
                  <BATCH_NO/>
                  <Rtrvl_Ref_No/>
                  <POS_TRC_NO/>
                  <Acpt_Lnd_Txn_Tm/>
                  <Acpt_Lnd_Txn_Dt/>
                  <OnLn_Py_Txn_Ordr_ID/>
                  <S2P_TRANS_TYPE/>
                  <Pos_Txn_Status><![CDATA[FA]]></Pos_Txn_Status>
                  <Cst_AccNo/>
                  <Ahn_TxnAmt/>
                  <CardNo_Inpt_MtdCd/>
                  <POS_ID><![CDATA[1050001531106760014]]></POS_ID>
                </response>
              </Transaction_Body>
            </Transaction>
            """
        val json = XmlParserUtils.pullTransactionXml(xmlDate)
        AppLog.e("XML", "json -> $json");
    }
    //endregion
}
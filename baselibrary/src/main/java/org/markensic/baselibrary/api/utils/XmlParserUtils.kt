package org.markensic.baselibrary.api.utils

import android.util.Xml
import org.json.JSONObject
import org.markensic.baselibrary.global.AppLog
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.ByteArrayInputStream
import java.io.IOException
import java.util.*

object XmlParserUtils {
    const val TAG = "XMLUtil"

    @Throws(XmlPullParserException::class, IOException::class)
    fun pullTransactionXml(xmlDate: String?): String? {
        val mapXml = mutableMapOf<String, Any?>()
        if (xmlDate?.isNotBlank() == true) {
            val date = xmlDate.replace(" ", "").replace("\n", "").replace("\r", "")
            val input = ByteArrayInputStream(date.toByteArray(Charsets.UTF_8))
            Xml.newPullParser().apply {
                val parent = Stack<String>()
                val values = mutableMapOf<String, Any?>()
                setInput(input, "UTF-8")
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    when (eventType) {
                        XmlPullParser.START_TAG -> {
                            AppLog.i(TAG, "$name START_TAG")
                            parent.push(name)
                            values[name] = ""
                        }
                        XmlPullParser.TEXT -> {
                            AppLog.i(TAG, "$name TEXT")
                            if (text?.isNotBlank() == true) {
                                values.put(parent.peek(), text)
                            }
                        }
                        XmlPullParser.END_TAG -> {
                            AppLog.i(TAG, "$name END_TAG")
                            val child = parent.pop()
                            if (!parent.empty()) {
                                when (values[parent.peek()]) {
                                    is String -> {
                                        val map = mutableMapOf<String, Any?>()
                                        map[child] = values[child]
                                        values[parent.peek()] = map
                                    }
                                    is MutableMap<*, *> -> {
                                        val parentValue: MutableMap<String, Any?> = values[parent.peek()] as MutableMap<String, Any?>
                                        parentValue[child] = values[child]
                                    }
                                }
                            } else {
                                mapXml[child] = values[child]
                            }
                        }
                    }
                    next()
                }
            }
        }
        return JSONObject(mapXml).toString()
    }
}
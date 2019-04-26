package com.ketian.android.silkv3

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ketian.android.silkv3.jni.JNI
import kotlinx.android.synthetic.main.fragment_voice.*
import java.io.File
import java.util.*

/**
 * Created by ketian.
 */
class VoiceFragment : Fragment() {

    private var mTask: LoadTask? = null
    private var mAdapter: ItemAdapter? = null
    private val mItems = ArrayList<String>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_voice, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = ItemAdapter(requireContext(), mItems).apply {
                mAdapter = this
            }
            initData()
        }
    }

    private fun initData() {
        mTask?.cancel(true)
        mTask = LoadTask(requireContext())
        mTask?.execute()
    }

    private inner class LoadTask(private val mContext: Context) : AsyncTask<Void, Void, List<String>>() {

        override fun onPreExecute() {

        }

        override fun doInBackground(vararg voids: Void): List<String> {
            val paths = PathUtils.getVoiceFiles_WeChat(mContext)
            Log.e("doInBackground", "${paths}")
            val voicePaths = ArrayList<String>()

            if (paths.size > 0) {
                var file: File?
                for (path in paths) {
                    file = File(path)
                    if (file.exists() && file.isDirectory) {
                        val stack = Stack<String>()
                        stack.push(path)
                        while (!stack.empty()) {
                            var fs: Array<File>? = null
                            val parent = stack.pop()
                            if (parent != null) {
                                file = File(parent)
                                if (file.isDirectory) { // ignore file, FIXME
                                    fs = file.listFiles()
                                } else {
                                    continue
                                }
                            }
                            if (fs == null || fs.isEmpty()) continue
                            for (i in fs.indices) {
                                val name = fs[i].name
                                if (fs[i].isDirectory && name != "."
                                        && name != "..") {
                                    stack.push(fs[i].path)
                                } else if (fs[i].isFile) {
                                    if (name.endsWith(".amr")) {
                                        voicePaths.add(fs[i].absolutePath)
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return voicePaths
        }

        override fun onPostExecute(strings: List<String>) {
            mItems.clear()
            mItems.addAll(strings)
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mTask != null) {
            mTask!!.cancel(true)
        }
    }

    private class ItemAdapter(private val mContext: Context, private val paths: List<String>?) : RecyclerView.Adapter<ItemAdapter.VH>() {

        private val listener = View.OnClickListener { view ->
            val index = view.tag as Int
            val path = paths?.get(index) ?: return@OnClickListener
            val file = File(path)
            if (file.exists() && file.canRead()) {
                val dest = PathUtils.getExportDir(mContext) + "/" + file.name + ".mp3"
                val temp = PathUtils.getExportDir(mContext) + "/temp_" + file.name + ".mp3"
                val rlt = JNI.convert(path, dest, temp)
                (mContext as? MainActivity)?.wechatVoiceDecodeResult(rlt,dest)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.VH {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
            return VH(view)
        }

        override fun onBindViewHolder(holder: ItemAdapter.VH, position: Int) {
            holder.textView.text = paths!![position]
            holder.button.tag = position
            holder.button.setOnClickListener(listener)
        }

        override fun getItemCount(): Int {
            return paths?.size ?: 0
        }

        class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var textView: TextView
            var button: Button

            init {
                textView = itemView.findViewById<View>(R.id.title) as TextView
                button = itemView.findViewById<View>(R.id.click) as Button
            }
        }
    }
}

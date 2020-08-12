package com.example.fintectapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fintectapp.R
import com.example.fintectapp.ui.main.contents.HistoryContent
import kotlinx.android.synthetic.main.fragment_auth_list.*
import kotlinx.android.synthetic.main.fragment_history_list.*

/**
 * A fragment representing a list of Items.
 */
class HistoryFragment : Fragment() {

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history_list, container, false)
        Log.e("OnCreateView HIST","RecyclerView making")
        val mRecyclerView = view.findViewById<RecyclerView>(R.id.list2)
        // Set the adapter
        if (mRecyclerView is RecyclerView) {
            with(mRecyclerView) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                val hello = HistoryContent
//                Thread.sleep(100)
                Log.e("LOAD ITEMS IN HISTORY", "${hello.ITEMS}")
                adapter = MyItemRecyclerViewAdapter2(HistoryContent.ITEMS)
                //클릭리스너 등록
                (adapter as MyItemRecyclerViewAdapter2).setItemClickListener( object : MyItemRecyclerViewAdapter2.ItemClickListener{
                    override fun onClick(view: View, position: Int) {
                        Log.d("SSS", "${position}번 리스트 선택")
                        val intent = Intent(requireContext(), ResultDetailActivity::class.java)
                        startActivity(intent)
                    }
                })
            }
        } else {
            Log.e("NOOO", "NOO")
        }
        return view
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        var ft = parentFragmentManager.beginTransaction();
        Log.e("ONACTIVITY HISTORY", "RESULT")

//        ft.detach(this).attach(this).commit()
        swipe2.setOnRefreshListener {
            Log.e("LOG_TAG", "onRefresh called from SwipeRefreshLayout")
            refresh()
        }
    }
    private fun refresh() {
        val ft = parentFragmentManager.beginTransaction();
        ft.detach(this).attach(this).commit()
    }
    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            HistoryFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
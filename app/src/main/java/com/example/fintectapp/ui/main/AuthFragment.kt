package com.example.fintectapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fintectapp.R
import com.example.fintectapp.ui.main.contents.AuthContent
import com.example.fintectapp.ui.main.contents.HistoryContent
import kotlinx.android.synthetic.main.fragment_auth_list.*

/**
 * A fragment representing a list of Items.
 */
class AuthFragment : Fragment() {

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
        val view = inflater.inflate(R.layout.fragment_auth_list, container, false)
        Log.e("OnCreateView AUTH","RecyclerView making")
        val mRecyclerView = view.findViewById<RecyclerView>(R.id.list)

        // Set the adapter
        if (mRecyclerView is RecyclerView) {
            with(mRecyclerView) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                val hello = AuthContent
                Log.e("LOAD ITEMS In AUTH", "${hello.ITEMS}")
                adapter = MyItemRecyclerViewAdapter(AuthContent.ITEMS)
                //클릭리스너 등록
                (adapter as MyItemRecyclerViewAdapter).setItemClickListener( object : MyItemRecyclerViewAdapter.ItemClickListener{
                    override fun onClick(view: View, position: Int) {
                        Log.d("SSS", "${position}번 리스트 선택")
                        val intent = Intent(requireContext(), UploadAgreementActivity::class.java)
                        val selected = AuthContent.ITEMS[position]
                        intent.putExtra("nameKey", selected.content)
                        startActivity(intent)

                    }
                })
                (adapter as MyItemRecyclerViewAdapter).notifyDataSetChanged()

            }
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        var ft = parentFragmentManager.beginTransaction();
        Log.e("ONACTIVITY AUTH", "RESULT")

//        ft.detach(this).attach(this).commit()
        swipe.setOnRefreshListener {
            Log.e("LOG_TAG", "onRefresh called from SwipeRefreshLayout")
            refresh()
        }
    }

    private fun refresh() {
        HistoryContent.refreshHistDB()
        AuthContent.refreshAuthDB()
        Thread.sleep(1000)
        val ft = parentFragmentManager.beginTransaction();
        ft.detach(this).attach(this).commit()
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            AuthFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
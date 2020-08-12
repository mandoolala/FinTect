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
import com.example.fintectapp.ui.main.contents.AuthContent

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

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                val hello = AuthContent
                Log.e("LOAD ITEMS", "${hello.ITEMS}")
                adapter = MyItemRecyclerViewAdapter(AuthContent.ITEMS)
                //클릭리스너 등록
                (adapter as MyItemRecyclerViewAdapter).setItemClickListener( object : MyItemRecyclerViewAdapter.ItemClickListener{
                    override fun onClick(view: View, position: Int) {
                        Log.d("SSS", "${position}번 리스트 선택")
                        val intent = Intent(requireContext(), UploadActivity::class.java)
                        val selected = AuthContent.ITEMS[position]
                        intent.putExtra("nameKey", selected.content)
                        startActivity(intent)

                    }
                })
            }
        }

        return view
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
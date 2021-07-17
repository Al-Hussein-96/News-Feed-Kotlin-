package com.test.beln.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.test.beln.R

import com.test.beln.databinding.FragmentNewsFeedBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 * Use the [NewsFeedFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class NewsFeedFragment : Fragment() {
    private val viewModel by viewModels<NewsViewModel>()


    private lateinit var viewBinding: FragmentNewsFeedBinding

    private lateinit var listAdapter: NewsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding = FragmentNewsFeedBinding.inflate(inflater, container, false);
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListAdapter()

    }

    private fun setupListAdapter() {
        Timber.tag("Mohammad").i("setupAdapter")
        Timber.tag("Mohammad").i("items: %s", viewModel.items.value?.size)
        listAdapter = NewsAdapter(viewModel)
        viewBinding.recyclerView.adapter = listAdapter

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NewsFeedFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            NewsFeedFragment().apply {

            }
    }
}
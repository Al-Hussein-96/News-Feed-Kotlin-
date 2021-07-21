package com.test.beln.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer

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


    private lateinit var viewDataBinding: FragmentNewsFeedBinding

    private lateinit var listAdapter: NewsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewDataBinding = FragmentNewsFeedBinding.inflate(inflater,container,false).apply {
            viewmodel = viewModel
        }
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner


        setupListAdapter()



        viewDataBinding.fab.setOnClickListener {
            viewModel.items.observe(viewLifecycleOwner, Observer {
                Timber.tag("Mohammad").i("Hello World")
            })
        }

    }

    private fun setupListAdapter() {
        val viewModel = viewDataBinding.viewmodel

        if(viewModel != null){
            listAdapter = NewsAdapter(viewModel)
            viewDataBinding.recyclerView.adapter = listAdapter
        }else{
            Timber.tag("Mohammad").w("ViewModel not initialized when attempting to set up adapter.")

        }



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
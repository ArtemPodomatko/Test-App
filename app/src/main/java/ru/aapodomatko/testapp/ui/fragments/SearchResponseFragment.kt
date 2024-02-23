package ru.aapodomatko.testapp.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.aapodomatko.testapp.adapters.SearchAdapter
import ru.aapodomatko.testapp.databinding.FragmentSearchResponseBinding
import ru.aapodomatko.testapp.viewmodels.SearchResponseFragmentViewModel

@AndroidEntryPoint
class SearchResponseFragment : Fragment() {
    private var _binding: FragmentSearchResponseBinding? = null
    private val mBinding get() = _binding!!

    private val bundleArgs by navArgs<SearchResponseFragmentArgs>()
    private val viewModel by viewModels<SearchResponseFragmentViewModel>()

    private lateinit var searchAdapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchResponseBinding.inflate(layoutInflater, container, false)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        viewModel.searchNews(bundleArgs.queryParams)
        viewModel.searchNewsResponse.observe(viewLifecycleOwner) {
            searchAdapter.differ.submitList(it.articles)
        }

        searchAdapter.setOnClickListener { article ->
            try {
                Intent()
                    .setAction(Intent.ACTION_VIEW)
                    .addCategory(Intent.CATEGORY_BROWSABLE)
                    .setData(Uri.parse(takeIf { URLUtil.isValidUrl(article.url) }
                        ?.let {
                            article.url
                        } ?: "https://google.com"
                    ))
                    .let {
                        ContextCompat.startActivity(requireContext(), it, null)
                    }
            } catch (e: Exception) {
                Toast.makeText(
                    context,
                    "The device doesn't have any browser to view the document",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        mBinding.iconBack.setOnClickListener { findNavController().navigateUp() }
    }

    private fun initAdapter() {
        searchAdapter = SearchAdapter()
        mBinding.searchRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = searchAdapter
        }
    }

}
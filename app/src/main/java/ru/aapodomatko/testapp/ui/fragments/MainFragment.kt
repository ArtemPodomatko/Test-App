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
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.aapodomatko.testapp.R
import ru.aapodomatko.testapp.adapters.BusinessAdapter
import ru.aapodomatko.testapp.adapters.EntertainmentAdapter
import ru.aapodomatko.testapp.adapters.GeneralAdapter
import ru.aapodomatko.testapp.adapters.HealthAdapter
import ru.aapodomatko.testapp.adapters.ScienceAdapter
import ru.aapodomatko.testapp.adapters.SportsAdapter
import ru.aapodomatko.testapp.adapters.TechnologyAdapter
import ru.aapodomatko.testapp.databinding.FragmentMainBinding
import ru.aapodomatko.testapp.models.Article
import ru.aapodomatko.testapp.viewmodels.MainFragmentViewModel

@AndroidEntryPoint
class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val mBinding get() = _binding!!

    private val viewModel by viewModels<MainFragmentViewModel>()
    private lateinit var businessAdapter: BusinessAdapter
    private lateinit var entertainmentAdapter: EntertainmentAdapter
    private lateinit var generalAdapter: GeneralAdapter
    private lateinit var healthAdapter: HealthAdapter
    private lateinit var scienceAdapter: ScienceAdapter
    private lateinit var sportsAdapter: SportsAdapter
    private lateinit var technologyAdapter: TechnologyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapters()
        observeLiveData()

        businessAdapter.setOnClickListener { article ->
            goSite(article)
        }
        entertainmentAdapter.setOnClickListener { article ->
            goSite(article)
        }
        generalAdapter.setOnClickListener { article ->
            goSite(article)
        }
        healthAdapter.setOnClickListener { article ->
            goSite(article)
        }
        scienceAdapter.setOnClickListener { article ->
            goSite(article)
        }
        sportsAdapter.setOnClickListener { article ->
            goSite(article)
        }
        technologyAdapter.setOnClickListener { article ->
            goSite(article)
        }

        mBinding.searchView.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    findNavController().navigate(
                        R.id.action_mainFragment_to_searchResponseFragment,
                        bundleOf("query_params" to query)
                    )
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })


    }

    private fun goSite(article: Article) {
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

    private fun initAdapters() {
        businessAdapter = BusinessAdapter()
        entertainmentAdapter = EntertainmentAdapter()
        generalAdapter = GeneralAdapter()
        healthAdapter = HealthAdapter()
        scienceAdapter = ScienceAdapter()
        sportsAdapter = SportsAdapter()
        technologyAdapter = TechnologyAdapter()

        mBinding.apply {
            businessRecyclerView.apply {
                layoutManager = LinearLayoutManager(
                    requireContext(), LinearLayoutManager.HORIZONTAL, false
                )
                adapter = businessAdapter
            }
            entertainmentRecyclerView.apply {
                layoutManager = LinearLayoutManager(
                    requireContext(), LinearLayoutManager.HORIZONTAL, false
                )
                adapter = entertainmentAdapter
            }
            generalRecyclerView.apply {
                layoutManager = LinearLayoutManager(
                    requireContext(), LinearLayoutManager.HORIZONTAL, false
                )
                adapter = generalAdapter
            }
            healthRecyclerView.apply {
                layoutManager = LinearLayoutManager(
                    requireContext(), LinearLayoutManager.HORIZONTAL, false
                )
                adapter = healthAdapter
            }
            scienceRecyclerView.apply {
                layoutManager = LinearLayoutManager(
                    requireContext(), LinearLayoutManager.HORIZONTAL, false
                )
                adapter = scienceAdapter
            }
            sportsRecyclerView.apply {
                layoutManager = LinearLayoutManager(
                    requireContext(), LinearLayoutManager.HORIZONTAL, false
                )
                adapter = sportsAdapter
            }
            technologyRecyclerView.apply {
                layoutManager = LinearLayoutManager(
                    requireContext(), LinearLayoutManager.HORIZONTAL, false
                )
                adapter = technologyAdapter
            }
        }
    }

    private fun observeLiveData() {
        viewModel.businessNewsLiveData.observe(viewLifecycleOwner) {
            businessAdapter.differ.submitList(it.articles)
        }
        viewModel.entertainmentNewsLiveData.observe(viewLifecycleOwner) {
            entertainmentAdapter.differ.submitList(it.articles)
        }
        viewModel.generalNewsLiveData.observe(viewLifecycleOwner) {
            generalAdapter.differ.submitList(it.articles)
        }
        viewModel.healthNewsLiveData.observe(viewLifecycleOwner) {
            healthAdapter.differ.submitList(it.articles)
        }
        viewModel.scienceNewsLiveData.observe(viewLifecycleOwner) {
            scienceAdapter.differ.submitList(it.articles)
        }
        viewModel.sportsNewsLiveData.observe(viewLifecycleOwner) {
            sportsAdapter.differ.submitList(it.articles)
        }
        viewModel.technologyNewsLiveData.observe(viewLifecycleOwner) {
            technologyAdapter.differ.submitList(it.articles)
        }
    }


}
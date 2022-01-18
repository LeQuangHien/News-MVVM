package com.hienle.thenews.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hienle.thenews.databinding.FragmentNewsBinding
import com.hienle.thenews.util.launchAndRepeatWithViewLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


@AndroidEntryPoint
class NewsFragment : Fragment() {

    private val viewModel: NewsViewModel by viewModels()
    private var _binding: FragmentNewsBinding? = null

    private lateinit var recyclerViewNews: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var newsAdapter: NewsAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        recyclerViewNews = binding.recyclerviewNews
        progressBar = binding.progressBar
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewNews.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        newsAdapter = NewsAdapter(requireContext())
        val mDividerItemDecoration = DividerItemDecoration(
            requireContext(),
            LinearLayoutManager.VERTICAL
        )
        recyclerViewNews.addItemDecoration(mDividerItemDecoration)
        recyclerViewNews.adapter = newsAdapter

        viewModel.getTopHeadlines()

        launchAndRepeatWithViewLifecycle {
            launch {
                viewModel.uiState.collect {
                    // Update UI elements
                    newsAdapter.addAll(it.newsItems)
                }
            }

            launch {
                // Bind the visibility of the progressBar to the state
                // of isFetchingArticles.
                viewModel.uiState
                    .map { it.isFetchingArticles }
                    .distinctUntilChanged()
                    .collect { binding.progressBar.isVisible = it }
            }

            launch {
                viewModel.uiState.collect { uiState ->
                    uiState.userMessages.firstOrNull()?.let { userMessage ->
                        // TODO: Show Snackbar with userMessage.
                        // Once the message is displayed and
                        // dismissed, notify the ViewModel.
                        viewModel.userMessageShown(userMessage.id)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
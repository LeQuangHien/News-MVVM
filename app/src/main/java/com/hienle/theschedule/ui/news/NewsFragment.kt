package com.hienle.theschedule.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hienle.theschedule.databinding.FragmentNewsBinding
import com.hienle.theschedule.util.launchAndRepeatWithViewLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFragment : Fragment() {

    companion object {
        fun newInstance() = NewsFragment()
    }

    private val viewModel: NewsViewModel by viewModels()
    private var _binding: FragmentNewsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* // The expand section event is processed by the UI that
         // modifies a View's internal state.
         binding.expandButton.setOnClickListener {
             binding.expandedSection.visibility = View.VISIBLE
         }*/

        viewModel.getTopHeadlines()

        // The refresh event is processed by the ViewModel that is in charge
        // of the business logic.
      /*  binding.buttonRefresh.setOnClickListener {
            viewModel.refreshNews(Util.isOnline(requireContext()))
        }
*/
        launchAndRepeatWithViewLifecycle {
            launch {
                viewModel.uiState.collect {
                    // Update UI elements
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
package company.bestmovies.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import company.bestmovies.databinding.MainFragmentBinding
import company.bestmovies.ui.main.models.MovieReviewsEvent
import company.bestmovies.ui.main.models.MovieReviewsViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var _binding: MainFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: MovieReviewsViewModel by viewModels()

    private lateinit var recViewAdapter: RecViewMovieReviews

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            recViewAdapter = RecViewMovieReviews()
            listOfReviews.adapter = recViewAdapter
            listOfReviews.layoutManager = LinearLayoutManager(requireContext())
            listOfReviews.addItemDecoration(RecViewMovieReviews.ItemDecoration(48, 32))
        }

        viewModel.obtainEvent(MovieReviewsEvent.EnterScreen)

        lifecycleScope.launchWhenStarted {
            viewModel.viewState.collect {
                when (it) {
                    is MovieReviewsViewState.Display -> {
                        binding.shimmerViewContainer.hideShimmer()
                        recViewAdapter.setData(it.reviews)
                    }
                    is MovieReviewsViewState.Error -> {
                        binding.shimmerViewContainer.hideShimmer()
                    }
                    is MovieReviewsViewState.Loading -> {
                        binding.shimmerViewContainer.startShimmer()
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
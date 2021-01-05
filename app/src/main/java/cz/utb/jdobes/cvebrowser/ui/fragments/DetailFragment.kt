package cz.utb.jdobes.cvebrowser.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import cz.utb.jdobes.cvebrowser.R
import cz.utb.jdobes.cvebrowser.databinding.FragmentDetailBinding
import cz.utb.jdobes.cvebrowser.ui.MainActivity
import cz.utb.jdobes.cvebrowser.viewmodels.DetailViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by lazy {
        ViewModelProvider(this).get(DetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Enable back button on this fragment
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val binding: FragmentDetailBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_detail,
                container,
                false)
        binding.setLifecycleOwner(viewLifecycleOwner)

        binding.viewModel = viewModel

        // Set header
        (activity as MainActivity).supportActionBar?.title = getString(R.string.detail_fragment_header)
        viewModel.loadCve((activity as MainActivity).currentCveDetail)

        return binding.root
    }
}
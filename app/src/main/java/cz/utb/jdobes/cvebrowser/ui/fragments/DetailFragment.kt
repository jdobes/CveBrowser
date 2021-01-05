package cz.utb.jdobes.cvebrowser.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import cz.utb.jdobes.cvebrowser.R
import cz.utb.jdobes.cvebrowser.ui.MainActivity

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Enable back button on this fragment
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }
}
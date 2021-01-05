package cz.utb.jdobes.cvebrowser.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cz.utb.jdobes.cvebrowser.R
import cz.utb.jdobes.cvebrowser.databinding.CveItemBinding
import cz.utb.jdobes.cvebrowser.databinding.FragmentListBinding
import cz.utb.jdobes.cvebrowser.domain.Cve
import cz.utb.jdobes.cvebrowser.ui.MainActivity
import cz.utb.jdobes.cvebrowser.viewmodels.ListViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ListFragment : Fragment() {

    private val viewModel: ListViewModel by lazy {
        ViewModelProvider(this).get(ListViewModel::class.java)
    }

    private var viewModelAdapter: CveListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Disable back button on this fragment
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        val binding: FragmentListBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_list,
            container,
            false)
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.setLifecycleOwner(viewLifecycleOwner)

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        viewModelAdapter = CveListAdapter(CveClick {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        })

        val _layoutManager = LinearLayoutManager(context)
        binding.root.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = _layoutManager
            adapter = viewModelAdapter
        }
        // when scrolling fetch next page
        binding.root.findViewById<RecyclerView>(R.id.recycler_view).addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = _layoutManager.childCount
                val pastVisibleItem = _layoutManager.findFirstCompletelyVisibleItemPosition()
                val total = viewModelAdapter!!.itemCount
                //Log.v("CVEBROWSER", "dy: " + dy.toString() + ", visibleItemCount: " + visibleItemCount.toString() + ", pastVisibleItem: " + pastVisibleItem.toString() + ", total: " + total.toString())
                if ((dy > 0) && (viewModel.isLoading.value == false) && ((visibleItemCount+pastVisibleItem) >= total)) {
                    viewModel.page++
                    viewModel.fetchDataFromRepository()
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })

        setHasOptionsMenu(true)

        // Observer for the network error.
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.cves.observe(viewLifecycleOwner, Observer<List<Cve>> { cves ->
            cves?.apply {
                viewModelAdapter?.cves = cves
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}

class CveClick(val block: (Cve) -> Unit) {
    /**
     * Called when a cve is clicked
     *
     * @param cve the cve that was clicked
     */
    fun onClick(cve: Cve) = block(cve)
}

/**
 * RecyclerView Adapter for setting up data binding on the items in the list.
 */
class CveListAdapter(val callback: CveClick) : RecyclerView.Adapter<CveViewHolder>() {

    /**
     * The CVEs that our Adapter will show
     */
    var cves: List<Cve> = emptyList()
        set(value) {
            field = value
            // For an extra challenge, update this to use the paging library.

            // Notify any registered observers that the data set has changed. This will cause every
            // element in our RecyclerView to be invalidated.
            notifyDataSetChanged()
        }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CveViewHolder {
        val withDataBinding: CveItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            CveViewHolder.LAYOUT,
            parent,
            false)
        return CveViewHolder(withDataBinding)
    }

    override fun getItemCount() = cves.size

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     */
    override fun onBindViewHolder(holder: CveViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.cve = cves[position]
            it.cveCallback = callback
        }
    }

}

/**
 * ViewHolder for Cve items. All work is done by data binding.
 */
class CveViewHolder(val viewDataBinding: CveItemBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.cve_item
    }
}
package pet.perpet.equal.presentation.search.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.viewpager.widget.ViewPager
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import pet.perpet.data.nonnull
import pet.perpet.domain.model.search.SearchSimple
import pet.perpet.equal.R
import pet.perpet.equal.databinding.SearchFragmentBinding
import pet.perpet.equal.presentation.observeBindingNotify
import pet.perpet.equal.presentation.observeBindingNotifyNew
import pet.perpet.equal.presentation.search.Channel
import pet.perpet.equal.presentation.search.subscribe
import pet.perpet.equal.presentation.search.viewholder.SearchSimpleViewHolder
import pet.perpet.equal.presentation.search.viewmodel.SearchViewModel
import pet.perpet.framework.fragment.Fragment
import pet.perpet.framework.fragment.OnTabSelectedBehavior
import pet.perpet.framework.widget.Keyboard
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder
import pet.perpet.framework.widget.recyclerview.OnViewHolderEventListener

class SearchFragment : Fragment<SearchViewModel>() {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: SearchFragmentBinding

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        subscribe(this ) {
            viewmodel.keyword = it.keyword
            binding.viewpager.currentItem = 0
            viewmodel.fragmentAdapter.notifyDataSetChanged()
            binding.model = viewmodel
        }

        if(viewmodel.args.keyword.nonnull().isNotEmpty()) {
            viewmodel.keyword = viewmodel.args.keyword.nonnull()
            binding.viewpager.currentItem = 0
            viewmodel.fragmentAdapter.notifyDataSetChanged()
            binding.model = viewmodel
        }

        Channel.keyword(this)?.observe(this) {

            viewmodel.keyword = it.nonnull()
            binding.viewpager.currentItem = 0
            viewmodel.fragmentAdapter.notifyDataSetChanged()
            binding.model = viewmodel
        }

        viewmodel.observeBindingNotify {
            binding.model = viewmodel
        }

        viewmodel.observeBindingNotifyNew {
            hide()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = SearchFragmentBinding.bind(view)
        binding.model = viewmodel

        var fragmentAdapter = viewmodel.fragmentAdapter
        binding.viewpager.run {
            offscreenPageLimit = 2
            adapter = fragmentAdapter
            isSaveEnabled = false
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {
                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int,
                ) {

                }

                override fun onPageSelected(position: Int) {
                }
            })
        }

        binding.tablayout.run {
            val pager = binding.viewpager
            setupWithViewPager(pager)
            addOnTabSelectedListener(object : OnTabSelectedBehavior() {
                override fun dependencyView(): ViewPager {
                    return pager
                }
            })
        }

        viewmodel.searchListDiffer.adapter.setHolderEventListener(object :
            OnViewHolderEventListener {
            override fun onRecyclerViewHolderEvent(
                holder: BaseRecyclerViewHolder<*>,
                id: Int,
                bundle: Bundle
            ) {

                if (holder is SearchSimpleViewHolder) {
                    val item = holder.item
                    if (item is SearchSimple) {
                        viewmodel.keyword =  item.text
                        binding.viewpager.currentItem = 0
                        viewmodel.fragmentAdapter.notifyDataSetChanged()
                        binding.model = viewmodel

                    }
                }
            }
        })


        loadTalentListOnKeyboardActionSearch()

        KeyboardVisibilityEvent.registerEventListener(requireActivity()) {
            if (it) {
                show()
            }
        }

        binding.tvCancel.setOnClickListener {
            hide()
        }

        if(viewmodel.args.type == 1) {
            binding.viewpager.currentItem = 1
        }
    }

    override fun onBackPressed(): Boolean {
        if( binding.clSearch.visibility == View.VISIBLE) {
            hide()
            return false
        }

        if(viewmodel.keyword.nonnull().isEmpty()) {
            return super.onBackPressed()
        } else {
            viewmodel.keyword = ""
            binding.viewpager.currentItem = 0
            viewmodel.fragmentAdapter.notifyDataSetChanged()
            binding.model = viewmodel
        }
        return false

    }

    private fun show() {
        binding.ivBack.visibility = View.GONE
        binding.tvCancel.visibility = View.VISIBLE
        binding.clSearch.visibility = View.VISIBLE
    }

    private fun hide() {
        binding.ivBack.visibility = View.VISIBLE
        binding.tvCancel.visibility = View.GONE
        binding.clSearch.visibility = View.GONE
        Keyboard.hideKeyboard(binding.evSearch)
    }

    private fun searchExecute() {
        viewmodel.fragmentAdapter.notifyDataSetChanged()
    }

    private fun loadTalentListOnKeyboardActionSearch() = binding.evSearch
        .setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                Keyboard.hideKeyboard(binding.evSearch)
                hide()
                searchExecute()
            }
            false
        }

}
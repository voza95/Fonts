package test.example.com.biographyandquotes.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import com.flipboard.bottomsheet.commons.BottomSheetFragment
import wondrousapps.com.fontsandgradient2.R
import wondrousapps.com.fontsandgradient2.adapter.StickersPagerAdapter


class StickerContainerSheetFragment : BottomSheetFragment() {

    private var mParam1: String? = null
    private var mParam2: String? = null
    private var tabLayout: TabLayout? = null

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.bottem_sheet_layout, container, false)
        setupViewPager(view)
        return view
    }

    private fun setupViewPager(view: View) {
        tabLayout = view.findViewById(R.id.sticker_tab_layout) as TabLayout?;
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Emoji"));
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Baby"));
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Feather"));
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Funny"));
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Heart"));
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Love"));
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Magic"));
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Zodiac"));

        val viewPagerAdapter = StickersPagerAdapter(activity.supportFragmentManager, tabLayout!!.tabCount, context)
        val viewPager = view.findViewById(R.id.stickerViewPager)
        //viewPager.adapter = viewPagerAdapter

        //viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout!!.setupWithViewPager(viewPager as ViewPager?)
        tabLayout!!.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {
                if (viewPager != null) {
                    viewPager.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) = Unit

            override fun onTabReselected(tab: TabLayout.Tab) = Unit
        })
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        /* if (context is OnFragmentInteractionListener) {
             mListener = context
         } else {
             throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
         }*/
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StickerFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): StickerContainerSheetFragment {
            val fragment = StickerContainerSheetFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }


}
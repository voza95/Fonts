package test.example.com.biographyandquotes.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import test.example.com.biographyandquotes.adapter.StickersAdapter
import wondrousapps.com.fontsandgradient2.R
import wondrousapps.com.fontsandgradient2.fragment.EditQuotesFragment
import wondrousapps.com.fontsandgradient2.listener.RecyclerItemClickListener
import wondrousapps.com.fontsandgradient2.utils.AppConstant
import wondrousapps.com.fontsandgradient2.utils.Utility


class StickerFragment : Fragment() {

    private var folderName: String? = null
    private var mParam2: String? = null

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            folderName = arguments.getString(ARG_PARAM1)
            // mParam2 = arguments.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_sticker, container, false)
        val recyclerView = view.findViewById(R.id.recycler_view) as RecyclerView
        recyclerView.layoutManager = GridLayoutManager(context, Utility.calculateNoOfColumns(context))
        val list = prePareDataSource()
        recyclerView.adapter = StickersAdapter(list, context)
        recyclerView.addOnItemTouchListener(RecyclerItemClickListener(context,
                RecyclerItemClickListener.OnItemClickListener { _view: View, i: Int ->
                    val frag = fragmentManager.findFragmentByTag(AppConstant.Key_TAG_EditFRagment)
                    if (frag != null) {
                        if (frag is EditQuotesFragment) {
                            frag.addStickers(list.get(i))
                        }
                    }
                }))

        return view
    }

    fun prePareDataSource(): List<String> {
        val list = ArrayList<String>()
        var f = context.getAssets().list("stickers/" + folderName)
        for (f1 in f) {
            Log.v("names", "stickers/" + folderName + f1)
            list.add("stickers/" + folderName + "/" + f1)
        }

        return list
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
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        @JvmStatic
        fun newInstance(folderPath: String): StickerFragment {
            val fragment = StickerFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, folderPath)
            // args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor

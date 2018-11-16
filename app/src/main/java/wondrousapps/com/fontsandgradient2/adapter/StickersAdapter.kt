package test.example.com.biographyandquotes.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import wondrousapps.com.fontsandgradient2.R
import wondrousapps.com.fontsandgradient2.utils.AppConstant.getBitmapFromAssets


class StickersAdapter(private val itemsData: List<String>?, private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // var qusImageIconArray: TypedArray = context.resources.obtainTypedArray(R.array.icon_array)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {

        when (viewType) {
            Content_List_ITEM_VIEW_TYPE -> {
                val contentItemView = LayoutInflater.from(viewGroup.context).inflate(
                        R.layout.sticker_list_row_item, viewGroup, false)
                return ContentsViewHolder(contentItemView)

            }

            else -> return null
        }

        //  case Grid_VIEW_TYPE:
        // fall through
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        when (viewType) {
            Content_List_ITEM_VIEW_TYPE -> {
                val holder = viewHolder as ContentsViewHolder
                holder.stickerIcon.setImageBitmap(getBitmapFromAssets(context,itemsData!![position]))
            }

        }
        // Log.d("Adapter", "ttile" + contentDto.selectedAns)

    }


    override fun getItemCount(): Int {
        return itemsData?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        return Content_List_ITEM_VIEW_TYPE

    }


    // inner class to hold a reference to each item of RecyclerView
    private inner class ContentsViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView) {

       // val titleText: TextView
        // val category: TextView
        val stickerIcon: ImageView


        init {
            stickerIcon = itemLayoutView.findViewById(R.id.sticker_Icon) as ImageView
            //   imageIcon = itemLayoutView.findViewById(R.id.icon_Image)
        }

    }

    // inner class to hold a reference to each item of RecyclerView

    fun dpToPx(dp: Int): Int {
        val displayMetrics = context.resources.displayMetrics
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    }

    companion object {
        private val Content_List_ITEM_VIEW_TYPE = 0
    }
}

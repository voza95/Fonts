package wondrousapps.com.fontsandgradient2.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import wondrousapps.com.fontsandgradient2.R
import wondrousapps.com.fontsandgradient2.model.ColorsImageDto


class ColorsImageFontAdapter(private val itemsData: ArrayList<ColorsImageDto>?, private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {

        when (viewType) {
            Content_List_ITEM_VIEW_TYPE -> {
                val contentItemView = LayoutInflater.from(viewGroup.context).inflate(
                        R.layout.image_color_list_row, viewGroup, false)
                return ContentsViewHolder(contentItemView)

            }
        /*  Content_Review_List_ITEM_VIEW_TYPE -> {
                val contentItemView = LayoutInflater.from(viewGroup.context).inflate(
                        R.layout.review_list_row, viewGroup, false)
                return TestReviewViewHolder(contentItemView)

            }
            else -> return null*/
        }

        //  case Grid_VIEW_TYPE:
        // fall through
        return null
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        val contentDto = itemsData!![position]
        when (viewType) {
            Content_List_ITEM_VIEW_TYPE -> {
                val holder = viewHolder as ContentsViewHolder
                //  Log.d("mAdapter","contentDto.resourceId "+contentDto.resourceId)
                holder.selectionText.visibility=View.VISIBLE
                holder.image.visibility=View.GONE

                holder.selectionText.setText("")
                 if (contentDto.title.contains("img")) {
                    holder.image.visibility=View.VISIBLE
                    holder.image.setImageResource(contentDto.resourceId)
                }
                if (contentDto.title.contains("#"))
                    holder.selectionText.setBackgroundColor(Color.parseColor(contentDto.title))


                if (contentDto.title.contains("fonts")) {
                    holder.selectionText.setBackgroundColor(Color.WHITE)
                    holder.selectionText.setTextColor(Color.BLACK)
                    holder.selectionText.setText("Aa")
                    holder.selectionText.setTypeface(Typeface.createFromAsset(context.assets, contentDto.path))
                }
            }
        /*    }
            Content_Review_List_ITEM_VIEW_TYPE -> {
                val holder = viewHolder as TestReviewViewHolder

            }*/
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

        //  val titleText: TextView
         val image: ImageView
        val selectionText: TextView


        init {
             image = itemLayoutView.findViewById(R.id.img_view) as ImageView
            selectionText = itemLayoutView.findViewById(R.id.img_colr_view) as TextView
        }

    }

    /*// inner class to hold a reference to each item of RecyclerView
    private inner class TestReviewViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView) {
        val title: TextView
        val ansText: TextView
        val rightAnsText: TextView
        //private val strip: TextView? = null
        val thumbnail: ImageView


        init {
            title = itemLayoutView.findViewById<TextView>(R.id.qusText) as TextView
            ansText = itemLayoutView.findViewById<TextView>(R.id.answerText) as TextView
            rightAnsText = itemLayoutView.findViewById<TextView>(R.id.rightAnswerText) as TextView
            thumbnail = itemLayoutView.findViewById<ImageView>(R.id.reviewIcon) as ImageView

        }
    }
*/
    fun dpToPx(dp: Int): Int {
        val displayMetrics = context.resources.displayMetrics
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    }

    companion object {
        private val Content_Review_List_ITEM_VIEW_TYPE = 1
        private val Content_List_ITEM_VIEW_TYPE = 0
        private var resourceType: String = ""
        public var isFont: Boolean = false
    }
}

package wondrousapps.com.fontsandgradient2.fragment


import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.*
import com.flipboard.bottomsheet.BottomSheetLayout
import com.xiaopo.flying.sticker.DrawableSticker
import com.xiaopo.flying.sticker.Sticker
import com.xiaopo.flying.sticker.StickerView
import it.sephiroth.android.library.bottomnavigation.BottomNavigation
import me.grantland.widget.AutofitTextView
import test.example.com.biographyandquotes.fragments.StickerContainerSheetFragment

import wondrousapps.com.fontsandgradient2.R
import wondrousapps.com.fontsandgradient2.adapter.ColorsImageFontAdapter
import wondrousapps.com.fontsandgradient2.adapter.PageAdapter
import wondrousapps.com.fontsandgradient2.listener.RecyclerItemClickListener
import wondrousapps.com.fontsandgradient2.model.ColorsImageDto
import wondrousapps.com.fontsandgradient2.utils.AppConstant
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class EditQuotesFragment : Fragment(), View.OnClickListener, BottomNavigation.OnMenuItemSelectionListener, StickerView.OnStickerOperationListener {

    var stckmainView : StickerView? = null
    lateinit var mViewPager: ViewPager
    lateinit var bg_image: ImageView
    lateinit var bottomSheet: BottomSheetLayout
    lateinit var alignLeft: ImageView
    lateinit var alignRight: ImageView
    lateinit var alignCenter: ImageView
    lateinit var boldImg: ImageView
    lateinit var bottomNavigation: BottomNavigation
    lateinit var recyclerView: RecyclerView
    lateinit var textSizeSeekBar: SeekBar
    lateinit var frameLayout: FrameLayout
    lateinit var alignMentContainer: LinearLayout
    var isBold: Boolean = false
    var isTemplate: Boolean = false
    var currentPosition: Int = 0
    var quoteList: String? = null
    lateinit var viewPager: ViewPager
    lateinit var dataList: ArrayList<ColorsImageDto>


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (arguments != null) {
            quoteList = arguments.getString(EditQuotesFragment.ARG_PARAM1)
            currentPosition = arguments.getInt(EditQuotesFragment.ARG_PARAM2)
            //   Log.d("onQuoteClick","onAttach position called "+currentPosition)
            Log.d("mviewPager ", "current  currentPosition " + currentPosition)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view = inflater!!.inflate(R.layout.fragment_edit_quotes, container, false)
        mViewPager = view.findViewById(R.id.viewPager) as ViewPager
        stckmainView = view.findViewById(R.id.msticker_view) as StickerView?
        //  frame_sticker_view = view.findViewById<ImageView>(R.id.frame_sticker_view)
        //   myZoomageView = view.findViewById<CustomImageView>(R.id.myZoomageView)
        bottomSheet = view.findViewById(R.id.bottomsheet) as BottomSheetLayout;
        bg_image = view.findViewById(R.id.bg_image) as ImageView
        alignLeft = view.findViewById(R.id.alignLeft) as ImageView
        alignCenter = view.findViewById(R.id.alignCenter) as ImageView
        alignRight = view.findViewById(R.id.alignRight) as ImageView
        boldImg = view.findViewById(R.id.boldText) as ImageView
        recyclerView = view.findViewById(R.id.recycler_view) as RecyclerView
        textSizeSeekBar = view.findViewById(R.id.seekBar) as SeekBar
        frameLayout = view.findViewById(R.id.squreLayout) as FrameLayout
        alignMentContainer = view.findViewById(R.id.alignMentContainer) as LinearLayout
        view.findViewById(R.id.btn_images).setOnClickListener(this)
        view.findViewById(R.id.btn_gallary).setOnClickListener(this)
        view.findViewById(R.id.btn_quote).setOnClickListener(this)
        view.findViewById(R.id.btn_template).setOnClickListener(this)
        view.findViewById(R.id.btn_save).setOnClickListener(this)
        view.findViewById(R.id.btn_share).setOnClickListener(this)

        alignCenter.setOnClickListener(this)
        alignLeft.setOnClickListener(this)
        alignRight.setOnClickListener(this)
        boldImg.setOnClickListener(this)
        setDefaultAlignmentState()

        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        bottomNavigation = view.findViewById(R.id.bottem_edt_optionBar) as BottomNavigation
        bottomNavigation.setOnMenuItemClickListener(this)
        bottomNavigation.setDefaultSelectedIndex(0)
        dataList = ArrayList<ColorsImageDto>()

        // initStickerIcons()
        stckmainView!!.setOnStickerOperationListener(this)
        viewPager = view.findViewById(R.id.viewPager) as ViewPager
///////////////////////////////////////////////////////////////////
        val list: List<String?> = quoteList!!.map { quoteList }
        viewPager.adapter = PageAdapter(context, list)
        viewPager.setCurrentItem(currentPosition, false)
        viewPager.adapter.notifyDataSetChanged()
//         Log.d("mviewPager ","current  viewPager item "+viewPager.currentItem)
        //Set up default list
        prepareDataSource(AppConstant.Key_IMAGE)
        recyclerView.adapter = ColorsImageFontAdapter(dataList, context)
        recyclerView.addOnItemTouchListener(RecyclerItemClickListener(context, RecyclerItemClickListener.OnItemClickListener
        { view: View, i: Int ->
            //   Log.d("DetailFragment ", "urlCategory " + dataList.get(i).title)
            val ddataObj = dataList.get(i)
            //  Log.d("DetailFragment ", "in " + ddataObj.title)

            if (ddataObj.title.contains("img")) {
                bg_image.setImageResource(android.R.color.transparent)
                bg_image.setBackgroundResource(ddataObj.resourceId)
            }
            if (ddataObj.title.contains("#")) {
                if (!isTemplate) {
                    // moveableText = stckView!!.mainView as AutoResizeTextView
                    getCurrentTextView()!!.setTextColor(Color.parseColor(ddataObj.title))
                }
                if (isTemplate) {
                    bg_image.setImageResource(android.R.color.transparent)
                    bg_image.setBackgroundColor(Color.parseColor(ddataObj.title))
                }
            }
            if (ddataObj.title.equals("fonts")) {
                getCurrentTextView()!!.setTypeface(Typeface.createFromAsset(context.assets, ddataObj.path))
            }
        }))
        textSizeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                getCurrentTextView()!!.setSizeToFit(true)
                when (progress) {
                    0 -> getCurrentTextView()!!.setTextSize(context.getResources().getDimension(R.dimen._5ssp))
                    1 -> getCurrentTextView()!!.setTextSize(context.getResources().getDimension(R.dimen._7ssp))
                    2 -> getCurrentTextView()!!.setTextSize(context.getResources().getDimension(R.dimen._9ssp))
                    3 -> getCurrentTextView()!!.setTextSize(context.getResources().getDimension(R.dimen._10ssp))
                    4 -> getCurrentTextView()!!.setTextSize(context.getResources().getDimension(R.dimen._11ssp))
                    5 -> getCurrentTextView()!!.setTextSize(context.getResources().getDimension(R.dimen._12ssp))
                    6 -> getCurrentTextView()!!.setTextSize(context.getResources().getDimension(R.dimen._13ssp))
                    7 -> getCurrentTextView()!!.setTextSize(context.getResources().getDimension(R.dimen._14ssp))
                }
                // getCurrentTextView()!!.setTextSize(progress + TextMultiplyFactor)

                // Log.d("onProgressChanged", "onProgressChanged " + stckView!!.minTextSizePixels + " " + TextMultiplyFactor.times(progress))
                // Log.d("onProgressChanged", "onProgressChanged progress+TextMultiplyFactor " + progress + TextMultiplyFactor)


            }

        })


        return view
    }


    fun getCurrentTextView(): AutofitTextView? {
        if (true) {
            //  Log.d("mviewPager ", "current  item " + viewPager.currentItem)
            val view: View = viewPager.findViewWithTag("myview" + viewPager.getCurrentItem())
            return view.findViewById(R.id.qoute) as AutofitTextView?

        }
        return null
    }

    fun addStickers(stickerPath: String) {
        bottomSheet.dismissSheet()
        val drawable = BitmapDrawable(resources, AppConstant.getBitmapFromAssets(context, stickerPath))
        stckmainView!!.addSticker(DrawableSticker(drawable))
        stckmainView!!.bringToFront()
    }

    fun setDefaultAlignmentState() {
        alignRight.setImageResource(R.drawable.right_align)
        alignLeft.setImageResource(R.drawable.left_align)
        alignCenter.setImageResource(R.drawable.center_align_)
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_gallary -> {
                DialogCamera2(activity!!).show()
            }
            R.id.btn_template -> {
                isTemplate = true
                hideVisibilityViews()
                recyclerView.visibility = View.VISIBLE
                prepareDataSource(AppConstant.Key_Templates)
                recyclerView.adapter.notifyDataSetChanged()
            }
            R.id.btn_quote -> {
                optionDialog()
            }
            R.id.btn_images -> {
                Log.d("images","btn_images called")
                StickerContainerSheetFragment().show(fragmentManager, R.id.bottomsheet)
            }
            R.id.btn_save -> {
                Log.d("save","btn_save called")

                var bitmap = viewToBitmap(frameLayout, frameLayout.width, frameLayout.height)
                storeImage(bitmap, false)
            }
            R.id.btn_share -> {
                Log.d("share","btn_share called")
                if (getOutputMediaFile() != null && getOutputMediaFile()!!.exists()) {
                    shareImage(getOutputMediaFile()!!.path)
                } else {
                    var bitmap = viewToBitmap(frameLayout, frameLayout.width, frameLayout.height)
                    storeImage(bitmap, true)
                }
            }
        /*  R.id.btn_frames -> {
              prepareDataSource(AppConstant.Key_Frames)
              recyclerView.adapter.notifyDataSetChanged()
          }*/
            R.id.alignRight -> {
                setDefaultAlignmentState()
                alignRight.setImageResource(R.drawable.right_align_active)
                setTextAlignment(Gravity.RIGHT)
            }
            R.id.alignCenter -> {
                setDefaultAlignmentState()
                alignCenter.setImageResource(R.drawable.center_align_active)
                setTextAlignment(Gravity.CENTER)
            }
            R.id.alignLeft -> {
                setDefaultAlignmentState()
                alignLeft.setImageResource(R.drawable.left_align_active)
                setTextAlignment(Gravity.LEFT)

            }
            R.id.boldText -> {
                if (!isBold) {
                    isBold = true
                    //  Log.d("DetailFragment ", "in if is not Bold ")
                    boldImg.setImageResource(R.drawable.bold_active)
                    getCurrentTextView()!!.setTypeface(Typeface.DEFAULT_BOLD)
                } else {
                    isBold = false
                    //   Log.d("DetailFragment ", "in else is  Bold ")
                    boldImg.setImageResource(R.drawable.ic_bold)
                    getCurrentTextView()!!.setTypeface(Typeface.DEFAULT)
                }
            }
        }
    }

    private fun shareImage(bitmapPath: String) {
        val sharingIntent = Intent(android.content.Intent.ACTION_SEND)
        sharingIntent.type = "image/*"
        val imageUri: Uri = Uri.fromFile(File(bitmapPath))
        sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        sharingIntent.putExtra(Intent.EXTRA_STREAM, imageUri)
        sharingIntent.putExtra(Intent.EXTRA_TEXT, "Download From : \nhttps://goo.gl/vhXSgA")
        startActivity(Intent.createChooser(sharingIntent, "Share via"))
    }

    private fun storeImage(image: Bitmap, isShare: Boolean) {
        // Log.d("ImageResult", " storeImage $image ")
        val pictureFile: File = getOutputMediaFile()!!

        try {
            val fos: FileOutputStream = FileOutputStream(pictureFile)
            image.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos.close()
            scanFile(pictureFile.path)
            if (isShare)
                shareImage(pictureFile.path)
            else
                Toast.makeText(activity, " Image is Saved. ", Toast.LENGTH_SHORT).show()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun scanFile(path: String) = MediaScannerConnection.scanFile(activity,
            arrayOf(path), null
    ) { path, uri -> Log.i("TAG", "Finished scanning " + path) }


    @SuppressLint("SimpleDateFormat")
    private fun getOutputMediaFile(): File? {
        // Create a media file name
        val timeStamp: String = SimpleDateFormat("ddMMyyyy_HHmm").format(Date())
        val mImageName: String = "IMG_" + timeStamp + ".png"
        return File(AppConstant.getImageDirectory().path + "/" + mImageName)

    }

    fun viewToBitmap(view: View, width: Int, height: Int): Bitmap {
        disableStickerBorder()

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    fun disableStickerBorder() {
        stckmainView!!.showBorder = false
        stckmainView!!.showIcons = false
    }

    private fun optionDialog() {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.layout_get_text)
        dialog.setCancelable(true)
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        //dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT

        dialog.window!!.attributes = lp
        val mtext: EditText = dialog.window.findViewById(R.id.EdtgetText) as EditText
        // Log.d("DetailFragment ", "viewPager child "+viewPager.findViewWithTag("myview" + viewPager.getCurrentItem()))
        if (getCurrentTextView()!!.text.isNotEmpty()) {
            mtext.setText(getCurrentTextView()!!.text)
            // stckmainView!!.remove(stckView!!)
        }
        dialog.window.findViewById(R.id.btnOK).setOnClickListener {
            dialog.dismiss()
            if (mtext.text.toString().isNotEmpty()) {
                Log.d("DetailFragment ", "stckView not null")
                getCurrentTextView()!!.text = mtext.text.toString()
            }
        }
        dialog.show()
    }
    /*  fun initStickerIcons() {

          val heartIcon = BitmapStickerIcon(ContextCompat.getDrawable(context, R.drawable.swap_icon),
                  BitmapStickerIcon.LEFT_BOTTOM)
          heartIcon.iconEvent = HelloIconEvent()

          stckmainView!!.icons = Arrays.asList( heartIcon)
          stckmainView!!.setConstrained(true);

      }*/

    fun hideVisibilityViews() {
        recyclerView.visibility = View.GONE
        textSizeSeekBar.visibility = View.GONE
        alignMentContainer.visibility = View.GONE
        //  recyclerView.visibility = View.GONE
    }


    override fun onMenuItemSelect(itemId: Int, position: Int, fromUser: Boolean) {
        // Log.i("EditFragment", "onMenuItemSelect(" + itemId + ", " + position + ", " + fromUser + ")");

        when (itemId) {
            R.id.bbn_selectImage -> {
                hideVisibilityViews()
                recyclerView.visibility = View.VISIBLE
                prepareDataSource(AppConstant.Key_IMAGE)
                recyclerView.adapter = ColorsImageFontAdapter(dataList, context)
            }
            R.id.bbn_fontSize -> {
                hideVisibilityViews()
                textSizeSeekBar.visibility = View.VISIBLE
            }
            R.id.bbn_alignmet -> {
                hideVisibilityViews()
                alignMentContainer.visibility = View.VISIBLE
            }
            R.id.bbn_textcolor -> {
                isTemplate = false
                hideVisibilityViews()
                recyclerView.invalidate()
                recyclerView.visibility = View.VISIBLE
                prepareDataSource(AppConstant.Key_Text_Color)
                recyclerView.adapter = ColorsImageFontAdapter(dataList, context)

            }
            R.id.bbn_font -> {
                hideVisibilityViews()
                recyclerView.visibility = View.VISIBLE
                prepareDataSource(AppConstant.Key_Font)
                recyclerView.adapter = ColorsImageFontAdapter(dataList, context)
            }
        }
    }

    override fun onStickerAdded(sticker: Sticker) {
    }

    override fun onStickerClicked(sticker: Sticker) {
        if (stckmainView!!.showBorder) {
            stckmainView!!.showBorder = false
            stckmainView!!.showIcons = false
        } else {
            stckmainView!!.showBorder = true
            stckmainView!!.showIcons = true
        }
    }

    override fun onStickerDeleted(sticker: Sticker) {
    }

    override fun onStickerDragFinished(sticker: Sticker) {
    }

    override fun onStickerTouchedDown(sticker: Sticker) {
    }

    override fun onStickerZoomFinished(sticker: Sticker) {
    }

    override fun onStickerFlipped(sticker: Sticker) {
    }

    override fun onStickerDoubleTapped(sticker: Sticker) {
    }

    // fun isNullSticker(): Boolean = if (stckView == null) true else false


    fun setTextAlignment(align: Int) {
        getCurrentTextView()!!.gravity = align
    }

    fun prepareDataSource(value: String) {
        dataList.clear()
        when (value) {
            AppConstant.Key_IMAGE -> {
                dataList.add(ColorsImageDto("img", R.drawable.img21, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img22, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img23, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img25, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img30, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img34, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img36, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img37, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img38, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img5, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img7, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img8, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img9, ""))
                dataList.add(ColorsImageDto("img", R.drawable.rsz_img13, ""))
                dataList.add(ColorsImageDto("img", R.drawable.rsz_img20, ""))
                dataList.add(ColorsImageDto("img", R.drawable.rsz_img21, ""))
                dataList.add(ColorsImageDto("img", R.drawable.rsz_img23, ""))
                dataList.add(ColorsImageDto("img", R.drawable.rsz_img24, ""))
                dataList.add(ColorsImageDto("img", R.drawable.rsz_img28, ""))
                dataList.add(ColorsImageDto("img", R.drawable.rsz_img29, ""))
                dataList.add(ColorsImageDto("img", R.drawable.rsz_img3, ""))
                dataList.add(ColorsImageDto("img", R.drawable.rsz_img30, ""))
                dataList.add(ColorsImageDto("img", R.drawable.rsz_img31, ""))
                dataList.add(ColorsImageDto("img", R.drawable.rsz_img32, ""))
                dataList.add(ColorsImageDto("img", R.drawable.rsz_img34, ""))
                dataList.add(ColorsImageDto("img", R.drawable.rsz_img35, ""))
                dataList.add(ColorsImageDto("img", R.drawable.rsz_img36, ""))
                dataList.add(ColorsImageDto("img", R.drawable.rsz_img37, ""))
                dataList.add(ColorsImageDto("img", R.drawable.rsz_img38, ""))
                dataList.add(ColorsImageDto("img", R.drawable.rsz_img39, ""))
                dataList.add(ColorsImageDto("img", R.drawable.rsz_img4, ""))
                dataList.add(ColorsImageDto("img", R.drawable.rsz_img41, ""))
                dataList.add(ColorsImageDto("img", R.drawable.rsz_img43, ""))
                dataList.add(ColorsImageDto("img", R.drawable.rsz_img46, ""))
                dataList.add(ColorsImageDto("img", R.drawable.rsz_img48, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img_41, ""))
                dataList.add(ColorsImageDto("img", R.drawable.rsz_img7, ""))
                dataList.add(ColorsImageDto("img", R.drawable.rsz_img8, ""))
                dataList.add(ColorsImageDto("img", R.drawable.rsz_img9, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img_3, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img_4, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img_45, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img_7, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img_8, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img_9, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img_10, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img_11, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img_12, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img_13, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img_14, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img_15, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img_16, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img_17, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img_18, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img_19, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img_20, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img_21, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img_24, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img_25, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img_27, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img_28, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img_29, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img_30, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img_31, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img_32, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img_33, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img_35, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img_36, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img_37, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img_38, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img_39, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img_40, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img_42, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img_43, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img_44, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img_1, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img10, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img16, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img17, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img19, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img_95, ""))
                dataList.add(ColorsImageDto("img", R.drawable.img_96, ""))
            }
            AppConstant.Key_Alignment -> {

            }
            AppConstant.Key_Templates -> {
                var imagNames = resources.getStringArray(R.array.colors_array)
                imagNames.iterator().forEach {
                    dataList.add(ColorsImageDto(it, -1, ""))
                }

            }
            AppConstant.Key_Frames -> {

            }
            AppConstant.Key_TextSize -> {
            }
            AppConstant.Key_Text_Color -> {
                var imagNames = resources.getStringArray(R.array.colors_array)
                imagNames.iterator().forEach {
                    dataList.add(ColorsImageDto(it, -1, ""))
                }
            }
            AppConstant.Key_Font -> {
                val f = context.assets.list("fonts")
                for (f1 in f) {
                    // Log.v("names", f1)
                    dataList.add(ColorsImageDto("fonts", -1, "fonts/" + f1))
                }
            }
        }
    }


    override fun onMenuItemReselect(p0: Int, p1: Int, p2: Boolean) {
    }


    companion object {
        val TAG: String = "EditFragment"
        val TextMultiplyFactor: Float = 13f
        val TextStyleLeft: String = "left"


        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"


        @JvmStatic
        fun newInstance(list: String): EditQuotesFragment {
            //Log.d("test",position)
            val fragment = EditQuotesFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, list)

            fragment.arguments = args
            return fragment
        }

    }

    public inner class DialogCamera2(var context: Activity) : Dialog(context), View.OnClickListener {
        lateinit var ivCamera: ImageView;
        lateinit var ivGallery: ImageView;


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            try {
                requestWindowFeature(1);
                setContentView(R.layout.dialog_box_camera);
                setCancelable(true);
                getWindow().setBackgroundDrawable(ColorDrawable(0));
                setCanceledOnTouchOutside(true);
                this.ivCamera = findViewById(R.id.iv_camera) as ImageView;
                this.ivGallery = findViewById(R.id.iv_gallery) as ImageView;
                this.ivCamera.setOnClickListener(this);
                this.ivGallery.setOnClickListener(this);
            } catch (e: Exception) {
                e.printStackTrace();
            }
        }

        override fun onClick(v: View?) {
            when (v!!.getId()) {
                R.id.iv_camera -> {
                    camera();
                    dismiss();
                }
                R.id.iv_gallery -> {
                    openGellery();
                    dismiss();
                }
            }
        }

        public fun openGellery() {
            if (Build.VERSION.SDK_INT >= 23) {
                this.context.requestPermissions(arrayOf("android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"), 2909)
            }
            if (ContextCompat.checkSelfPermission(getContext(), "android.permission.READ_EXTERNAL_STORAGE") == 0) {
                val intent = Intent();
                intent.setType("image/*");
                intent.setAction("android.intent.action.GET_CONTENT");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 100);
            }
        }

        public fun camera() {
            var takePictureIntent = Intent("android.media.action.IMAGE_CAPTURE");
            if (takePictureIntent.resolveActivity(EditQuotesFragment@ this.context.getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, 101);
            }
        }

        init {
            //   Log.d("DialogCamera", " DialogCamera 2")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //   Log.d("DialogCamera", " DialogCamera m1 ")

        if (requestCode == 101 && data != null) {
            val bitmap = data!!.extras.get("data") as Bitmap

            var d: Drawable = BitmapDrawable(getResources(), bitmap);
            bg_image.setImageDrawable(d)
            bg_image.bringToFront()
            // myZoomageView.setImageBitmap(bitmap)
            // myZoomageView.bringToFront()
            //   Log.d("DialogCamera", " DialogCamera m2")
        }
        if (requestCode == 100 && data != null) {
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), data.getData());

                var d: Drawable = BitmapDrawable(getResources(), bitmap);

                bg_image.setImageDrawable(d)
                bg_image!!.bringToFront()
                //    Log.d("DialogCamera", " DialogCamera m3")

            } catch (e: IOException) {
                e.printStackTrace();
            }
        }
    }

//    override fun onResume() {
//        super.onResume()
//        AppConstant.loadFristFragment(EditQuotesFragment.newInstance(quotes, position),
//                getSupportFragmentManager(), R.id.fragmentContainer, AppConstant.Key_TAG_EditFRagment)
//    }
}
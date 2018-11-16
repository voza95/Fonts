package wondrousapps.com.fontsandgradient2.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.List;

import me.grantland.widget.AutofitTextView;
import wondrousapps.com.fontsandgradient2.R;


/*
 * Created by ntf-13 on 3/6/17.
 */

public class PageAdapter extends PagerAdapter {
    private Context context;
    private List<String> list;
    private String title;
    private AutofitTextView quot;

    public PageAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        //baseUrl="file:///android_asset/" + title + "/";
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);

    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        // Declare Variables
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View itemView = layoutInflater.inflate(R.layout.layout_pager_data, container, false);
         itemView.setTag("myview"+position);
        quot = (AutofitTextView) itemView.findViewById(R.id.qoute);

        quot.setText(list.get(position).replace("â\u0080\u0099","\'").replace("â\u0080\u0099","'").replace("â\u0080\u0098","").replace("â\u0080\u0099m","'m").replace("â\u0080\u0099s","'s")
                .replace("â\u0080\u009Cnoâ\u0080\u009D","\"No\""));


        ((ViewPager) container).addView(itemView);
        return itemView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) { // Remove viewpager_item.xml from ViewPager

        ((ViewPager) container).removeView((RelativeLayout) object);
    }



}


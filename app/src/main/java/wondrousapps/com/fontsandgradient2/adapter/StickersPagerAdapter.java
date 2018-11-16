package wondrousapps.com.fontsandgradient2.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import test.example.com.biographyandquotes.fragments.StickerFragment;


public class StickersPagerAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;
    private String[] tabTitles = new String[]{"Emoji", "Baby","Feather","Funny","Heart","Love","Magic","Zodiac"};
   // private String[] stickerFolders = new String[]{"Category", "Favorites"};

    private String selectedImagePath;

    public StickersPagerAdapter(FragmentManager fm, int NumOfTabs, Context context) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        //Context mContext = context;
        //this.selectedImagePath = selectedImagePath;
    }


    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return  StickerFragment.newInstance(tabTitles[position].toLowerCase());
            case 1:
                return  StickerFragment.newInstance(tabTitles[position].toLowerCase());
            case 2:
                return  StickerFragment.newInstance(tabTitles[position].toLowerCase());
            case 3:
                return  StickerFragment.newInstance(tabTitles[position].toLowerCase());
            case 4:
                return  StickerFragment.newInstance(tabTitles[position].toLowerCase());
            case 5:
                return  StickerFragment.newInstance(tabTitles[position].toLowerCase());
            case 6:
                return  StickerFragment.newInstance(tabTitles[position].toLowerCase());
            case 7:
                return  StickerFragment.newInstance(tabTitles[position].toLowerCase());
            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }


}

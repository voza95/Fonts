package wondrousapps.com.fontsandgradient2.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ntf-42 on 8/3/18.
 */

public class AppConstant {

    public static final String Key_IMAGE = "Image";
    private static final String M_DIRECTORY_NAME = "Quote Images";
    public static final String LANG_KEY = "LANGUAGE";
    public static String Key_Templates = "Templates";
    public static String Key_TextSize = "TextSize";
    public static String Key_Alignment = "Alignment";
    public static String Key_Frames = "Frames";
    public static String Key_Text_Color = "TextColor";
    public static String Key_Font = "Font";
    public static String Key_TAG_EditFRagment = "EditFragment";
    public static String Key_TAG_TabFRagment = "TabFragment";
    public static String Key_TAG_CategoryFRagment = "CategoryFragment";

    public static void loadFragment(Fragment fragment, FragmentManager fragmentManager, int replaceViewId, String tag) {
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(replaceViewId, fragment, tag)
                .addToBackStack(null);
        fragmentTransaction.commit(); // save the changes
    }

    public static void loadFristFragment(Fragment fragment, FragmentManager fragmentManager, int replaceViewId, String tag) {
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(replaceViewId, fragment, tag);
        fragmentTransaction.commit(); // save the changes
    }

    public static int getResourseId(Context context, String pVariableName, String pResourcename, String pPackageName) {
        try {
            return context.getResources().getIdentifier(pVariableName, pResourcename, pPackageName);
        } catch (Exception e) {
            // throw RuntimeException("Error getting Resource ID.", e)
            Log.d("mexaption", "Resource not found " + e);
        }
        return -1;
    }

    public static String loadPrefs(Context context, String key) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(key,"biography_quotes");

    }


    public static void savePrefs(Context context,String key, String value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.apply();
    }

    public static float pixelsToSp(Context context, float px) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return px / scaledDensity;
    }
    public static Bitmap getBitmapFromAssets(Context context, String path )
    {
        InputStream inStream = null;
        try {
            inStream = context.getResources().getAssets().open(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (inStream!=null)
            return BitmapFactory.decodeStream(inStream);
        else
            return null;
    }


    public static File getImageDirectory() {
        String directory = Environment.getExternalStorageDirectory().toString();
        //  String directory = context.getFilesDir().getAbsolutePath();
        Log.d("FileDir", "is dir " + directory);
        File mFolder = new File(directory, M_DIRECTORY_NAME);
        if (!mFolder.exists()) {
            mFolder.mkdir();
        } else {
            return mFolder;
        }
        return mFolder;
    }

    /*public static String getDatabasePath() {
      return getDirectory().getAbsolutePath()+"/"+ MyDatabase.DATABASE_NAME;
    }

    public static void showAlert(final Activity activity, String title, String msg)
    {

    }*/
}

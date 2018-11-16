package wondrousapps.com.fontsandgradient2.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by ntf-9 on 3/2/18.
 */

public class Utility {
    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 70);
        return noOfColumns;
    }
}
package wondrousapps.com.fontsandgradient2.activity;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import wondrousapps.com.fontsandgradient2.R;
import wondrousapps.com.fontsandgradient2.fragment.EditQuotesFragment;
import wondrousapps.com.fontsandgradient2.utils.AppConstant;

public class EditActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{
String quote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();

            if(extras == null) {
                quote=null;
                //  newString= R.drawable.ic_launcher_background;
                Toast.makeText(getApplicationContext(),"No Quotes Available",Toast.LENGTH_SHORT).show();
            } else {
                quote= extras.getString("text",null);

            }
        } else {
            quote= (String) savedInstanceState.getSerializable("text");
        }
        if (hasStoragePermissions()) {
            Log.d("quote",quote);

            AppConstant.loadFristFragment(EditQuotesFragment.newInstance(quote),
                    getSupportFragmentManager(), R.id.fragmentContainer, AppConstant.Key_TAG_EditFRagment);
        } else{
            new AppSettingsDialog.Builder(this).build().show();
        }

    }
    boolean hasStoragePermissions() {
        return EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("PermissionsResult", "onRequestPermissionsResult:" + requestCode );
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (hasStoragePermissions() && quotes!=null) {
//            AppConstant.loadFristFragment(EditQuotesFragment.newInstance(quotes, position),
//                    getSupportFragmentManager(), R.id.fragmentContainer, AppConstant.Key_TAG_EditFRagment);
//        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //        if (hasStoragePermissions() && quotes!=null) {
//            AppConstant.loadFristFragment(EditQuotesFragment.newInstance(quotes, position),
//                    getSupportFragmentManager(), R.id.fragmentContainer, AppConstant.Key_TAG_EditFRagment);
//        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }
}

package wondrousapps.com.fontsandgradient2.activity;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pub.devrel.easypermissions.EasyPermissions;
import wondrousapps.com.fontsandgradient2.R;

public class MainActivity extends AppCompatActivity {
Button button;
EditText editText;
    String s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        storageTask();
        editText= (EditText) findViewById(R.id.edit_string);
        button= (Button) findViewById(R.id.edit);

        button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editText.getText().toString().matches("")){
                        Toast.makeText(getApplicationContext(),"Please enter some text",Toast.LENGTH_SHORT).show();
                    }else {
                        s = editText.getText().toString();
                        Intent intent=new Intent(MainActivity.this,EditActivity.class);
                        intent.putExtra("text",s);
                        Log.d("quote",s);
                        startActivity(intent);
                    }
                }
            });
    }
    private void storageTask() {
        if (!hasStoragePermissions()) {
            // Ask for both permissions
            EasyPermissions.requestPermissions(
                    this,
                    "This app needs storage permission.",
                    111,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
        } else {
            // Have permissions, do the thing!
        }
    }
    private boolean hasStoragePermissions() {
        return EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}

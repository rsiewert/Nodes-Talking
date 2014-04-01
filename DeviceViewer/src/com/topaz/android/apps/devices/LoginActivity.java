package com.topaz.android.apps.devices;

import android.app.Activity;
import android.os.Bundle;
import com.topaz.devices.R;
import android.view.*;
import android.content.Intent;
import android.widget.EditText;

/**
 * Created by rbgodwin on 3/31/14.
 */
public class LoginActivity extends Activity {

    static String USER = "USER";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.login);

    }

    /** Called when the user clicks login button */
    public void loginClicked(View view) {
        System.out.println("Clicked the Login");

        Intent intent = new Intent(this, DeviceListActivity.class);
//        EditText editText = (EditText) findViewById(R.id.);
//        String text = editText.getText().toString();
//        intent.putExtra(this.USER,text);
        startActivity(intent);
    }

}
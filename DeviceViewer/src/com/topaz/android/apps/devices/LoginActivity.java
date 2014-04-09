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

    /**
     * Called when the user clicks login button
     */
    public void loginClicked(View view) {
        System.out.println("Clicked the Login");

        EditText editUserText = (EditText) findViewById(R.id.user_name);
        EditText editPassWordText = (EditText) findViewById(R.id.user_password);
        String user = editUserText.getText().toString();
        String password = editPassWordText.getText().toString();

        // Only allow guest guest in at this time.  TODO: Tie this into login to the system
        if (user.equals("guest") && password.equals("guest")) {
            Intent intent = new Intent(this, DeviceListActivity.class);
            startActivity(intent);
        } else {
            editUserText.setText(null);
            editPassWordText.setText(null);
        }
    }
}
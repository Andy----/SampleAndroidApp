package com.andrew.sampleandroidapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.andrew.sampleandroidapp.helper.*;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText userNameIn, passwordIn;
    private ProgressDialog pDialog;
    private SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userNameIn = (EditText) findViewById(R.id.userNameInput);
        passwordIn = (EditText) findViewById(R.id.passwordInput);
        Button submit = (Button) findViewById(R.id.loginButton);

        db = new SQLiteHandler(getApplicationContext());

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        db.addUser(1, "user", "user");

        // Login button Click Event
        submit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String userName = userNameIn.getText().toString();
                String password = passwordIn.getText().toString();
                // Check for empty data in the form
                if (userName.trim().length() > 0 && password.trim().length() > 0) {
                    // login user
                    checkLogin(userName, password);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please Enter Your Login Details!", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });
    }

    public void checkLogin(final String userName, final String password) {
        pDialog.setMessage("Logging in ...");
        showDialog();

        if(db.authenticateUser(userName, password)) {
            hideDialog();
            Toast.makeText(getApplicationContext(),"User Logged In", Toast.LENGTH_LONG).show();
        } else {
            hideDialog();
            Toast.makeText(getApplicationContext(),"User Name or Password Incorrect", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}

// Comment
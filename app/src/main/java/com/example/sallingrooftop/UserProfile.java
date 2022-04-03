package com.example.sallingrooftop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class UserProfile extends AppCompatActivity {

    private static final int SHOW = 0;
    private static final int HIDE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        getSupportActionBar().hide();

        //change title bar background color
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.black));
        }

        //get user input through sharedPrefs and set text
        SharedPreferences myPrefsFile = getSharedPreferences("MyPrefsFile", Activity.MODE_PRIVATE);
        String userName = myPrefsFile.getString("username", null);
        String userEmail = myPrefsFile.getString("userEmail", null);
        TextView userNameTV = (TextView) findViewById(R.id.username);
        TextView userEmailTV = (TextView) findViewById(R.id.user_email);

        if (userName != null) { userNameTV.setText(userName);}
        if (userEmail != null) { userEmailTV.setText(userEmail);}

        //get and set image
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File mypath = new File(directory,"profile.jpg");
        try {
            FileInputStream inputStream = new FileInputStream(mypath);
            Bitmap b = BitmapFactory.decodeStream(inputStream);
            ImageView img = (ImageView)findViewById(R.id.profilePicture);
            img.setImageBitmap(b);
            inputStream.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //radio button event handler
        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = radioGroup.findViewById(checkedId);
                int index = radioGroup.indexOfChild(radioButton);
                switch (index) {
                    case SHOW: // User chose "Yes."
                        displayFragment();
                        break;
                    case HIDE: // User chose "No."
                        closeFragment();
                        break;
                    default: // No choice made.
                        // Do nothing.
                        break;
                }
            }
        });

    }

    public void displayFragment(){
        BfriendFragment bfriendFragment = BfriendFragment.newInstance();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //Add the bfFragment
        fragmentTransaction.add(R.id.show_bffragment, bfriendFragment);
        fragmentTransaction.addToBackStack(null).commit();
    }

    public void closeFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        //check if the fragment is already showing
        BfriendFragment bfriendFragment = (BfriendFragment) fragmentManager.findFragmentById(R.id.show_bffragment);
        if (bfriendFragment != null) {
            //Create and commit the transaction to remove the fragment
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(bfriendFragment).commit();
        }
    }

    public void toCreateProfile(View view) {
        Intent intent = new Intent(this, CreateProfile.class);
        startActivity(intent);
        finish();
    }
}
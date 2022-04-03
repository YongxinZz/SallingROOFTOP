package com.example.sallingrooftop;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CreateProfile extends AppCompatActivity {
    private EditText firstname;
    private EditText lastname;
    private EditText email;
    private EditText bestfriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        getSupportActionBar().hide();

        //change title bar background color
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.black));
        }

        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        email = (EditText) findViewById(R.id.email);
        bestfriend = (EditText) findViewById(R.id.bestFriend);
    }


    public void saveProfile(View view) {
        SharedPreferences myPrefsFile = getSharedPreferences("MyPrefsFile", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = myPrefsFile.edit();

        String firstname = this.firstname.getText().toString();
        String lastname = this.lastname.getText().toString();
        String email = this.email.getText().toString();
        String bestfriend = this.bestfriend.getText().toString();

        String username = firstname + " " + lastname;
        editor.putString("username", username);
        editor.putString("userEmail", email);
        editor.putString("bestFriendName", bestfriend);
        editor.commit();


        Intent intent = new Intent(this, UserProfile.class);
        startActivity(intent);
        finish();
    }

    private static final int CAMERA_REQUEST = 1888;
    public void takePicture(View view) {
        Intent myCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(myCameraIntent, CAMERA_REQUEST);
    }

    private static final int CONTACT = 0;
    public void findBestFriendFromContact(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 1);
        }

        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, CONTACT);
    }

    public void onActivityResult(int reqCode, int resCode, Intent data){
        super.onActivityResult(reqCode, resCode, data);
        if(reqCode == CAMERA_REQUEST){
            if(resCode == RESULT_OK){
                Bitmap NewFace = (Bitmap) data.getExtras().get("data");
                //we find the Imageview to put them
                ImageView image = (ImageView) findViewById(R.id.addProfilePicture);
                image.setImageBitmap(NewFace);
            }
            Bitmap newFace = (Bitmap) data.getExtras().get("data");
            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            File mypath = new File(directory,"profile.jpg");
            FileOutputStream outputstream = null;
            try{
                outputstream = new FileOutputStream(mypath);
                newFace.compress(Bitmap.CompressFormat.JPEG, 90, outputstream);
                outputstream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(reqCode == CONTACT){
            if(resCode == RESULT_OK){
                Uri contactData = data.getData();
                Cursor person = getContentResolver().query(contactData, null, null, null, null);
                int indexName = person.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);

                person.moveToFirst();

                String name = person.getString(indexName);
                EditText display = (EditText) findViewById(R.id.bestFriend);
                display.setText(name);
            }
        }
    }

}
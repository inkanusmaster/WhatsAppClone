package com.example.parseproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import mwfuryapp.whatsappclone.MainActivity;
import mwfuryapp.whatsappclone.R;

public class ChatActivity extends AppCompatActivity {

    String activeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        activeUser = intent.getStringExtra("username");

        Toast.makeText(ChatActivity.this, "Clicked user:" + activeUser, Toast.LENGTH_SHORT).show();
    }
}
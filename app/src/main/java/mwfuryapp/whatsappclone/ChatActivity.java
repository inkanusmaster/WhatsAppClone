package mwfuryapp.whatsappclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class ChatActivity extends AppCompatActivity {

    String activeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        activeUser = intent.getStringExtra("username");
        setTitle("Chat with "+activeUser);

        Toast.makeText(ChatActivity.this, "Clicked user:" + activeUser, Toast.LENGTH_SHORT).show();
    }
}
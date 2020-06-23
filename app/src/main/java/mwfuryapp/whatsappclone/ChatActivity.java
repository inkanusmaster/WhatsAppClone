package mwfuryapp.whatsappclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class ChatActivity extends AppCompatActivity {

    String activeUser = "";
    EditText chatEditText;
    ParseObject message;

    public void sendMessage(View view) {
        message = new ParseObject("Message");
        message.put("Sender", ParseUser.getCurrentUser().getUsername());
        message.put("Recipient", activeUser);
        message.put("Message", chatEditText.getText().toString());
        message.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(ChatActivity.this, "Message sent!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChatActivity.this, "Failed to send message!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        chatEditText = findViewById(R.id.chatEditText);
        Intent intent = getIntent();
        activeUser = intent.getStringExtra("username");
        setTitle("Chat with " + activeUser);

        Toast.makeText(ChatActivity.this, "Clicked user:" + activeUser, Toast.LENGTH_SHORT).show();
    }
}
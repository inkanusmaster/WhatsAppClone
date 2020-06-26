package mwfuryapp.whatsappclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChatActivity extends AppCompatActivity {

    String activeUser = "";
    EditText chatEditText;
    ParseObject message;

    ListView chatListView;
    ArrayList<String> messages = new ArrayList<>(); //ArrayList zawierający wiadomości z czatu
    ArrayAdapter arrayAdapter;

    public void sendMessage(View view) {
        message = new ParseObject("Message");
        final String messageContent = chatEditText.getText().toString();
        message.put("Sender", ParseUser.getCurrentUser().getUsername());
        message.put("Recipient", activeUser);
        message.put("Message", messageContent);
        chatEditText.setText(""); //czyścimy edittext po wysłaniu wiadomości
        message.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    messages.add(messageContent);
                    arrayAdapter.notifyDataSetChanged();
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

        chatListView = findViewById(R.id.chatListView);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, messages); //arrayadapter z wiadomościami czatu
        chatListView.setAdapter(arrayAdapter);


        // DWA ZAPYTANIA MUSZĄ BYC DO CHATU!!! OGARNIJ TO!!! QUERY1 i QUERY2!!!!
        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Message");
        query1.whereEqualTo("Sender", ParseUser.getCurrentUser().getUsername()); //sender to obecnie zalogowany user, a recipient to activeuser (ten z ktorym gadamy)
        query1.whereEqualTo("Recipient", activeUser);

        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Message");
        query2.whereEqualTo("Recipient", ParseUser.getCurrentUser().getUsername());
        query2.whereEqualTo("Sender", activeUser); //sender to activeuser, a recipient to obecnie zalogowany user)

        //TERAZ ROBIMY KOMBINACJĘ TYCH DWÓCH ZAPYTAN
        //Mianowicie Arraylist typu ParseQuery<ParseObject> o nazwie queries
        ArrayList<ParseQuery<ParseObject>> queries = new ArrayList<>();

        //Dodajemy wyniki obu zapytań do arraylisty queries
        queries.add(query1);
        queries.add(query2);

        //Na końcu robimy GLÓWNE ZAPYTANIE. Query wykona OR na queries czyli wyświetli wynik, który będzie równy query1 or query2.
        ParseQuery<ParseObject> query = ParseQuery.or(queries);

        //Sortujemy po dacie
        query.orderByAscending("createdAt");

        //No i wykonujemy zapytanie z callbackiem
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    messages.clear();
                    //Teraz dodajemy do listy, ale chcemy żeby było jasne, które są od nas, a które od rozmówcy.
                    //Aby to osiągnąć, zrobimy append znaku ">" jeśli wiadomość jest od innego usera.
                    //ROZKMIN JAK TO LEPIEJ ZROBIC
                    for (ParseObject message : objects) {
                        String messageContent = message.getString("Message"); //Pobieramy zawartość Message do zmienneej messageContent
                        if (!message.getString("Sender").equals(ParseUser.getCurrentUser().getUsername())) {
                            messageContent = "> " + messageContent;
                        }
                        messages.add(messageContent);
                    }
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
package mwfuryapp.whatsappclone;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {

    EditText usernameEditText;
    EditText passwordEditText;
    Boolean loginModeActive;
    Button signupLoginButton;
    TextView toggleLoginModeTextView;

    public void redirectIfLoggedIn() {
        if (ParseUser.getCurrentUser() != null) {
            Intent intent = new Intent(getApplicationContext(), UserListActivity.class);
            startActivity(intent);
        }
    }

    @SuppressLint("SetTextI18n")
    public void toggleLoginMode(View view) {
        signupLoginButton = findViewById(R.id.signupLoginButton);
        toggleLoginModeTextView = findViewById(R.id.toggleLoginModeTextView);
        if (loginModeActive) {
            signupLoginButton.setText("Signup");
            toggleLoginModeTextView.setText("Or, login");
            loginModeActive = false;
        } else {
            signupLoginButton.setText("Login");
            toggleLoginModeTextView.setText("Or, signup");
            loginModeActive = true;
        }
    }

    public void signupLogin(View view) {
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        if (loginModeActive) { // zalogowanie z callbackiem
            ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (e == null) {
                        Toast.makeText(MainActivity.this, "User logged in!", Toast.LENGTH_SHORT).show();
                        redirectIfLoggedIn();
                    } else {
                        Toast.makeText(MainActivity.this, "Invalid credentials!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else { // stworzenie usera
            ParseUser user = new ParseUser();
            user.setUsername(usernameEditText.getText().toString().toLowerCase());
            user.setPassword(passwordEditText.getText().toString());
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Toast.makeText(MainActivity.this, "User signed up!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, e.getMessage().substring(e.getMessage().indexOf(" ")), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginModeActive = false;
        redirectIfLoggedIn();

        // pozwala niby analizować ilu userów używa aplikacji
        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }
}

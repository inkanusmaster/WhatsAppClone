package mwfuryapp.whatsappclone;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class StarterApplication extends Application {

    public void onCreate() {
        super.onCreate();
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here. Dane z instancji serwera na AWS
        // /home/bitnami/apps/parse/htdocs/server.js
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("59e9bd65c9b09064c7a4c497852caaf79f636262")
                .clientKey("9ef06bb019c6c76c1dafe53dac25bcaa32c1f4e2") //masterkey in .js file
                .server("http://18.130.9.67:80/parse/")
                .build()
        );
//        // Dodaję klasę ExampleObject do moich apps. W tym Example object.put pierszy parametr to nazwa kolumny, a drugi parametr to wartość
//        // Myśl o klasie jako o tabeli w bazie danych
//        ParseObject object = new ParseObject("ExampleObject");
//        object.put("myNumber", "321");
//        object.put("myString", "fury");
//        object.put("myMaster", "ZiomekMistrz");
//        // Zapisujemy objekt. Log wyrzuci nam czy się udało
//        object.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException ex) {
//                if (ex == null) {
//                    System.out.println("Parse Result Successful!");
//                } else {
//                    System.out.println("Parse Result Failed" + ex.toString());
//                }
//            }
//        });
        // Daje dostęp anonimowemu użytkownikowi do mojej bazy. W tabeli users jest taki dziwny ziomek          ParseUser.enableAutomaticUser();
        // Robi ACL z prawami dostępu. To dodatkowa kolumna w klasie ExampleObject
//        ParseACL defaultACL = new ParseACL();
//        defaultACL.setPublicReadAccess(true);
//        defaultACL.setPublicWriteAccess(true);
//        ParseACL.setDefaultACL(defaultACL, true);
    }
}



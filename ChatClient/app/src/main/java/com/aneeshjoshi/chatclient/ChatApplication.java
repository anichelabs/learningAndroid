package com.aneeshjoshi.chatclient;

import android.app.Application;

import com.aneeshjoshi.chatclient.models.Message;
import com.parse.Parse;
import com.parse.ParseObject;

public class ChatApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        ParseObject.registerSubclass(Message.class);
        Parse.initialize(this, "NQGxucwopcOoXgJfflymXZpBdATfyxjnjFqaf44N", "OWiSXMYRwGlchteS9E6VU4gQdTgHHxjjqx1YghF3");

        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();
    }
}

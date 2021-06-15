package com.example.bus.dental.utilities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class MySession {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;


    public MySession(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("appsave", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public  void setLoggedin(boolean check_login, String uid) {
        editor.putBoolean("MySession", check_login);
        editor.putString("uid", uid);

        editor.apply();
    }

    public boolean logged() {
        return sharedPreferences.getBoolean("MySession", false);
    }

    public String getUid() {
        return sharedPreferences.getString("Uid", "");

    }


    public void logout() {
        editor.clear();
        editor.commit();
    }
}

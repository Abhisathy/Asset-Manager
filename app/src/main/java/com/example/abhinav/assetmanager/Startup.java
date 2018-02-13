package com.example.abhinav.assetmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Abhinav on 23-06-2016.
 */
public class Startup extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page1);
    }
    public void cont(View view){
        Intent intent = new Intent(Startup.this, MainActivity1.class);
        finish();
        startActivity(intent);
    }
}

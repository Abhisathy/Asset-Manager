package com.example.abhinav.assetmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity1 extends AppCompatActivity {

    String aDataRow;
    String FILENAME="lists";
    ArrayList<String> assets = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page2);
        try {
            //File myFile1 = new File(Environment.getExternalStorageDirectory().getPath() + "/AssetManager/");
            //myFile1.mkdir();
            String filename = "ScannerType";
            if(!isFilePresent(filename)) {
                String string = "phCam";
                FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);
                fos.write(string.getBytes());
                fos.close();
            }
            if(!isFilePresent(FILENAME)) {
                String string = "";
                FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
                fos.write(string.getBytes());
                fos.close();
            }
            else {
                FileInputStream fis = openFileInput(FILENAME);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                    assets.add(line);
                    sb.append("\n");
                }
            }
            /*File myFile = new File(myFile1, FILENAME);
            if (!myFile.exists()) {
                myFile.createNewFile();
                FileOutputStream fOut = new FileOutputStream(myFile);
                OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                myOutWriter.write("phoneCam");
                myOutWriter.close();
                fOut.close();
            }
            else{
                try {
                    File myFile2 = new File(Environment.getExternalStorageDirectory().getPath() + "/AssetManager/" + FILENAME);
                    FileInputStream fIn = new FileInputStream(myFile2);
                    BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
                    aDataRow = "";
                    if (myReader.readLine() != null) {
                        aDataRow=myReader.readLine();
                    }
                    myReader.close();
                }
                catch (Exception e) {
                    Toast.makeText(getBaseContext(), e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }*/

        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isFilePresent(String fileName) {
        String path = getApplicationContext().getFilesDir().getAbsolutePath() + "/" + fileName;
        File file = new File(path);
        return file.exists();
    }

    public void click1(View view) {
        // Do something in response to button
        /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Select the type of file:")
                .setCancelable(false)
                .setPositiveButton("AssociativeFile", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(MainActivity1.this, AssociativeFile.class);
                        startActivity(intent);
                    }
                })
                .setNeutralButton("Simple", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(MainActivity1.this, SimpleFile.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();*/
        Bundle basket = new Bundle();
        basket.putStringArrayList("arraylist", assets);
        Intent a = new Intent(MainActivity1.this, NewFile.class);
        finish();
        a.putExtras(basket);
        startActivity(a);
    }

    public void click2(View view) {
        // Do something in response to button
        //Bundle basket = new Bundle();
        //basket.putString("scanType", aDataRow);
        Intent a = new Intent(MainActivity1.this, openfile.class);
        finish();
        //a.putExtras(basket);
        startActivity(a);

    }

    public void click3(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, openFile1.class);
        finish();
        startActivity(intent);

    }

    public void click4(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, openFile2.class);
        finish();
        startActivity(intent);

    }

    public void click5(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, Asset.class);
        finish();
        startActivity(intent);

    }
    public void click6(View view) {
        // Do something in response to button
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        System.exit(0);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
        alert.setCanceledOnTouchOutside(true);

    }

    @Override
    public void onBackPressed() {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                            System.exit(0);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
            alert.setCanceledOnTouchOutside(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.settings) {
            Intent intent = new Intent(this, ScannerType.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

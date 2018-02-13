package com.example.abhinav.assetmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class Main1Activity extends openfile {
    private ListView listView;
    private TextView textView;

        // GUI controls
        TextView txt;
        //Button btnWriteSDFile;
       // Button Load;
        String str;
        //Button btnClearScreen;
        //Button btnClose;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_1main);
            // bind GUI elements with local controls
            txt = (TextView)findViewById(R.id.tv);

            Bundle gt=getIntent().getExtras();
            str=gt.getString("abc");
           // txt.setText(str);
            textView = (TextView) findViewById(R.id.Filename);
            textView.setText(str);
            try {
                File myFile = new File(Environment.getExternalStorageDirectory().getPath()+"/AssetManager/"+str);
                FileInputStream fIn = new FileInputStream(myFile);
                BufferedReader myReader = new BufferedReader(
                        new InputStreamReader(fIn));
                String aDataRow = "";
                String aBuffer = "";
                txt.setText("");
                while ((aDataRow = myReader.readLine()) != null) {
                    aBuffer += aDataRow;
                    aBuffer=aBuffer.replace('#', ' ');
                    aBuffer=aBuffer.replace('*', ' ');
                    //aBuffer=aBuffer.replace(',', ' ');
                    aBuffer=aBuffer.replace(';', ' ');
                    aBuffer=aBuffer.trim();
                    int t=aDataRow.length()*2;
                    aBuffer += "\n";
                    for(int i=0;i<t;i++)
                        aBuffer += "-";

                    aBuffer += "\n";
                }

                if(!aBuffer.equals("")) {
                    txt.append(aBuffer);
                }
                else
                    txt.setText(R.string.emptyfile);

                myReader.close();
            } catch (Exception e) {
                Toast.makeText(getBaseContext(), e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
            /*setContentView(R.layout.activity_display);
            listView = (ListView) findViewById(R.id.listView);
            textView = (TextView) findViewById(R.id.Filename);
            textView.setText(str);
            itemArrayAdapter = new ItemArrayAdapter(getApplicationContext(), R.layout.activity_1main);

            //Parcelable state = listView.onSaveInstanceState();
            listView.setAdapter(itemArrayAdapter);
            //listView.onRestoreInstanceState(state);
            try{
            File myFile = new File(Environment.getExternalStorageDirectory().getPath()+"/AssetManager/"+str);
            FileInputStream fIn = new FileInputStream(myFile);

            //InputStream inputStream = fIn;
            CSVFile csvFile = new CSVFile(fIn);
            List<String[]> scoreList = csvFile.read();

            for(String[] scoreData:scoreList ) {
                itemArrayAdapter.add(scoreData);
            }
            } catch (Exception e) {
                Toast.makeText(getBaseContext(), e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }*/
        }// onCreate

    public void onCancel(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to close the CSV file?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent a = new Intent(Main1Activity.this, MainActivity1.class);
                        finish();
                        startActivity(a);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to close the CSV file?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent a = new Intent(Main1Activity.this, MainActivity1.class);
                        finish();
                        startActivity(a);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}


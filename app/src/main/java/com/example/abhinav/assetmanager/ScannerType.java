package com.example.abhinav.assetmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by Abhinav on 23-06-2016.
 */
public class ScannerType extends MainActivity1 {
    String scanType="";
    String FILENAME="ScannerType";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanner_type);
        Switch aSwitch=(Switch) findViewById(R.id.scantype);
        try {

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
                String x="";
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                    x=line;
                }
                scanType=x;
                bufferedReader.close();
                isr.close();
                fis.close();
            }
        /*try {
            File myFile2 = new File(Environment.getExternalStorageDirectory().getPath() + "/AssetManager/ScannerType.txt");
            FileInputStream fIn = new FileInputStream(myFile2);
            BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
            String aDatarow="";
            if ((aDatarow=myReader.readLine()) != null) {
                scanType=aDatarow;
            }
            myReader.close();*/
        }
        catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
        try {
            if (scanType.equals("phoneCam")) {
                aSwitch.setChecked(false);
            } else if (scanType.equals("externalScan")) {
                aSwitch.setChecked(true);
            }
            if (!(aSwitch.isChecked())) {
                TextView txt = (TextView) findViewById(R.id.phCamera);
                TextView txt1 = (TextView) findViewById(R.id.exScan);
                if (txt != null && txt1 != null) {
                    txt.setTextColor(getResources().getColor(R.color.colorAccent));
                    txt1.setTextColor(getResources().getColor(R.color.colorBlack));
                }
            } else {
                TextView txt = (TextView) findViewById(R.id.exScan);
                TextView txt1 = (TextView) findViewById(R.id.phCamera);
                if (txt != null && txt1 != null) {
                    txt.setTextColor(getResources().getColor(R.color.colorAccent));
                    txt1.setTextColor(getResources().getColor(R.color.colorBlack));
                }
            }
        }
        catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }


    }
    public boolean isFilePresent(String fileName) {
        String path = getApplicationContext().getFilesDir().getAbsolutePath() + "/" + fileName;
        File file = new File(path);
        return file.exists();
    }

    public void scan(View view){
        Switch aSwitch=(Switch) view.findViewById(R.id.scantype);
        if(aSwitch.isChecked()){
            scanType="externalScan";
            TextView txt=(TextView) findViewById(R.id.exScan);
            TextView txt1=(TextView) findViewById(R.id.phCamera);
            if(txt!=null && txt1!=null){
                txt.setTextColor(getResources().getColor(R.color.colorAccent));
                txt1.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        }
        else {
            scanType="phoneCam";
            TextView txt=(TextView) findViewById(R.id.phCamera);
            TextView txt1=(TextView) findViewById(R.id.exScan);
            if(txt!=null && txt1!=null){
                txt.setTextColor(getResources().getColor(R.color.colorAccent));
                txt1.setTextColor(getResources().getColor(R.color.colorBlack));
            }
        }
        try {
            /*File myFile = new File(Environment.getExternalStorageDirectory().getPath()+"/AssetManager/");
            myFile.mkdir();
            File myFile1 = new File(myFile, FILENAME);
            myFile.createNewFile();
            FileOutputStream fOut = new FileOutputStream(myFile1);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.write(scanType);
            myOutWriter.close();*/
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(scanType.getBytes());
            fos.close();

        }
        catch (Exception e){
            Toast.makeText(getBaseContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed(){
       this.finish();
    }

    public void onCancel(View view){
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }
}
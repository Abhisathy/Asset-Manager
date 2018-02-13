package com.example.abhinav.assetmanager;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Asset extends MainActivity1 {
    EditText type;
    TextView tt;
    Context context=this;
    String FILENAME="lists";
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asset);
        type = (EditText) findViewById(R.id.textView3);
        //tt = (TextView) findViewById(R.id.textView5);
        type.setPressed(false);
        type.setActivated(false);
        type.setSelected(false);
        lv = (ListView) findViewById(R.id.listView5);
        //type.setText("");
        //tt.setText("");

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
                ArrayList<String> assets =new ArrayList<String>();
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                    assets.add(line);
                    sb.append("\n");
                }
                //ArrayList<String> assets =GetAssets(FILENAME);
                if(assets!=null) {
                    lv.setAdapter(new ArrayAdapter<String>(this,
                            android.R.layout.simple_list_item_1, assets));

                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                            // Clicking on items
                            final String assetName = (String) parent.getItemAtPosition(position);
                            LayoutInflater li = LayoutInflater.from(context);
                            View promptsView = li.inflate(R.layout.open_prompt2, null);

                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                    context);

                            // set prompts.xml to alertdialog builder
                            alertDialogBuilder.setView(promptsView);
                            final AlertDialog alertDialog = alertDialogBuilder.create();

                            // show it

                            alertDialog.show();
                            Button b1 = (Button) promptsView.findViewById(R.id.delete);
                            b1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    //  openFile(filename);
                                    try {
                                        FileInputStream fis = openFileInput(FILENAME);
                                        InputStreamReader isr = new InputStreamReader(fis);
                                        BufferedReader bufferedReader = new BufferedReader(isr);
                                        String line;
                                        StringBuilder sb = new StringBuilder();
                                        while ((line = bufferedReader.readLine()) != null) {
                                            if (!line.equals(assetName)) {
                                                sb.append(line);
                                                sb.append("\n");
                                            }
                                        }
                                        FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
                                        fos.write(sb.toString().getBytes());
                                        fos.close();
                                    } catch (Exception e) {
                                        Toast.makeText(getBaseContext(), e.getMessage(),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    alertDialog.cancel();
                                    Intent a = new Intent(Asset.this, Asset.class);
                                    finish();
                                    startActivity(a);
                                }
                            });

                        }
                    });
                    lv.isLongClickable();
                    lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
                            // Clicking on items
                            final String assetName = (String) parent.getItemAtPosition(position);
                            LayoutInflater li = LayoutInflater.from(context);
                            View promptsView = li.inflate(R.layout.open_prompt2, null);

                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                    context);

                            // set prompts.xml to alertdialog builder
                            alertDialogBuilder.setView(promptsView);
                            final AlertDialog alertDialog = alertDialogBuilder.create();

                            // show it

                            alertDialog.show();
                            Button b1 = (Button) promptsView.findViewById(R.id.delete);
                            b1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    //  openFile(filename);
                                    try {
                                        FileInputStream fis = openFileInput(FILENAME);
                                        InputStreamReader isr = new InputStreamReader(fis);
                                        BufferedReader bufferedReader = new BufferedReader(isr);
                                        String line;
                                        StringBuilder sb = new StringBuilder();
                                        while ((line = bufferedReader.readLine()) != null) {
                                            if (!line.equals(assetName)) {
                                                sb.append(line);
                                                sb.append("\n");
                                            }
                                        }
                                        FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
                                        fos.write(sb.toString().getBytes());
                                        fos.close();
                                    } catch (Exception e) {
                                        Toast.makeText(getBaseContext(), e.getMessage(),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    alertDialog.cancel();
                                    Intent a = new Intent(Asset.this, Asset.class);
                                    finish();
                                    startActivity(a);
                                }
                            });
                            return true;
                        }
                    });
                }
                else {
                    /*LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View rowView = inflater.inflate(R.layout.empty_view, null);
                    lv.setEmptyView(rowView);*/

                }
                //tt.setText(sb);
                /*bufferedReader.close();
                isr.close();
                fis.close();*/
            }
            /*File myFile1 = new File(Environment.getExternalStorageDirectory().getPath() + "/AssetManager/");
            myFile1.mkdir();
            File myFile = new File(myFile1, filename);
            if (!myFile.exists()) {
                myFile.createNewFile();
                FileOutputStream fOut = new FileOutputStream(myFile);
                OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                myOutWriter.write("");
                myOutWriter.close();
                fOut.close();
            }
            else{
                try {
                    File myFile2 = new File(Environment.getExternalStorageDirectory().getPath() + "/AssetManager/" + filename);
                    FileInputStream fIn = new FileInputStream(myFile2);
                    BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
                    String aDataRow = "";
                    String aBuffer = "";
                    while ((aDataRow = myReader.readLine()) != null) {
                        aBuffer += aDataRow+"\n";
                    }
                    tt.append(aBuffer);*/
            /*for (int i = 0; i <(int) aDataRow.length(); i++) {
                while (!(aDataRow.charAt(i) == ',')){
                    tt.setText(aDataRow.charAt(i));
                }
                if(aDataRow.charAt(i)==',') {
                    i++;
                    tt.setText("\n");
                }
            }*/
                    /*myReader.close();
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

    /*public ArrayList<String> GetAssets(String DirectoryPath) {
        ArrayList<String> MyAssets = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        String x[]=new String[100];
        try {
            FileInputStream fis = openFileInput(DirectoryPath);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);

            String line;
            int i=0;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
                x[i]=line;
                i++;
            }
        }
        catch (Exception e){
            Toast.makeText(getBaseContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
        String[] assets = x;
        if (assets.length == 0)
            return null;
        else {
            for (int i = 0; i < assets.length; i++) {
                    MyAssets.add(assets[i]);
            }
        }

        return MyAssets;
    }*/

    @Override
    public void onBackPressed(){
        Intent a = new Intent(Asset.this, MainActivity1.class);
        finish();
        startActivity(a);
    }

    public void onCancel(View view){
        Intent a = new Intent(Asset.this, MainActivity1.class);
        finish();
        startActivity(a);
    }

    public void click2(View view) {
        Editable str;
        str = type.getText();
        String str1=str.toString()+"\n";
        if(str.toString().equals("")||str.toString().equals(" ")){
            Toast.makeText(getBaseContext(), str.toString() + "Invalid asset type",
                    Toast.LENGTH_SHORT).show();
            type.setText("");
        }
        else {
            Toast.makeText(getBaseContext(), str.toString() + " added to asset type",
                    Toast.LENGTH_SHORT).show();
            type.setText("");

            try {
                FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_APPEND);
                fos.write(str1.getBytes());
                fos.close();
                FileInputStream fis = openFileInput(FILENAME);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String line;

                ArrayList<String> assets = new ArrayList<String>();
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                    assets.add(line);
                    sb.append("\n");
                }
                if (assets != null) {
                    lv.setAdapter(new ArrayAdapter<String>(this,
                            android.R.layout.simple_list_item_1, assets));
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                            // Clicking on items
                            final String assetName = (String) parent.getItemAtPosition(position);
                            LayoutInflater li = LayoutInflater.from(context);
                            View promptsView = li.inflate(R.layout.open_prompt2, null);

                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                    context);

                            // set prompts.xml to alertdialog builder
                            alertDialogBuilder.setView(promptsView);
                            final AlertDialog alertDialog = alertDialogBuilder.create();

                            // show it

                            alertDialog.show();
                            Button b1 = (Button) promptsView.findViewById(R.id.delete);
                            b1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    //  openFile(filename);
                                    try {
                                        FileInputStream fis = openFileInput(FILENAME);
                                        InputStreamReader isr = new InputStreamReader(fis);
                                        BufferedReader bufferedReader = new BufferedReader(isr);
                                        String line;
                                        StringBuilder sb = new StringBuilder();
                                        while ((line = bufferedReader.readLine()) != null) {
                                            if (!line.equals(assetName)) {
                                                sb.append(line);
                                                sb.append("\n");
                                            }
                                        }
                                        FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
                                        fos.write(sb.toString().getBytes());
                                        fos.close();
                                    } catch (Exception e) {
                                        Toast.makeText(getBaseContext(), e.getMessage(),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    alertDialog.cancel();
                                    Intent a = new Intent(Asset.this, Asset.class);
                                    finish();
                                    startActivity(a);
                                }
                            });

                        }
                    });
                    lv.isLongClickable();

                    lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
                            // Clicking on items
                            final String assetName = (String) parent.getItemAtPosition(position);
                            LayoutInflater li = LayoutInflater.from(context);
                            View promptsView = li.inflate(R.layout.open_prompt2, null);

                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                    context);

                            // set prompts.xml to alertdialog builder
                            alertDialogBuilder.setView(promptsView);
                            final AlertDialog alertDialog = alertDialogBuilder.create();

                            // show it

                            alertDialog.show();
                            Button b1 = (Button) promptsView.findViewById(R.id.delete);
                            b1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    //  openFile(filename);
                                    try {
                                        FileInputStream fis = openFileInput(FILENAME);
                                        InputStreamReader isr = new InputStreamReader(fis);
                                        BufferedReader bufferedReader = new BufferedReader(isr);
                                        String line;
                                        StringBuilder sb = new StringBuilder();
                                        while ((line = bufferedReader.readLine()) != null) {
                                            if (!line.equals(assetName)) {
                                                sb.append(line);
                                                sb.append("\n");
                                            }
                                        }
                                        FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
                                        fos.write(sb.toString().getBytes());
                                        fos.close();
                                    } catch (Exception e) {
                                        Toast.makeText(getBaseContext(), e.getMessage(),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    alertDialog.cancel();
                                    Intent a = new Intent(Asset.this, Asset.class);
                                    finish();
                                    startActivity(a);
                                }
                            });
                            return true;
                        }
                    });
                } else {
                /*LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View rowView = inflater.inflate(R.layout.empty_view, null);
                lv.setEmptyView(rowView);*/
                }
                //tt.append(x+"\n");
                bufferedReader.close();
                isr.close();
                fis.close();
            /*File myFile1 = new File(Environment.getExternalStorageDirectory().getPath() + "/AssetManager/" + filename);

            FileInputStream fIn = new FileInputStream(myFile1);
            BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
            String aDataRow = "";
            String aBuffer = "";
            while ((aDataRow = myReader.readLine()) != null) {
                aBuffer += aDataRow+"\n";
            }
            myReader.close();
            fIn.close();
            FileOutputStream fOut = new FileOutputStream(myFile1);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.write(aBuffer);
            myOutWriter.append(str);
            myOutWriter.close();
            fOut.close();*/
            } catch (Exception e) {
                Toast.makeText(getBaseContext(), e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        /*try {
            File myFile1 = new File(Environment.getExternalStorageDirectory().getPath() + "/AssetManager/" + filename);
            FileInputStream fIn = new FileInputStream(myFile1);
            BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
            String aDataRow = "";
            String aBuffer = "";
            while ((aDataRow = myReader.readLine()) != null) {
                aBuffer = aDataRow+"\n";
            }
            tt.append(aBuffer);
            myReader.close();
        }
            catch (Exception e) {
                Toast.makeText(getBaseContext(), e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }*/
        }
    }
}

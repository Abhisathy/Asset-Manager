package com.example.abhinav.assetmanager;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


public class NewFile extends MainActivity1  {
    private Spinner spinner1;
    private LinearLayout mw;
    EditText et;
    int pos=0;
    int ct=1;
    Editable sr;
    String filetype=null;
    CheckBox c,c1;
    private static final String []Scan = {"Normal", "Associated"};;
    String itemList[]=new String[100];
    String itemCateg[]=new String[100];
    int count,count1=0;
    int k=0;
    boolean p=false;
    private LinearLayout mContainerView;
    private EditText temp;
    private View temp1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_file);
        spinner1 = (Spinner) findViewById(R.id.spinner);
        mContainerView=(LinearLayout) findViewById(R.id.line1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(NewFile.this, android.R.layout.simple_spinner_item, Scan);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setPrompt("Select Job Type");
        spinner1.setSelected(false);
        FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.fab);
        Button bab=(Button)findViewById(R.id.button4);

        //sp = (Spinner) mw.findViewById(R.id.spinner2);
        //sp.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                if(!p)
                filetype = (String) parent.getItemAtPosition(position);
                else {
                    spinner1.setEnabled(false);
                    spinner1.setSelected(false);
                }
            }
        });


    }

    public ArrayList<String> GetFiles(String DirectoryPath) {
        ArrayList<String> MyFiles = new ArrayList<String>();
        File f = new File(DirectoryPath);

        f.mkdirs();
        File[] files = f.listFiles();
        if (files.length == 0)
            return null;
        else {
            for (int i = 0; i < files.length; i++)
                MyFiles.add(files[i].getName());
        }

        return MyFiles;
    }

    public void Continue(View view) {
        int ntchck=1;
        int flag=0;
        count1=0;
        count=0;
        String scant = null;
        mw = (LinearLayout) findViewById(R.id.line1);
        for (int i = 0; i < mw.getChildCount() - 1; i++) {
            temp1 = mw.getChildAt(i);
            c = (CheckBox) temp1.findViewById(R.id.checkBox);
            c1 = (CheckBox) temp1.findViewById(R.id.checkBox2);
            if(c1!=null && c1.isChecked() && c != null && c.isChecked()){
                ntchck = 0;
                count++;
                count1++;
            }
            else if(c1!=null && !c1.isChecked() && c != null && c.isChecked() && temp1!=null){
                ntchck = 1;
                count1++;
            }
            else if(c1!=null && c1.isChecked() && c!=null){
                count1++;
            }
            else if(c1!=null && c1.isChecked()){
                count1++;
            }

        }
        if(count<1 && filetype.equals("Associated")){
            Toast.makeText(getBaseContext(), "At least one item should have Key field checked",
                    Toast.LENGTH_SHORT).show();
            flag = 1;
        }
        else if(count>1 && filetype.equals("Associated")){
            Toast.makeText(getBaseContext(), "Only one item should have Key field checked",
                    Toast.LENGTH_SHORT).show();
            flag = 1;
        }
        else if(filetype.equals("Associated") && ntchck==1){
            Toast.makeText(getBaseContext(), "Key item should have Required field checked",
                    Toast.LENGTH_SHORT).show();
            flag = 1;
        }
        else if(count1==0 && filetype.equals("Normal")){
            Toast.makeText(getBaseContext(), "Atleast one item should have Required field checked",
                    Toast.LENGTH_SHORT).show();
            flag = 1;
        }
        else if(count1<2 && filetype.equals("Associated")){
            Toast.makeText(getBaseContext(), "At least 2 items should be checked as Required (one Key and one associate)",
                    Toast.LENGTH_SHORT).show();
            flag = 1;
        }
        else
        flag=0;

        if(flag==0) {
            try {
                boolean f=false;
                et = (EditText) findViewById(R.id.textView7);
                sr= et.getText();
                ArrayList<String> FilesInFolder = GetFiles(Environment.getExternalStorageDirectory().getPath() + "/AssetManager/");
                if(sr!=null && !sr.toString().equals("")) {
                    if (FilesInFolder != null) {
                        for (int i = 0; i < FilesInFolder.size(); i++) {
                            if ((FilesInFolder.get(i)).equals(sr.toString()+".csv")) {
                                // add here
                                f = true;
                                break;
                            } else {
                                f = false;
                            }
                        }
                    }

                if(f) {
                    Toast.makeText(getBaseContext(),
                            "File exists",
                            Toast.LENGTH_SHORT).show();
                    flag=1;
                }
                else {
                    String FILENAME = "ScannerType";
                    FileInputStream fis = openFileInput(FILENAME);
                    InputStreamReader isr = new InputStreamReader(fis);
                    BufferedReader bufferedReader = new BufferedReader(isr);
                    String line;
                    String x = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        x = line;
                    }
                    scant = x;
                    bufferedReader.close();
                    isr.close();
                    fis.close();
							/*File myFile2 = new File("ScannerType.txt");
							FileInputStream fIn1 = new FileInputStream(myFile2);
							BufferedReader myReader1 = new BufferedReader(new InputStreamReader(fIn1));
							String scant=myReader1.readLine();
							myReader1.close();
							fIn1.close();*/

                    File myFile = new File(Environment.getExternalStorageDirectory().getPath() + "/AssetManager/");
                    myFile.mkdir();
                    File myFile1 = new File(myFile, sr.toString()+".csv");
                    myFile.createNewFile();
                    FileOutputStream fOut = new FileOutputStream(myFile1);
                    OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                    myOutWriter.write("");
                    for (int i = 0; i < mContainerView.getChildCount() - 1; i++) {
                        temp1 = mContainerView.getChildAt(i);
                        c = (CheckBox) temp1.findViewById(R.id.checkBox);
                        c1 = (CheckBox) temp1.findViewById(R.id.checkBox2);
                        Spinner sp=(Spinner) temp1.findViewById(R.id.spinner2);
                        if (c != null && c1 != null && sp!=null) {
                            if (c.isChecked())
                                myOutWriter.append("#" + sp.getSelectedItem().toString() + "*");
                            else if (c1.isChecked())
                                myOutWriter.append(sp.getSelectedItem().toString() + "*");
                            else
                                myOutWriter.append(sp.getSelectedItem().toString());
                            int j = i + 1;
                            int lastc = 0;
                            while (j < mContainerView.getChildCount() - 1) {
                                temp1 = mContainerView.getChildAt(i);
                                c1 = (CheckBox) temp1.findViewById(R.id.checkBox2);
                                if (c1 != null)
                                    lastc++;
                                j++;
                            }
                            if (lastc == 0) {
                                myOutWriter.append(";,");
                            } else
                                myOutWriter.append(",");
                        }
                        else if(c1 != null && sp!=null){
                            if (c1.isChecked())
                                myOutWriter.append(sp.getSelectedItem().toString() + "*");
                            else
                                myOutWriter.append(sp.getSelectedItem().toString());
                            int j = i + 1;
                            int lastc = 0;
                            while (j < mContainerView.getChildCount() - 1) {
                                temp1 = mContainerView.getChildAt(i);
                                c1 = (CheckBox) temp1.findViewById(R.id.checkBox2);
                                if (c1 != null)
                                    lastc++;
                                j++;
                            }
                            if (lastc == 0) {
                                myOutWriter.append(";,");
                            } else
                                myOutWriter.append(",");
                        }
                    }
                    myOutWriter.close();
                    fOut.close();

                    Toast.makeText(getBaseContext(),
                            "File saved",
                            Toast.LENGTH_SHORT).show();
                    flag = 0;
                }
                }
                else {
                    Toast.makeText(getBaseContext(),
                            "Invalid Filename",
                            Toast.LENGTH_SHORT).show();
                    flag=1;
                }
            } catch (Exception e) {
                Toast.makeText(getBaseContext(), e.getMessage(),
                        Toast.LENGTH_SHORT).show();
                flag = 1;
            }
            if (flag == 0) {
                Bundle basket = new Bundle();
                basket.putString("abc", sr.toString()+".csv");
                basket.putString("scanType", scant);
                Intent intent = new Intent(NewFile.this, ScanFile.class);
                intent.putExtras(basket);
                finish();
                startActivity(intent);
            }
        }
    }

    public void cancel(View view) {
        Intent i = new Intent(NewFile.this,MainActivity1.class);
        finish();
        startActivity(i);
    }

    private void inflateEditRow(String name) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.additem, null);
        //final ImageButton deleteButton = (ImageButton) rowView.findViewById(R.id.buttonDelete);
        final Spinner spinner2=(Spinner) rowView.findViewById(R.id.spinner2);
        final CheckBox chbx1=(CheckBox) rowView.findViewById(R.id.checkBox);
        //final CheckBox chbx2=(CheckBox) rowView.findViewById(R.id.checkBox2);
        final TextView item=(TextView) rowView.findViewById(R.id.itemcount);

        Bundle extras = getIntent().getExtras();
        ArrayList<String> arraylist  = extras.getStringArrayList("arraylist");
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(NewFile.this, android.R.layout.simple_spinner_item, arraylist);
        spinner2.setAdapter(adapter1);
        if (name != null && name.equalsIgnoreCase("Normal")) {
            chbx1.setEnabled(false);
            chbx1.setVisibility(View.GONE);

        } else if(name != null && name.equalsIgnoreCase("Associated")){
            chbx1.setEnabled(true);
            chbx1.setVisibility(View.VISIBLE);
        }

        item.setText("Item"+ct);
        ct++;

        // Inflate at the end of all rows but before the "Add new" button
        mContainerView.addView(rowView, mContainerView.getChildCount() -1);
    }
    public void onAddNewClicked(View v) {
        // Inflate a new row and hide the button self.
        inflateEditRow(filetype);
        p=true;
        spinner1.setEnabled(false);
        spinner1.setSelected(false);
        //v.setVisibility(View.GONE);
    }

    // onClick handler for the "X" button of each row
    public void onDeleteClicked(View v) {
        // remove the row by calling the getParent on button
        ct=1;
        mContainerView.removeView((View) v.getParent());
        for (int i = 0; i < mContainerView.getChildCount(); i++) {
            final TextView item = (TextView) mContainerView.getChildAt(i).findViewById(R.id.itemcount);
            if (item != null) {
                item.setText("Item"+ct);
                ct++;
            }

        }

        if(mContainerView.getChildCount()==1) {
            p=false;
            spinner1.setEnabled(true);
        }
    }

}

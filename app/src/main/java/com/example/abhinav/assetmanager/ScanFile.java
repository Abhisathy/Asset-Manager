package com.example.abhinav.assetmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abhinav.assetmanager.MainActivity1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class ScanFile extends MainActivity1{
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    private TextView tv,scanResult,textView;
    private TextView tv1;
    Button skip, next, cancel;
    String contents, format,str,str1;
    String itemList[]=new String[100];
    String itemCateg[]=new String[100];
    private LinearLayout containerview;
    int k=0,i=0;
    View vc=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanfile);
        containerview=(LinearLayout)findViewById(R.id.backView);
        skip = (Button) findViewById(R.id.skipbutt);
        next = (Button) findViewById(R.id.nextbutt);
        next.setEnabled(false);
        next.setVisibility(View.INVISIBLE);
        cancel = (Button) findViewById(R.id.cancelbutt);
        for(int i=0;i<100;i++){
            itemCateg[i]="";
            itemList[i]="";
        }
        Bundle gt=getIntent().getExtras();
        str=gt.getString("abc");
        Bundle gt1=getIntent().getExtras();
        str1=gt1.getString("scanType");
        try {
            File myFile = new File(Environment.getExternalStorageDirectory().getPath()+"/AssetManager/"+str);
            FileInputStream fIn = new FileInputStream(myFile);
            BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
            String aDataRow = "";
            String nBuffer = "";
            aDataRow=myReader.readLine();
            k=0;
            for (int i = 0; i < aDataRow.length(); i++) {
                if (aDataRow.charAt(i) == '#') {
                    itemCateg[k] = "P";
                } else if (aDataRow.charAt(i) == '*') {
                    if(itemCateg[k].equals("P"))
                        itemCateg[k] = "PR";
                    else
                        itemCateg[k] = "R";
                } else if (aDataRow.charAt(i) == ';') {
                    if(str1.equals("phoneCam"))
                        inflateEditRow(nBuffer,k);
                    else
                        inflateEditRow1(nBuffer, k);
                    itemList[k] = nBuffer;
                    k++;
                    break;
                } else if (aDataRow.charAt(i) == ',') {
                    if(str1.equals("phoneCam"))
                        inflateEditRow(nBuffer,k);
                    else
                        inflateEditRow1(nBuffer,k);
                    itemList[k] = nBuffer;
                    k++;
                    nBuffer = "";
                } else {
                    nBuffer = nBuffer + aDataRow.charAt(i);
                }
            }

            myReader.close();
            Toast.makeText(getBaseContext(),
                    "Ready to scan",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
        if(itemCateg[0].equals("R") || itemCateg[0].equals("PR")){
            skip.setEnabled(false);
            skip.setVisibility(View.INVISIBLE);
        }
       /* tv=(TextView) findViewById(R.id.textView);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tv=(TextView) findViewById(R.id.textView);
                scanBar(v);
                Toast.makeText(getBaseContext(),
                        "code selected",
                        Toast.LENGTH_SHORT).show();
               Intent intent1 = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent1);
                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                intent.setPackage("com.google.zxing.client.android");
                intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                startActivityForResult(intent, 0);
            }
        });*/
        vc=containerview.getChildAt(i);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // TODO: Handle screen rotation:
        // encapsulate information in a parcelable object, and save it
        // into the state bundle.

    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // TODO: Handle screen rotation:
        // restore the saved items and inflate each one with inflateEditRow;

    }

    public void cancel(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to close the file?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(ScanFile.this, MainActivity1.class);
                        finish();
                        startActivity(intent);
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

    public void skip(View view){
        Toast.makeText(getBaseContext(),
                "Item Skipped",
                Toast.LENGTH_SHORT).show();
        //ImageButton tempr=null;
        //RadioGroup temp=null;

        if (i < k - 1) {
            i++;
            if (itemCateg[i].equals("R") || itemCateg[i].equals("PR")) {
                skip.setEnabled(false);
                skip.setVisibility(View.INVISIBLE);
            } else {
                skip.setEnabled(true);
                skip.setVisibility(View.VISIBLE);
            }
            EditText ed=(EditText) vc.findViewById(R.id.scan_result);
            if(ed!=null) {
                ed.setSelected(false);
                ed.setPressed(false);
                ed.setActivated(false);
            }
            EditText ed1=(EditText) containerview.getChildAt(i+1).findViewById(R.id.scan_result);
            if(ed1!=null) {
                ed1.setSelected(true);
                ed1.setPressed(true);
                ed1.setEnabled(true);
            }
            //View v=containerview.getChildAt(i);
            //View v1=containerview.getChildAt(i-1);
            //if(v1!=null) {
            //tempr = (ImageButton) v1.findViewById(R.id.scanbutt);
            //temp = (RadioGroup) v1.findViewById(R.id.type);
                    /*if(temp!=null) {
                        temp.setEnabled(false);
                        temp.setVisibility(View.INVISIBLE);
                    }*/
                    /*if(tempr!=null) {
                        tempr.setEnabled(false);
                        tempr.setVisibility(View.INVISIBLE);
                    }
                }
                if(v!=null) {
                    tempr = (ImageButton) v.findViewById(R.id.scanbutt);
                    //temp = (RadioGroup) v.findViewById(R.id.type);
                    //temp.setEnabled(true);
                    //temp.setVisibility(View.VISIBLE);
                    tempr.setEnabled(true);
                    tempr.setVisibility(View.VISIBLE);
                }*/

        } else {
            skip.setEnabled(false);
            skip.setVisibility(View.INVISIBLE);
            next.setEnabled(true);
            next.setVisibility(View.VISIBLE);
        }

    }

    public void next(View view){
        Bundle gt=getIntent().getExtras();
        str=gt.getString("abc");
        View v=(View) view.getParent();
        int np=0;
        for(int mn=0;mn<containerview.getChildCount();mn++){
            if(containerview.getChildAt(mn)==v)
                np=mn;
        }
        EditText scanRes1=(EditText)v.findViewById(R.id.scan_result);
        if(scanRes1!=null &&(scanRes1.getText().toString().equals("")||scanRes1.getText().toString().equals(" "))&&
                (itemCateg[np].equals("PR")||itemCateg[np].equals("R"))) {
            Toast.makeText(getBaseContext(),
                    "Required field cannot be empty",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            try {
                File myFile1 = new File(Environment.getExternalStorageDirectory().getPath() + "/AssetManager/" + str);
                FileInputStream fIn = new FileInputStream(myFile1);
                BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
                String aDataRow = "";
                String aBuffer = "";
                while ((aDataRow = myReader.readLine()) != null) {
                    aBuffer += aDataRow + "\n";
                }
                myReader.close();
                fIn.close();
                myFile1 = new File(Environment.getExternalStorageDirectory().getPath() + "/AssetManager/");
                myFile1.mkdir();
                File myFile = new File(myFile1, str);
                myFile.createNewFile();
                FileOutputStream fOut = new FileOutputStream(myFile);
                OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);

                myOutWriter.append(aBuffer);
                myOutWriter.append("\n");

                for (int l = 0; l < containerview.getChildCount(); l++) {
                    View temp1 = containerview.getChildAt(l);
                    EditText scanRes = (EditText) temp1.findViewById(R.id.scan_result);

                    if ((scanRes != null) && !scanRes.getText().toString().equals("")) {
                        String tempstr = scanRes.getText().toString();
                        tempstr = tempstr.replace(',', ':');
                        myOutWriter.append(tempstr);
                        myOutWriter.append(" , ");
                    } else if (scanRes != null) {
                        // scanResult.setText("  ");
                        //myOutWriter.append(scanResult.getText());
                        myOutWriter.append("     ,");
                    }
                }
                myOutWriter.append("\n");
                myOutWriter.close();
                fOut.close();
                Toast.makeText(getBaseContext(),
                        "Saved to file",
                        Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(getBaseContext(), e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
            Bundle basket = new Bundle();
            basket.putString("abc", str);
            basket.putString("scanType", str1);
            Intent intent = new Intent(ScanFile.this, ScanFile.class);
            this.finish();
            intent.putExtras(basket);
            startActivity(intent);
        }
    }

    public void scanBar(View v) {
        vc=(View) v.getParent();
        try {
            //start the scanning activity from the com.google.zxing.client.android.SCAN intent
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "PRODUCT_MODE,QR_CODE_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            //on catch, show the download dialog
            showDialog(ScanFile.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }

    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                //get the extras that are returned from the intent
                contents = intent.getStringExtra("SCAN_RESULT");
                format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                //View v = containerview.getChildAt(i);
                View v1=null;


                for(int mn=0;mn<containerview.getChildCount();mn++){
                    if(containerview.getChildAt(mn)==vc)
                        i=mn;
                    else {
                        EditText ed1 = (EditText) containerview.getChildAt(mn).findViewById(R.id.scan_result);
                        if (ed1 != null) {
                            ed1.setSelected(true);
                            ed1.setPressed(true);
                            ed1.setEnabled(true);
                        }
                    }
                }
                scanResult = (TextView) vc
                        .findViewById(R.id.scan_result);
                scanResult.setText(contents);
                if(i<k) {
                    if (itemCateg[i + 1].equals("R") || itemCateg[i + 1].equals("PR")) {
                        skip.setEnabled(false);
                        skip.setVisibility(View.INVISIBLE);
                    } else {
                        skip.setEnabled(true);
                        skip.setVisibility(View.VISIBLE);
                    }
                    i++;
                }
               /* View v1 = containerview.getChildAt(i);
                img = (ImageButton) v1
                        .findViewById(R.id.scanbutt);
                img.setEnabled(true);*/
                if(i==k){
                    skip.setEnabled(false);
                    skip.setVisibility(View.INVISIBLE);
                    next.setEnabled(true);
                    next.setVisibility(View.VISIBLE);
                }
                //final Button button1 = (Button) findViewById(R.id.scanner);
                //final Button button2 = (Button) findViewById(R.id.scanner2);
                //TextView textview3 = (TextView) findViewById(R.id.butt2);
              /*  if (textview != null)
                    textview = null;
                textview = (TextView) findViewById(R.id.textView3);
                textview.setText("Content:" + contents + " Format:" + format);
                //Save.setEnabled(true);
*/
            }
        }

    }

    public void onBackPressed(){
            Bundle basket = new Bundle();
            basket.putString("abc", str);
            basket.putString("scanType", str1);
            Intent intent = new Intent(ScanFile.this, ScanFile.class);
            this.finish();
            intent.putExtras(basket);
            startActivity(intent);
    }
    private void inflateEditRow(String name,int k) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.scancont, null);
        //RadioGroup temp=(RadioGroup) rowView.findViewById(R.id.type);
        //ImageButton tempr=(ImageButton) rowView.findViewById(R.id.scanbutt);
        textView = (TextView) rowView
                .findViewById(R.id.textView10);
        scanResult = (TextView) rowView
                .findViewById(R.id.scan_result);
        boolean y=false;
        if(name!=null) {
            if(itemCateg[k].equals("PR")||itemCateg[k].equals("R")) {
                textView.setTextColor(getResources().getColor(R.color.colorRed));
                textView.setText("*"+name);
            }
            else{
                textView.setText(name);
            }
        }
        for(int mn=0;mn<itemCateg.length;mn++){
            if(itemCateg[mn].equals("PR")) {
                y = true;
                break;
            }
        }
        //if(tempr!=null) {
        if (y) {

            if (itemCateg[k].equals("PR")) {
                skip.setEnabled(false);
                skip.setVisibility(View.INVISIBLE);
            }
            else if(itemCateg[k].equals("R")){
                skip.setEnabled(false);
                skip.setVisibility(View.INVISIBLE);
                scanResult.setEnabled(false);
            }
            else {
                scanResult.setEnabled(false);
                skip.setEnabled(true);
                skip.setVisibility(View.VISIBLE);
            }
        }
        else if(k==0){
            if (itemCateg[k].equals("R") || itemCateg[k].equals("PR")) {
                skip.setEnabled(false);
                skip.setVisibility(View.INVISIBLE);
            } else {
                skip.setEnabled(true);
                skip.setVisibility(View.VISIBLE);
            }
        }
        else {
            scanResult.setEnabled(false);
        }
        //}
        containerview.addView(rowView, containerview.getChildCount() - 1);
    }

    private void inflateEditRow1(String name,int nk) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.scancont, null);
        textView = (TextView) rowView
                .findViewById(R.id.textView10);
        final EditText scanRes = (EditText) rowView
                .findViewById(R.id.scan_result);

        if(name!=null) {
            if(itemCateg[k].equals("PR")||itemCateg[k].equals("R")) {
                textView.setTextColor(getResources().getColor(R.color.colorRed));
                textView.setText("*"+name);
            }
            else{
                textView.setText(name);
            }
        }
        scanRes.setText("");
        if (k == 0) {
            if (itemCateg[k].equals("R") || itemCateg[k].equals("PR")) {
                skip.setEnabled(false);
                skip.setVisibility(View.INVISIBLE);
            } else {
                skip.setEnabled(true);
                skip.setVisibility(View.VISIBLE);
            }
            scanRes.setEnabled(true);
        }
        else {
            scanRes.setEnabled(false);
        }
        scanRes.setOnClickListener(null);
        scanRes.addTextChangedListener(new TextWatcher() {
            int g=containerview.getChildCount();


            @Override
            public void afterTextChanged(Editable s) {
                View a=null;
                EditText ab1=null;
                for(int f=0;f<g;f++){
                    if(containerview.getChildAt(f)==rowView) {
                        i=f;
                        while (a==null){
                            f++;
                            a=containerview.getChildAt(f);
                        }
                        break;
                    }
                }
                if(a!=null) {
                    ab1 = (EditText) a.findViewById(R.id.scan_result);
                }
                if(s.toString().endsWith("\n")){
                    final EditText ab = (EditText) rowView
                            .findViewById(R.id.scan_result);
                    if(ab!=null){
                        ab.setText(s.toString().replace('\n', ' '));
                        ab.setActivated(false);
                        ab.setPressed(false);
                        ab.setSelected(false);
                    }
                    if(i<k-1) {
                        if (itemCateg[i + 1].equals("R") || itemCateg[i + 1].equals("PR")) {
                            skip.setEnabled(false);
                            skip.setVisibility(View.INVISIBLE);
                        } else {
                            skip.setEnabled(true);
                            skip.setVisibility(View.VISIBLE);
                        }
                        i++;
                    }
                    if (i == k-1) {
                        skip.setEnabled(false);
                        skip.setVisibility(View.INVISIBLE);
                        next.setEnabled(true);
                        next.setVisibility(View.VISIBLE);
                    }
                    if (ab1 != null) {
                        ab1.setEnabled(true);
                        ab1.setVisibility(View.VISIBLE);
                        ab1.setActivated(true);
                    }
                    //scanRes.setEnabled(false);
                    //scanRes.setCursorVisible(false);
                    for(int f=0;f<g;f++){
                        if(containerview.getChildAt(f)==a) {
                            while (a==null){
                                f++;
                                a=containerview.getChildAt(f);
                                ab1 = (EditText) a.findViewById(R.id.scan_result);
                                if (ab1 != null) {
                                    ab1.setEnabled(true);
                                    ab1.setVisibility(View.VISIBLE);
                                    ab1.setActivated(true);
                                }
                            }

                        }
                    }



                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                              int count) {

            }
    });
        containerview.addView(rowView, containerview.getChildCount() - 1);
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
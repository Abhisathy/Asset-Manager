package com.example.abhinav.assetmanager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Abhinav on 23-06-2016.
 */
public class openFile1 extends MainActivity1 {
    int i;
    Context context=this;
    SearchView search;
    boolean p=true;
    boolean p1=true;
    ArrayList<String> filedel =new ArrayList<String>();
    private ShareActionProvider mShareActionProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main23);
        search = (SearchView) findViewById(R.id.searchView);
        search.setQueryHint("search filename");
        search.setEnabled(false);
        search.setVisibility(View.GONE);

        final ArrayList<String> FilesInFolder = GetFiles(Environment.getExternalStorageDirectory().getPath()+"/AssetManager/");
        final ListView lv = (ListView) findViewById(R.id.listView);
        Button bb=(Button)findViewById(R.id.deletemultiple);
        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(p1) {
                    lv.setAdapter(new ArrayAdapter<String>(openFile1.this,
                            android.R.layout.select_dialog_multichoice, FilesInFolder));
                    lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    lv.setOnItemClickListener(new CheckBoxClick());
                    p1=false;
                }
                else {
                    if(!filedel.isEmpty()){
                        for(int i=0;i<filedel.size();i++) {
                            File file = new File(Environment.getExternalStorageDirectory().getPath() + "/AssetManager/" + filedel.get(i));
                            boolean deleted = file.delete();
                        }
                        Intent a = new Intent(openFile1.this, openFile1.class);
                        p1=true;
                        finish();
                        startActivity(a);
                    }
                    else {
                        p1=true;
                        if(FilesInFolder!=null) {

                            lv.setAdapter(new ArrayAdapter<String>(openFile1.this,
                                    android.R.layout.simple_list_item_1, FilesInFolder));

                            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                                    // Clicking on items
                                    final String filename = (String) parent.getItemAtPosition(position);
                                    //  openFile(filename);
                                    Bundle basket = new Bundle();
                                    basket.putString("abc", filename);
                                    Intent a = new Intent(openFile1.this, Main1Activity.class);
                                    finish();
                                    a.putExtras(basket);
                                    startActivity(a);
                                }
                            });

                            lv.setLongClickable(true);
                            lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                @Override
                                public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {

                                    final String filename = (String) parent.getItemAtPosition(position);
                                    LayoutInflater li = LayoutInflater.from(context);
                                    View promptsView = li.inflate(R.layout.open_prompt, null);

                                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                            context);

                                    // set prompts.xml to alertdialog builder
                                    alertDialogBuilder.setView(promptsView);
                                    final AlertDialog alertDialog = alertDialogBuilder.create();

                                    // show it

                                    alertDialog.show();
                                    Button b1 = (Button) promptsView.findViewById(R.id.openCSV);
                                    Button b2 = (Button) promptsView.findViewById(R.id.delete);
                                    // Clicking on items
                                    b1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            //  openFile(filename);
                                            Bundle basket = new Bundle();
                                            basket.putString("abc", filename);
                                            Intent a = new Intent(openFile1.this, Main1Activity.class);
                                            finish();
                                            a.putExtras(basket);
                                            startActivity(a);
                                        }
                                    });
                                    b2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            //  openFile(filename);
                                            File file = new File(Environment.getExternalStorageDirectory().getPath() + "/AssetManager/" + filename);
                                            boolean deleted = file.delete();
                                            alertDialog.cancel();
                                            Intent a = new Intent(openFile1.this, openFile1.class);
                                            finish();
                                            startActivity(a);
                                        }
                                    });

                                    return true;
                                }
                            });
                        }
                        else {
                            setContentView(R.layout.empty_view);
                        }
                    }
                }
            }
        });

        if(FilesInFolder!=null) {

            lv.setAdapter(new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, FilesInFolder));

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    // Clicking on items
                    final String filename = (String) parent.getItemAtPosition(position);
                    //  openFile(filename);
                    Bundle basket = new Bundle();
                    basket.putString("abc", filename);
                    Intent a = new Intent(openFile1.this, Main1Activity.class);
                    finish();
                    a.putExtras(basket);
                    startActivity(a);
                }
            });

            lv.setLongClickable(true);
            lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {

                    final String filename = (String) parent.getItemAtPosition(position);
                    LayoutInflater li = LayoutInflater.from(context);
                    View promptsView = li.inflate(R.layout.open_prompt, null);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            context);

                    // set prompts.xml to alertdialog builder
                    alertDialogBuilder.setView(promptsView);
                    final AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it

                    alertDialog.show();
                    Button b1 = (Button) promptsView.findViewById(R.id.openCSV);
                    Button b2 = (Button) promptsView.findViewById(R.id.delete);
                    // Clicking on items
                    b1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            //  openFile(filename);
                            Bundle basket = new Bundle();
                            basket.putString("abc", filename);
                            Intent a = new Intent(openFile1.this, Main1Activity.class);
                            finish();
                            a.putExtras(basket);
                            startActivity(a);
                        }
                    });
                    b2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            //  openFile(filename);
                            File file = new File(Environment.getExternalStorageDirectory().getPath() + "/AssetManager/" + filename);
                            boolean deleted = file.delete();
                            alertDialog.cancel();
                            Intent a = new Intent(openFile1.this, openFile1.class);
                            finish();
                            startActivity(a);
                        }
                    });

                    return true;
                }
            });
        }
        else {
            setContentView(R.layout.empty_view);
        }
        Button b=(Button)findViewById(R.id.searchb);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(p) {
                    search.setEnabled(true);
                    search.setVisibility(View.VISIBLE);
                    search.setActivated(true);
                    search.setClickable(true);
                    search.setPressed(true);
                    //*** setOnQueryTextFocusChangeListener ***
                    search.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            // TODO Auto-generated method stub

                            //Toast.makeText(getBaseContext(), String.valueOf(hasFocus),
                            //Toast.LENGTH_SHORT).show();
                        }
                    });

                    //*** setOnQueryTextListener ***
                    search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        ListView lv1;

                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            // TODO Auto-generated method stub

                            final ArrayList<String> FilesInFolder = GetFiles(Environment.getExternalStorageDirectory().getPath() + "/AssetManager/", query);
                            lv1 = (ListView) findViewById(R.id.listView);
                            if (FilesInFolder != null) {

                                lv1.setAdapter(new ArrayAdapter<String>(openFile1.this,
                                        android.R.layout.simple_list_item_1, FilesInFolder));

                                lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                                        // Clicking on items
                                        final String filename = (String) parent.getItemAtPosition(position);
                                        //  openFile(filename);

                                        String scant = null;
                                        try {
                        /*File myFile2 = new File(Environment.getExternalStorageDirectory().getPath() + "/AssetManager/ScannerType.txt");
                        FileInputStream fIn1 = new FileInputStream(myFile2);
                        BufferedReader myReader1 = new BufferedReader(new InputStreamReader(fIn1));
                        scant=myReader1.readLine();
                        myReader1.close();
                        fIn1.close();*/
                                            String FILENAME = "ScannerType";
                                            FileInputStream fis = openFileInput(FILENAME);
                                            InputStreamReader isr = new InputStreamReader(fis);
                                            BufferedReader bufferedReader = new BufferedReader(isr);
                                            StringBuilder sb = new StringBuilder();
                                            String line;
                                            String x = "";
                                            while ((line = bufferedReader.readLine()) != null) {
                                                sb.append(line);
                                                x = line;
                                            }
                                            scant = x;
                                            bufferedReader.close();
                                            isr.close();
                                            fis.close();
                                        } catch (Exception e) {
                                            Toast.makeText(getBaseContext(), e.getMessage(),
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                        Bundle basket = new Bundle();
                                        basket.putString("abc", filename);
                                        basket.putString("scanType", scant);
                                        basket.putBoolean("classopen", true);
                                        Intent a = new Intent(openFile1.this, Main1Activity.class);
                                        finish();
                                        a.putExtras(basket);
                                        startActivity(a);

                                    }
                                });

                                lv1.setLongClickable(true);
                                lv1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                    @Override
                                    public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                                                   int pos, long id) {

                                        final String filename = (String) arg0.getItemAtPosition(pos);
                                        LayoutInflater li = LayoutInflater.from(context);
                                        View promptsView = li.inflate(R.layout.open_prompt, null);

                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                                context);

                                        // set prompts.xml to alertdialog builder
                                        alertDialogBuilder.setView(promptsView);
                                        final AlertDialog alertDialog = alertDialogBuilder.create();

                                        // show it

                                        alertDialog.show();
                                        Button b1 = (Button) promptsView.findViewById(R.id.openCSV);
                                        Button b2 = (Button) promptsView.findViewById(R.id.delete);
                                        // Clicking on items
                                        if (b1 != null) {
                                            b1.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {

                                                    //  openFile(filename);
                                                    Bundle basket = new Bundle();
                                                    basket.putString("abc", filename);
                                                    Intent a = new Intent(openFile1.this, Main1Activity.class);
                                                    finish();
                                                    a.putExtras(basket);
                                                    startActivity(a);
                                                }
                                            });
                                        }
                                        if (b2 != null) {
                                            b2.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {

                                                    //  openFile(filename);
                                                    File file = new File(Environment.getExternalStorageDirectory().getPath() + "/AssetManager/" + filename);
                                                    boolean deleted = file.delete();
                                                    alertDialog.cancel();
                                                    Intent a = new Intent(openFile1.this, openfile.class);
                                                    finish();
                                                    startActivity(a);
                                                }
                                            });
                                        }

                                        return true;
                                    }
                                });
            /*lv.setOnItemLongClickListener(new OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                               int pos, long id) {
                    // TODO Auto-generated method stub
                    //   final String filename = (String) arg0.getItemAtPosition(pos);
                    //File myFile = new File("/sdcard/AssetManager/"+filename);
                    Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/AssetManager/" + arg0.getItemAtPosition(pos));
                    // File file = new File("/sdcard/AssetManager/");
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/*");
                    sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
                    // String shareBody = "Here is the share content body";
                    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
                    //sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                    startActivity(Intent.createChooser(sharingIntent, "Share via"));
                    return true;
                }
            });*/

                            } else {
                                setContentView(R.layout.empty_view);
                            }
                            search.setEnabled(false);
                            search.setVisibility(View.GONE);
                            return true;
                        }

                        @Override
                        public boolean onQueryTextChange(String newText) {
                            // TODO Auto-generated method stub

                            //	Toast.makeText(getBaseContext(), newText,
                            final ArrayList<String> FilesInFolder = GetFiles(Environment.getExternalStorageDirectory().getPath() + "/AssetManager/", newText);
                            lv1 = (ListView) findViewById(R.id.listView);
                            if (FilesInFolder != null) {

                                lv1.setAdapter(new ArrayAdapter<String>(openFile1.this,
                                        android.R.layout.simple_list_item_1, FilesInFolder));

                                lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                                        // Clicking on items
                                        final String filename = (String) parent.getItemAtPosition(position);
                                        //  openFile(filename);

                                        String scant = null;
                                        try {
                        /*File myFile2 = new File(Environment.getExternalStorageDirectory().getPath() + "/AssetManager/ScannerType.txt");
                        FileInputStream fIn1 = new FileInputStream(myFile2);
                        BufferedReader myReader1 = new BufferedReader(new InputStreamReader(fIn1));
                        scant=myReader1.readLine();
                        myReader1.close();
                        fIn1.close();*/
                                            String FILENAME = "ScannerType";
                                            FileInputStream fis = openFileInput(FILENAME);
                                            InputStreamReader isr = new InputStreamReader(fis);
                                            BufferedReader bufferedReader = new BufferedReader(isr);
                                            StringBuilder sb = new StringBuilder();
                                            String line;
                                            String x = "";
                                            while ((line = bufferedReader.readLine()) != null) {
                                                sb.append(line);
                                                x = line;
                                            }
                                            scant = x;
                                            bufferedReader.close();
                                            isr.close();
                                            fis.close();
                                        } catch (Exception e) {
                                            Toast.makeText(getBaseContext(), e.getMessage(),
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                        Bundle basket = new Bundle();
                                        basket.putString("abc", filename);
                                        basket.putString("scanType", scant);
                                        basket.putBoolean("classopen", true);
                                        Intent a = new Intent(openFile1.this, Main1Activity.class);
                                        finish();
                                        a.putExtras(basket);
                                        startActivity(a);

                                    }
                                });

                                lv1.setLongClickable(true);
                                lv1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                    @Override
                                    public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                                                   int pos, long id) {

                                        final String filename = (String) arg0.getItemAtPosition(pos);
                                        LayoutInflater li = LayoutInflater.from(context);
                                        View promptsView = li.inflate(R.layout.open_prompt, null);

                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                                context);

                                        // set prompts.xml to alertdialog builder
                                        alertDialogBuilder.setView(promptsView);
                                        final AlertDialog alertDialog = alertDialogBuilder.create();

                                        // show it

                                        alertDialog.show();
                                        Button b1 = (Button) promptsView.findViewById(R.id.openCSV);
                                        Button b2 = (Button) promptsView.findViewById(R.id.delete);
                                        // Clicking on items
                                        if (b1 != null) {
                                            b1.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {

                                                    //  openFile(filename);
                                                    Bundle basket = new Bundle();
                                                    basket.putString("abc", filename);
                                                    Intent a = new Intent(openFile1.this, Main1Activity.class);
                                                    finish();
                                                    a.putExtras(basket);
                                                    startActivity(a);
                                                }
                                            });
                                        }
                                        if (b2 != null) {
                                            b2.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {

                                                    //  openFile(filename);
                                                    File file = new File(Environment.getExternalStorageDirectory().getPath() + "/AssetManager/" + filename);
                                                    boolean deleted = file.delete();
                                                    alertDialog.cancel();
                                                    Intent a = new Intent(openFile1.this, openfile.class);
                                                    finish();
                                                    startActivity(a);
                                                }
                                            });
                                        }

                                        return true;
                                    }
                                });
            /*lv.setOnItemLongClickListener(new OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                               int pos, long id) {
                    // TODO Auto-generated method stub
                    //   final String filename = (String) arg0.getItemAtPosition(pos);
                    //File myFile = new File("/sdcard/AssetManager/"+filename);
                    Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/AssetManager/" + arg0.getItemAtPosition(pos));
                    // File file = new File("/sdcard/AssetManager/");
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/*");
                    sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
                    // String shareBody = "Here is the share content body";
                    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
                    //sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                    startActivity(Intent.createChooser(sharingIntent, "Share via"));
                    return true;
                }
            });*/

                            } else {
                                setContentView(R.layout.empty_view);
                            }
                            return true;
                        }
                    });//textlistener
                    p=false;
                }
                else {
                    search.setEnabled(false);
                    search.setVisibility(View.GONE);
                    p=true;
                }
            }
        });//onClicklistener
        final SearchView searchView=(SearchView)findViewById(R.id.searchView);
        searchView.setClickable(true);
        searchView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                searchView.setActivated(true);
                searchView.setSelected(true);
                searchView.setPressed(true);
            }
        });//on
    }

    public void onCancel(View view){
        Intent a = new Intent(openFile1.this, MainActivity1.class);
        finish();
        startActivity(a);
    }

   /* public void openFile(final String fileName) {
        // To Open the file in the default viewer of the device

        File file = new File("/sdcard/AssetManager/" + fileName);
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(Uri.fromFile(file), "text/plain");
        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        Intent intent = Intent.createChooser(target, "Open txt File");
        try {

            startActivity(intent);
        }
        catch (ActivityNotFoundException e)
        {
        }
    }*/

    public ArrayList<String> GetFiles(String DirectoryPath) {
        ArrayList<String> MyFiles = new ArrayList<String>();
        File f = new File(DirectoryPath);

        f.mkdirs();
        File[] files = f.listFiles();
        if (files.length == 0)
            return null;
        else {
            for (int i = 0; i < files.length; i++)
                if((files[i].getName()).endsWith(".csv"))
                    MyFiles.add(files[i].getName());
        }

        return MyFiles;
    }

    public ArrayList<String> GetFiles(String DirectoryPath,String text) {
        ArrayList<String> MyFiles = new ArrayList<String>();
        File f = new File(DirectoryPath);
        f.mkdirs();
        File[] files = f.listFiles();
        if (files.length == 0)
            return null;
        else {
            for (int i = 0; i < files.length; i++) {
                if ((files[i].getName()).endsWith(".csv")) {
                    if(files[i].getName().contains(text))
                        MyFiles.add(files[i].getName());
                }
            }
        }

        return MyFiles;
    }

    @Override
    public void onBackPressed(){
        Intent a = new Intent(openFile1.this, MainActivity1.class);
        finish();
        startActivity(a);
    }

   /* Button myButton = new Button(this);
    myButton.setText("Push Me");

    LinearLayout ll = (LinearLayout)findViewById(R.id.buttonlayout);
    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    ll.addView(myButton, lp);*/
    /*public void click4(View view) {
        // Do something in response to button
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file/*");
        startActivityForResult(intent, PICKFILE_RESULT_CODE);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == RESULT_OK) {
                    String FilePath = data.getData().getPath();
                    textFile.setText(FilePath);
                }
                break;
        }
    }*/
   public class CheckBoxClick implements AdapterView.OnItemClickListener {

       @Override
       public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
           // TODO Auto-generated method stub
           CheckedTextView ctv = (CheckedTextView) arg1;
           if (ctv.isChecked()) {
               filedel.add(ctv.getText().toString());
               //Toast.makeText(openfile.this, "now it is checked", Toast.LENGTH_SHORT).show();
           } else {
               if(!filedel.isEmpty())
                   filedel.remove(ctv.getText().toString());
               //Toast.makeText(openfile.this, "now it is unchecked", Toast.LENGTH_SHORT).show();
           }
       }
   }
}

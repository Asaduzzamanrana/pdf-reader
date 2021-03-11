package com.example.pdfviewer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    private Button btn_create;
    public static ArrayList<File> fileList = new ArrayList<>();
    Adapter adapter;
    public static int REQUEST_PERMISSION = 1;
    boolean boolean_permission;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fileList.clear();
        btn_create = findViewById(R.id.create_btn);
        listView = findViewById(R.id.listView_id);
        file = new File(Environment.getExternalStorageDirectory().toString());
        permission_phone();

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CreateActivity.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),ViewPdf.class);
                intent.putExtra("position",position);
                startActivity(intent);

            }
        });

    }

    private void permission_phone() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)){
            if ((ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE))){

            }else {
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_PERMISSION);
            }
        }
        else {
            boolean_permission = true;
            getfile(file);

            adapter = new Adapter(getApplicationContext(),fileList);
            listView.setAdapter(adapter);

           }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION)
        {
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    boolean_permission = true;
                    getfile(file);

                    adapter = new Adapter(getApplicationContext(),fileList);
                    listView.setAdapter(adapter);


                }
            else {
                Toast.makeText(this, "Please Allow", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public ArrayList<File> getfile(File file){

        File listFile[] = file.listFiles();
        if (listFile != null && listFile.length>0){
            for (int i=0; i<listFile.length; i++){
                if (listFile[i].isDirectory()){
                    getfile(listFile[i]);
                }
                else {
                    boolean booleanpdf = false;
                    if (listFile[i].getName().endsWith(".pdf")){
                        for (int j=0;j<fileList.size();j++){
                            if (fileList.get(j).getName().equals(listFile[i].getName())){
                                booleanpdf = false;
                            }else {

                            }
                        }
                        if (booleanpdf){
                            booleanpdf = false;
                        }else {
                            fileList.add(listFile[i]);
                        }
                    }
                }
            }
        }
        return fileList;
    }

    //alert dialog
    @Override
    public void onBackPressed(){
        AlertDialog.Builder alertDialogBuilder;
        alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setIcon(R.drawable.ques);
        alertDialogBuilder.setMessage(R.string.message_text);
        alertDialogBuilder.setTitle(R.string.alert_text);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        alertDialogBuilder.setNeutralButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
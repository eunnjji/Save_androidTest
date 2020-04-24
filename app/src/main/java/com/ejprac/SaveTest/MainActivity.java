package com.ejprac.SaveTest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {


    String dirPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button savebtn = (Button)this.findViewById(R.id.button);
        final EditText editName = (EditText)this.findViewById(R.id.editText);
        final EditText editBirth = (EditText)this.findViewById(R.id.editText2);
        setupPermission();
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strName = editName.getText().toString();
                String strBirth = editBirth.getText().toString();

                SimpleDateFormat dateformat = new SimpleDateFormat("yyMMdd_HHmmss");
                String filename = "test_"+dateformat.format(System.currentTimeMillis());

                Log.d("directory", Environment.getExternalStorageDirectory().getAbsolutePath());
                Log.d("directory", getFilesDir().getAbsolutePath());
//                Log.d("tag",Environment.DIRECTORY_DOCUMENTS);
                Log.d("tag","function 1");

                if(CheckWritable()){
                    Log.d("tag","Check Write success");
                    //dirPath = Environment.DIRECTORY_DOWNLOADS+"/testDir";
                    //dirPath = getFilesDir().getAbsolutePath();
                    //dirPath =Environment.getDataDirectory().getAbsolutePath()+"/testdir";
                    //dirPath = "/sdcard/Android/data/testDir";
                    //dirPath = Environment.getDataDirectory().getAbsolutePath()+"/data/testDir";
                    //dirPath = getFilesDir().getAbsolutePath()+"/testDir"; // 되지만 확인 불가
                    //dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/testDir";
                    //dirPath = Environment.DIRECTORY_DOWNLOADS+"/testDir";
                    //dirPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
                    dirPath = Environment.getExternalStorageDirectory().getAbsolutePath();
                    File dir = new File(dirPath);
                    /*if(!dir.exists()){
                        if (!dir.mkdirs()) {
                            Log.d("tag", "make dir success3");
                        } else {
                            Log.e("tag","Directory 생성 실패");
                        }
                    }*/
                    if(Save(filename,"이름:"+strName+"\n생일:"+strBirth)){
                        Log.d("tag","Save success");
                    }else{
                        Log.d("tag","Save 실패!!!!!!!-----");
                    }

                }
                Log.d("tag","function end");
            }
        });
    }

    void ShowToast(String data){
        Toast.makeText(this,data, Toast.LENGTH_SHORT).show();
    }


    private void setupPermission() { //check for permission
        /*if (SDK_INT <= O) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                //ask for permission
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            }
        }*/

        if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }

    }


    private boolean CheckWritable(){  // sdcard mount check
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){
            //Log.d("tag","storage 사용 가능");
            return true;
        }else{
            Log.d("tag","언마운트 됨");
            return false;
        }
    }

    private boolean Save(String filename, String contents){
        File dir = new File(dirPath+"/testDir");
        dir.mkdir();
        File savefile = new File(dirPath+"/testDir/"+filename+".txt");
        try{

            BufferedWriter buf = new BufferedWriter(new FileWriter(savefile.getPath(),true));
            buf.append(contents);
            buf.newLine();
            buf.close();

            /*FileOutputStream fos = new FileOutputStream(savefile);
            fos.write(contents.getBytes());
            fos.close();*/
            Log.d("tag","save file success!");
            return true;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }


}

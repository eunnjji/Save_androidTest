package com.ejprac.SaveTest;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.json.JSONException;
import org.json.JSONObject;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MainActivity extends AppCompatActivity {

    EditText editName;
    EditText editBirth;
    TextView tv_readjson;
    Button savebtn, readbtn;
    String dirPath;
    String strName;
    String strBirth;

    String readPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Color color;
        savebtn = (Button)this.findViewById(R.id.button);
        readbtn = (Button)this.findViewById(R.id.ReadBtn);
        editName = (EditText)this.findViewById(R.id.editText);
        editBirth = (EditText)this.findViewById(R.id.editText2);
        tv_readjson = (TextView)this.findViewById(R.id.tv_json);
        setupPermission();
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strName = editName.getText().toString();
                strBirth = editBirth.getText().toString();

                @SuppressLint("SimpleDateFormat") SimpleDateFormat dateformat = new SimpleDateFormat("yyMMdd_HHmmss");
                String filename = "test_"+dateformat.format(System.currentTimeMillis());

                Log.d("directory", Environment.getExternalStorageDirectory().getAbsolutePath()); // 결과: /storage/emulated/0
//                Log.d("directory", getFilesDir().getAbsolutePath()); //결과: /data/user/0/com.ejprac.SaveTest/files
//                Log.d("tag",Environment.DIRECTORY_DOCUMENTS);
                Log.d("tag","function 1");

                if(CheckWritable()){
                    Log.d("tag","Check Write success");
                    dirPath = Environment.getExternalStorageDirectory().getAbsolutePath();
                    //File dir = new File(dirPath);
                    //txtSave(filename);
                    //xlsSave(filename);
                    //xlsxSave(filename);
                    jsonSave(filename);

                }
                Log.d("tag","function end");
            }
        });

        readbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("tag","read function start");
                jsonRead();
                Log.d("tag","read function end");
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

    private void txtSave(String filename){
        File dir = new File(dirPath+"/testDir");
        dir.mkdir();
        File savefile = new File(dirPath+"/testDir/"+filename+".txt");
        try{

            /*usage 1*/
            BufferedWriter buf = new BufferedWriter(new FileWriter(savefile.getPath(),true));
            buf.append(strName+" ");
            buf.append(strBirth);
            buf.newLine();
            buf.close();

            /*usage 2*/
            /*FileOutputStream fos = new FileOutputStream(savefile);
            fos.write(contents.getBytes());
            fos.close();*/

            Log.d("tag","save file success!");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void xlsSave(String filename){
        File dir = new File(dirPath+"/testDir");
        dir.mkdir();
        File savefile = new File(dirPath+"/testDir/"+filename+".xls");

        try{
            FileOutputStream fos = new FileOutputStream(savefile);
            HSSFWorkbook workbook = new HSSFWorkbook();
            //Sheet sheet = workbook.createSheet("main_sheet");
            HSSFSheet sheet = workbook.createSheet("main_sheet");

            Row headerRow = sheet.createRow(0);
            Cell cell1 = headerRow.createCell(0);
            cell1.setCellValue("이름");
            Cell cell2 = headerRow.createCell(1);
            cell2.setCellValue("생일");

            Row valRow = sheet.createRow(1);
            Cell cell3 = valRow.createCell(0);
            cell3.setCellValue(strName);
            Cell cell4 = valRow.createCell(1);
            cell4.setCellValue(strBirth);
            workbook.write(fos);
            fos.close();
            workbook.close();
            Log.d("tag","save file success!");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void xlsxSave(String filename){
        File dir = new File(dirPath+"/testDir");
        dir.mkdir();
        File savefile = new File(dirPath+"/testDir/"+filename+".xlsx");

        try{
            FileOutputStream fos = new FileOutputStream(savefile);

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("main_sheet");

            XSSFRow headerRow = sheet.createRow(0);
            XSSFCell cell1 = headerRow.createCell(0);
            cell1.setCellValue("이름");
            XSSFCell cell2 = headerRow.createCell(1);
            cell2.setCellValue("생일");

            XSSFRow valRow = sheet.createRow(1);
            XSSFCell cell3 = valRow.createCell(0);
            cell3.setCellValue(strName);
            XSSFCell cell4 = valRow.createCell(1);
            cell4.setCellValue(strBirth);

            fos.close();
            workbook.close();
            Log.d("tag","save file success!");
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    private  void jsonSave(String filename){

        File dir = new File(dirPath+"/testDir");
        dir.mkdir();

        JSONObject obj = new JSONObject();
        try {
            obj.put("name",strName);
            obj.put("birth",strBirth);
            FileWriter fw = new FileWriter(dirPath+"/testDir/"+filename+".json");
            fw.write(obj.toString());
            fw.flush();
            fw.close();
            readPath = dirPath+"/testDir/"+filename+".json";
            Log.d("tag","save file success!");
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            Log.d("tag","save file failed---------");
        }

    }

    private  void jsonRead(){
        JsonParser parser = new JsonParser();

        try{
            JsonObject obj = (JsonObject)parser.parse(new FileReader(readPath));
            tv_readjson.setText(obj.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}

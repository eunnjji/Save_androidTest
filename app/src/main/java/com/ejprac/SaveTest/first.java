package com.ejprac.SaveTest;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class first extends Fragment {

    MainActivity mainActivity;

    EditText editName;
    EditText editBirth;
    TextView tv_readjson;
    Button savebtn, readbtn, connectbtn;
    String dirPath;
    String strName;
    String strBirth;
    String readPath;

    public first(MainActivity ma) {
        this.mainActivity = ma;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootview = (ViewGroup)inflater.inflate(R.layout.first_frag, container, false);
        savebtn = (Button)rootview.findViewById(R.id.savebtn);
        readbtn = (Button)rootview.findViewById(R.id.ReadBtn);
        editName = (EditText)rootview.findViewById(R.id.editText);
        editBirth = (EditText)rootview.findViewById(R.id.editText2);
        tv_readjson = (TextView)rootview.findViewById(R.id.tv_json);
        connectbtn = (Button)rootview.findViewById(R.id.connectBtn);

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


        connectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectServer();
            }
        });


        return rootview;
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

    public void ConnectServer(){
        Log.d("tag","서버 연결해서 값 가져오기");


        mainActivity.mMyControl.buildNetworkService(mainActivity.baseurl);
        mainActivity.mNetworkService = mainActivity.mMyControl.getNetworkService();
        Call<List<TestItem>> getCall = mainActivity.mNetworkService.get_test();
        /*getCall.enqueue(new Callback<TestItem>() {
            @Override
            public void onResponse(Call<TestItem> call, Response<TestItem> response) {
                if(response.isSuccessful()){
                    //mainActivity.testItemList = response.body();
                    mainActivity.ShowToast(response.body().getName()+" "+response.body().getBirth());
                    Log.d("tag","가져오기 성공!");
                }else{
                    Log.d("tag","가져오기 실패!, 상태 코드:"+response.code());
                }
            }

            @Override
            public void onFailure(Call<TestItem> call, Throwable t) {
                Log.d("tag","실패!, 상태 코드: "+t.getMessage());
            }
        });*/
        getCall.enqueue(new Callback<List<TestItem>>() {
            @Override
            public void onResponse(Call<List<TestItem>> call, Response<List<TestItem>> response) {
                if(response.isSuccessful()){
                    mainActivity.testItemList = response.body();
                    mainActivity.ShowToast(Integer.toString(response.body().size()));
                    Log.d("tag","가져오기 성공!");
                }else{
                    Log.d("tag","가져오기 실패!, 상태 코드:"+response.code());
                }
            }

            @Override
            public void onFailure(Call<List<TestItem>> call, Throwable t) {
                Log.d("tag","실패!, 상태 코드: "+t.getMessage());
            }
        });

        Log.d("tag","함수 끝!");
    }



}

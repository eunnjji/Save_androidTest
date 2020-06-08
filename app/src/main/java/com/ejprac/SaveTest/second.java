package com.ejprac.SaveTest;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class second extends Fragment {
    MainActivity mainActivity;
    public second(MainActivity ma) {
        this.mainActivity = ma;
    }
    TextView getServerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootview = (ViewGroup)inflater.inflate(R.layout.second_frag, container, false);

        getServerView = (TextView)rootview.findViewById(R.id.get_jsontv);

        StringBuilder result = new StringBuilder();
        if(mainActivity.testItemList.size() != 0){
            for(TestItem i : mainActivity.testItemList){
                result.append("name: ").append(i.getName()).append("\n birth: ")
                        .append(i.getBirth()).append("\n");
            }
            getServerView.setText(result);
        }else{
            mainActivity.ShowToast("서버 DB에 값이 없음");
        }


        return rootview;
    }
}

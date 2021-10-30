package com.csgo.test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class city_add extends AppCompatActivity implements View.OnClickListener{
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_add);
        TextView chengdu = findViewById(R.id.chengdu);
        TextView  xian = findViewById(R.id.xian);
        TextView  nanjing = findViewById(R.id.nanjing);
        TextView shijiazhuang = findViewById(R.id.shijiazhuang);
        TextView beijing = findViewById(R.id.beijing);
        ImageView image1 = findViewById(R.id.search_iv_submit);
        image1.setOnClickListener(this);

        chengdu.setOnClickListener(this);
        xian.setOnClickListener(this);
        nanjing.setOnClickListener(this);
        shijiazhuang.setOnClickListener(this);
        beijing.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        sp = getSharedPreferences("myrate", Activity.MODE_PRIVATE);;
        SharedPreferences.Editor editor = sp.edit();
        AlertDialog.Builder builder = new AlertDialog.Builder(city_add.this);
        switch (view.getId()) {
            case R.id.search_iv_submit:
                intent.setClass(this, MainActivity.class);
                TextView textView = findViewById(R.id.search_et);
                String city_name = textView.getText().toString();
                Log.i("获取",city_name);
                if(city_name.equals("")) {
                    builder.setTitle("错误");
                    builder.setMessage("未获取到数据，请重新填写");
                    builder.setPositiveButton("确定",null);
                    builder.create().show();
                    break;
                }
                else {
                    editor.putString("city_name", city_name);
                    editor.apply();
                    startActivity(intent);
                    break;
                }

            case R.id.chengdu:
                intent.setClass(this, MainActivity.class);
                editor.putString("city_name","成都");
                editor.apply();
                startActivity(intent);
                break;
            case  R.id.xian:
                 intent.setClass(this, MainActivity.class);

                editor.putString("city_name","西安");
                editor.apply();
                 startActivity(intent);
                break;
            case R.id.nanjing:
                 intent.setClass(this, MainActivity.class);

                editor.putString("city_name","南京");
                editor.apply();
                 startActivity(intent);
                break;
            case R.id.shijiazhuang:
                intent.setClass(this, MainActivity.class);

                editor.putString("city_name","石家庄");
                editor.apply();
                startActivity(intent);
                break;
            case R.id.beijing:
                intent.setClass(this, MainActivity.class);

                editor.putString("city_name","北京");
                editor.apply();
                startActivity(intent);
                break;

        }

    }
}
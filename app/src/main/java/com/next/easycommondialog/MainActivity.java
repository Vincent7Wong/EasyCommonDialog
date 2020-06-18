package com.next.easycommondialog;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.next.dialoglib.EasyCommonDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onShow(View view) {
       new EasyCommonDialog.Builder(MainActivity.this)
               .title("提示")
               .titleBold(true)
               .content("确认退出吗？")
               .create()
               .show();
    }
}
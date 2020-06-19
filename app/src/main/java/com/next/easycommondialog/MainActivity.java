package com.next.easycommondialog;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.next.dialoglib.DialogInterface;
import com.next.dialoglib.EasyDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onShow(View view) {
        new EasyDialog.Builder(MainActivity.this)
                .content("退出后不会删除任何历史数据，下次登录依然可以使用本账号。")
                .rightTextStr("退出")
                .create()
                .show();
    }

    public void onTest(View view) {
        new EasyDialog.Builder(MainActivity.this)
                // .dialogWidth(270)
                .dialogMinHeight(200)
//                .dialogHeight(220)
                .title("标题")
                .content("1. Sitka spruce 清亮的音色，极广域的音色动态表现，适合给节奏性强烈的曲风.\n" +
                        "2. Western Red Cedar 温暖柔和的音色，适合指法弹奏与分散合弦的玩家.\n" +
                        "3. Tropical American Mahogany 清亮的音色，独特的音色平衡反应让极细微的拨弦都能清楚地呈现。\n" +
                        "4. African Mahogany 音色温暖中频饱满，音色平衡反应与Tropical American Mahogany类似。\n" +
                        "5. Engelmann Spruce 比Sitka较为轻的木料，提供温和的泛音共鸣。\n" +
                        "6. Big Left Maple 木料上的颗粒，提供了较为紧实与平衡的音色反应。")
                .rightTextStr("哈哈")
                .leftTextStr("嘻嘻")
                .lineHeight(2)
                .titleTextColor(Color.parseColor("#999999"))
                .contentTextColor(Color.parseColor("#999999"))
                .lineColor(Color.parseColor("#0f0f0f"))
                .rightTextColor(Color.parseColor("#ff0000"))
                .leftTextColor(Color.parseColor("#00ff00"))
                .rightTextSize(18)
                .leftTextSize(18)
                .contentTextSize(18)
                .titleTextSize(18)
                .titleBold(false)
                .contentBold(true)
                .leftBold(false)
                .rightBold(false)
                .dialogRadius(20)
                .dialogBgColor(Color.parseColor("#e6e6e6"))
                .btnHeight(40)
                .touchOutsideCancelable(true)
                .backDismiss(false)
                .setOnLeftListener(new DialogInterface.OnLeftListener() {
                    @Override
                    public boolean onLeftEvent(EasyDialog dialog) {
                        Toast.makeText(MainActivity.this,"您点击了左边的按钮",Toast.LENGTH_SHORT).show();
                        return false;
                    }
                })
                .setOnRightListener(new DialogInterface.OnRightListener() {
                    @Override
                    public boolean onRightEvent(EasyDialog dialog) {
                        Toast.makeText(MainActivity.this,"您点击了右边的按钮",Toast.LENGTH_SHORT).show();
                        return false;
                    }
                })
                .create()
                .show();
    }
}
package com.fly.myuiassemble;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;

import com.fly.myuiassemble.action.ActionActivity;
import com.fly.myuiassemble.addressbook.WaveSideActivity;
import com.fly.viewlibrary.sectormenu.ButtonData;
import com.fly.viewlibrary.sectormenu.ButtonEventListener;
import com.fly.viewlibrary.sectormenu.SectorMenuButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private SectorMenuButton sectorMenuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sectorMenuButton = findViewById(R.id.bottomSectorMenu);
        int[] menuIconItems = {R.drawable.main_tab_add_ic_selector, R.drawable.main_tab_scan_ic_selector,
                R.drawable.main_tab_doctor_ic_selector, R.drawable.main_tab_record_ic_selector,
                R.drawable.main_tab_programme_ic_selector};
        String[] menuTextItems = {"", getString(R.string.main_tab_scan_text), getString(R.string.main_tab_doctor_text),
                getString(R.string.main_tab_record_text), getString(R.string.main_tab_porgramme_text)};

        final List<ButtonData> buttonDatas = new ArrayList<>();
        for (int i = 0; i < menuIconItems.length; i++) {
            ButtonData buttonData = ButtonData.buildTextButton(menuTextItems[i]);
            buttonData.setIconResId(this, menuIconItems[i]);
            buttonDatas.add(buttonData);
        }
        sectorMenuButton.setButtonDatas(buttonDatas);
        sectorMenuButton.setButtonEventListener(new ButtonEventListener() {
            @Override
            public void onButtonClicked(int index) {

            }

            @Override
            public void onExpand() {

            }

            @Override
            public void onCollapse() {
            }
        });
    }

    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btnProgress:
                intent.setClass(MainActivity.this, ProgressActivity.class);
                break;
            case R.id.btnLine:
                intent.setClass(MainActivity.this, LineActivity.class);
                break;
            case R.id.btnSort:
                intent.setClass(MainActivity.this, WaveSideActivity.class);
                break;
            case R.id.btnAction:
                intent.setClass(MainActivity.this, ActionActivity.class);
                break;
            case R.id.btnTime:
                intent.setClass(MainActivity.this, TimeActivity.class);
                break;
            default:
                Log.d("FLY", "onClick: " + view.getId());
                return;
        }
        startActivity(intent);
    }
}

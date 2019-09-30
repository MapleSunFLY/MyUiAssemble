package com.fly.myuiassemble;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.fly.myuiassemble.addressbook.WaveSideActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            default:
                Log.d("FLY", "onClick: " + view.getId());
                return;
        }
        startActivity(intent);
    }
}

package com.fly.myuiassemble.addressbook;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fly.myuiassemble.MainActivity;
import com.fly.myuiassemble.R;
import com.fly.viewlibrary.sortlist.CharacterParser;
import com.fly.viewlibrary.sortlist.WaveSideBar;
import com.fly.viewlibrary.text.ClearEditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class WaveSideActivity extends AppCompatActivity {

    private ClearEditText mClearEditText;

    private ListView sortListView;
    private WaveSideBar sideBar;
    private TextView dialog;
    private MySortAdapter adapter;

    private Map<String, String> callRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave_side);
        initView();
        initData();
    }

    private void initView() {
        mClearEditText = this.findViewById(R.id.etClear);
        sideBar = (WaveSideBar) this.findViewById(R.id.sidrbar);
        dialog = (TextView) this.findViewById(R.id.dialog);
        sortListView = (ListView) this.findViewById(R.id.sortlist);
    }


    private void initData() {

        sideBar.setTextView(dialog);

        sortListView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            // 这里要利用adapter.getItem(position)来获取当前position所对应的对象
            String number = ((SortEntity) adapter.getItem(position)).getName();
            Toast.makeText(this, number, Toast.LENGTH_SHORT).show();
        });


        adapter = new MySortAdapter(this);
        adapter.setWaveSideBar(sideBar,sortListView);
        sortListView.setAdapter(adapter);

        new ConstactAsyncTask().execute(0);
    }


    private class ConstactAsyncTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... arg0) {
            int result = -1;
            callRecords = ConstactUtils.getAllCallRecords(WaveSideActivity.this);
            result = 1;
            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            if (result == 1) {
                List<SortEntity> constact = new ArrayList<SortEntity>();
                for (Iterator<String> keys = callRecords.keySet().iterator(); keys.hasNext(); ) {
                    String key = keys.next();
                    constact.add(new SortEntity(key, callRecords.get(key)));
                }
                adapter.setListDate(constact);
                mClearEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                    @Override
                    public void onFocusChange(View arg0, boolean arg1) {
                        mClearEditText.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);

                    }
                });
                // 根据输入框输入值的改变来过滤搜索
                mClearEditText.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        // 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                        filterData(s.toString(), (List<SortEntity>) adapter.getListDate());
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start,
                                                  int count, int after) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr, List<SortEntity> SourceDateList) {
        List<SortEntity> filterDateList = new ArrayList<SortEntity>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = SourceDateList;
        } else {
            filterDateList.clear();
            for (SortEntity sortModel : SourceDateList) {
                String name = sortModel.getName();
                if (name.indexOf(filterStr.toString()) != -1
                        || CharacterParser.getInstance().getSelling(name)
                        .startsWith(filterStr.toString())) {
                    filterDateList.add(sortModel);
                }
            }
        }
        adapter.setListDate(filterDateList);
    }

}



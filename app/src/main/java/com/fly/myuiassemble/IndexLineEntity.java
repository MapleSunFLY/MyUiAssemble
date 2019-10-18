package com.fly.myuiassemble;

import com.fly.viewlibrary.statistics.line.index.IndexLineMode;

import java.util.List;

/**
 * 包    名 : com.fly.myuiassemble
 * 作    者 : FLY
 * 创建时间 : 2019/10/17
 * 描述:
 */
public class IndexLineEntity implements IndexLineMode {

    private long time;

    private List<Double> list;

    @Override
    public long getRecordTime() {
        return time;
    }

    @Override
    public List<Double> getValues() {
        return list;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setList(List<Double> list) {
        this.list = list;
    }
}

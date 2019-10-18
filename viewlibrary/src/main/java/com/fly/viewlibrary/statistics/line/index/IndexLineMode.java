package com.fly.viewlibrary.statistics.line.index;

import java.util.List;

/**
 * 包    名 : com.fly.viewlibrary.statistics.line.index
 * 作    者 : FLY
 * 创建时间 : 2019/10/16
 * 描述: 参数曲线模型
 */
public interface IndexLineMode {

    /**
     * 指标记录时间
     *
     * @return
     */
    long getRecordTime();

    /**
     * 指标值数组 每条值数组必须相同且不为空
     *
     * @return
     */
    List<Double> getValues();
}

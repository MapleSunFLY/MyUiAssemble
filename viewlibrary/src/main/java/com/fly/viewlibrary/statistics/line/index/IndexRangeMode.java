package com.fly.viewlibrary.statistics.line.index;

/**
 * 包    名 : com.fly.viewlibrary.statistics.line.index
 * 作    者 : FLY
 * 创建时间 : 2019/10/16
 * 描述: 参数曲线模型
 */
public interface IndexRangeMode {

    /**
     * 正常值区间上线
     *
     * @return
     */
    Integer getMaxNormalValue();

    /**
     * 正常值区间下线
     *
     * @return
     */
    Integer getMinNormalValue();

    /**
     * 正常值区间描述
     *
     * @return
     */
    String getNormalDescribe();

    /**
     * 正常值区间颜色
     *
     * @return
     */
    Integer getNormalRangeColor();

    /**
     * 正常值文本颜色
     *
     * @return
     */
    Integer getNormalTextColor();
}

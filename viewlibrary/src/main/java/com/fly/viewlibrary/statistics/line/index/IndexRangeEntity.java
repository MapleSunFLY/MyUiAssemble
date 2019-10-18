package com.fly.viewlibrary.statistics.line.index;

/**
 * 包    名 : com.fly.viewlibrary.statistics.line.index
 * 作    者 : FLY
 * 创建时间 : 2019/10/16
 * 描述: 参数曲线模型
 */
public class IndexRangeEntity implements IndexRangeMode {

    private Integer maxNormalValue;

    private Integer minNormalValue;

    private String normalDescribe;

    private int normalRangeColor;

    private int normalTextColor;

    public IndexRangeEntity(Integer maxNormalValue, Integer minNormalValue, String normalDescribe, Integer normalRangeColor, Integer normalTextColor) {
        this.maxNormalValue = maxNormalValue;
        this.minNormalValue = minNormalValue;
        this.normalDescribe = normalDescribe;
        this.normalRangeColor = normalRangeColor;
        this.normalTextColor = normalTextColor;
    }

    @Override
    public Integer getMaxNormalValue() {
        return maxNormalValue;
    }

    @Override
    public Integer getMinNormalValue() {
        return minNormalValue;
    }

    @Override
    public String getNormalDescribe() {
        return normalDescribe;
    }

    @Override
    public Integer getNormalRangeColor() {
        return normalRangeColor;
    }

    @Override
    public Integer getNormalTextColor() {
        return normalTextColor;
    }


    public void setMaxNormalValue(Integer maxNormalValue) {
        this.maxNormalValue = maxNormalValue;
    }

    public void setMinNormalValue(Integer minNormalValue) {
        this.minNormalValue = minNormalValue;
    }

    public void setNormalDescribe(String normalDescribe) {
        this.normalDescribe = normalDescribe;
    }

    public void setNormalRangeColor(Integer normalRangeColor) {
        this.normalRangeColor = normalRangeColor;
    }

    public void setNormalTextColor(Integer normalTextColor) {
        this.normalTextColor = normalTextColor;
    }
}

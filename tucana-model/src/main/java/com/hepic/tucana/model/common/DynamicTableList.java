package com.hepic.tucana.model.common;

import java.util.List;

public class DynamicTableList<T, S> {

    /**
     * 列头
     */
    private List<T> column;

    /**
     * 数据列表
     */
    private List<S> list;

    public List<T> getColumn() {
        return column;
    }

    public void setColumn(List<T> column) {
        this.column = column;
    }

    public List<S> getList() {
        return list;
    }

    public void setList(List<S> list) {
        this.list = list;
    }
}

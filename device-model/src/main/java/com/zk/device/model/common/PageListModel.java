package com.zk.device.model.common;

import java.util.List;

/**
 * @author device
 * @Title:
 * @Description:
 * @date 2018/8/27.
 */
public class PageListModel<T> {

    private Long total;

    private Integer page;

    private List<T> list;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}

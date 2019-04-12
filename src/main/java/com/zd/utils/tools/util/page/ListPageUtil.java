package com.zd.utils.tools.util.page;

import java.util.Collections;
import java.util.List;

/**
 * List分页
 * 注意：实例化 ListPageUtil的时候，需要判断从数据库中查询出来的 list的长度，如果是 0，那么请手动分页（返回空对象），否则将会报错 IllegalArgumentException
 * @author Zidong
 * @date 2019/3/12 上午8:12
 */
public class ListPageUtil<T> {
    /**
     * 每页显示条数
     */
    private int pageSize;

    /**
     * 总页数
     */
    private int pageCount;

    /**
     * 原集合
     */
    private List<T> data;

    public ListPageUtil(List<T> data, int pageSize) {
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("data must be not empty!");
        }
        this.data = data;
        this.pageSize = pageSize;
        this.pageCount = data.size()/pageSize;
        if(data.size()%pageSize!=0){
            this.pageCount++;
        }
    }

    /**
     * 得到分页后的数据
     *
     * @param pageNum 页码
     * @return 分页后结果
     */
    public List<T> getPagedList(int pageNum) {
        int fromIndex = (pageNum - 1) * pageSize;
        if (fromIndex >= data.size()) {
            return Collections.emptyList();
        }

        int toIndex = pageNum * pageSize;
        if (toIndex >= data.size()) {
            toIndex = data.size();
        }
        return data.subList(fromIndex, toIndex);
    }

    public int getPageSize() {
        return pageSize;
    }

    public List<T> getData() {
        return data;
    }

    public int getPageCount() {
        return pageCount;
    }

}

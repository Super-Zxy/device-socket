package com.util;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @data 2021/4/29
 * @Description
 */
@Data
@Accessors(chain = true)
public class Pager<T> {
    private int pageNum;//分页起始页
    private int pageSize;//每页记录数
    private List<T> rows;//返回的记录集合
    private long totals;//总记录条数

}

package com.asdf.funny.images.util;

import java.util.List;

/**
 * 分页查询条件封装类
 *
 * @author witty
 */
public class MybatisBaseQuery {

    /**
     * 当前页，需要查询的页数
     */
    private int currentPage = 0;

    /**
     * 每页显示的条数
     */
    private int pageSize = 10;

    /**
     * 开始条数
     */
    private int startNum = 0;

    /**
     * 结束条数
     */
    private int endNum;

    /**
     * 数据总页数
     */
    private int totalPage;

    /**
     * 查询关键字
     */
    private String searchKey;

    private List<?> list;

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    /**
     *设置分页查询的所有参数
     * @param currentPage 当前页
     * @param pageSize 每页条数
     * @param totalNum 总条数
     */
    public void setBaseQueryInfo(int currentPage, int pageSize, int totalNum){
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        totalPage = totalNum % pageSize == 0 ? totalNum / pageSize : totalNum / pageSize + 1;
        this.currentPage = currentPage > totalPage ? totalPage : currentPage;
        startNum = currentPage > 0 ? (currentPage - 1) * pageSize : 0;
        endNum = (currentPage * pageSize) > totalNum ? totalNum : (currentPage * pageSize);
    }

    public MybatisBaseQuery() {
    }

    public MybatisBaseQuery(int currentPage, int pageSize, int totalNum) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        totalPage = totalNum % pageSize == 0 ? totalNum / pageSize : totalNum / pageSize + 1;
        this.currentPage = currentPage > totalPage ? totalPage : currentPage;
        startNum = currentPage > 0 ? (currentPage - 1) * pageSize : 0;
        endNum = (currentPage * pageSize) > totalNum ? totalNum : (currentPage * pageSize);
    }

    public MybatisBaseQuery(int currentPage, int pageSize, int totalNum, String searchKey) {
        this(currentPage, pageSize, totalNum);
        this.searchKey = searchKey;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartNum() {
        return startNum;
    }

    public void setStartNum(int startNum) {
        this.startNum = startNum;
    }

    public int getEndNum() {
        return endNum;
    }

    public void setEndNum(int endNum) {
        this.endNum = endNum;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }
}
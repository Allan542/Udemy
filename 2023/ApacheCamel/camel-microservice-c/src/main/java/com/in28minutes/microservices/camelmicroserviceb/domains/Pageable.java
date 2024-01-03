package com.in28minutes.microservices.camelmicroserviceb.domains;

public class Pageable {
    private Sort sort;
    private Integer offset;
    private Integer pageNumber;
    private Integer pageSize;
    private boolean unpaged;
    private boolean paged;

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isUnpaged() {
        return unpaged;
    }

    public void setUnpaged(boolean unpaged) {
        this.unpaged = unpaged;
    }

    public boolean isPaged() {
        return paged;
    }

    public void setPaged(boolean paged) {
        this.paged = paged;
    }

    @Override
    public String toString() {
        return "Pageable{" +
            "sort=" + sort +
            ", offset=" + offset +
            ", pageNumber=" + pageNumber +
            ", pageSize=" + pageSize +
            ", unpaged=" + unpaged +
            ", paged=" + paged +
            '}';
    }
}

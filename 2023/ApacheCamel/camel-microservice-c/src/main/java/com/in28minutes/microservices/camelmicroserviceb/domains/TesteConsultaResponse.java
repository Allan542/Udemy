package com.in28minutes.microservices.camelmicroserviceb.domains;

import java.util.List;

public class TesteConsultaResponse {
    private List<Content> content;
    private Pageable pageable;
    private Integer totalElements;
    private Integer totalPages;
    private boolean last;
    private Integer size;
    private Integer number;
    private Sort sort;
    private Integer numberOfElements;
    private String first;
    private boolean empty;

    public List<Content> getContent() {
        return content;
    }

    public void setContent(List<Content> content) {
        this.content = content;
    }

    public Pageable getPageable() {
        return pageable;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }

    public Integer getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Integer totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public Integer getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(Integer numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    @Override
    public String toString() {
        return "TesteConsultaResponse{" +
            "content=" + content +
            ", pageable=" + pageable +
            ", totalElements=" + totalElements +
            ", totalPages=" + totalPages +
            ", last=" + last +
            ", size=" + size +
            ", number=" + number +
            ", sort=" + sort +
            ", numberOfElements=" + numberOfElements +
            ", first='" + first + '\'' +
            ", empty=" + empty +
            '}';
    }
}

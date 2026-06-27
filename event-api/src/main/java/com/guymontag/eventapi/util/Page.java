package com.guymontag.eventapi.util;

import com.guymontag.eventapi.exception.PageSizeOutOfBoundException;

import java.util.List;

public class Page<T> {

    private List<T> sendElementDTOs;

    private int pageNumber;

    private int maxSize;

    private int totalElements;

    public Page(List<T> elementDTOs, int pageNumber, int maxSize,int totalElements) {

        if (elementDTOs == null) {
            throw new IllegalArgumentException("elementDTOs is null");
        }

        if (elementDTOs.size() > maxSize) {
            throw new PageSizeOutOfBoundException("List given is bigger than maxSize");
        }

        this.sendElementDTOs = elementDTOs;
        this.pageNumber = pageNumber;
        this.maxSize = maxSize;
        this.totalElements = totalElements;
    }

    public List<T> getSendElementDTOs() {
        return sendElementDTOs;
    }

    public void setSendElementDTOs(List<T> sendElementDTOs) {
        this.sendElementDTOs = sendElementDTOs;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public int getNumberElements() {
        if( sendElementDTOs == null){
            throw new NullPointerException("SendElementDTOs is null");
        }
        return sendElementDTOs.size();
    }

    public T getPageNumber() {
        return null;
    }

    public List<T> getElements() {
        return List.of();
    }
}

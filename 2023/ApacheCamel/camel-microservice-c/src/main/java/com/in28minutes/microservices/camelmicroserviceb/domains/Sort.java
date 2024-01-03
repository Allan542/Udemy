package com.in28minutes.microservices.camelmicroserviceb.domains;

public class Sort {
    private boolean empty;
    private boolean unsorted;
    private boolean sorted;

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public boolean isUnsorted() {
        return unsorted;
    }

    public void setUnsorted(boolean unsorted) {
        this.unsorted = unsorted;
    }

    public boolean isSorted() {
        return sorted;
    }

    public void setSorted(boolean sorted) {
        this.sorted = sorted;
    }

    @Override
    public String toString() {
        return "Sort{" +
            "empty=" + empty +
            ", unsorted=" + unsorted +
            ", sorted=" + sorted +
            '}';
    }
}

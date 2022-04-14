package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class WorkSection extends Section{
    private static final long serialVersionUID = 1L;
    private List<Work> workList = new ArrayList<>();

    public List<Work> getWorkList() {
        return workList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkSection that = (WorkSection) o;

        return workList.equals(that.workList);
    }

    @Override
    public int hashCode() {
        return workList.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for(Work work : workList) {
            sb.append(work);
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

}

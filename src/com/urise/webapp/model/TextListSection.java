package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class TextListSection extends Section{
    private static final long serialVersionUID = 1L;
    private List<String> descriptionList = new ArrayList<>();

    public List<String> getDescriptionList() {
        return descriptionList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextListSection that = (TextListSection) o;

        return descriptionList.equals(that.descriptionList);
    }

    @Override
    public int hashCode() {
        return descriptionList.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for(String description : descriptionList) {
            sb.append(description);
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}

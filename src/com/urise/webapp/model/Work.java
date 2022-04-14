package com.urise.webapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Work implements Serializable {
    private static final long serialVersionUID = 1L;
    private String title;

    private List<WorkDescription> descriptionList = new ArrayList<>();

    public Work(String title) {
        this.title = title;
    }

    public void addDescription(String dates, String title, String description) {
        descriptionList.add(new WorkDescription(dates, title, description));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(title);
        for (WorkDescription workDescription : descriptionList) {
            sb.append(System.lineSeparator());
            sb.append(workDescription);
        }
        return sb.toString();
    }


    private class WorkDescription implements Serializable {
        private static final long serialVersionUID = 1L;
        private String dates;
        private String title;
        private String description;

        public WorkDescription(String dates, String title, String description) {
            this.dates = dates;
            this.title = title;
            this.description = description;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder(dates);
            sb.append("\t");
            sb.append(title);
            if (description != null) {
                sb.append(System.lineSeparator());
                sb.append("\t\t").append(description);
            }
            return sb.toString();
        }
    }
}

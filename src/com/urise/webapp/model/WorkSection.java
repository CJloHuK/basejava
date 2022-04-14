package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class WorkSection extends Section{
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

    public static class Work {
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
            for(WorkDescription workDescription : descriptionList) {
                sb.append(System.lineSeparator());
                sb.append(workDescription);
            }
            return sb.toString();
        }


        private class WorkDescription {
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
                if(description != null) {
                    sb.append(System.lineSeparator());
                    sb.append("\t\t").append(description);
                }
                return sb.toString();
            }
        }
    }
}
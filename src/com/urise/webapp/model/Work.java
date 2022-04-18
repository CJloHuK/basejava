package com.urise.webapp.model;

import com.urise.webapp.util.DateUtil;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Work implements Serializable {
    private static final long serialVersionUID = 1L;
    private String title;

    private List<WorkDescription> descriptionList = new ArrayList<>();

    public Work(String title) {
        this.title = title;
    }

    public void addDescription(WorkDescription workDescription) {
        descriptionList.add(workDescription);
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


    public class WorkDescription implements Serializable {
        private static final long serialVersionUID = 1L;
        //private String dates;
        private LocalDate startDate;
        private LocalDate endDate;
        private String title;
        private String description;

        public WorkDescription(int startYear, Month startMonth, String title, String description) {
            this(DateUtil.of(startYear, startMonth), DateUtil.NOW, title, description);
        }

        public WorkDescription(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description) {
            this(DateUtil.of(startYear, startMonth), DateUtil.of(endYear, endMonth), title, description);
        }

        public WorkDescription(LocalDate startDate, LocalDate endDate, String title, String description) {
            Objects.requireNonNull(startDate, "startDate must not be null");
            Objects.requireNonNull(endDate, "endDate must not be null");
            Objects.requireNonNull(title, "title must not be null");
            this.startDate = startDate;
            this.endDate = endDate;
            this.title = title;
            this.description = description;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder(startDate.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            sb.append(" - ");
            if(endDate.equals(DateUtil.NOW)) {
                sb.append("настоящее время");
            }
            else {
                sb.append(endDate.format(DateTimeFormatter.ofPattern("MMM yyyy")));
            }
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

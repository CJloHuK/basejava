package com.urise.webapp.model;

import com.urise.webapp.util.DateUtil;
import com.urise.webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Work implements Serializable {
    private static final long serialVersionUID = 1L;
    private String title;

    private List<WorkDescription> descriptionList = new ArrayList<>();

    public Work() {
    }

    public Work(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public List<WorkDescription> getDescriptionList() {
        return descriptionList;
    }

    public void addDescription(WorkDescription workDescription) {
        descriptionList.add(workDescription);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Work work = (Work) o;
        return Objects.equals(title, work.title) && Objects.equals(descriptionList, work.descriptionList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, descriptionList);
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


    @XmlAccessorType(XmlAccessType.FIELD)
    public static class WorkDescription implements Serializable {
        private static final long serialVersionUID = 1L;
        //private String dates;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate startDate;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate endDate;
        private String title;
        private String description;

        public WorkDescription() {
        }

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

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            WorkDescription that = (WorkDescription) o;
            return startDate.equals(that.startDate) && endDate.equals(that.endDate) && title.equals(that.title) && Objects.equals(description, that.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(startDate, endDate, title, description);
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

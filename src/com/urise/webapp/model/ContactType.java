package com.urise.webapp.model;

public enum ContactType {

    PHONE("Тел.", false),
    SKYPE("Skype", false),
    EMAIL("Почта", false),
    LINKEDIN("Профиль LinkedIn", true),
    GITHUB("Профиль GitHub", true),
    STACKOVERFLOW("Профиль Stackoverflow", true),
    HOMEPAGE("Домашняя страница", true);

    private String title;
    private boolean titleIsUrl;

    ContactType(String title, boolean titleIsUrl) {
        this.title = title;
        this.titleIsUrl = titleIsUrl;
    }

    public String getTitle() {
        return title;
    }

    public boolean isTitleIsUrl() {
        return titleIsUrl;
    }
}

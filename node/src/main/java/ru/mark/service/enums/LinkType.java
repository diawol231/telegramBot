package ru.mark.service.enums;

public enum LinkType {
    GET_PHOTO("file/get-photo"),
    GET_DOC("file/get-doc");

    private final String link;

    LinkType(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return link;
    }
}

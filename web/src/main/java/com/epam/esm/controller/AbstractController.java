package com.epam.esm.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public abstract class AbstractController<T> {
    private static final String PAGE = "page=";
    private static final String FIRST_PAGE = "first";
    private static final String NEXT_PAGE = "next";
    private static final String PREV_PAGE = "prev";
    private static final String LAST_PAGE = "last";

    protected List<Link> addPagesLinks(CollectionModel<T> tagMethod, int page, int lastPage) {
        List<Link> links = new ArrayList<>();
        Link link = linkTo(tagMethod).withSelfRel();

        final int firstPage = 1;
        int prevPage = page != firstPage ? page - 1 : page;
        int nextPage = page != lastPage ? page + 1 : page;

        links.add(Link.of(link.getHref().replace(PAGE + page, PAGE + firstPage), FIRST_PAGE));
        links.add(Link.of(link.getHref().replace(PAGE + page, PAGE + prevPage), PREV_PAGE));
        links.add(link);
        links.add(Link.of(link.getHref().replace(PAGE + page, PAGE + nextPage), NEXT_PAGE));
        links.add(Link.of(link.getHref().replace(PAGE + page, PAGE + lastPage), LAST_PAGE));
        return links;
    }
}

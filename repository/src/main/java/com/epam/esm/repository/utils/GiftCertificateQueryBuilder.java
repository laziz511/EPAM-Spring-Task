package com.epam.esm.repository.utils;

import java.util.ArrayList;
import java.util.List;

public class GiftCertificateQueryBuilder {

    public static final String BASE_QUERY = "SELECT * FROM gift_certificates gc WHERE 1=1";
    public static final String TAG_CRITERIA = " AND EXISTS (SELECT 1 FROM gift_certificate_tags gct JOIN tags t ON gct.tag_id = t.id WHERE gct.gift_certificate_id = gc.id AND t.name = ?)";
    public static final String SEARCH_CRITERIA = " AND (gc.name ILIKE ? OR gc.description ILIKE ?)";
    public static final String ORDER_BY_NAME = " ORDER BY gc.name";
    public static final String ORDER_BY_CREATE_DATE = " ORDER BY gc.create_date";
    public static final String ASCENDING = " ASC";
    public static final String DESCENDING = " DESC";

    private final StringBuilder queryBuilder;
    private final List<Object> queryParams;

    public GiftCertificateQueryBuilder() {
        this.queryBuilder = new StringBuilder(BASE_QUERY);
        this.queryParams = new ArrayList<>();
    }

    public String build() {
        return queryBuilder.toString();
    }

    public Object[] getQueryParams() {
        return queryParams.toArray();
    }

    public void addTagCriteria(String tagName) {
        queryBuilder.append(TAG_CRITERIA);
        queryParams.add(tagName);
    }

    public void addSearchCriteria(String search) {
        queryBuilder.append(SEARCH_CRITERIA);
        queryParams.add("%" + search + "%");
        queryParams.add("%" + search + "%");
    }

    public void addSortingCriteria(String sortBy, boolean ascending) {
        String sortField = sortBy.equals("name") ? ORDER_BY_NAME : ORDER_BY_CREATE_DATE;
        queryBuilder.append(sortField);

        if (ascending) {
            queryBuilder.append(ASCENDING);
        } else {
            queryBuilder.append(DESCENDING);
        }
    }
}

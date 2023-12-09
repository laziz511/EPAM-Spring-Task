package com.epam.esm.repository;

public class Column {
    public static final String ID = "id";
    public static final String NAME = "name";

    private static final String ERROR_MESSAGE = "Utility class";

    private Column() {
        throw new IllegalStateException(ERROR_MESSAGE);
    }

    public static class GiftCertificateTable {
        public static final String DESCRIPTION = "description";
        public static final String PRICE = "price";
        public static final String DURATION = "duration";
        public static final String CREATE_DATE = "create_date";
        public static final String LAST_UPDATE_DATE = "last_update_date";

        private GiftCertificateTable() {
            throw new IllegalStateException(ERROR_MESSAGE);
        }
    }

    public static class GiftCertificateTagsTable {
        public static final String GIFT_CERTIFICATE_ID = "gift_certificate_id";
        public static final String TAG_ID = "tag_id";

        private GiftCertificateTagsTable() {
            throw new IllegalStateException(ERROR_MESSAGE);
        }
    }
}

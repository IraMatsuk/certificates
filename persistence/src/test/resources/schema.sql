create table gift_certificate(
    gift_certificate_id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name varchar(100),
    description varchar(200),
    price numeric(8,2),
    duration int,
    create_date timestamp,
    last_update_date timestamp
);

create table tag(
    tag_id bigint GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name varchar(100) UNIQUE
);

create table tag_gift_certificate(
    gift_certificate_id bigint REFERENCES gift_certificate(gift_certificate_id) ON DELETE CASCADE ON UPDATE CASCADE,
    tag_id bigint REFERENCES tag(tag_id) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (gift_certificate_id, tag_id)
);
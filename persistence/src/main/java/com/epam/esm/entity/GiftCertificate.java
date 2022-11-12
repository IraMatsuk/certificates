package com.epam.esm.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;


@Entity
@Table(name = "gift_certificate")
@Data
@Builder
public class GiftCertificate implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gift_certificate_id")
    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int duration;

    @Column(name = "create_date", insertable = false)
    private LocalDateTime createDate;

    @Column(name = "last_update_date", insertable = false)
    private LocalDateTime lastUpdateDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tag_gift_certificate",
               joinColumns = {@JoinColumn(name = "gift_certificate_id")},
               inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private Set<Tag> tags;

    /**
     * Instantiates a new Gift certificate.
     */
    @Tolerate
    public GiftCertificate() {
        tags = new LinkedHashSet<>();
    }

    @Tolerate
    public GiftCertificate(long id) {
        this.id = id;
        tags = new LinkedHashSet<>();
    }


}

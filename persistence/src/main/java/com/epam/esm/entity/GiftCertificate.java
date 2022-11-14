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

import static com.epam.esm.entity.AuditListener.*;

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

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "last_update_date")
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

    @PrePersist
    public void onPrePersist() {
        audit(this, INSERT_OPERATION);
        createDate = auditDateTime;
    }

    /**
     * On pre update.
     */
    @PreUpdate
    public void onPreUpdate() {
        audit(this, UPDATE_OPERATION);
        lastUpdateDate = auditDateTime;
    }

    /**
     * On pre remove.
     */
    @PreRemove
    public void onPreRemove() {
        audit(this, DELETE_OPERATION);
        lastUpdateDate = auditDateTime;
    }
}

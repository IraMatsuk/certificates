package com.epam.esm.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.epam.esm.entity.AuditListener.*;

@Entity
@EntityListeners(AuditListener.class)
@Table(name = "orders")
@Data
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private long id;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "gift_certificate_id")
    private GiftCertificate certificate;

    @Tolerate
    public Order() {
        user = new User();
        certificate = new GiftCertificate();
    }

    @PrePersist()
    public void onPrePersist() {
        createDate = auditDateTime;
        audit(this, INSERT_OPERATION);
    }
}

package com.epam.esm.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

/**
 * The type Audit listener.
 */
class AuditListener {
    /**
     * The Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(AuditListener.class);
    /**
     * The Insert operation.
     */
    static final String INSERT_OPERATION = "INSERT";
    /**
     * The Update operation.
     */
    static final String UPDATE_OPERATION = "UPDATE";
    /**
     * The Delete operation.
     */
    static final String DELETE_OPERATION = "DELETE";
    /**
     * The Audit date time.
     */
    static LocalDateTime auditDateTime;

    /**
     * On pre persist.
     *
     * @param object the object
     */
    @PrePersist
    public void onPrePersist(Object object) {
        audit(object, INSERT_OPERATION);
    }

    /**
     * On pre update.
     *
     * @param object the object
     */
    @PreUpdate
    public void onPreUpdate(Object object) {
        audit(object, UPDATE_OPERATION);
    }

    /**
     * On pre remove.
     *
     * @param object the object
     */
    @PreRemove
    public void onPreRemove(Object object) {
        audit(object, DELETE_OPERATION);
    }

    /**
     * Audit.
     *
     * @param object    the object
     * @param operation the operation
     */
    static void audit(Object object, String operation) {
        auditDateTime = LocalDateTime.now();
        LOGGER.info("Class: {}", object.getClass());
        LOGGER.info("Entity: {}", object);
        LOGGER.info("Operation: {}", operation);
        LOGGER.info("Date & time: {}", auditDateTime);
    }
}

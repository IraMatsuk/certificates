package com.epam.esm.dto;

/**
 * The type View.
 */
public class View {
    /**
     * The interface User without orders.
     */
    public interface UserWithoutOrders {}

    /**
     * The interface User with orders.
     */
    public interface UserWithOrders extends UserWithoutOrders {}
}
package org.vaadin.tori.service;

/**
 * Provides methods for specifying access rights to resources or certain
 * operations.
 */
public interface AuthorizationService {

    /**
     * Returns {@code true} if the current user is authorized to create and
     * rearrange categories.
     * 
     * @return {@code true} if the user can create and rearrange categories.
     */
    boolean isCategoryAdministrator();

    boolean mayReportPosts();

    boolean mayEditPosts();
}

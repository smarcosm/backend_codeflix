package com.smarcosm.admin_catalogo.domain.exception;

import com.smarcosm.admin_catalogo.domain.validation.handler.Notification;

public class NotificationException extends DomainException {

    public NotificationException(final String aMessage, final Notification notification) {
        super(aMessage, notification.getErrors());
    }
}

package com.smarcosm.admin_catalogo.domain.castmember;

import com.smarcosm.admin_catalogo.domain.AggregateRoot;
import com.smarcosm.admin_catalogo.domain.exception.NotificationException;
import com.smarcosm.admin_catalogo.domain.utils.InstantUtils;
import com.smarcosm.admin_catalogo.domain.validation.ValidationHandler;
import com.smarcosm.admin_catalogo.domain.validation.handler.Notification;

import java.time.Instant;

public class CastMember extends AggregateRoot<CastMemberID> {
    private String name;
    private CastMemberType type;
    public Instant createdAt;
    public Instant updatedAt;

    public CastMember(final CastMemberID anId, final String aName, final CastMemberType aType, final Instant aCreationDate, final Instant aUpdationDate) {
        super(anId);
        this.name = aName;
        this.type = aType;
        this.createdAt = aCreationDate;
        this.updatedAt = aUpdationDate;
        selfValidate();
    }
    public static CastMember newMember(final String aName, final CastMemberType aType){
        final var anId = CastMemberID.unique();
        final var now = InstantUtils.now();
        return new CastMember(anId, aName, aType, now,now);
    }
    @Override
    public void validate(final ValidationHandler aHandler){
        new CastMemberValidator(this, aHandler).validate();
    }

    public String getName() {
        return name;
    }

    public CastMemberType getType() {
        return type;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    private void selfValidate(){
        final var notification = Notification.create();
        validate(notification);

        if (notification.hasError()){
            throw new NotificationException("Failed to create a Aggregate CastMember", notification);
        }
    }
}
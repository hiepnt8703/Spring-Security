package com.jmaster.shop_app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class UserPermissionOverrideId implements java.io.Serializable{
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "permission_id")
    private Long permissionId;
}

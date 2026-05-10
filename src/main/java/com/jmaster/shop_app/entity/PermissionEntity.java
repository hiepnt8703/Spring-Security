package com.jmaster.shop_app.entity;

import com.jmaster.shop_app.enums.PermissionAction;
import jakarta.persistence.*;
import lombok.*;

import java.net.http.HttpRequest;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "permission",
        indexes = {
                @Index(name = "idx_permission_code", columnList = "code"),
                @Index(name = "idx_permission_resource_action", columnList = "resource, action")
        }
)
public class PermissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Ví dụ:
     * USER_READ
     * USER_CREATE
     * PAYROLL_APPROVE
     */
    @Column(name = "code", nullable = false, length = 64)
    private String code;

    /**
     * Tên hiển thị của quyền
     * Ví dụ: Xem người dùng, Tạo người dùng
     */
    @Column(name = "name", nullable = false, length = 128)
    private String name;

    /**
     * Nhóm tài nguyên / module
     * Ví dụ: users, payroll, roles
     */
    @Column(name = "resource", length = 128)
    private String resource;

    /**
     * Hành động
     * Ví dụ: READ, CREATE, UPDATE, DELETE, APPROVE, EXPORT
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "action", length = 32)
    private PermissionAction action;

    @Column(name = "description", length = 255)
    private String description;

    @Version
    @Column(name = "version")
    private Long version;

    @OneToMany(mappedBy = "permission")
    @Builder.Default
    private Set<RolePermission> rolePermissions = new HashSet<>();

    @OneToMany(mappedBy = "permission")
    @Builder.Default
    private Set<UserPermissionOverride> userPermissionOverrides = new HashSet<>();
}

package com.jmaster.shop_app.entity;

import com.jmaster.shop_app.enums.RoleCode;
import com.jmaster.shop_app.enums.convert.RoleCodeConverter;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "role",
        indexes = {
                @Index(name = "idx_role_code", columnList = "code")
        }
)
public class RoleEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = RoleCodeConverter.class)
    @Column(name = "code", nullable = false, length = 64)
    private RoleCode code;

    @Column(name = "name", nullable = false, length = 128)
    private String name;

    @Column(name = "description", length = 255)
    private String description;

    @Version
    @Column(name = "version")
    private Long version;

    @OneToMany(mappedBy = "role")
    @Builder.Default
    private Set<UserRole> userRoles = new HashSet<>();

    @OneToMany(
            mappedBy = "role",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private Set<RolePermission> rolePermissions = new HashSet<>();

}

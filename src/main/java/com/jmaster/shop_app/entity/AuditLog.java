package com.jmaster.shop_app.entity;

import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "audit_log",
        indexes = {
                @Index(name = "idx_audit_log_occurred_at", columnList = "occurred_at"),
                @Index(name = "idx_audit_log_username", columnList = "username"),
                @Index(name = "idx_audit_log_path", columnList = "path"),
                @Index(name = "idx_audit_log_request_id", columnList = "request_id")
        }
)
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "occurred_at", nullable = false)
    private LocalDateTime occurredAt;

    @Column(name = "username", length = 64)
    private String username;

    @Column(name = "method", length = 16)
    private String method;

    @Column(name = "path", length = 255)
    private String path;

    @Column(name = "status")
    private Integer status;

    @Column(name = "ip", length = 64)
    private String ip;

    @Column(name = "user_agent", length = 255)
    private String userAgent;

    /**
     * Ví dụ:
     * USER_CREATE
     * ROLE_ASSIGN
     * LOGIN_SUCCESS
     * LOGIN_FAILED
     */
    @Column(name = "action", length = 128)
    private String action;

    /**
     * Trace ID / Request ID
     */
    @Column(name = "request_id", length = 64)
    private String requestId;

    /**
     * MySQL có thể dùng TEXT/JSON.
     * PostgreSQL có thể dùng jsonb.
     */
    @Lob
    @Column(name = "payload_snapshot")
    private String payloadSnapshot;

    @PrePersist
    public void prePersist() {
        if (this.occurredAt == null) {
            this.occurredAt = LocalDateTime.now();
        }
    }
}

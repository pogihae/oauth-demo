package com.ms.oauth.core.domain.account;

import com.ms.oauth.core.domain.client.ClientId;
import com.ms.oauth.core.domain.common.validation.ValidEmail;
import com.ms.oauth.core.domain.common.validation.ValidName;
import com.ms.oauth.core.domain.common.validation.ValidPassword;
import com.ms.oauth.core.domain.customer.CustomerId;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Account Aggregate Root
 * 독립적으로 관리되는 계정 엔티티
 * 인증을 위한 password, 2FA, 비밀번호 만료 등 지원
 */
@Entity
@Table(name = "accounts", indexes = {
    @Index(name = "idx_account_customer_id", columnList = "customer_id"),
    @Index(name = "idx_account_email", columnList = "email")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @EmbeddedId
    private AccountId id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "customer_id", nullable = false))
    private CustomerId customerId;

    @ValidName
    @Column(nullable = false, length = 100)
    private String accountName;

    @ValidEmail
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccountStatus status;

    /**
     * 2차 인증 활성화 여부
     */
    @Column(nullable = false)
    private boolean twoFactorEnabled;

    /**
     * 마지막 2차 인증 검증 시각
     * 2차 인증 패스 기간 계산에 사용
     */
    private LocalDateTime lastTwoFactorVerifiedAt;

    /**
     * 비밀번호 마지막 변경 시각
     * 비밀번호 만료 검증에 사용
     */
    @Column(nullable = false)
    private LocalDateTime passwordChangedAt;

    /**
     * 로그인 실패 횟수
     */
    @Column(nullable = false)
    private int failedLoginAttempts;

    /**
     * 계정 잠금 시각
     */
    private LocalDateTime lockedAt;

    /**
     * 접근 가능한 Client 목록
     * 이 Account가 로그인할 수 있는 Client들
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "account_accessible_clients", joinColumns = @JoinColumn(name = "account_id"))
    @Column(name = "client_id")
    private Set<String> accessibleClientIds = new HashSet<>();

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime lastLoginAt;

    private Account(AccountId id, CustomerId customerId, String accountName, String email, String password) {
        this.id = id;
        this.customerId = customerId;
        this.accountName = accountName;
        this.email = email;
        this.password = password;
        this.status = AccountStatus.ACTIVE;
        this.twoFactorEnabled = false;
        this.passwordChangedAt = LocalDateTime.now();
        this.failedLoginAttempts = 0;
        this.createdAt = LocalDateTime.now();
    }

    public static Account create(CustomerId customerId, String accountName, String email, String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or blank");
        }
        return new Account(AccountId.generate(), customerId, accountName, email, password);
    }

    public void updateAccountName(@ValidName String accountName) {
        this.accountName = accountName;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateEmail(@ValidEmail String email) {
        this.email = email;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * 비밀번호 변경 (이미 암호화된 비밀번호를 받음)
     */
    public void changePassword(String encodedPassword) {
        this.password = encodedPassword;
        this.passwordChangedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * 비밀번호 검증 (인코딩된 비밀번호와 비교)
     */
    public boolean matchesPassword(String encodedPassword) {
        return this.password.equals(encodedPassword);
    }

    /**
     * 비밀번호 만료 여부 확인
     */
    public boolean isPasswordExpired(int passwordExpiryDays) {
        if (passwordExpiryDays <= 0) {
            return false; // 만료 정책이 없음
        }
        LocalDateTime expiryDate = this.passwordChangedAt.plusDays(passwordExpiryDays);
        return LocalDateTime.now().isAfter(expiryDate);
    }

    /**
     * 2차 인증 활성화
     */
    public void enableTwoFactor() {
        this.twoFactorEnabled = true;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * 2차 인증 비활성화
     */
    public void disableTwoFactor() {
        this.twoFactorEnabled = false;
        this.lastTwoFactorVerifiedAt = null;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * 2차 인증 검증 완료 기록
     */
    public void recordTwoFactorVerification() {
        this.lastTwoFactorVerifiedAt = LocalDateTime.now();
    }

    /**
     * 2차 인증 필요 여부 확인
     * 2차 인증이 활성화되어 있고, 마지막 검증 이후 특정 시간이 지났는지 확인
     */
    public boolean requiresTwoFactor(int twoFactorPassPeriodMinutes) {
        if (!this.twoFactorEnabled) {
            return false;
        }

        if (this.lastTwoFactorVerifiedAt == null) {
            return true; // 한번도 검증한 적 없음
        }

        if (twoFactorPassPeriodMinutes <= 0) {
            return true; // 패스 기간이 없으면 항상 필요
        }

        LocalDateTime passUntil = this.lastTwoFactorVerifiedAt.plusMinutes(twoFactorPassPeriodMinutes);
        return LocalDateTime.now().isAfter(passUntil);
    }

    /**
     * 로그인 성공 시 호출
     */
    public void recordLoginSuccess() {
        this.lastLoginAt = LocalDateTime.now();
        this.failedLoginAttempts = 0;
        this.lockedAt = null;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * 로그인 실패 기록
     */
    public void recordLoginFailure() {
        this.failedLoginAttempts++;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * 계정 잠금
     */
    public void lock() {
        this.status = AccountStatus.LOCKED;
        this.lockedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * 계정 잠금 해제
     */
    public void unlock() {
        if (this.status != AccountStatus.LOCKED) {
            throw new IllegalStateException("Account is not locked");
        }
        this.status = AccountStatus.ACTIVE;
        this.failedLoginAttempts = 0;
        this.lockedAt = null;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * 계정 잠금 여부 확인
     */
    public boolean isLocked() {
        return this.status == AccountStatus.LOCKED;
    }

    /**
     * 로그인 실패 횟수 기반 잠금 필요 여부
     */
    public boolean shouldBeLocked(int maxFailedAttempts) {
        return this.failedLoginAttempts >= maxFailedAttempts;
    }

    public void activate() {
        if (this.status == AccountStatus.ACTIVE) {
            throw new IllegalStateException("Account is already active");
        }
        this.status = AccountStatus.ACTIVE;
        this.updatedAt = LocalDateTime.now();
    }

    public void deactivate() {
        if (this.status == AccountStatus.INACTIVE) {
            throw new IllegalStateException("Account is already inactive");
        }
        this.status = AccountStatus.INACTIVE;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isActive() {
        return this.status == AccountStatus.ACTIVE;
    }
}

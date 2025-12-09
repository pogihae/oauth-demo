package com.ms.oauth.core.domain.scope;

import com.ms.oauth.core.common.jpa.BaseEntity;
import com.ms.oauth.core.domain.client.Client;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Table(uniqueConstraints = {

})
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Scope extends BaseEntity {

    public static final String SCOPE_SEPARATOR = ".";

    @Column(nullable = false)
    private String key;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScopeType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Scope parent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    private List<Scope> children = new ArrayList<>();

    public boolean isRoot() {
        return parent == null;
    }

    public String getScopeValue() {
        if (parent == null) {
            return key + SCOPE_SEPARATOR + type.getSuffix();
        }
        return parent.getScopeValue() + SCOPE_SEPARATOR + key + SCOPE_SEPARATOR + type.getSuffix();
    }
}

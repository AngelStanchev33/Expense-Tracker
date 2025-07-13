package com.angelstanchev.expense_tracker.model.user;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

@Getter
public class ExpenseTrackerUser extends User {

    private Long id;

    public ExpenseTrackerUser(
            Long id,
            String username,
            String password,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;

    }

}

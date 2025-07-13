package com.angelstanchev.expense_tracker.service.impl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.angelstanchev.expense_tracker.model.entity.UserEntity;
import com.angelstanchev.expense_tracker.model.entity.UserRoleEntity;
import com.angelstanchev.expense_tracker.model.user.ExpenseTrackerUser;
import com.angelstanchev.expense_tracker.repository.UserRepository;

@Service
public class ExpenseTrackerDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public ExpenseTrackerDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        return userRepository.findByEmail(email).map(this::map)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User with email " + email + " not found!"));
    }

    public UserDetails map(UserEntity user) {

        return new ExpenseTrackerUser(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream().map(this::map).toList());
    }

    public GrantedAuthority map(UserRoleEntity role) {
        return new SimpleGrantedAuthority("ROLE_" + role.getRoleName().name());
    }

}

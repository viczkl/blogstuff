package com.rmpader.security.util;

import com.rmpader.security.data.model.UserAuthentication;
import com.rmpader.security.data.model.UserAuthority;
import com.rmpader.security.data.model.UserProfile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author RMPader
 */
public class UserDetailsImpl implements UserDetails {

    private List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    private UserAuthentication userAuthentication;

    private UserDetailsImpl() {
        UserProfile anonymousProfile = new UserProfile("anonymous", null, -1, null);
        this.userAuthentication = new UserAuthentication(anonymousProfile, "");
    }

    public static UserDetailsImpl anonymous() {
        return new UserDetailsImpl();
    }

    public UserDetailsImpl(UserAuthentication userAuthentication) {
        this.userAuthentication = userAuthentication;

        for(UserAuthority authority :userAuthentication.getUserProfile()
                                .getAuthorities()){
            authorities.add(new SimpleGrantedAuthority(authority.getId().getAuthority()));
        }

    }

    public UserAuthentication getUserAuthentication() {
        return userAuthentication;
    }

    public UserProfile getUserProfile() {
        return userAuthentication.getUserProfile();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.unmodifiableList(authorities);
    }

    @Override
    public String getPassword() {
        return userAuthentication.getPassword();
    }

    @Override
    public String getUsername() {
        return userAuthentication.getUserProfile()
                                 .getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

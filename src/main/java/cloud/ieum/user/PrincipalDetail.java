package cloud.ieum.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.lang.reflect.Member;
import java.util.*;

public class PrincipalDetail implements UserDetails, OAuth2User {
    private User user;
    private Collection<? extends GrantedAuthority> authorities;

    private Map<String, Object> attributes;
    public PrincipalDetail(User user, Collection<? extends GrantedAuthority> authorities){
        this.user = user;
        this.authorities = authorities;

    }


    public PrincipalDetail(User user) {
        this.user = user;
    }

    public PrincipalDetail(User user, Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes){
        this.user = user;
        this.authorities = authorities;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public User getUser() {
        return user;
    }

    public Integer getId() {return user.getId();}


    @Override
    public String getName() {
        return getUsername();
    }
}

package ru.kaawork.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kaawork.model.User;
import ru.kaawork.model.UserProfile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 18.06.17.
 */
@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private UserService userSevice;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String ssoId) throws UsernameNotFoundException {
        User user = userSevice.findBySSO(ssoId);
        logger.info("User: {}", user);
        if(user == null) {
            logger.info("User not found");
            throw new UsernameNotFoundException("Username not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getSsoId(),
                user.getPassword(), true, true ,true, true,
                getGrantedAuthorities(user));
    }

    private List<GrantedAuthority> getGrantedAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        for(UserProfile userProfile : user.getUserProfiles()) {
            logger.info("UserPofile: {}", userProfile);
            authorities.add(new SimpleGrantedAuthority("ROLE_" + userProfile.getType()));
        }
        logger.info("authorities: {} ", authorities);
        return authorities;
    }


}

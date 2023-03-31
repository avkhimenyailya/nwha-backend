package io.grayproject.nwha.api.security.util;

import io.grayproject.nwha.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Ilya Avkhimenya
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return userRepository
                .findUserByUsername(username)
                .map(UserDetailsImpl::new)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("User named '%s' not found", username)));
    }
}

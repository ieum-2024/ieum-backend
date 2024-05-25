package cloud.ieum.user.service;

import cloud.ieum.user.PrincipalDetail;
import cloud.ieum.user.User;
import cloud.ieum.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userEntity = userRepository.findByName(username);
        if(userEntity.isEmpty()) {
            return null;
        } else {
            return new PrincipalDetail(userEntity.get());
        }
    }
}
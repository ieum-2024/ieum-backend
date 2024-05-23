package cloud.ieum.user.repository;

import cloud.ieum.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);
    //Optional<User> findByEmail(String email);
    //Optional<User> findByRefreshToken(String refreshToken);
    Optional<User> findBySocialId(String socialId);
}

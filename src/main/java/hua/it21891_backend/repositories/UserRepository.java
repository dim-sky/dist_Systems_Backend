package hua.it21891_backend.repositories;

import hua.it21891_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    public Optional<User> findByUserName(final String username );
    public boolean existsByUserName( final String username );
    public boolean existsByEmail( final String email );
    public boolean existsByPassword( final String password );
}

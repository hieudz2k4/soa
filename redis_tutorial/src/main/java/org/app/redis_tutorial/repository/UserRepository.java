package org.app.redis_tutorial.repository;

import java.util.UUID;
import org.app.redis_tutorial.model.Account;
import org.app.redis_tutorial.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
}

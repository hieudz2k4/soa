package org.app.redis_tutorial.repository;

import java.util.Optional;
import java.util.UUID;
import org.app.redis_tutorial.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

  @Query("SELECT a FROM Account a ORDER BY a.account_id ASC LIMIT 1 OFFSET :index")
  Optional<Account> findByIndex(@Param("index") int index);
}

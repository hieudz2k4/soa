package org.app.redis_tutorial.repository;

import java.util.UUID;
import org.app.redis_tutorial.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

}

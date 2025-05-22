package uet.soa.pastebin.infrastructure.persistence.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import uet.soa.pastebin.infrastructure.persistence.model.JpaUserPaste;

public interface UserPasteRepository extends JpaRepository<JpaUserPaste, UUID> {

  List<JpaUserPaste> findByUserId(String userId);

  JpaUserPaste getJpaUserPasteByUserId(String userId);

  long getTotalViewByUserId(String userId);

  long countByUserId(String userId);
}

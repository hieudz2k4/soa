package uet.soa.pastebin.infrastructure.persistence.repository;

import java.util.List;
import java.util.UUID;
import javax.swing.text.html.Option;
import org.springframework.beans.factory.annotation.Autowired;
import uet.soa.pastebin.infrastructure.persistence.model.JpaUserPaste;
import uet.soa.pastebin.infrastructure.persistence.model.JpaPaste;
import java.util.Optional;


public class UserPasteRepositoryImpl implements UserPasteRepository {
  @Autowired
  private UserPasteRepository userPasteRepository;

  @Autowired
  private PasteJpaRepository pasteJpaRepository;

  @Override
  public long getTotalViewByUserId(String userId) {
    List<JpaUserPaste> userPastes = userPasteRepository.findByUserId(userId);

    return userPastes.stream()
        .mapToLong(userPaste -> {
          Optional<JpaPaste> pasteOpt = pasteJpaRepository.findByUrl(userPaste.getUrlPaste());
          return pasteOpt.map(JpaPaste::getViewCount).orElse(0L);
        })
        .sum();
  }

  @Override
  public long countByUserId(String userId) {
    return userPasteRepository.countByUserId(userId);
  }
}

package uet.soa.pastebin.infrastructure.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import uet.soa.pastebin.infrastructure.persistence.model.JpaUserPaste;
import uet.soa.pastebin.infrastructure.persistence.repository.UserPasteRepository;

@Service
@AllArgsConstructor
public class UserPasteService {

  private final UserPasteRepository userPasteRepository;

  public String getUrl(String url) {
    return url;
  }

  public Long getTotalViewsByUserId(String userId) {
    return null;
  }

  public Long getTotalPasteByUserId(String userId) {
    return null;
  }

  public void saveUserPaste(String url, String userId) {
    if (!userId.equals(null)) {
      JpaUserPaste jpaUserPaste = new JpaUserPaste();
      jpaUserPaste.setUrlPaste(url);
      jpaUserPaste.setUserId(userId);

      userPasteRepository.save(jpaUserPaste);
    }
  }
}
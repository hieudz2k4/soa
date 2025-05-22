package uet.soa.pastebin.infrastructure.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "user_paste")
public class JpaUserPaste {
  @Id
  @GeneratedValue
  UUID id;

  @Column(name = "user_id", nullable = false)
  String userId;

  @Column(name = "url_paste", nullable = false, unique = true)
  String urlPaste;
}

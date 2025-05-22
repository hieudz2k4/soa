package org.app.userservice.controller;

import org.app.userservice.dto.Paste;
import org.app.userservice.service.PasteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin("*")
public class UserPaste {
  @Autowired
  private PasteService pasteService;

  @GetMapping("/{userId}/pastes/count")
  public Mono<Long> getPasteCountByUserId(@PathVariable String userId) {
    return pasteService.countByUserId(userId);
  }

  @GetMapping("/{userId}/pastes/totalViews")
  public Mono<Long> getTotalViewsByUserId(@PathVariable String userId) {
    return pasteService.countTotalViewsByUserId(userId);
  }

  @GetMapping("/{userId}/pastes")
  public Flux<Paste> getPastesByUserId(@PathVariable String userId) {
    return pasteService.findByUserId(userId);
  }

  PostMapping("/{userId}/save-pastes")

}

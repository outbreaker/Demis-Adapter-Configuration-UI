package de.gematik.demis.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class IdentityProvider {

  private String username;
  private String authcertkeystore;
  private String authcertpassword;
  private String authcertalias;
}

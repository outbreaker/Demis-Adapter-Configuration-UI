package de.gematik.demis.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class ReportingFacility {

  private String bsnr;
  private String name;
  private String einrichtungsArt;
  private String ansprechspartnerNachname;
  private String ansprechspartnerVorname;
  private String anschriftenzeile;
  private String postleitzahl;
  private String stadt;
  private String telefonnummer;
  private String faxnummer;
  private String email;
  private String webseite;

  @JsonProperty("BSNR")
  public String getBsnr() {
    return bsnr;
  }

  @JsonProperty("BSNR")
  public void setBsnr(String bsnr) {
    this.bsnr = bsnr;
  }
}

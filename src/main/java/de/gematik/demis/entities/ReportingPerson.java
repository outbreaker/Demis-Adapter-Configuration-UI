package de.gematik.demis.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter @Setter
public class ReportingPerson {
    private String nachname;
    private String vorname;
    private String anschriftenzeile;
    private String postleitzahl;
    private String stadt;
    private String telefonnummer;
    private String erreichbarkeit;
}

package de.gematik.demis.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class Laboratory {
    private String identifikator;
    private String[] positiveTestergebnisBezeichnungen;
    private ReportingPerson melderPerson;
    private ReportingFacility melderEinrichtung;
    private IdentityProvider idp;
}

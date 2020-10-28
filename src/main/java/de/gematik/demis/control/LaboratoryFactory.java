package de.gematik.demis.control;

import de.gematik.demis.entities.IdentityProvider;
import de.gematik.demis.entities.LABORATORY_JSON;
import de.gematik.demis.entities.Laboratory;
import de.gematik.demis.entities.ReportingFacility;
import de.gematik.demis.entities.ReportingPerson;

public class LaboratoryFactory {

  public static Laboratory createDefaultLaboratory(){
    Laboratory laboratory = new Laboratory();
    laboratory.setPositiveTestergebnisBezeichnungen(LABORATORY_JSON.POSITIVE_TESTERGEBNIS_BEZEICHNUNGEN.getDefaultValue().split(","));
    laboratory.setMelderEinrichtung(createMelderEinrichtung());
    laboratory.setMelderPerson(createMelderPerson());
    laboratory.setIdp(createIdp());

    return laboratory;
  }

  private static IdentityProvider createIdp() {
    return new IdentityProvider();
  }

  private static ReportingPerson createMelderPerson() {
    ReportingPerson reportingPerson = new ReportingPerson();
    return reportingPerson;
  }

  private static ReportingFacility createMelderEinrichtung() {
    ReportingFacility reportingFacility = new ReportingFacility();
    return reportingFacility;
  }
}

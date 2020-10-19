package de.gematik.demis.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.gematik.demis.control.ConfigurationLoader;
import de.gematik.demis.entities.IdentityProvider;
import de.gematik.demis.entities.Laboratory;
import de.gematik.demis.entities.ReportingFacility;
import de.gematik.demis.entities.ReportingPerson;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;

@RunWith(JUnitPlatform.class)
public class LaboratoryViewTest {

    @Test
    void loadJson() throws IOException {
        URL resource = getClass().getClassLoader().getResource("Demis-Adapter");
        String absolutePath = new File(resource.getFile()).getAbsolutePath();
        Set<Path> paths = new ConfigurationLoader().listFilesUsingFileWalk(absolutePath, 10);
        Assert.assertEquals(3, paths.stream().filter(f -> f.toFile().getAbsolutePath().endsWith("json")).collect(Collectors.toSet()).size());
        paths.stream().filter(f -> f.toFile().getAbsolutePath().endsWith("json")).forEach(LaboratoryView::new);
    }

    @Test
    void createJSON() throws IOException {
        Laboratory laboratory = new Laboratory();
        laboratory.setIdentifikator("MyIdentificator");
        laboratory.setPositiveTestergebnisBezeichnungen(new String[]{"Postiv", "Schwach Positiv", "POSITIV"});

        ReportingPerson reportingPerson = new ReportingPerson();
        reportingPerson.setNachname("reportingPerson Nachname");
        reportingPerson.setVorname("reportingPerson Vorname");
        reportingPerson.setAnschriftenzeile("reportingPerson Anschrift");
        reportingPerson.setPostleitzahl("12345");
        reportingPerson.setStadt("reportingPerson STadt");
        reportingPerson.setTelefonnummer("546645465456645");
        reportingPerson.setErreichbarkeit("reportingPerson Mo-DO");

        laboratory.setMelderPerson(reportingPerson);

        ReportingFacility reportingFacility = new ReportingFacility();
        reportingFacility.setBsnr("reportingFacility bsnr");
        reportingFacility.setName("reportingFacility Name");
        reportingFacility.setEinrichtungsArt("reportingFacility Art");
        reportingFacility.setAnsprechspartnerNachname("reportingFacility Nachname");
        reportingFacility.setAnsprechspartnerVorname("reportingFacility Vorname");
        reportingFacility.setAnschriftenzeile("reportingFacility Anschrift");
        reportingFacility.setPostleitzahl("reportingFacility plz");
        reportingFacility.setStadt("reportingFacility Stadt");
        reportingFacility.setTelefonnummer("reportingFacility Telefonnummer");
        reportingFacility.setFaxnummer("reportingFacility Fax");
        reportingFacility.setEmail("reportingFacility email");
        reportingFacility.setWebseite("reportingFacility Webseite");

        laboratory.setMelderEinrichtung(reportingFacility);

        IdentityProvider idp = new IdentityProvider();
        idp.setUsername("idp username");
        idp.setAuthcertkeystore("idp keystore");
        idp.setAuthcertpassword("idp pw");
        idp.setAuthcertalias("idp alias");

        laboratory.setIdp(idp);

        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(System.getProperty("java.io.tmpdir") + File.separator + "Test.json");
        System.out.println("TEST FILE: " + file.getAbsolutePath());
        objectMapper.writeValue(file, laboratory);

        Laboratory laboratory1 = objectMapper.readValue(file, Laboratory.class);
        Assert.assertEquals(laboratory.getIdentifikator(), laboratory1.getIdentifikator());
        Assert.assertArrayEquals(laboratory.getPositiveTestergebnisBezeichnungen(), laboratory1.getPositiveTestergebnisBezeichnungen());
        Assert.assertEquals(laboratory.getMelderPerson().getAnschriftenzeile(), laboratory1.getMelderPerson().getAnschriftenzeile());
        Assert.assertEquals(laboratory.getMelderPerson().getErreichbarkeit(), laboratory1.getMelderPerson().getErreichbarkeit());
        Assert.assertEquals(laboratory.getMelderPerson().getNachname(), laboratory1.getMelderPerson().getNachname());
        Assert.assertEquals(laboratory.getMelderPerson().getPostleitzahl(), laboratory1.getMelderPerson().getPostleitzahl());
        Assert.assertEquals(laboratory.getMelderPerson().getStadt(), laboratory1.getMelderPerson().getStadt());
        Assert.assertEquals(laboratory.getMelderPerson().getTelefonnummer(), laboratory1.getMelderPerson().getTelefonnummer());
        Assert.assertEquals(laboratory.getMelderPerson().getVorname(), laboratory1.getMelderPerson().getVorname());
        Assert.assertEquals(laboratory.getMelderEinrichtung().getAnschriftenzeile(), laboratory1.getMelderEinrichtung().getAnschriftenzeile());
        Assert.assertEquals(laboratory.getMelderEinrichtung().getAnsprechspartnerNachname(), laboratory1.getMelderEinrichtung().getAnsprechspartnerNachname());
        Assert.assertEquals(laboratory.getMelderEinrichtung().getAnsprechspartnerVorname(), laboratory1.getMelderEinrichtung().getAnsprechspartnerVorname());
        Assert.assertEquals(laboratory.getMelderEinrichtung().getBsnr(), laboratory1.getMelderEinrichtung().getBsnr());
        Assert.assertEquals(laboratory.getMelderEinrichtung().getEinrichtungsArt(), laboratory1.getMelderEinrichtung().getEinrichtungsArt());
        Assert.assertEquals(laboratory.getMelderEinrichtung().getEmail(), laboratory1.getMelderEinrichtung().getEmail());
        Assert.assertEquals(laboratory.getMelderEinrichtung().getFaxnummer(), laboratory1.getMelderEinrichtung().getFaxnummer());
        Assert.assertEquals(laboratory.getMelderEinrichtung().getName(), laboratory1.getMelderEinrichtung().getName());
        Assert.assertEquals(laboratory.getMelderEinrichtung().getPostleitzahl(), laboratory1.getMelderEinrichtung().getPostleitzahl());
        Assert.assertEquals(laboratory.getMelderEinrichtung().getStadt(), laboratory1.getMelderEinrichtung().getStadt());
        Assert.assertEquals(laboratory.getMelderEinrichtung().getTelefonnummer(), laboratory1.getMelderEinrichtung().getTelefonnummer());
        Assert.assertEquals(laboratory.getMelderEinrichtung().getWebseite(), laboratory1.getMelderEinrichtung().getWebseite());
        Assert.assertEquals(laboratory.getIdp().getAuthcertalias(), laboratory1.getIdp().getAuthcertalias());
        Assert.assertEquals(laboratory.getIdp().getAuthcertkeystore(), laboratory1.getIdp().getAuthcertkeystore());
        Assert.assertEquals(laboratory.getIdp().getAuthcertpassword(), laboratory1.getIdp().getAuthcertpassword());
        Assert.assertEquals(laboratory.getIdp().getUsername(), laboratory1.getIdp().getUsername());
        file.delete();

    }
}
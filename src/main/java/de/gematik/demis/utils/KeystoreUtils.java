package de.gematik.demis.utils;

import de.gematik.demis.entities.IdentityProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.CertificateException;

public class KeystoreUtils {

  private static Logger LOG = LoggerFactory.getLogger(KeystoreUtils.class.getName());
  private IdentityProvider idp;
  private File keystore;
  private String password;

  public KeystoreUtils(File keystore, String password) {
    this.idp = new IdentityProvider();
    this.keystore = keystore;
    this.password = password;

    this.checkValidity();
  }

  public IdentityProvider loadIdpProperties() {
    LOG.debug("Loaded Keystore: " + keystore.getName());
    String keystoreName = keystore.getName();
    String authCertAlias = extractAuthCertAlias(keystoreName);
    String username = extractUsername(authCertAlias);
    LOG.debug("Set ipdProperties: AuthCertName=" + authCertAlias + "; Username=" + username);
    idp.setAuthcertkeystore(keystore.getAbsolutePath());
    idp.setUsername(username);
    idp.setAuthcertalias(authCertAlias);
    idp.setAuthcertpassword(password);
    return idp;
  }

  private boolean checkValidity() {
    boolean valid = false;

    try (FileInputStream fis = new FileInputStream(keystore)) {
      KeyStore p12 = KeyStore.getInstance(KeyStore.getDefaultType());
      p12.load(fis, password.toCharArray());
      valid = true;

      // TODO ExceptionHandling
    } catch (KeyStoreException e) {
      LOG.debug("Keystore Exception!");
    } catch (CertificateException e) {
      LOG.debug("Certificate could not be loaded");
    } catch (IOException e) {
       // Cause UnrecoverableKeyException --> falsches PW
        LOG.debug("Wrong Password!");
    }
    catch (Exception e) {
    }

    return valid;
  }

  private String extractAuthCertAlias(String keystorename) {
    LOG.debug("Extract AuthCertName");
    String authCertAlias = keystorename.split("_")[0];
    authCertAlias = authCertAlias.toLowerCase();
    return authCertAlias;
  }

  private String extractUsername(String authCertName) {
    LOG.debug("Extract Username");
    String username = authCertName.split("-")[1];
    return username;
  }
}

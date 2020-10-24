package de.gematik.demis.utils;

import de.gematik.demis.entities.IdentityProvider;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeystoreUtils {

  private static Logger LOG = LoggerFactory.getLogger(KeystoreUtils.class.getName());
  private IdentityProvider idp;
  private File keystoreFile;
  private String password;
  private KeyStore keystore;

  public KeystoreUtils(File keystore, String password) throws UnrecoverableKeyException {
    this.idp = new IdentityProvider();
    this.keystoreFile = keystore;
    this.password = password;

    this.checkValidity();
  }

  public IdentityProvider loadIdpProperties() throws KeyStoreException {
    LOG.debug("Loaded Keystore: " + keystoreFile.getName());
    String keystoreName = keystoreFile.getName();
    String authCertAlias = extractAuthCertAlias(keystoreName);
    String username = extractUsername(authCertAlias);
    LOG.debug("Set ipdProperties: AuthCertName=" + authCertAlias + "; Username=" + username);
    idp.setAuthcertkeystore(keystoreFile.getAbsolutePath());
    idp.setUsername(username);
    idp.setAuthcertalias(authCertAlias);
    idp.setAuthcertpassword(password);
    return idp;
  }

  private boolean checkValidity() throws UnrecoverableKeyException {
    boolean valid = false;
    var messages = ResourceBundle.getBundle("MessagesBundle", Locale.getDefault());

    try (FileInputStream fis = new FileInputStream(keystoreFile)) {
      keystore = KeyStore.getInstance(KeyStore.getDefaultType());
      keystore.load(fis, password.toCharArray());
      valid = true;
    } catch (KeyStoreException | NoSuchAlgorithmException e) {
      LOG.debug("Keystore Exception!");
      throw new UnrecoverableKeyException(
          messages.getString("LOAD_KEYSTORE_PASSWORD_ERROR").replace("XX_ERROR_XX", e.getMessage() == null ? "--" : e.getMessage()));
    } catch (CertificateException e) {
      LOG.debug("Certificate could not be loaded");
      throw new UnrecoverableKeyException(
          messages.getString("LOAD_KEYSTORE_CERT_ERROR").replace("XX_ERROR_XX", e.getMessage() == null ? "--" : e.getMessage()));
    } catch (IOException e) {
      LOG.debug(String.valueOf(e.getCause()));
      LOG.debug("Wrong Password!");
      throw new UnrecoverableKeyException(
          messages.getString("LOAD_KEYSTORE_PASSWORD_ERROR").replace("XX_ERROR_XX", e.getMessage() == null ? "--" : e.getMessage()));
    }
    return valid;
  }

  private String extractAuthCertAlias(String keystorename) throws KeyStoreException {
    LOG.debug("Extract AuthCertName");
    String authCertAlias = keystorename.split("_")[0];
    Enumeration<String> enumeration = keystore.aliases();
    authCertAlias = enumeration.nextElement();
    if (enumeration.hasMoreElements()) {
      throw new KeyStoreException("Keystore Exception!");
    }
    return authCertAlias;
  }

  private String extractUsername(String authCertName) {
    LOG.debug("Extract Username");
    String username = "";
    if (authCertName.contains("demis-")) {
      username = authCertName.substring(6);
    }
    if (authCertName.contains("lab-")) {
      username = authCertName.substring(4);
    }
    return username;
  }
}

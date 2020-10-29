package de.gematik.demis.ui;

import de.gematik.demis.control.ConfigurationLoader;
import de.gematik.demis.entities.IdentityProvider;
import de.gematik.demis.entities.LABORATORY_JSON;
import de.gematik.demis.ui.value.editor.IValueTypeView;
import de.gematik.demis.ui.value.editor.PasswordEditor;
import de.gematik.demis.ui.value.editor.StringEditor;
import de.gematik.demis.utils.ImageUtils;
import de.gematik.demis.utils.KeystoreUtils;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.io.File;
import java.security.KeyStoreException;
import java.security.UnrecoverableKeyException;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IdentityProviderView extends AbstractEditorsView {

  private static Logger LOG = LoggerFactory.getLogger(IdentityProviderView.class.getName());
  private static String lastPath;
  private IdentityProvider identityProvider;

  public IdentityProviderView(IdentityProvider identityProvider) {
    this.identityProvider = identityProvider;
    initComponents();
  }

  private void initComponents() {
    setLayout(new GridBagLayout());
    this.setBorder(new TitledBorder(LABORATORY_JSON.IDP.getDisplayName()));
    GridBagConstraints c = new GridBagConstraints();
    c.gridy = 0;

    addLabel(c, LABORATORY_JSON.LOAD_DATA);

    JButton loadJB = new JButton(ImageUtils.loadResizeImage("OPEN_FILE", 20));
    loadJB.addActionListener(actionEvent -> selectCertificateStore());

    c.gridx = 1;
    c.weightx = 1.0;
    this.add(loadJB, c);
    c.gridy++;

    addLabel(c, LABORATORY_JSON.USERNAME);
    addEditor(new StringEditor(identityProvider.getUsername()), c, LABORATORY_JSON.USERNAME);
    c.gridy++;

    addLabel(c, LABORATORY_JSON.AUTHCERTKEYSTORE);
    addEditor(
        new StringEditor(identityProvider.getAuthcertkeystore()),
        c,
        LABORATORY_JSON.AUTHCERTKEYSTORE);
    c.gridy++;

    addLabel(c, LABORATORY_JSON.AUTHCERTPASSWORD);
    addEditor(
        new PasswordEditor(identityProvider.getAuthcertpassword()),
        c,
        LABORATORY_JSON.AUTHCERTPASSWORD);
    c.gridy++;

    addLabel(c, LABORATORY_JSON.AUTHCERTALIAS);
    addEditor(
        new StringEditor(identityProvider.getAuthcertalias()), c, LABORATORY_JSON.AUTHCERTALIAS);
    c.gridy++;

    this.repaint();
  }

  private void addEditor(IValueTypeView editor, GridBagConstraints c, LABORATORY_JSON id) {
    c.gridx = 1;
    c.weightx = 1.0;
    this.add(editor.getViewComponent(), c);
    addAndConfigEditor(editor, id);
  }

  private void addLabel(GridBagConstraints c, LABORATORY_JSON value) {
    c.weighty = 0.1;
    c.fill = GridBagConstraints.BOTH;
    c.gridx = 0;
    c.insets = new Insets(0, 10, 0, 10); // top padding
    c.anchor = GridBagConstraints.LAST_LINE_START;
    c.weightx = 0;
    JLabel label =  new JLabel(value.getDisplayName());
    label.setToolTipText(value.getToolTip());
    this.add(label, c);
  }

  private void selectCertificateStore() {
    var messages = ResourceBundle.getBundle("MessagesBundle", Locale.getDefault());
    JFileChooser jFileChooser = new JFileChooser(lastPath == null? ConfigurationLoader.getInstance().getPathToMainFolder().toFile().getAbsolutePath():lastPath);
    jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    jFileChooser.setFileFilter(
        new FileFilter() {
          @Override
          public boolean accept(File file) {
            return file.getName().toLowerCase().endsWith("jks")
                || file.getName().endsWith("p12")
                || file.isDirectory();
          }

          @Override
          public String getDescription() {
            return messages.getString("LOAD_CERT_DESCRIPTION");
          }
        });
    int opt = jFileChooser.showOpenDialog(MainView.getInstance().getMainComponent());
    if (opt == JFileChooser.APPROVE_OPTION) {
      File selectedFile = jFileChooser.getSelectedFile();
      lastPath = selectedFile.getAbsolutePath();

      String password = JOptionPane.showInputDialog(messages.getString("KEYSTORE_SMS_PASSWORD"));
      IdentityProvider idp = null;
      if (password == null || password.isEmpty()) {
        showWarningDialog(messages.getString("LOAD_KEYSTORE_PASSWORD_EMPTY"));
      } else {
        try {
          idp = new KeystoreUtils(selectedFile, password).loadIdpProperties();
        } catch (UnrecoverableKeyException e) {
          JOptionPane.showMessageDialog(
              MainView.getInstance().getMainComponent(),
              messages
                  .getString("LOAD_KEYSTORE_PASSWORD_ERROR_SHORT")
                  .replace("XX_ERROR_XX", e.getMessage() == null ? "--" : e.getMessage()),
              "Error",
              JOptionPane.ERROR_MESSAGE);
        } catch (KeyStoreException e) {
          JOptionPane.showMessageDialog(
              MainView.getInstance().getMainComponent(),
              messages
                  .getString("LOAD_KEYSTORE_ERROR")
                  .replace("XX_ERROR_XX", e.getMessage() == null ? "--" : e.getMessage()),
              "Error",
              JOptionPane.ERROR_MESSAGE);
        }
      }
      this.repaint(idp);
    }
  }

  private void showWarningDialog(String msg) {
    JOptionPane.showMessageDialog(this, msg, "Warning", JOptionPane.WARNING_MESSAGE);
  }

  private void repaint(IdentityProvider idp) {
    if (idp != null) {
      getValueEditors().get(LABORATORY_JSON.USERNAME).setValue(idp.getUsername());
      getValueEditors().get(LABORATORY_JSON.AUTHCERTALIAS).setValue(idp.getAuthcertalias());
      getValueEditors().get(LABORATORY_JSON.AUTHCERTKEYSTORE).setValue(idp.getAuthcertkeystore());
      getValueEditors().get(LABORATORY_JSON.AUTHCERTPASSWORD).setValue(idp.getAuthcertpassword());
    } else {
      getValueEditors().get(LABORATORY_JSON.USERNAME).setValue("");
      getValueEditors().get(LABORATORY_JSON.AUTHCERTALIAS).setValue("");
      getValueEditors().get(LABORATORY_JSON.AUTHCERTKEYSTORE).setValue("");
      getValueEditors().get(LABORATORY_JSON.AUTHCERTPASSWORD).setValue("");
    }
    setUnsaved();
    this.repaint();
  }

  public IdentityProvider getIdentityProvider() {
    identityProvider.setAuthcertalias(getValueEditors().get(LABORATORY_JSON.AUTHCERTALIAS).getValue());
    identityProvider.setAuthcertpassword(getValueEditors().get(LABORATORY_JSON.AUTHCERTPASSWORD).getValue());
    identityProvider.setAuthcertkeystore(getValueEditors().get(LABORATORY_JSON.AUTHCERTKEYSTORE).getValue());
    identityProvider.setUsername(getValueEditors().get(LABORATORY_JSON.USERNAME).getValue());
    return identityProvider;
  }

  public void checkExpertMode() {
    getValueEditors().values().forEach(IValueTypeView::checkExpertMode);
  }
  ;

  public void activateForExperts() {
    getValueEditors().values().forEach(IValueTypeView::activateForExperts);
  }

  public void setJsonValue(LABORATORY_JSON property, String value) {
    if (getValueEditors().containsKey(property)) getValueEditors().get(property).setValue(value);
  }

  public boolean contains(LABORATORY_JSON property) {
    if (getValueEditors().containsKey(property)) return true;
    return false;
  }

  void openLoadCertificate() {
    selectCertificateStore();
  }
}

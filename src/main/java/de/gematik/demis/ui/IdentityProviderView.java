package de.gematik.demis.ui;

import de.gematik.demis.entities.IdentityProvider;
import de.gematik.demis.ui.value.editor.IValueTypeView;
import de.gematik.demis.ui.value.editor.PasswordEditor;
import de.gematik.demis.ui.value.editor.StringEditor;
import de.gematik.demis.utils.ImageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

public class IdentityProviderView extends JPanel {
    private static Logger LOG = LoggerFactory.getLogger(IdentityProviderView.class.getName());
    private final HashMap<String, IValueTypeView> values = new HashMap<>();
    private static String lastPath;
    private JTextField username;
    private IdentityProvider idp;
    public IdentityProviderView(IdentityProvider identityProvider) {
        initComponents(identityProvider);
    }


    private void initComponents(IdentityProvider identityProvider) {
        idp = identityProvider;
        setLayout(new GridBagLayout());
        this.setBorder(new TitledBorder("Identity Provider"));
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;

        addLabel(c, new Label("Daten laden"));
        JButton loadJB = new JButton(ImageUtils.loadResizeImage("open-file-icon",20));
        loadJB.addActionListener(actionEvent -> selectFolder());

        c.gridx = 1;
        c.weightx = 1.0;
        this.add(loadJB, c);
        c.gridy++;

        addLabel(c, new Label("Username"));
        addEditor(new StringEditor(identityProvider.getUsername()), c, "Username");
        c.gridy++;

        addLabel(c, new Label("Authcert Keystore"));
        addEditor(new StringEditor(identityProvider.getAuthcertkeystore()), c, "Authcert Keystore");
        c.gridy++;

        addLabel(c, new Label("Authcert Keystore Password"));
        addEditor(new PasswordEditor(identityProvider.getAuthcertpassword()), c, "Authcert Keystore Password");
        c.gridy++;

        addLabel(c, new Label("Authcert Alias"));
        addEditor(new StringEditor(identityProvider.getAuthcertalias()), c, "Authcert Alias");
        c.gridy++;

        this.repaint();
    }

    private void addEditor(IValueTypeView editor, GridBagConstraints c, String id) {
        c.gridx = 1;
        c.weightx = 1.0;
        this.add(editor.getViewComponent(), c);
        values.put(id, editor);
    }

    private void addLabel(GridBagConstraints c, Label label) {
        c.weighty = 0.1;
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.insets = new Insets(0, 10, 0, 10);  //top padding
        c.anchor = GridBagConstraints.LAST_LINE_START;
        c.weightx = 0;
        this.add(label, c);
    }

    private void selectFolder(){
        var messages = ResourceBundle.getBundle("MessagesBundle", Locale.getDefault());
        JFileChooser jFileChooser = new JFileChooser(lastPath);
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jFileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().toLowerCase().endsWith("jks") || file.getName().endsWith("p12") || file.isDirectory();
            }

            @Override
            public String getDescription() {
                return messages.getString("LOAD_CERT_DESCRIPTION");
            }
        });
        int opt = jFileChooser.showOpenDialog(MainView.getInstance().getMainComponent());
        if (opt == JFileChooser.APPROVE_OPTION) {
            File folderToLoad = jFileChooser.getSelectedFile();
            lastPath = folderToLoad.getAbsolutePath();
            //TODO load P12, JKS
            setIdpProperties(folderToLoad);
        }
    }

    private void setIdpProperties(File keystore) {
        LOG.debug("Loaded Keystore: " + keystore.getName());
        String keystoreName = keystore.getName();
        String authCertAlias = extractAuthCertAlias(keystoreName);
        String username = extractUsername(authCertAlias);
        LOG.debug("Set ipdProperties: AuthCertName=" + authCertAlias + "; Username=" + username);
        idp.setAuthcertkeystore(keystore.getAbsolutePath());
        idp.setUsername(username);
        idp.setAuthcertalias(authCertAlias);

        values.get("Username").setValue(username);
        values.get("Authcert Alias").setValue(authCertAlias);
        values.get("Authcert Keystore").setValue(keystore.getAbsolutePath());

        this.repaint();
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

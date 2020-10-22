package de.gematik.demis.ui;

import de.gematik.demis.entities.IdentityProvider;
import de.gematik.demis.entities.LABORATORY_JSON;
import de.gematik.demis.entities.ReportingPerson;
import de.gematik.demis.entities.VALUE_TYPE;
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
    private final HashMap<LABORATORY_JSON, IValueTypeView> values = new HashMap<>();
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

        addLabel(c, new Label(LABORATORY_JSON.LOAD_DATA.getDisplayName()));
        JButton loadJB = new JButton(ImageUtils.loadResizeImage("open-file-icon",20));
        loadJB.addActionListener(actionEvent -> selectFolder());

        c.gridx = 1;
        c.weightx = 1.0;
        this.add(loadJB, c);
        c.gridy++;

        addLabel(c, new Label(LABORATORY_JSON.USERNAME.getDisplayName()));
        addEditor(new StringEditor(identityProvider.getUsername()), c, LABORATORY_JSON.USERNAME);
        c.gridy++;

        addLabel(c, new Label(LABORATORY_JSON.AUTHCERTKEYSTORE.getDisplayName()));
        addEditor(new StringEditor(identityProvider.getAuthcertkeystore()), c, LABORATORY_JSON.AUTHCERTKEYSTORE);
        c.gridy++;

        addLabel(c, new Label(LABORATORY_JSON.AUTHCERTPASSWORD.getDisplayName()));
        addEditor(new PasswordEditor(identityProvider.getAuthcertpassword()), c, LABORATORY_JSON.AUTHCERTPASSWORD);
        c.gridy++;

        addLabel(c, new Label(LABORATORY_JSON.AUTHCERTALIAS.getDisplayName()));
        addEditor(new StringEditor(identityProvider.getAuthcertalias()), c, LABORATORY_JSON.AUTHCERTALIAS);
        c.gridy++;

        this.repaint();
    }

    private void addEditor(IValueTypeView editor, GridBagConstraints c, LABORATORY_JSON id) {
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
        }
    }

    public IdentityProvider getIdentityProvider() {
        identityProvider.setAuthcertalias(values.get(LABORATORY_JSON.AUTHCERTALIAS).getValue());
        identityProvider.setAuthcertpassword(values.get(LABORATORY_JSON.AUTHCERTPASSWORD).getValue());
        identityProvider.setAuthcertkeystore(values.get(LABORATORY_JSON.AUTHCERTKEYSTORE).getValue());
        identityProvider.setUsername(values.get(LABORATORY_JSON.USERNAME).getValue());
        return identityProvider;
    }
}

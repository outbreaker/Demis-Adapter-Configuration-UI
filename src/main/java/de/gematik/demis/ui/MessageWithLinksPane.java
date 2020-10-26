package de.gematik.demis.ui;

import java.io.IOException;
import java.net.URI;
import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageWithLinksPane extends JEditorPane {

  private static final long serialVersionUID = 1L;
  private static Logger LOG = LoggerFactory.getLogger(MessageWithLinksPane.class.getName());

  public MessageWithLinksPane(String htmlBody) {
    super("text/html", "<html><body>" + htmlBody + "</body></html>");
    addHyperlinkListener(new HyperlinkListener() {
      @Override
      public void hyperlinkUpdate(HyperlinkEvent e) {
        if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) {
          try {
            java.awt.Desktop.getDesktop().browse(URI.create(e.getURL().toString()));
          } catch (IOException ioException) {
            LOG.debug("Error while opening link!");
          }
        }
      }
    });
    setEditable(false);
    setBorder(null);
  }
}

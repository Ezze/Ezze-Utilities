package org.ezze.utils.listeners;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JDialog;
import javax.swing.JLabel;

/**
 * Mouse listener for web links.
 *
 * @author Dmitriy Pushkov
 * @version 1.0.0
 */
public class LinkMouseListener extends MouseAdapter {

    /**
     * URL to follow on click.
     */
    private String url = null;

    /**
     * {@link javax.swing.JDialog} instance to change mouse pointer icon.
     */
    private JDialog dialog = null;

    /**
     * Link's normal color.
     */
    private Color linkColorNormal = new Color(118, 36, 14);

    /**
     * Link's hover color.
     */
    private Color linkColorHover = new Color(205, 91, 60);

    /**
     * Link mouse listener's constructor.
     *
     * @param url
     *      Web link
     * @param dialog
     *      Dialog instance to have an ability to change cursor on hover
     */
    public LinkMouseListener(String url, JDialog dialog) {

        this(url, dialog, null, null);
    }

    /**
     * Link mouse listener's constructor.
     *
     * @param url
     *      Web link
     * @param dialog
     *      Dialog instance to have an ability to change cursor on hover
     * @param linkColorNormal
     *      Normal link's color
     * @param linkColorHover
     *      Hover link's color
     */
    public LinkMouseListener(String url, JDialog dialog, Color linkColorNormal, Color linkColorHover) {

        this.url = url;
        this.dialog = dialog;
        setLinkColors(linkColorNormal, linkColorHover);
    }

    /**
     * Sets normal color for a link.
     *
     * @param color
     *      Normal link's color
     */
    public void setNormalLinkColor(Color color) {

        if (color != null)
            this.linkColorNormal = color;
    }

    /**
     * Sets hover color for a link.
     *
     * @param color
     *      Hover link's color
     */
    public void setHoverLinkColor(Color color) {

        if (color != null)
            this.linkColorHover = color;
    }

    /**
     * Sets normal and hover color for a link.
     *
     * @param linkColorNormal
     *      Normal link's color
     * @param linkColorHover
     *      Hover link's color
     */
    public final void setLinkColors(Color linkColorNormal, Color linkColorHover) {

        setNormalLinkColor(linkColorNormal);
        setHoverLinkColor(linkColorHover);
    }

    /**
     * Retrieves a color of normal link.
     *
     * @return
     *      Normal link's color
     */
    public Color getNormalLinkColor() {

        return linkColorNormal;
    }

    /**
     * Retrieves a color of hover link.
     *
     * @return
     *      Hover link's color
     */
    public Color getHoverLinkColor() {

        return linkColorHover;
    }

    @Override
    public void mousePressed(MouseEvent e) {

        try {
            
            if (url != null && Desktop.isDesktopSupported())
                Desktop.getDesktop().browse(new URI(url));
            else {
                
                String operatingSystem = System.getProperty("os.name");
                if (!operatingSystem.matches("^.*Windows.*$") && !operatingSystem.matches("^.*Mac OS.*$")) {
                    
                    Runtime.getRuntime().exec(String.format("x-www-browser %s", url));
                }
            }
        }
        catch (URISyntaxException ex) {

        }
        catch (IOException ex) {

        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

        if (dialog != null)
            dialog.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Object source = e.getSource();
        if (source instanceof JLabel) {

            JLabel label = (JLabel)source;
            label.setForeground(linkColorHover);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

        if (dialog != null)
            dialog.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

        Object source = e.getSource();
        if (source instanceof JLabel) {

            JLabel label = (JLabel)source;
            label.setForeground(linkColorNormal);
        }
    }
}
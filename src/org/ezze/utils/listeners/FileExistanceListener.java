package org.ezze.utils.listeners;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

/**
 * Implements {@link javax.swing.event.DocumentListener} and checks file's or directory's
 * existance specified by {@link javax.swing.JTextField} and colors this text field's
 * text corresponding to the check result.
 *
 * It's also possible to disable some {@link javax.swing.JComponent} instances
 * in the case of file or directory doesn't exist by passing them as {@link java.util.ArrayList}
 * to constructor {@link #FileExistanceListener(javax.swing.JTextField, boolean, java.util.ArrayList)}
 * or adding them separately using {@link #addBlockedComponent(javax.swing.JComponent)} method.
 *
 * @author Dmitriy Pushkov
 * @version 0.0.1
 */
public class FileExistanceListener implements DocumentListener {

    /**
     * Text field that stores file's or directory's path and
     * this {@link FileExistanceListener} must be attached to.
     */
    private JTextField sourceTextField = null;

    /**
     * Show whether directory's existance must be checked instead of file's one
     */
    boolean checkDirectory = false;

    /**
     * List of {@link javax.swing.JComponent} instances which must be blocked
     * on negative existance check result.
     *
     * @see #FileExistanceListener(javax.swing.JTextField, boolean, java.util.ArrayList)
     * @see #addBlockedComponent(javax.swing.JComponent)
     */
    ArrayList<JComponent> blockedComponents = new ArrayList<JComponent>();

    /**
     * {@link #sourceTextField} instance's text color in the case of positive existance check result.
     */
    private Color colorSuccess = Color.BLACK;

    /**
     * {@link #sourceTextField} instance's text color in the case of negative existance check result.
     */
    private Color colorError = Color.RED;

    /**
     * Checks for file's existance without blocking any components on negative check result.
     * 
     * @param sourceTextField
     *      Text field that stores a path to file
     * @see #FileExistanceListener(javax.swing.JTextField, boolean)
     * @see #FileExistanceListener(javax.swing.JTextField, boolean, java.util.ArrayList)
     */
    public FileExistanceListener(JTextField sourceTextField) {

        this(sourceTextField, false, null);
    }

    /**
     * Checks for file's or directory's existance without blocking any components on negative check result.
     * 
     * @param sourceTextField
     *      Text field that stores a path to file
     * @param checkDirectory
     *      Shows whether a directory must be check for existance instead of a file
     * @see #FileExistanceListener(javax.swing.JTextField)
     * @see #FileExistanceListener(javax.swing.JTextField, boolean, java.util.ArrayList)
     */
    public FileExistanceListener(JTextField sourceTextField, boolean checkDirectory) {

        this(sourceTextField, checkDirectory, null);
    }

    /**
     * Checks for file's or directory's existance with blocking components
     * specified by {@code blockedComponents} on negative check result.
     *
     * @param sourceTextField
     *      Text field that stores a path to file
     * @param checkDirectory
     *      Shows whether a directory must be check for existance instead of a file
     * @param blockedComponents
     *      List of {@link javax.swing.JComponent} instances which must be blocked on negative check result
     * @see #FileExistanceListener(javax.swing.JTextField)
     * @see #FileExistanceListener(javax.swing.JTextField, boolean)
     */
    public FileExistanceListener(JTextField sourceTextField, boolean checkDirectory, ArrayList<JComponent> blockedComponents) {

        super();

        if (sourceTextField != null) {

            colorSuccess = sourceTextField.getForeground();
            this.sourceTextField = sourceTextField;
        }
        this.checkDirectory = checkDirectory;
        if (blockedComponents != null)
            this.blockedComponents = blockedComponents;
    }

    /**
     * Adds new blocked component on negative check result.
     * 
     * @param blockedComponent
     *      Component to block on negative file's or directory's existance check result
     * @see #blockedComponents
     */
    public void addBlockedComponent(JComponent blockedComponent) {

        if (blockedComponent == null)
            return;

        blockedComponents.add(blockedComponent);
    }

    @Override
    public void insertUpdate(DocumentEvent e) {

        update(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {

        update(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {

        update(e);
    }

    /**
     * Updates text field and blocks/enables components on file's or directory's path changes.
     *
     * @param e
     *      Instance of {@link javax.swing.event.DocumentEvent}
     */
    public void update(DocumentEvent e) {

        if (sourceTextField == null)
            return;

        boolean exists = false;
        try {

            File file = new File(e.getDocument().getText(0, e.getDocument().getLength()));
            if (file.exists() && checkDirectory ? file.isDirectory() : file.isFile())
                exists = true;
        }
        catch (BadLocationException ex) {

        }

        if (exists)
            sourceTextField.setForeground(colorSuccess);
        else
            sourceTextField.setForeground(colorError);

        if (!blockedComponents.isEmpty()) {

            for (JComponent blockedComponent : blockedComponents) {

                if (blockedComponent != null)
                    blockedComponent.setEnabled(exists);
            }
        }
    }
}
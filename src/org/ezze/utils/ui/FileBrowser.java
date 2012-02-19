package org.ezze.utils.ui;

import org.ezze.utils.io.DirectoryManager;
import java.awt.Component;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * Collects a set of static methods to browse for files and directories.
 *
 * @author Dmitriy Pushkov
 * @version 0.0.1
 */
public class FileBrowser {

    /**
     * Browses for a directory with specified initial directory.
     *
     * @param initialDir
     *      Initial directory's name
     * @return
     *      Browsed directory's {@link java.io.File} instance
     * @see #browseDirectory(java.lang.String, java.lang.String)
     * @see #browseDirectory(java.lang.String, java.lang.String, java.awt.Component)
     */
    public static File browseDirectory(String initialDir) {

        return browseDirectory(initialDir, "", null);
    }

    /**
     * Browses for a directory with specified initial directory and dialog's title.
     *
     * @param initialDir
     *      Initial directory's name
     * @param title
     *      Dialog's title
     * @return
     *      Browsed directory's {@link java.io.File} instance
     * @see #browseDirectory(java.lang.String)
     * @see #browseDirectory(java.lang.String, java.lang.String, java.awt.Component)
     */
    public static File browseDirectory(String initialDir, String title) {

        return browseDirectory(initialDir, title, null);
    }

    /**
     * Browses for a directory with specified initial directory,
     * dialog's title and dialog's parent component.
     * 
     * @param initialDir
     *      Initial directory's name
     * @param title
     *      Dialog's title
     * @param parent
     *      Dialog's parent component
     * @return
     *      Browsed directory's {@link java.io.File} instance
     * @see #browseDirectory(java.lang.String)
     * @see #browseDirectory(java.lang.String, java.lang.String)
     */
    public static File browseDirectory(String initialDir, String title, Component parent) {
        
        JFileChooser dirChooser = new JFileChooser();

        if (title.isEmpty())
            title = "Select Directory";

        dirChooser.setDialogTitle(title);
        dirChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        dirChooser.setMultiSelectionEnabled(false);
        dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (initialDir != null) {
            
            File initialDirFile = DirectoryManager.findClosestExistingDirectory(new File(initialDir));
            if (initialDirFile != null)
                dirChooser.setCurrentDirectory(initialDirFile);
        }

        int openResult = dirChooser.showOpenDialog(parent);
        if (openResult == JFileChooser.APPROVE_OPTION) {
            return dirChooser.getSelectedFile();
        }
        
        return null;
    }
    
    public static File browseFile(String initialDir) {
        
        return browseFile(initialDir, "", null, null);
    }
    
    public static File browseFile(String initialDir, String title) {
        
        return browseFile(initialDir, title, null, null);
    }
    
    public static File browseFile(String initialDir, String title, FileFilter filter) {
        
        return browseFile(initialDir, title, filter, null);
    }

    /**
     * Browses for a file with specified initial directory, dialog's title,
     * file filter and dialog's parent component.
     *
     * @param initialDir
     *      Initial directory's name
     * @param title
     *      Dialog's title
     * @param filter
     *      File filter
     * @param parent
     *      Dialog's parent component
     * @return
     *      Browsed file's {@link java.io.File} instance
     * @see #browseFile(java.lang.String, java.lang.String, javax.swing.filechooser.FileFilter, boolean, java.awt.Component)
     */
    public static File browseFile(String initialDir, String title, FileFilter filter, Component parent) {

        File[] selectedFiles = browseFile(initialDir, title, filter, false, parent);
        if (selectedFiles != null) {

            if (selectedFiles.length > 0)
                return selectedFiles[0];
        }

        return null;
    }

    /**
     * Browses for a file or files with specified initial directory, dialog's title,
     * file filter, multiselection mode and dialog's parent component.
     * 
     * @param initialDir
     *      Initial directory's name
     * @param title
     *      Dialog's title
     * @param filter
     *      File filter
     * @param multiSelection
     *      Files' multiselection flag
     * @param parent
     *      Dialog's parent component
     * @return
     *      Browsed files' array of {@link java.io.File} instances
     */
    public static File[] browseFile(String initialDir, String title, FileFilter filter, boolean multiSelection, Component parent) {

        JFileChooser fileChooser = new JFileChooser();
        if (title.isEmpty())
            title = "Select File";
        fileChooser.setDialogTitle(title);
        fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        fileChooser.setMultiSelectionEnabled(multiSelection);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (initialDir != null) {
            
            File initialDirFile = DirectoryManager.findClosestExistingDirectory(new File(initialDir));
            if (initialDirFile != null)
                fileChooser.setCurrentDirectory(initialDirFile);
        }
        if (filter != null) {
            
            fileChooser.setFileFilter(filter);
        }
        int openResult = fileChooser.showOpenDialog(parent);
        if (openResult == JFileChooser.APPROVE_OPTION) {
            
            File[] selectedFiles;
            if (!multiSelection) {
                
                selectedFiles = new File[1];
                selectedFiles[0] = fileChooser.getSelectedFile();
            }
            else {
                
                selectedFiles = fileChooser.getSelectedFiles();
            }
            
            if (selectedFiles.length > 0)
                return selectedFiles;
        }
        return null;
    }
}
package org.ezze.utils.io;

import java.io.File;
import java.io.FileFilter;

/**
 * Collects a set of static methods to manage directories.
 *
 * @author Dmitriy Pushkov
 * @version 0.0.2
 */
public class DirectoryManager {

    /**
     * Creates directory with specified name if it is possible.
     * If some directories of high level don't exist they
     * will be created recursevely too.
     *
     * @param directoryName
     *      Directory's name
     * @return
     *      {@code true} on success, {@code false} otherwise
     */
    public static boolean createDirectory(String directoryName) {

        // Checking that directory's name is specified
        if (directoryName == null)
            return false;

        if (directoryName.isEmpty())
            return false;

        // Checking directory's existance
        File directoryFile = new File(directoryName);
        if (directoryFile.exists() && directoryFile.isDirectory())
            return true;

        // Retrieving nearest parent directory
        String parentDirectory = directoryFile.getParent();
        if (parentDirectory == null)
            return false;

        // Creating parent directory if it is possible
        if (!createDirectory(parentDirectory))
            return false;

        // Parent directory exists here so we can easily create specified one
        return directoryFile.mkdir();
    }

    /**
     * Removes directory with all inner files and directories.
     * 
     * @param directoryName
     *      Directory's name
     * @return
     *      {@code true} on success, {@code false} otherwise
     */
    public static boolean removeDirectory(String directoryName) {

        // Checking that directory's name is specified
        if (directoryName == null)
            return false;

        if (directoryName.isEmpty())
            return false;

        // Attempt to remove all directory's files
        if (DirectoryManager.removeDirectoryFiles(directoryName, null)) {

            // Removing empty directory
            File directoryFile = new File(directoryName);
            return directoryFile.delete();
        }

        return false;
    }

    /**
     * Removes directory's items specified by file filter.
     *
     * @param directoryName
     *      Directory's name
     * @param filter
     *      File filter for directory's items being removed
     * @return
     *      {@code true} if all directory's items passed through file filter
     *      were successfully removed, {@code false} otherwise
     */
    public static boolean removeDirectoryFiles(String directoryName, FileFilter filter) {

        // Checking that directory's name is specified
        if (directoryName == null)
            return false;

        if (directoryName.isEmpty())
            return false;

        // Retrieving directory's items using file filter
        File directoryFile = new File(directoryName);
        if (!directoryFile.exists() || !directoryFile.isDirectory())
            return false;

        File[] files = directoryFile.listFiles(filter);

        // Method's execution result
        boolean removeResult = true;
        
        if (files.length > 0) {

            // Removing all filtered files and directories
            for (File file : files) {

                if (file.isDirectory())
                    removeResult &= DirectoryManager.removeDirectory(file.getAbsolutePath());
                else
                    removeResult &= file.delete();
            }
        }

        return removeResult;
    }

    /**
     * Truncates ending slashes in directory name.
     *
     * @param directoryName
     *      Directory name to truncate ending slashes of
     * @return
     *      Directory name with truncated ending slashes
     */
    public static String truncateSlashes(String directoryName) {

        if (directoryName == null)
            return null;

        String truncatedDirectoryName = new String(directoryName);
        while (truncatedDirectoryName.length() > 0 && truncatedDirectoryName.endsWith("\\"))
            truncatedDirectoryName = truncatedDirectoryName.substring(0, truncatedDirectoryName.length() - 1);

        return truncatedDirectoryName;
    }

    /**
     * Looks for the closest existing directory to specified file or directory
     *
     * @param fileName
     *      File's or directory's name
     * @return
     *      Existing directory's name or {@code null} if such directory wasn't found
     */
    public static String findClosestExistingDirectory(String fileName) {

        File closestDirectory = findClosestExistingDirectory(new File(fileName));
        if (closestDirectory == null)
            return null;

        return closestDirectory.getAbsolutePath();
    }

    /**
     * Looks for the closest existing directory to specified file or directory
     *
     * @param file
     *      File or directory
     * @return
     *      Existing directory's {@link java.io.File} instance or {@code null} if such directory wasn't found
     */
    public static File findClosestExistingDirectory(File file) {

        File closestDirectory = new File(file.getAbsolutePath());
        boolean isDirectoryFound = false;
        while (closestDirectory != null && !isDirectoryFound) {

            if (closestDirectory.exists() && closestDirectory.isDirectory())
                isDirectoryFound = true;
            else
                closestDirectory = closestDirectory.getParentFile();
        }

        if (isDirectoryFound)
            return closestDirectory;

        return null;
    }
}
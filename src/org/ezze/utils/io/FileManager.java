package org.ezze.utils.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

/**
 * Collects a set of static methods to manage files.
 *
 * @author Dmitriy Pushkov
 * @version 0.0.1
 */
public class FileManager {

    /**
     * Copies source file to specified destination file or existing destination directory.
     *
     * @param sourceName
     *      Source file
     * @param destinationName
     *      Source file or existing destination directory
     * @return
     *      {@code true} if file has been successfully copied, {@code false} otherwise
     * @see #copyFile(java.io.File, java.io.File)
     */
    public static boolean copyFile(String sourceName, String destinationName) {

        return copyFile(new File(sourceName), new File(destinationName));
    }

    /**
     * Copies source file to specified destination file or existing destination directory.
     * 
     * @param sourceFile
     *      Source file
     * @param destinationFile
     *      Source file or existing destination directory
     * @return
     *      {@code true} if file has been successfully copied, {@code false} otherwise
     * @see #copyFile(java.lang.String, java.lang.String)
     */
    public static boolean copyFile(File sourceFile, File destinationFile) {

        // Checking whether source and destination files are specified
        if (sourceFile == null || destinationFile == null)
            return false;

        // Checking whether destination is a directory
        if (destinationFile.exists() && destinationFile.isDirectory()) {

            String destinationDirectoryName = destinationFile.getAbsolutePath();
            destinationFile = new File(String.format("%s\\%s", destinationDirectoryName, sourceFile.getName()));
        }

        try {

            FileInputStream sourceStream = null;
            FileOutputStream destinationStream = null;

            // Opening source stream
            try {

                sourceStream = new FileInputStream(sourceFile);
            }
            catch (FileNotFoundException ex) {

                // Source file is not found here
                return false;
            }

            // Making sure that destination directory exists
            DirectoryManager.createDirectory(destinationFile.getParent());

            // Opening destination stream
            try {

                destinationStream = new FileOutputStream(destinationFile);
            }
            catch (FileNotFoundException ex) {

                // Destination output stream cannot be created here
                return false;
            }

            byte buffer[] = new byte[1024];
            int bytesRead = 0;
            try {

                // Reading source stream and writing read data to destination one
                while ((bytesRead = sourceStream.read(buffer)) > 0)
                    destinationStream.write(buffer, 0, bytesRead);
            }
            catch (IOException ex) {

                return false;
            }

            // Closing source and destination streams
            destinationStream.close();
            sourceStream.close();

            return true;
        }
        catch (IOException ex) {
            
        }

        return false;
    }

    /**
     * Copies all files from source directory to destination directory.
     *
     * @param sourceDirectoryName
     *      Source directory
     * @param destinationDirectoryName
     *      Destination directory
     * @return
     *      {@code true} if all files have been copied, {@code false} otherwise
     * @see #copyDirectoryFiles(java.lang.String, java.lang.String, java.io.FileFilter)
     * @see #copyDirectoryFiles(java.io.File, java.io.File)
     * @see #copyDirectoryFiles(java.io.File, java.io.File, java.io.FileFilter)
     */
    public static boolean copyDirectoryFiles(String sourceDirectoryName, String destinationDirectoryName) {

        return copyDirectoryFiles(new File(sourceDirectoryName), new File(destinationDirectoryName));
    }

    /**
     * Copies all files from source directory to destination directory.
     *
     * @param sourceDirectoryFile
     *      Source directory
     * @param destinationDirectoryFile
     *      Destination directory
     * @return
     *      {@code true} if all files have been copied, {@code false} otherwise
     * @see #copyDirectoryFiles(java.lang.String, java.lang.String)
     * @see #copyDirectoryFiles(java.lang.String, java.lang.String, java.io.FileFilter)
     * @see #copyDirectoryFiles(java.io.File, java.io.File, java.io.FileFilter)
     */
    public static boolean copyDirectoryFiles(File sourceDirectoryFile, File destinationDirectoryFile) {

        return copyDirectoryFiles(sourceDirectoryFile, destinationDirectoryFile, null);
    }

    /**
     * Copies filtered by {@code fileFilter} files from source directory to destination directory.
     *
     * @param sourceDirectoryName
     *      Source directory
     * @param destinationDirectoryName
     *      Destination directory
     * @param fileFilter
     *      File filter
     * @return
     *      {@code true} if all filtered files have been copied, {@code false} otherwise
     * @see #copyDirectoryFiles(java.lang.String, java.lang.String)
     * @see #copyDirectoryFiles(java.io.File, java.io.File)
     * @see #copyDirectoryFiles(java.io.File, java.io.File, java.io.FileFilter)
     */
    public static boolean copyDirectoryFiles(String sourceDirectoryName, String destinationDirectoryName, FileFilter fileFilter) {

        return copyDirectoryFiles(new File(sourceDirectoryName), new File(destinationDirectoryName), fileFilter);
    }

    /**
     * Copies filtered by {@code fileFilter} files from source directory to destination directory.
     * 
     * @param sourceDirectoryFile
     *      Source directory
     * @param destinationDirectoryFile
     *      Destination directory
     * @param fileFilter
     *      File filter
     * @return
     *      {@code true} if all filtered files have been copied, {@code false} otherwise
     * @see #copyDirectoryFiles(java.lang.String, java.lang.String)
     * @see #copyDirectoryFiles(java.lang.String, java.lang.String, java.io.FileFilter)
     * @see #copyDirectoryFiles(java.io.File, java.io.File)
     */
    public static boolean copyDirectoryFiles(File sourceDirectoryFile, File destinationDirectoryFile, FileFilter fileFilter) {

        // Checking whether source and destination directories are specified
        if (sourceDirectoryFile == null || destinationDirectoryFile == null)
            return false;

        // Checking source and destination directories' existance
        if ((!sourceDirectoryFile.exists() || !sourceDirectoryFile.isDirectory())
                || (!destinationDirectoryFile.exists()) || !destinationDirectoryFile.isDirectory())
            return false;

        // Retrieving source directory's files list
        File[] sourceFiles = fileFilter != null ? sourceDirectoryFile.listFiles(fileFilter) : sourceDirectoryFile.listFiles();

        // Are there any files to copy?
        if (sourceFiles.length == 0)
            return true;

        boolean overallCopyResult = true;
        for (File sourceFile : sourceFiles)
            overallCopyResult &= copyFile(sourceFile, destinationDirectoryFile);

        return overallCopyResult;
    }

    public static boolean mergeTextFiles(File sourceFile1, File sourceFile2, File destinationFile, String lineSeparator) {

        File[] sourceFiles = new File[2];
        sourceFiles[0] = sourceFile1;
        sourceFiles[1] = sourceFile2;
        return mergeTextFiles(sourceFiles, destinationFile, lineSeparator);
    }

    public static boolean mergeTextFiles(File[] sourceFiles, File destinationFile, String lineSeparator) {

        if (sourceFiles == null || destinationFile == null)
            return false;

        if (sourceFiles.length == 0)
            return false;

        if (!DirectoryManager.createDirectory(destinationFile.getParent()))
            return false;
        
        BufferedWriter outputWriter = null;
        try {

            outputWriter = new BufferedWriter(new FileWriter(destinationFile));
        }
        catch (IOException ex) {

            return false;
        }

        for (File sourceFile : sourceFiles) {

            try {

                BufferedReader inputReader = new BufferedReader(new FileReader(sourceFile));
                String line = null;

                try {
                    
                    while ((line = inputReader.readLine()) != null) {
                        
                        outputWriter.write(line);
                        if (lineSeparator == null)
                            outputWriter.newLine();
                        else
                            outputWriter.write(line);
                    }
                }
                catch (IOException ex) {

                    return false;
                }

                try {

                    inputReader.close();
                }
                catch (IOException ex) {
                    
                }
            }
            catch (FileNotFoundException ex) {
                
            }
        }

        try {
            
            outputWriter.close();
        }
        catch (IOException ex) {

            return false;
        }

        return true;
    }

    /**
     * Extracts GZip archive to its containing directory
     *
     * @param sourceFileName
     *      GZip archive to extract
     * @return
     *      {@code true} if GZip archive has been successfully extracted, {@code false} otherwise
     * @see #extractGZipFile(java.io.File)
     * @see #extractGZipFile(java.lang.String, java.lang.String)
     * @see #extractGZipFile(java.io.File, java.io.File)
     */
    public static boolean extractGZipFile(String sourceFileName) {

        return extractGZipFile(new File(sourceFileName));
    }

    /**
     * Extracts GZip archive to its containing directory
     * 
     * @param sourceFile
     *      GZip archive to extract
     * @return
     *      {@code true} if GZip archive has been successfully extracted, {@code false} otherwise
     * @see #extractGZipFile(java.lang.String)
     * @see #extractGZipFile(java.lang.String, java.lang.String)
     * @see #extractGZipFile(java.io.File, java.io.File)
     */
    public static boolean extractGZipFile(File sourceFile) {

        return extractGZipFile(sourceFile, new File(sourceFile.getParent()));
    }

    /**
     * Extracts GZip archive to specified destination.
     *
     * @param sourceFileName
     *      GZip archive to extract
     * @param destinationFileName
     *      Destination file's name or destination directory's name if this directory already exists
     * @return
     *      {@code true} if GZip archive has been successfully extracted, {@code false} otherwise
     * @see #extractGZipFile(java.lang.String)
     * @see #extractGZipFile(java.io.File)
     * @see #extractGZipFile(java.io.File, java.io.File)
     */
    public static boolean extractGZipFile(String sourceFileName, String destinationFileName) {

        return extractGZipFile(new File(sourceFileName), new File(destinationFileName));
    }

    /**
     * Extracts GZip archive to specified destination.
     * 
     * @param sourceFile
     *      GZip archive to extract
     * @param destinationFile
     *      Destination file or destination directory if this directory already exists
     * @return
     *      {@code true} if GZip archive has been successfully extracted, {@code false} otherwise
     * @see #extractGZipFile(java.lang.String)
     * @see #extractGZipFile(java.io.File)
     * @see #extractGZipFile(java.lang.String, java.lang.String)
     */
    public static boolean extractGZipFile(File sourceFile, File destinationFile) {

        // Checking whether source archive and destination are specified
        if (sourceFile == null || destinationFile == null)
            return false;

        // Checking source file's extension
        if (!sourceFile.getName().endsWith(".gz"))
            return false;

        // Checking whether destination is an existing directory
        if (destinationFile.exists() && destinationFile.isDirectory()) {

            String destinationDirectory = destinationFile.getAbsolutePath();
            int sourceFileExtensionDotPosition = sourceFile.getName().lastIndexOf('.');
            destinationFile = new File(String.format("%s\\%s", destinationDirectory,
                    sourceFile.getName().substring(0, sourceFileExtensionDotPosition)));
        }

        // Opening source stream
        FileInputStream sourceStream = null;
        try {

            sourceStream = new FileInputStream(sourceFile);
        }
        catch (FileNotFoundException ex) {

            // Unable to open source stream
            return false;
        }
        
        // Creating GZIP source stream
        GZIPInputStream gzipSourceStream = null;
        try {
            
            gzipSourceStream = new GZIPInputStream(sourceStream);
        }
        catch (IOException ex) {
            
            // Unable to create GZIP stream
            return false;
        }

        // Making sure that destination directory exists
        if (!DirectoryManager.createDirectory(destinationFile.getParent()))
            return false;

        // Creating output stream
        FileOutputStream destinationStream = null;
        try {

            destinationStream = new FileOutputStream(destinationFile);
        }
        catch (IOException ex) {

            // Unable to create destination stream
            return false;
        }

        // Extracting
        try {

            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = gzipSourceStream.read(buffer)) > 0)
                destinationStream.write(buffer, 0, bytesRead);
        }
        catch (IOException ex) {

            // Something went wrong during the extraction
            return false;
        }

        // Closing streams
        try {

            destinationStream.close();
            gzipSourceStream.close();
            sourceStream.close();
        }
        catch (IOException ex) {

            return false;
        }

        return true;
    }
}
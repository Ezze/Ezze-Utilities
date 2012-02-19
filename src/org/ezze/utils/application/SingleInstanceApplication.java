package org.ezze.utils.application;
        
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * This class impements an algorithm of single instance application.
 *
 * @author Dmitriy Pushkov
 * @version 0.0.1
 */
public class SingleInstanceApplication {

    /**
     * File to lock by application.
     */
    File lockedFile = null;

    /**
     * Locked file's channel.
     */
    FileChannel fileChannel = null;

    /**
     * {@link java.nio.channels.FileLock} instance of {@link #lockedFile}.
     */
    FileLock fileLock = null;

    /**
     * Single application's instance constructor.
     *
     * @param fileName
     *      File to lock
     */
    public SingleInstanceApplication(String fileName) {

        if (fileName != null)
            setLockedFile(new File(fileName));
    }

    /**
     * Single application's instance constructor.
     *
     * @param file
     *      File to lock
     */
    public SingleInstanceApplication(File file) {

        setLockedFile(file);
    }

    /**
     * Sets locked file's istance of {@link java.io.File}.
     *
     * @param file
     *      Locked file's {@link java.io.File} instance
     */
    public final void setLockedFile(File file) {

        if (file == null)
            return;
        this.lockedFile = file;
    }

    /**
     * Checks whether application has locked a file.
     *
     * @return
     *      {@code true} if file is locked, {@code false} otherwise
     */
    public boolean isFileLocked() {

        return fileLock != null;
    }

    /**
     * Locks the file (makes currently running application's instance single)
     *
     * @return
     *      {@code true} if application has locked a file, {@code false} otherwise
     */
    public boolean lock() {

        // Checking whether locked file is set
        if (lockedFile == null)
            return false;

        // Trying to remove existing file - it will be removed if another
        // application's instance hasn't locked it.
        if (lockedFile.exists())
            lockedFile.delete();

        try {

            fileChannel = new RandomAccessFile(lockedFile, "rw").getChannel();
            fileLock = fileChannel.tryLock();
            if (fileLock == null) {

                // File is locked by another application's instance or by another application.
                fileChannel.close();
                fileChannel = null;
                return false;
            }

            // Adding shutdown hook to unlock a file
            Runtime.getRuntime().addShutdownHook(new Thread() {

                @Override
                public void run() {

                    unlock();
                }
            });

            return true;
        }
        catch (IOException ex) {

        }

        return false;
    }

    /**
     * Unlocks the file.
     *
     * @return
     *      {@code true} if file is not locked anymore, {@code false} otherwise
     */
    public boolean unlock() {

        if (!isFileLocked() || fileChannel == null)
            return true;

        try {

            fileLock.release();
            fileChannel.close();
            fileLock = null;
            fileChannel = null;
            lockedFile.delete();
        }
        catch (IOException ex) {

        }

        return false;
    }
}
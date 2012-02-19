package org.ezze.utils.io;

import java.io.File;
import java.util.ArrayList;
import javax.swing.filechooser.FileFilter;

/**
 * Represents compound file filter providing a set
 * of single choosable file filters.
 * 
 * @author Dmitriy Pushkov
 * @version 0.0.1
 */
public class CompoundFileFilter extends FileFilter {

    /**
     * Keeps compound file filter's description.
     */
    String description = "Undescribed files";
    
    /**
     * A list of single choosable file filters.
     */
    ArrayList<FileFilter> fileFilters = new ArrayList<FileFilter>();
    
    /**
     * Keeps an index of default single choosable file filter.
     */
    int defaultFileFilterIndex = -1;
    
    /**
     * Creates empty compound file filter.
     */
    public CompoundFileFilter() {
        
        this(null);
    }
    
    /**
     * Creates compound file filter using specified single filters as choosable ones.
     * 
     * @param fileFilters
     *      An array of single file filters to be used as choosable ones.
     */
    public CompoundFileFilter(ArrayList<FileFilter> fileFilters) {
        
        if (fileFilters != null && !fileFilters.isEmpty()) {
            
            for (FileFilter fileFilter : fileFilters)
                add(fileFilter);
        }
    }
    
    /**
     * Adds new single choosable file filter.
     * 
     * @param fileFilter 
     *      Single file filter.
     */
    public final void add(FileFilter fileFilter) {
        
        // Checking whether provided file filter is not in the list yet
        if (fileFilter == null || fileFilters.contains(fileFilter))
            return;
        
        fileFilters.add(fileFilter);
    }

    /** {@inheritDoc} */
    @Override
    public boolean accept(File file) {
        
        int fileFilterIndex = 0;
        while (fileFilterIndex < fileFilters.size()) {
            
            FileFilter fileFilter = fileFilters.get(fileFilterIndex);
            if (fileFilter.accept(file))
                return true;
            fileFilterIndex++;
        }
        
        return false;
    }
    
    /** {@inheritDoc} */
    @Override
    public String getDescription() {
        
        return description;
    }
    
    /**
     * Sets description of compound file filter.
     * 
     * @param description 
     */
    public void setDescription(String description) {
        
        if (description == null)
            return;
        
        this.description = description;
    }
    
    /**
     * Retrieves a list of single choosable file filters.
     */
    public ArrayList<FileFilter> getFileFilters() {
        
        return fileFilters;
    }
    
    /**
     * Retrieves an index of default single choosable file filter.
     * 
     * @return 
     *      Index of default file filter.
     */
    public int getDefaultFileFilterIndex() {
        
        return defaultFileFilterIndex;
    }
    
    /**
     * Sets an index of default single choosable file filter.
     * 
     * @param
     *      Index of default file filter.
     */
    public void setDefaultFileFilterIndex(int defaultFileFilterIndex) {
        
        if (defaultFileFilterIndex < 0 || defaultFileFilterIndex >= fileFilters.size())
            return;
        
        this.defaultFileFilterIndex = defaultFileFilterIndex;
    }
}
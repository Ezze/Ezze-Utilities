package org.ezze.utils.ui.aboutbox;

import java.awt.Image;
import java.util.ArrayList;

/**
 * This is an abstract class to extend for providing
 * an information for application's about box {@link AboutBox}.
 * 
 * @author Dmitriy Pushkov
 * @version 0.0.1
 */
public abstract class AboutBoxInformation {
    
    /**
     * Interface to implement to provide information line.
     */
    public interface InformationLine {

        /**
         * Returns information line's title.
         * 
         * @return 
         *      Information line's title or {@code null} if title is not specified
         */
        public String getTitle();
        
        /**
         * Returns information line's value.
         * 
         * @return 
         *      Information line's value
         */
        public String getValue();
        
        /**
         * Returns information line's link if any
         * 
         * @return 
         *      Information line's link or {@code null} if no link is specified
         */
        public String getLink();
    }
    
    /**
     * Collects a set information lines {@link InformationLine} to display in {@link AboutBox}.
     * 
     * One can extend about information adding new information lines using
     * {@link #addInformationLine(java.lang.String, java.lang.String, java.lang.String)} method.
     * 
     * @see #addInformationLine(java.lang.String)
     * @see #addInformationLine(java.lang.String, java.lang.String)
     * @see #addInformationLine(java.lang.String, java.lang.String, java.lang.String)
     */
    protected ArrayList<InformationLine> informationLines = new ArrayList<InformationLine>();
    
    /**
     * Information's default empty constructor.
     */
    public AboutBoxInformation() {
        
    }
   
    /**
     * Retrieves about box contents' horizontal margin.
     * 
     * @return 
     *      Horizontal margin in pixels
     */
    abstract public int getHorizontalMargin();
    
    /**
     * Retrieves about box contents' vertical margin.
     * 
     * @return 
     *      Vertical margin in pixels
     */
    abstract public int getVerticalMargin();
    
    /**
     * Retrieves width of about box' information area.
     * 
     * @return
     *      Width of information area in pixels
     */
    abstract public int getInformationAreaWidth();
    
    /**
     * Retrieves vertical gap between about box' information lines.
     * 
     * @return 
     *      Vertical gap between information lines in pixels
     */
    abstract public int getInformationLinesGap();
    
    /**
     * Retrieves a reference to about image.
     * 
     * @return
     *      Image for about box or {@code null} if no image is provided
     */
    abstract public Image getApplicationImage();
    
    /**
     * Retrieves application's vendor name.
     * 
     * @return 
     *      Application's vendor name
     */
    abstract public String getApplicationVendor();
    
    /**
     * Retrieves application's name
     * 
     * @return 
     *      Application's name
     */
    abstract public String getApplicationName();
    
    /**
     * Retrieves application's version.
     * 
     * @return 
     *      Application's version
     */
    abstract public String getApplicationVersion();
    
    /**
     * Retrieves application's description
     * 
     * @return 
     *      Application's description
     */
    abstract public String getApplicationDescription();
    
    /**
     * Retrieves a text for about box' close button.
     * 
     * @return 
     *      Close button's text
     */
    abstract public String getCloseButtonText();
    
    /**
     * Adds new information line {@link InformationLine}.
     * 
     * @param value 
     *      Information line's value
     * @see #addInformationLine(java.lang.String, java.lang.String) 
     * @see #addInformationLine(java.lang.String, java.lang.String, java.lang.String) 
     */
    public void addInformationLine(String value) {
        
        addInformationLine(null, value, null);
    }
    
    /**
     * Adds new information line {@link InformationLine}.
     * 
     * @param title
     *      Information line's title
     * @param value
     *      Information line's value
     * @see #addInformationLine(java.lang.String) 
     * @see #addInformationLine(java.lang.String, java.lang.String, java.lang.String) 
     */
    public void addInformationLine(String title, String value) {
        
        addInformationLine(title, value, null);
    }
    
    /**
     * Adds new information line {@link InformationLine}.
     * 
     * @param title
     *      Information line's title
     * @param value
     *      Information line's value
     * @param link 
     *      Information line's link
     * @see #addInformationLine(java.lang.String) 
     * @see #addInformationLine(java.lang.String, java.lang.String)
     */
    public void addInformationLine(final String title, final String value, final String link) {
        
        if (value == null)
            return;
                   
        informationLines.add(new InformationLine() {

            @Override
            public String getTitle() {
                
                return title;
            }

            @Override
            public String getValue() {
                
                return value;
            }

            @Override
            public String getLink() {
                
                return link;
            }
            
        });
    }
    
    /**
     * Retrieves overall count of information lines.
     * 
     * @return 
     *      Count of information lines
     */
    public int getInformationLinesCount() {
        
        return informationLines.size();
    }

    /**
     * Retrieves information line {@link InformationLine} instance.
     * 
     * @param informationLineIndex
     *      Information line's index in the range [0, {@link #getInformationLinesCount()} - 1]
     * @return 
     *      Information line instance
     */
    public InformationLine getInformationLine(int informationLineIndex) {
        
        if (informationLineIndex < 0 || informationLineIndex >= informationLines.size())
            return null;
        
        return informationLines.get(informationLineIndex);
    }
}
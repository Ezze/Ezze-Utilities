package org.ezze.utils.ui.aboutbox;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Insets;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.border.Border;

/**
 * @author Dmitriy Pushkov
 * @version 0.0.1
 */
public class MultilineLabel extends JLabel {
 
    private static final int DEFAULT_MAXIMUM_WIDTH = 200;
    
    private int maximumWidth = 0;
    private String initialText = "";
    
    public MultilineLabel() {
        
        this("", DEFAULT_MAXIMUM_WIDTH);
    }
    
    public MultilineLabel(String text) {
        
        this(text, DEFAULT_MAXIMUM_WIDTH);
    }
    
    public MultilineLabel(String text, int maximumWidth) {
        
        initialText = text;
        this.maximumWidth = maximumWidth;
        setText(initialText);
    }
    
    @Override
    public void setFont(Font font) {
        
        super.setFont(font);
        setText(initialText);
    }
    
    @Override
    public void setBorder(Border border) {
        
        super.setBorder(border);
        setText(initialText);
    }
    
    @Override
    public final void setText(String text) {

        if (text == null)
            return;
        
        String multilineText = "";
        if (!text.isEmpty()) {
        
            Insets borderInsets = getInsets();
            FontMetrics fontMetrics = getFontMetrics(getFont());
            
            String[] textWords = text.split(" ");
            
            int textWordIndex = 0;

            ArrayList<String> lines = new ArrayList<String>();
            String currentLine = "";
            while (textWordIndex < textWords.length) {
                
                String textWord = textWords[textWordIndex];
                if (currentLine.isEmpty())
                    currentLine = textWord;
                else {
                    
                    String possibleLine = String.format("%s %s", currentLine, textWord);
                    if (fontMetrics.stringWidth(possibleLine) <= maximumWidth - borderInsets.left - borderInsets.right)
                        currentLine = possibleLine;
                    else {
                        
                        lines.add(currentLine);
                        currentLine = textWord;
                    }
                }
                
                textWordIndex++;
            }
            
            if (!currentLine.isEmpty())
                lines.add(currentLine);
            
            if (!lines.isEmpty()) {
                
                for (String line : lines) {
                    
                    if (!multilineText.isEmpty())
                        multilineText += "<br />";
                    multilineText += line;
                }
            }
        }
        
        super.setText(String.format("<html><div>%s</div></html>", multilineText));
    }
}
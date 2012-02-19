package org.ezze.utils.ui.aboutbox;

import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import org.ezze.utils.listeners.LinkMouseListener;

/**
 * @author Dmitriy Pushkov
 * @version 0.0.1
 */
public class AboutBox extends JDialog {
    
    public AboutBox(AboutBoxInformation information) {
        
        super((JDialog)null, true);

        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
           
            @Override
            public void windowClosing(WindowEvent we) {
                
                dispose();
            }
        });
        
        JPanel contentPane = new JPanel();
        SpringLayout contentLayout = new SpringLayout();
        contentPane.setLayout(contentLayout);
        
        // Creating about image
        final Image aboutImage = information.getApplicationImage();
        final int aboutImageWidth = aboutImage != null ? aboutImage.getWidth(null) : 0;
        final int aboutImageHeight = aboutImage != null ? aboutImage.getHeight(null) : 0;
        
        JPanel aboutImagePanel = new JPanel() {
            
            @Override
            public void paint(Graphics g) {
                
                super.paint(g);
                Graphics2D g2d = (Graphics2D)g;
                g2d.drawImage(aboutImage, 0, 0, aboutImageWidth >= 0 ? aboutImageWidth : 0,
                        aboutImageHeight >= 0 ? aboutImageHeight : null, this);
            }
        };
        //aboutImagePanel.setBorder(BorderFactory.createLoweredBevelBorder());
        
        contentLayout.putConstraint(SpringLayout.WEST, aboutImagePanel, 0, SpringLayout.WEST, contentPane);
        contentLayout.putConstraint(SpringLayout.NORTH, aboutImagePanel, 0, SpringLayout.NORTH, contentPane);
        contentLayout.putConstraint(SpringLayout.EAST, aboutImagePanel,
                aboutImageWidth >= 0 ? aboutImageWidth : 0, SpringLayout.WEST, aboutImagePanel);
        contentLayout.putConstraint(SpringLayout.SOUTH, aboutImagePanel,
                aboutImageHeight >= 0 ? aboutImageHeight : 0, SpringLayout.NORTH, aboutImagePanel);
        contentPane.add(aboutImagePanel);
        
        // Setting box' title
        String applicationVendor = information.getApplicationVendor();
        if (applicationVendor == null)
            applicationVendor = "";
        String applicationName = information.getApplicationName();
        if (applicationName == null)
            applicationName = "";
        String applicationVersion = information.getApplicationVersion();
        if (applicationVersion == null)
            applicationVersion = "";
        String aboutBoxTitle = String.format("%s %s %s", applicationVendor, applicationName, applicationVersion).trim();
        if (aboutBoxTitle.isEmpty())
            aboutBoxTitle = "About";
        setTitle(aboutBoxTitle);
        
        // Creating about title
        MultilineLabel titleLabel = new MultilineLabel(information.getApplicationName(), information.getInformationAreaWidth());
        Font defaultFont = titleLabel.getFont();
        titleLabel.setFont(defaultFont.deriveFont(Font.BOLD, (int)(defaultFont.getSize() * 1.2)));
        
        contentLayout.putConstraint(SpringLayout.WEST, titleLabel,
                aboutImageWidth > 0 ? information.getHorizontalMargin() * 2 : 0, SpringLayout.EAST, aboutImagePanel);
        contentLayout.putConstraint(SpringLayout.NORTH, titleLabel, 0, SpringLayout.NORTH, aboutImagePanel);
        contentLayout.putConstraint(SpringLayout.EAST, titleLabel, information.getInformationAreaWidth(), SpringLayout.WEST, titleLabel);
        contentPane.add(titleLabel);
        
        // Creating about description
        MultilineLabel descriptionLabel = new MultilineLabel(information.getApplicationDescription(), information.getInformationAreaWidth());
        
        contentLayout.putConstraint(SpringLayout.WEST, descriptionLabel, 0, SpringLayout.WEST, titleLabel);
        contentLayout.putConstraint(SpringLayout.NORTH, descriptionLabel, information.getInformationLinesGap(), SpringLayout.SOUTH, titleLabel);
        contentLayout.putConstraint(SpringLayout.EAST, descriptionLabel, 0, SpringLayout.EAST, titleLabel);
        contentPane.add(descriptionLabel);
        
        // Creating information lines
        JLabel fontMetricsLabel = new JLabel();
        FontMetrics fontMetrics = fontMetricsLabel.getFontMetrics(fontMetricsLabel.getFont());
        
        Component lastInformationComponent = descriptionLabel;

        int maximumTitleWidth = 0;
        int informationLineIndex = 0;
        while (informationLineIndex < information.getInformationLinesCount()) {
            
            String informationLineTitle = information.getInformationLine(informationLineIndex).getTitle();
            JLabel informationLineTitleLabel = new JLabel(informationLineTitle != null ? informationLineTitle : "");
            
            contentLayout.putConstraint(SpringLayout.WEST, informationLineTitleLabel, 0, SpringLayout.WEST, lastInformationComponent);
            contentLayout.putConstraint(SpringLayout.NORTH, informationLineTitleLabel,
                    information.getInformationLinesGap() * (informationLineIndex == 0 ? 2 : 1), SpringLayout.SOUTH, lastInformationComponent);
            contentPane.add(informationLineTitleLabel);
                    
            int titleWidth = informationLineTitleLabel.getFontMetrics(informationLineTitleLabel.getFont()).stringWidth(informationLineTitleLabel.getText());
            if (titleWidth > maximumTitleWidth)
                maximumTitleWidth = titleWidth;
            
            lastInformationComponent = informationLineTitleLabel;
            
            informationLineIndex++;
        }
        
        lastInformationComponent = descriptionLabel;
        
        int valueGap = maximumTitleWidth + fontMetrics.stringWidth("     ");
        
        informationLineIndex = 0;
        while (informationLineIndex < information.getInformationLinesCount()) {
            
            JLabel informationLineValueLabel = new JLabel(information.getInformationLine(informationLineIndex).getValue());
            String informationLineLink = information.getInformationLine(informationLineIndex).getLink();
            if (informationLineLink != null) {
             
                LinkMouseListener linkMouseListener = new LinkMouseListener(informationLineLink, this);
                informationLineValueLabel.addMouseListener(linkMouseListener);
                linkMouseListener.mouseExited(new MouseEvent(informationLineValueLabel, MouseEvent.MOUSE_EXITED, 0, 0, 0, 0, 1, false));
            }
            
            contentLayout.putConstraint(SpringLayout.WEST, informationLineValueLabel, valueGap, SpringLayout.WEST, titleLabel);
            contentLayout.putConstraint(SpringLayout.NORTH, informationLineValueLabel,
                    information.getInformationLinesGap() * (informationLineIndex == 0 ? 2 : 1), SpringLayout.SOUTH, lastInformationComponent);
            contentPane.add(informationLineValueLabel);
            
            lastInformationComponent = informationLineValueLabel;
            
            informationLineIndex++;
        }
        
        // Creating close button
        JButton closeButton = new JButton(information.getCloseButtonText());
        final JDialog aboutBox = this;
        closeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                for (WindowListener windowListener : aboutBox.getWindowListeners())
                    windowListener.windowClosing(new WindowEvent(aboutBox, WindowEvent.WINDOW_CLOSING));
            }
        });
        
        contentLayout.putConstraint(SpringLayout.NORTH, closeButton,
                information.getInformationLinesGap() * 2, SpringLayout.SOUTH, lastInformationComponent);
        contentLayout.putConstraint(SpringLayout.EAST, closeButton, 0, SpringLayout.EAST, titleLabel);
        contentPane.add(closeButton);
        
        int aboutImagePanelSouth = contentLayout.getConstraint(SpringLayout.SOUTH, aboutImagePanel).getValue();
        int informationSouth = contentLayout.getConstraint(SpringLayout.SOUTH, closeButton).getValue();
        contentLayout.putConstraint(SpringLayout.EAST, contentPane, information.getInformationAreaWidth(), SpringLayout.WEST, titleLabel);
        contentLayout.putConstraint(SpringLayout.SOUTH, contentPane,
                0, SpringLayout.SOUTH, aboutImagePanelSouth > informationSouth ? aboutImagePanel : closeButton);

        contentPane.setBorder(BorderFactory.createEmptyBorder(information.getVerticalMargin(),
                information.getHorizontalMargin(), information.getVerticalMargin(), information.getHorizontalMargin()));
       
        setContentPane(contentPane);
        
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }
}
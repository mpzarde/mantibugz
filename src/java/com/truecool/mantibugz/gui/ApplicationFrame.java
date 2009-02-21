package com.truecool.mantibugz.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by IntelliJ IDEA.
 * User: mpzarde
 * Date: Sep 24, 2008
 * Time: 3:19:18 PM
 */
public class ApplicationFrame extends JFrame {

    private MainForm _form;

    public ApplicationFrame(MainForm form) throws HeadlessException {
        super();
        _form = form;
        setContentPane((Container) _form.getRootContainer());

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                int answer = JOptionPane.showConfirmDialog(null,
                        "Do you want to exit the program?", getTitle(), JOptionPane.YES_NO_OPTION);
                if (answer == JOptionPane.YES_OPTION) {
                    dispose();
                    System.exit(0);
                }

            }
        });
    }

    public void centerOnScreen() {
        // Center the Frame
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();

        int screenWidth = (int) screenSize.getWidth();     // getting Screen width
        int screenHeight = (int) screenSize.getHeight();  // getting Screen height

        int xPos = (screenWidth - this.getWidth()) / 2;
        int yPos = (screenHeight - this.getHeight()) / 2;
        this.setLocation(xPos, yPos);
    }


}

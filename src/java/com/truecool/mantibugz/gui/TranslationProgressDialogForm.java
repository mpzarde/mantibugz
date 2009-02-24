package com.truecool.mantibugz.gui;

import com.truecool.mantibugz.MantiBugzController;
import org.mantisbt.connect.model.IIssue;
import org.mantisbt.connect.model.IProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TranslationProgressDialogForm extends JDialog {
  private MantiBugzController _controller;

  private JPanel _contentPane;
  private JButton _buttonOK;
  private JButton _buttonCancel;
  private JLabel _source;
  private JLabel _destination;
  private JLabel _status;

  public TranslationProgressDialogForm(MantiBugzController controller) {
    if (controller == null) {
      throw new IllegalArgumentException("Controller cannot be empty or null");
    }

    _controller = controller;
    setContentPane(_contentPane);
    setModal(true);
    getRootPane().setDefaultButton(_buttonOK);
    setTitle("MantiBugz 1.0");

    _source.setText("Source : " + _controller.getMantisConnection().getBaseURL());
    _destination.setText("Destination : " + _controller.getFogBugzConnection().getBaseURL());
    _status.setText("Status : waiting; " + _controller.getSourceIssues().size() + " issues to import.");

    _buttonOK.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        onOK();
      }
    });

    _buttonCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        onCancel();
      }
    });

    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        onCancel();
      }
    });

    _contentPane.registerKeyboardAction(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        onCancel();
      }
    }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
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

  private void onOK() {
    doImport();
    dispose();
  }

  private void onCancel() {
    dispose();
  }

  private void doImport() {
    java.util.List<IIssue> issues = _controller.getSourceIssues();

    if (issues != null && issues.size() > 0) {
      int index = 0;

      try {
        IProject fogBugzProject = _controller.getFogBugzConnection().getCurrentProject();

        for (index = 0; index < issues.size(); index++) {
          IIssue issue = issues.get(index);
          _controller.getFogBugzConnection().createNewIssue(issue, fogBugzProject);
          final String message = "Imported " + index + " of " + issues.size() + " issues...";

          SwingUtilities.invokeLater(new Runnable() {
            public void run() {
              _status.setText(message);
            }
          });

        }

        JOptionPane.showMessageDialog(null, "Import complete; " + index + " issues were imported.");

      } catch (Exception e) {
        // @todo add logging
        JOptionPane.showMessageDialog(null, "Import failed after importing " + index + " issues.");
      }

    } else {
      JOptionPane.showMessageDialog(null, "No issues to import.");
    }
  }

}

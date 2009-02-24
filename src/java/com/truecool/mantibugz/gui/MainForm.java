package com.truecool.mantibugz.gui;

import com.truecool.mantibugz.MantiBugzController;
import org.mantisbt.connect.model.IIssue;
import org.mantisbt.connect.model.IProject;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: mpzarde
 * Date: Feb 18, 2009
 * Time: 4:59:28 PM
 */
public class MainForm {
  private JPanel _mainPanel;
  private JTextField _mantisURL;
  private JTextField _fogBugzURL;
  private JTextField _fogBugzUid;
  private JTextField _fogBugzPwd;
  private JTextField _mantisSOAPAccessPoint;
  private JTextField _mantisUid;
  private JTextField _mantisPwd;
  private JButton _mantisConnectButton;
  private JLabel _mantisStatus;
  private JButton _fogBugzConnectButton;
  private JLabel _fogBugzStatus;
  private JComboBox _mantisProject;
  private JComboBox _fogBugzProject;
  private JTable _mantisIssues;
  private JScrollPane _mantisIssuesPane;
  private JButton _goButton;
  private JCheckBox _filterResolvedCheckBox;

  private MantiBugzController _controller;
  private static final int RESOLVED_STATE = 80;

  public MainForm(MantiBugzController controller) {
    _controller = controller;

    initializeUIComponents();

    _mantisConnectButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
        mantisConnect();
      }
    });

    _fogBugzConnectButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
        fogBuzConnect();
      }
    });

    _mantisProject.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        SwingUtilities.invokeLater(new Runnable() {
          public void run() {
            updateIssuesTable();
          }
        });
      }
    });

    _goButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        runFogBugzImport();
      }
    });

    _filterResolvedCheckBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        filterMantisIssues();
      }
    });
  }

  /**
   * Components were already created, we're just resetting column sizes and stuff
   */
  private void initializeUIComponents() {
    ProjectIssuesTableModel model = new ProjectIssuesTableModel();

    _mantisIssues.setModel(model);

    TableColumnModel columnModel = _mantisIssues.getColumnModel();
    columnModel.getColumn(0).setPreferredWidth(50);
    columnModel.getColumn(1).setPreferredWidth(325);
    columnModel.getColumn(2).setPreferredWidth(75);

    _mantisIssuesPane.setViewportView(_mantisIssues);

    setData(_controller);

  }

  public void setData(MantiBugzController controller) {
    _mantisURL.setText(controller.getMantisConnection().getBaseURL());
    _mantisSOAPAccessPoint.setText(controller.getMantisConnection().getSoapAccessPoint());
    _mantisUid.setText(controller.getMantisConnection().getUid());
    _mantisPwd.setText(controller.getMantisConnection().getPwd());

    _fogBugzURL.setText(controller.getFogBugzConnection().getBaseURL());
    _fogBugzUid.setText(controller.getFogBugzConnection().getUid());
    _fogBugzPwd.setText(controller.getFogBugzConnection().getPwd());
  }

  public void getData(MantiBugzController controller) {
    controller.getMantisConnection().setBaseURL(_mantisURL.getText());
    controller.getMantisConnection().setSoapAccessPoint(_mantisSOAPAccessPoint.getText());
    controller.getMantisConnection().setUid(_mantisUid.getText());
    controller.getMantisConnection().setPwd(_mantisPwd.getText());

    controller.getFogBugzConnection().setBaseURL(_fogBugzURL.getText());
    controller.getFogBugzConnection().setUid(_fogBugzUid.getText());
    controller.getFogBugzConnection().setPwd(_fogBugzPwd.getText());

    IProject selectedProject = null;
    List<IProject> projects = null;

    String selectedProjectName = (String) _mantisProject.getSelectedItem();

    if (controller.getMantisConnection().isConnected() && selectedProjectName != null) {

      try {
        projects = controller.getMantisConnection().getProjects();
      } catch (Exception e) {
        // @todo add logging
        JOptionPane.showMessageDialog(null, "An error occured fetching the Mantis projects.");
        projects = null;
      }

      if (projects != null && projects.size() > 0) {

        for (int index = 0; index < projects.size(); index++) {
          IProject project = projects.get(index);
          if (selectedProjectName.equals(project.getName())) {
            selectedProject = project;
            break;
          }
        }

        controller.getMantisConnection().setCurrentProject(selectedProject);
      }
    }

    selectedProject = null;
    selectedProjectName = (String) _fogBugzProject.getSelectedItem();

    if (controller.getFogBugzConnection().isConnected() && selectedProjectName != null) {

      try {
        projects = controller.getFogBugzConnection().getProjects();
      } catch (Exception e) {
        // @todo add logging
        JOptionPane.showMessageDialog(null, "An error occured fetching the FogBugz projects.");
        projects = null;
      }

      if (projects != null && projects.size() > 0) {

        for (int index = 0; index < projects.size(); index++) {
          IProject project = projects.get(index);
          if (selectedProjectName != null && selectedProjectName.equals(project.getName())) {
            selectedProject = project;
            break;
          }
        }
        controller.getFogBugzConnection().setCurrentProject(selectedProject);
      }
    }

    boolean filterMantisIssues = _filterResolvedCheckBox.isSelected();
    controller.setFilterMantisIssues(filterMantisIssues);
  }

  public boolean isModified() {
    return false;
  }

  public Component getRootContainer() {
    return _mainPanel;
  }


  private void mantisConnect() {
    getData(_controller);

    try {
      _controller.getMantisConnection().connect();

      List<IProject> projects = _controller.getMantisConnection().getProjects();

      final DefaultComboBoxModel projectsModel = new DefaultComboBoxModel();

      if (projects != null && projects.size() > 0) {
        for (int index = 0; index < projects.size(); index++) {
          IProject iProject = projects.get(index);
          projectsModel.addElement(iProject.getName());
        }

      } else {
        projectsModel.addElement("No projects");
      }

      _mantisProject.setModel(projectsModel);

      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          updateIssuesTable();
        }
      });

      _mantisStatus.setText("Connected");

    } catch (Exception e) {
      // @todo add logging
      _mantisStatus.setText("Not Connected");
      JOptionPane.showMessageDialog(null, "An error occured trying to connect to Mantis, pls check your settings and try again.");
    }

  }

  private void fogBuzConnect() {
    getData(_controller);

    try {
      _controller.getFogBugzConnection().connect();

      if (!_controller.getFogBugzConnection().isConnected()) {
        throw new IllegalStateException("Not connected to FogBugz");
      }

      List<IProject> projects = _controller.getFogBugzConnection().getProjects();

      final DefaultComboBoxModel projectsModel = new DefaultComboBoxModel();

      if (projects != null && projects.size() > 0) {
        for (int index = 0; index < projects.size(); index++) {
          IProject iProject = projects.get(index);
          projectsModel.addElement(iProject.getName());
        }

      } else {
        projectsModel.addElement("No projects");
      }

      _fogBugzProject.setModel(projectsModel);

      _fogBugzStatus.setText("Connected");

    } catch (Exception e) {
      // @todo add logging
      _fogBugzStatus.setText("Not Connected");
      JOptionPane.showMessageDialog(null, "An error occured trying to connect to FogBugz, pls check your settings and try again.");
    }

  }

  private void updateIssuesTable() {

    Cursor hourglassCursor = Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
    Cursor normalCursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);

    getData(_controller);

    try {

      getRootContainer().getParent().setCursor(hourglassCursor);
      ProjectIssuesTableModel model = (ProjectIssuesTableModel) _mantisIssues.getModel();

      IProject selectedProject = _controller.getMantisConnection().getCurrentProject();

      if (selectedProject != null) {
        List<IIssue> projectIssues = _controller.getMantisConnection().getIssues(selectedProject);

        if (_controller.isFilterMantisIssues()) {
          List<IIssue> filteredIssues = new ArrayList<IIssue>();

          for (int index = 0; index < projectIssues.size(); index++) {
            IIssue issue = projectIssues.get(index);

            if (issue.getStatus().getId() < RESOLVED_STATE) {
              filteredIssues.add(issue);
            }
          }

          model.setIssues(filteredIssues);
        } else {

          model.setIssues(projectIssues);
        }

        _mantisIssuesPane.setViewportView(_mantisIssues);
      }

    }

    catch (
        Exception e
        )

    {
      // @todo add logging
      JOptionPane.showMessageDialog(null, "An error occured trying to retrieve Mantis project issues, pls check your settings and try again.");
    }

    finally

    {
      getRootContainer().getParent().setCursor(normalCursor);
    }

  }

  private void runFogBugzImport() {
    getData(_controller);

    // To avoid re-fetching them we just use the ones currently in the table model
    ProjectIssuesTableModel model = (ProjectIssuesTableModel) _mantisIssues.getModel();
    _controller.setSourceIssues(model.getIssues());

    TranslationProgressDialogForm dialog = new TranslationProgressDialogForm(_controller);
    dialog.centerOnScreen();
    dialog.pack();
    dialog.setVisible(true);

  }

  private void filterMantisIssues() {
    getData(_controller);
    updateIssuesTable();
  }

}

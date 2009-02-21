package com.truecool.mantibugz.gui;

import com.truecool.mantibugz.MantiBugzController;
import org.mantisbt.connect.model.IIssue;
import org.mantisbt.connect.model.IProject;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: mpzarde
 * Date: Feb 18, 2009
 * Time: 4:59:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class MainForm {
  private JPanel _mainPanel;
  private JPanel _connectionsPanel;
  private JPanel _issuesPanel;
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
  private JButton _goButton;
  private JTable _mantisIssues;
  private JScrollPane _mantisIssuesPane;

  private MantiBugzController _controller;

  public MainForm(MantiBugzController controller) {
    createUIComponents();
    _controller = controller;

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
  }

  private void createUIComponents() {
    _mantisIssues = new JTable(new ProjectIssuesTableModel());
    _mantisIssuesPane = new JScrollPane(_mantisIssues);
    _mantisIssues.setPreferredScrollableViewportSize(new Dimension(450, 400));
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

    getData(_controller);

    try {

      String selectedProjectName = (String) _mantisProject.getSelectedItem();
      IProject selectedProject = null;

      List<IProject> projects = _controller.getMantisConnection().getProjects();

      if (projects != null && projects.size() > 0) {

        for (int index = 0; index < projects.size(); index++) {
          IProject project = projects.get(index);
          if (selectedProjectName.equals(project.getName())) {
            selectedProject = project;
            break;
          }
        }

        if (selectedProject != null) {
          List<IIssue> issues = _controller.getMantisConnection().getIssues(selectedProject);
          ProjectIssuesTableModel issuesModel = (ProjectIssuesTableModel) _mantisIssues.getModel();
          issuesModel.setIssues(issues);
          _mantisIssues.tableChanged(new TableModelEvent(issuesModel));
          _mantisIssues.repaint();
          _mantisIssuesPane.setViewportView(_mantisIssues);
          System.out.println("Model updated...loaded " + issues.size() + " issues.");
        }
      }
    } catch (Exception e) {
      // @todo add logging
      JOptionPane.showMessageDialog(null, "An error occured trying to retrieve Mantis project issues, pls check your settings and try again.");
    }
  }

}

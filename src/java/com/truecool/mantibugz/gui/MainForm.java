package com.truecool.mantibugz.gui;

import com.truecool.mantibugz.MantiBugzController;

import javax.swing.*;

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
  private JButton _connectButton;
  private JLabel _fogBugzStatus;
  private JComboBox _mantisProject;
  private JComboBox _fogBugzProject;
  private JButton _goButton;
  private JTable _mantisIssues;

  private MantiBugzController _controller;

  public MainForm(MantiBugzController controller) {
    _controller = controller;
  }

  private void createUIComponents() {
    // TODO: place custom component creation code here 
  }

  public void setData(MantiBugzController data) {
    _mantisURL.setText(data.getMantisConnection().getBaseURL());
    _mantisSOAPAccessPoint.setText(data.getMantisConnection().getSoapAccessPoint());
    _mantisUid.setText(data.getMantisConnection().getUid());
    _mantisPwd.setText(data.getMantisConnection().getPwd());

    _fogBugzURL.setText(data.getFogBugzConnection().getBaseURL());
    _fogBugzUid.setText(data.getFogBugzConnection().getUid());
    _fogBugzPwd.setText(data.getFogBugzConnection().getPwd());
  }

  public void getData(MantiBugzController data) {
    data.getMantisConnection().setBaseURL(_mantisURL.getText());
  }

  public boolean isModified(MantiBugzController data) {
    return false;
  }
}

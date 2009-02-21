package com.truecool.mantibugz.gui;

import org.mantisbt.connect.model.IIssue;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: mpzarde
 * Date: Feb 20, 2009
 * Time: 2:57:52 PM
 */
public class ProjectIssuesTableModel extends AbstractTableModel {
  private String _columnNames[] = {"Bug ID", "Summary", "Status"};
  private List<IIssue> _issues;

  public ProjectIssuesTableModel() {
    super();
  }

  public void setIssues(List<IIssue> issues) {
    _issues = issues;
    fireTableDataChanged();
  }

  public List<IIssue> getIssues() {
    return _issues;
  }

  public int getRowCount() {
    int count = 0;

    if (_issues != null) {
      count = _issues.size();
    }

    return count;
  }

  public int getColumnCount() {
    return _columnNames.length;
  }

  public String getColumnName(int column) {
    return _columnNames[column];
  }

  public Object getValueAt(int rowIndex, int columnIndex) {
    if (_issues == null) {
      return new Object();
    } else {

      IIssue issue = _issues.get(rowIndex);

      switch (columnIndex) {
        case 0:
          return issue.getId();
        case 1:
          return issue.getSummary();
        case 2:
          return issue.getStatus().getName();
        default:
          return new Object();
      }
    }

  }
}

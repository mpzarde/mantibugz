package com.truecool.mantibugz;

import com.truecool.mantibugz.fogbugz.FogBugzConnection;
import com.truecool.mantibugz.mantis.MantisConnection;
import org.mantisbt.connect.model.IIssue;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: mpzarde
 * Date: Feb 18, 2009
 * Time: 2:15:03 PM
 */
public class MantiBugzController {
  private MantisConnection _mantisConnection;
  private FogBugzConnection _fogBugzConnection;
  private List<IIssue> _sourceIssues;
  private boolean _filterMantisIssues;


  public MantisConnection getMantisConnection() {
    if (_mantisConnection == null) {
      _mantisConnection = new MantisConnection();
    }

    return _mantisConnection;
  }

  public FogBugzConnection getFogBugzConnection() {
    if (_fogBugzConnection == null) {
      _fogBugzConnection = new FogBugzConnection();
    }

    return _fogBugzConnection;
  }

  public List<IIssue> getSourceIssues() {
    return _sourceIssues;
  }

  public void setSourceIssues(List<IIssue> sourceIssues) {
    _sourceIssues = sourceIssues;
  }

  public boolean isFilterMantisIssues() {
    return _filterMantisIssues;
  }

  public void setFilterMantisIssues(boolean filterMantisIssues) {
    _filterMantisIssues = filterMantisIssues;
  }
}

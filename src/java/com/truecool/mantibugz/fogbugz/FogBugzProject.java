package com.truecool.mantibugz.fogbugz;

import org.mantisbt.connect.AccessLevel;
import org.mantisbt.connect.model.IMCAttribute;
import org.mantisbt.connect.model.IProject;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: mpzarde
 * Date: Feb 18, 2009
 * Time: 2:13:19 PM
 */
public class FogBugzProject implements IProject {
  private Map<String, String> _bugzData;

  public FogBugzProject(Map<String, String> bugzData) {
    _bugzData = bugzData;
  }

  public AccessLevel getAccessLevelMin() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  public String getDescription() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  public boolean isEnabled() {
    return false;  //To change body of implemented methods use File | Settings | File Templates.
  }

  public String getFilePath() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  public long getId() {
    return Long.parseLong(this._bugzData.get("ixProject"));
  }

  public String getName() {
    return this._bugzData.get("sProject");
  }

  public IMCAttribute getStatus() {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  public IProject[] getSubProjects() {
    return new IProject[0];  //To change body of implemented methods use File | Settings | File Templates.
  }

  public boolean isPrivate() {
    return false;  //To change body of implemented methods use File | Settings | File Templates.
  }

  public void setAccessLevelMin(AccessLevel accessLevel) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  public void setDesription(String s) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  public void setEnabled(boolean b) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  public void setFilePath(String s) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  public void setName(String s) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  public void setSubProjects(IProject[] iProjects) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  public void setPrivate(boolean b) {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  public IProject getSubProject(long l) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  public IProject getSubProject(String s) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }
}

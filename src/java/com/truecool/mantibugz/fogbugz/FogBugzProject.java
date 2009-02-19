package com.truecool.mantibugz.fogbugz;

import org.mantisbt.connect.AccessLevel;
import org.mantisbt.connect.model.IMCAttribute;
import org.mantisbt.connect.model.IProject;
import org.mantisbt.connect.model.MCAttribute;

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
    return AccessLevel.ANYBODY;
  }

  public String getDescription() {
    return getName();
  }

  public boolean isEnabled() {
    return true;
  }

  public String getFilePath() {
    return null;
  }

  public long getId() {
    return Long.parseLong(this._bugzData.get("ixProject"));
  }

  public String getName() {
    return this._bugzData.get("sProject");
  }

  public IMCAttribute getStatus() {
    return new MCAttribute(0, "NONE");
  }

  public IProject[] getSubProjects() {
    return new IProject[0];
  }

  public boolean isPrivate() {
    return false;
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

package com.truecool.mantibugz.mantis;

import com.truecool.mantibugz.connection.IConnection;
import com.truecool.mantibugz.connection.Result;
import org.mantisbt.connect.AccessLevel;
import org.mantisbt.connect.IMCSession;
import org.mantisbt.connect.axis.MCSession;
import org.mantisbt.connect.model.IAccount;
import org.mantisbt.connect.model.IIssue;
import org.mantisbt.connect.model.IProject;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: mpzarde
 * Date: Feb 18, 2009
 * Time: 11:27:17 AM
 */
public class MantisConnection implements IConnection {

  private String _baseURL;
  private String _soapAccessPoint = "api/soap/mantisconnect.php";
  private String _uid;
  private String _pwd;
  private IMCSession _session;
  private boolean _connected;

  private IProject _currentProject;

  public MantisConnection() {
    this._connected = false;
  }

  public void connect() throws Exception {
    if (this._baseURL == null) {
      throw new IllegalArgumentException("Base URL cannot be null or empty");
    }

    if (this._soapAccessPoint == null) {
      throw new IllegalArgumentException("SOAP access point cannot be null or empty");
    }

    if (this._uid == null) {
      throw new IllegalArgumentException("User ID cannot be null or empty");
    }

    if (this._pwd == null) {
      throw new IllegalArgumentException("User password cannot be null or empty");
    }

    URL mantisURL = new URL(this._baseURL + "/" + this._soapAccessPoint);

    this._session = new MCSession(mantisURL, this._uid, this._pwd);

    this._connected = true;
  }

  public void disconnect() throws Exception {
    this._session.flush();
    this._session = null;
    this._connected = false;
  }

  public String getBaseURL() {
    return _baseURL;
  }

  public List<IProject> getProjects() throws Exception {
    IProject projects[] = this._session.getAccessibleProjects();

    if (projects != null && projects.length > 0) {
      return Arrays.asList(projects);
    }

    return null;
  }

  public List<IIssue> getIssues(IProject project) throws Exception {
    if (project == null) {
      throw new IllegalArgumentException("Project cannot be null or empty");
    }

    IIssue issues[] = this._session.getProjectIssues(project.getId(), 1000);

    if (issues != null && issues.length > 0) {
      return Arrays.asList(issues);
    }

    return null;
  }

  public List<IAccount> getUsers(IProject project) throws Exception {
    if (project == null) {
      throw new IllegalArgumentException("Project cannot be null or empty");
    }

    IAccount users[] = this._session.getProjectUsers(project.getId(), AccessLevel.ANYBODY);

    if (users != null && users.length > 0) {
      return Arrays.asList(users);
    }

    return null;

  }

  public Result createNewIssue(IIssue issue, IProject project) throws Exception {
    // @TODO - empty implementation for now
    //
    return null;
  }


  public IProject getCurrentProject() {
    return _currentProject;
  }

  public void setCurrentProject(IProject currentProject) {
    _currentProject = currentProject;
  }

  public boolean isConnected() {
    return _connected;
  }

  public void setBaseURL(String baseURL) {
    _baseURL = baseURL;
  }

  public String getSoapAccessPoint() {
    return _soapAccessPoint;
  }

  public void setSoapAccessPoint(String soapAccessPoint) {
    _soapAccessPoint = soapAccessPoint;
  }

  public String getUid() {
    return _uid;
  }

  public void setUid(String uid) {
    _uid = uid;
  }

  public String getPwd() {
    return _pwd;
  }

  public void setPwd(String pwd) {
    _pwd = pwd;
  }

  public IMCSession getSession() {
    return _session;
  }

  public void setSession(IMCSession session) {
    _session = session;
  }


}

package com.truecool.mantibugz.fogbugz;

import com.truecool.mantibugz.connection.IConnection;
import com.truecool.mantibugz.connection.Result;
import org.apache.xerces.parsers.DOMParser;
import org.mantisbt.connect.model.IAccount;
import org.mantisbt.connect.model.IIssue;
import org.mantisbt.connect.model.IProject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: mpzarde
 * Date: Feb 18, 2009
 * Time: 11:26:44 AM
 */
public class FogBugzConnection implements IConnection {
  private static final String API_XML_PATH = "api.xml";

  private String _baseURL;
  private String _realURL;
  private String _uid;
  private String _pwd;
  private String _token;
  private boolean _connected;

  private IProject _currentProject;

  public FogBugzConnection() {
  }

  public void connect() throws Exception {

    if (this._baseURL == null) {
      throw new IllegalArgumentException("Base URL cannot be null or empty");
    }

    if (this._uid == null) {
      throw new IllegalArgumentException("User ID cannot be null or empty");
    }

    if (this._pwd == null) {
      throw new IllegalArgumentException("User password cannot be null or empty");
    }

    String apiURL = this._baseURL + "/" + API_XML_PATH;
    Map response = executeSimplecommand(apiURL);

    this._realURL = this._baseURL + "/" + response.get("url");

    String loginURL = this._realURL + "cmd=logon&email=" + _uid + "&password=" + this._pwd;

    response = executeSimplecommand(loginURL);

    this._token = (String) response.get("token");

    this._connected = true;

  }

  public void disconnect() throws Exception {
    String logoffURL = this._realURL + "cmd=logoff&token=" + this._token;
    executeSimplecommand(logoffURL);
    this._connected = false;
  }

  public List<IProject> getProjects() throws Exception {
    List<IProject> projects = new ArrayList<IProject>();

    String commandURL = this._realURL + "token=" + this._token + "&cmd=listProjects";
    List<Map<String, String>> response = executeListCommand(commandURL);

    if (response != null && response.size() > 0) {
      for (int index = 0; index < response.size(); index++) {
        Map<String, String> projectMap = response.get(index);
        FogBugzProject project = new FogBugzProject(projectMap);
        projects.add(project);
      }
    }

    return projects;
  }

  public List<IIssue> getIssues(IProject project) throws Exception {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  public List<IAccount> getUsers(IProject project) throws Exception {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  public Result createNewIssue(IIssue issue) throws Exception {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  public IProject getCurrentProject() {
    return _currentProject;
  }

  public void setCurrentProject(IProject currentProject) {
    _currentProject = currentProject;
  }

  public boolean isConnected() {
    return this._connected;
  }


  public String getBaseURL() {
    return _baseURL;
  }

  public void setBaseURL(String baseURL) {
    _baseURL = baseURL;
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

  protected Map<String, String> executeSimplecommand(String url) throws SAXException, IOException {
    Map map = new HashMap();

    DOMParser parser = new DOMParser();
    parser.parse(url);

    Document document = parser.getDocument();

    Node node = document.getFirstChild().getFirstChild();
    
    while (node != null) {
      Node parentNode = node;
      
      while (node != null) {
        if (node.getNodeName() != null) {
          map.put(node.getNodeName(), node.getTextContent());
        }
        node = node.getNextSibling();
      }
      node = parentNode.getFirstChild();
    }
    return map;
  }

  protected List<Map<String, String>> executeListCommand(String url) throws SAXException, IOException {
    List<Map<String, String>> list = new ArrayList<Map<String, String>>();

    DOMParser parser = new DOMParser();
    parser.parse(url);

    Document document = parser.getDocument();

    Node node = document.getFirstChild().getFirstChild().getFirstChild();

    while (node != null) {
      Map map = new HashMap();
      Node parentNode = node;

      node = node.getFirstChild();

      // List level nodes - i.e. project
      while (node != null) {
        // List item nodes - i.e. project data
        if (node.getNodeName() != null) {
          map.put(node.getNodeName(), node.getTextContent());
        }
        
        node = node.getNextSibling();
      }
      list.add(map);

      node = parentNode.getNextSibling();

    }

    return list;
  }


}

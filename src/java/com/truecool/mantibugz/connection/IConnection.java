package com.truecool.mantibugz.connection;

import org.mantisbt.connect.model.IAccount;
import org.mantisbt.connect.model.IIssue;
import org.mantisbt.connect.model.IProject;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: mpzarde
 * Date: Feb 18, 2009
 * Time: 11:23:26 AM
 */
public interface IConnection {
  public void connect() throws Exception;
  public void disconnect() throws Exception;
  public List<IProject>getProjects() throws Exception;
  public List<IIssue>getIssues(IProject project) throws Exception;
  public List<IAccount>getUsers(IProject project) throws Exception;
  public Result createNewIssue(IIssue issue, IProject project) throws Exception;
  public boolean isConnected();
}

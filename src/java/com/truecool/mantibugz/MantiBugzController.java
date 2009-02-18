package com.truecool.mantibugz;

import com.truecool.mantibugz.fogbugz.FogBugzConnection;
import org.mantisbt.connect.model.IProject;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: mpzarde
 * Date: Feb 18, 2009
 * Time: 2:15:03 PM
 */
public class MantiBugzController {
  public static void main(String[] args) {
    FogBugzConnection connection = new FogBugzConnection();
    connection.setBaseURL("http://pancho.truecool.com/fogbugz");
    connection.setUid("mpzarde@truecool.com");
    connection.setPwd("shareen2");
    try {
      connection.connect();
      List<IProject> projects = connection.getProjects();

      if (projects != null && projects.size() > 0) {
        for (int index = 0; index < projects.size(); index++) {
          IProject project = projects.get(index);
          System.out.println(project.getName());

        }
      }

      connection.disconnect();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}

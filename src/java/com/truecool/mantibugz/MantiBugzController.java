package com.truecool.mantibugz;

import com.truecool.mantibugz.fogbugz.FogBugzConnection;
import com.truecool.mantibugz.mantis.MantisConnection;

/**
 * Created by IntelliJ IDEA.
 * User: mpzarde
 * Date: Feb 18, 2009
 * Time: 2:15:03 PM
 */
public class MantiBugzController {
  private MantisConnection _mantisConnection;
  private FogBugzConnection _fogBugzConnection;


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

}

package com.truecool.mantibugz.connection;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: mpzarde
 * Date: Feb 18, 2009
 * Time: 1:10:52 PM
 */
public class Result {
  private boolean _success;
  private List<String> _messages;
  private List<Throwable> _exceptions;

  public Result() {
  }

  public boolean isSuccess() {
    return _success;
  }

  public void setSuccess(boolean success) {
    _success = success;
  }

  public List<String> getMessages() {
    return _messages;
  }

  public List<Throwable> getExceptions() {
    return _exceptions;
  }

  public void addMessage(String message) {
    if (_messages == null) {
      _messages = new ArrayList<String>();
    }

    _messages.add(message);
  }

  public void addException(Throwable exception) {
    if (_exceptions == null) {
      _exceptions = new ArrayList<Throwable>();
    }

    _exceptions.add(exception);
  }


  public static Result getDefaultResult() {
    Result result = new Result();
    result._success = true;
    return result;
  }
}

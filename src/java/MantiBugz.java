import com.truecool.mantibugz.MantiBugzController;
import com.truecool.mantibugz.gui.ApplicationFrame;
import com.truecool.mantibugz.gui.MainForm;

/**
 * Created by IntelliJ IDEA.
 * User: mpzarde
 * Date: Feb 20, 2009
 * Time: 1:32:14 PM
 */
public class MantiBugz {
  public static void main(String[] args) {
    //Schedule a job for the event-dispatching thread:
    //creating and showing this application's GUI.
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        MainForm mainForm = new MainForm(new MantiBugzController());
        ApplicationFrame frame = new ApplicationFrame(mainForm);
        frame.setVisible(false);
        frame.setTitle("MantiBugz 1.0");
        frame.pack();
        frame.centerOnScreen();
        frame.setVisible(true);
      }
    });

  }
}

// Copyright 17-Mar-2019 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>


import es.dm.Std;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.stream.*;

public class Main {

  static final String libjdm = "/dm/dmJava/lib/libjdm/pack/libjdm.jar";

  static JFrame frame = null;
  static JTextArea console = null;

  static void log(String e) {
    if (frame == null) {
      frame = new JFrame("Console jsh");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      JPanel pn = new JPanel();
      pn.setLayout(new BorderLayout());
      console = new JTextArea(e + "\n");
      console.setLineWrap(true);
      console.setFont(new Font("monospaced", Font.PLAIN, 12));
      console.setTabSize(4);
      console.setEditable(false);
      JScrollPane scr = new JScrollPane();
      scr.setHorizontalScrollBarPolicy(
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
      );
      scr.setPreferredSize(new Dimension(800, 600));
      scr.setViewportView(console);
      JButton cerrar = new JButton("Cerrar");
      cerrar.addActionListener((ActionEvent e1) -> {
        frame.dispose();
      });
      pn.add(scr, BorderLayout.CENTER);
      pn.add(cerrar, BorderLayout.SOUTH);
      frame.add(pn);
      frame.pack();
      Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
      frame.setLocation(
        dim.width / 2 - frame.getSize().width / 2,
        dim.height / 2 - frame.getSize().height / 2);
      frame.setVisible(true);
    } else {
      console.append(e + "\n");
    }
  }


  public static boolean outOfDate(String fjava, String fclass) {
    return Std.fmodified(fjava) > Std.fmodified(fclass);
  }

  public static boolean compile(String fjava, String dir) {
    Std.mkdir(dir);
    String msg = Std.cmd(
      "javac",
      "-cp",
      libjdm,
      "-d",
      dir,
      fjava
    );
    if (msg.length() != 0) {
      log(msg);
      return false;
    }
    return true;
  }

  public static String execute(String fclass) {
    return Std.cmd(
      "java",
      "-cp",
      libjdm + ":" + Std.fparent(fclass),
      Std.fonlyName(fclass)
    );
  }

  public static void main(String[] args) {
    Std.sysInit("jsh");

    if (args.length != 1) {
      log("File java is missing.");
      return;
    }

    try {
      String fjava = new File(args[0]).getCanonicalPath();
      if (!Std.fextension(fjava).equals(".java")) {
        throw new IllegalArgumentException(
          "'" + fjava + "' is not a java file"
        );
      }

      String fclass = Std.fpath(
        Std.home(),
        Std.fparent(fjava).substring(1),
        Std.fonlyName(fjava),
        Std.fonlyName(fjava) + ".class"
      );

      boolean exec = true;
      if (!Std.fexists(fclass) || outOfDate(fjava, fclass)) {
        exec = compile(fjava, Std.fparent(fclass));
      }

      if (exec) {
        String r = execute(fclass);
        if (r.length() != 0) {
          log(r);
        }
      }
    } catch (Exception e) {
      log(Std.stackTace(e));
      return;
    }

  }
}

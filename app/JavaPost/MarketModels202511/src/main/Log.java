// Copyright 18-Mar-2024 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package main;

import java.util.ArrayList;
import kut.*;

/**
  Log utility.<p>
  Function 'setPath' must be called before any other function!!!.<p>
  Log table is initialized with 'reset'.
*/
public class Log {
  private Log(){};

  static String fileOp = null;
  static int sizeOp = 0;

  /**
    Set file path of log.
    @param fpath Log file path.
    @param size Log lines number.
  */
  public static void init (String fpath, int size) {
    fileOp = fpath;
    sizeOp = size;
  }

  static String date () {
    return Time.fmt(Time.now(), "%D/%M/%Y(%t)");
  }

  static void write (ArrayList<Tp3<Boolean, String, String>> l) {
    File.write(fileOp, Js.w(
      Arr.map(l, (tp) -> {
        return Js.w(Arr.mk(Js.w(tp.e1), Js.w(tp.e2), Js.w(tp.e3)));
      })
    ));
  }

  static ArrayList<Tp3<Boolean, String, String>> read () {
    return Arr.map(
      Js.ra(File.read(fileOp)),
      (j) -> {
        ArrayList<String> a = Js.ra(j);
        return new Tp3<>(Js.rb(a.get(0)), Js.rs(a.get(1)), Js.rs(a.get(2)));
      }
    );
  }

  /**
  Returns the log serialized.
  @return The log serialized.
  */
  public static String readJs () {
    return File.read(fileOp);
  }

  /**
  Adds a warning to log.
  @param msg Message.
  */
  public static void warning (String msg) {
    ArrayList<Tp3<Boolean, String, String>> l = read();
    l.add(new Tp3<>(false, date(), Str.left(msg, 400)));
    write(Arr.drop(l, l.size() - sizeOp));
  }

  /**
  Adds an error to log.
  @param msg Message.
  */
  public static void error (String msg) {
    ArrayList<Tp3<Boolean, String, String>> l = read();
    l.add(new Tp3<>(true, date(), Str.left(msg, 400)));
    write(Arr.drop(l, l.size() - sizeOp));
  }

  /** Reset log. */
  public static void reset () {
    File.write(fileOp, "[]");
  }
}

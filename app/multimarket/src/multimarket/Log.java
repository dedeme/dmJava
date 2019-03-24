// Copyright 19-Mar-2019 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package multimarket;

import es.dm.Std;
import es.dm.Js;
import es.dm.Date;
import java.util.ArrayList;

/** Log class */
public class Log {
  static String path = null;

  static void init () {
    path = Std.fpath(Util.dataDir(), "log.txt");
    if (!Std.fexists(path)) {
      Std.write(path, "[]");
    }
  }

  // prefix can be 'E' or 'W'
  static String mkMsg (String prefix, String msg) {
    return prefix + Date.dtToStr(Date.tnow()) + " - " + msg;
  }

  static void writeLog(String prefix, String format, Object... args) {
    String js = Std.read(path);
    ArrayList<Js> a = new Js(js).rArray();
    while (a.size() > Ct.LOG_ENTRIES) {
      a.remove(a.size() - 1);
    }
    a.add(0, Js.write(mkMsg(prefix, String.format(format, args))));
    Std.write(path, Js.write(a).toString());
  }

  /**
   * Writes a error message.
   * @param format Equals to String.format
   * @param args Equals to String.format
   */
  public static void write(String format, Object... args) {
    writeLog("E", format, args);
  }

  /**
   * Writes a warning message.
   * @param format Equals to String.format
   * @param args Equals to String.format
   */
  public static void writew(String format, Object... args) {
    writeLog("W", format, args);
  }

}

// Copyright 19-Mar-2019 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package multimarket;

import java.util.Map;
import es.dm.Std;
import es.dm.Cgi;
import es.dm.Js;

/** Utilities class */
public class Util {
  static Cgi cgi = null;

  /** Create application Cgi */
  public static void mkCgi () {
    cgi = new Cgi(3600);
  }

  /**
   * Returns application Cgi.
   * @return Application Cgi.
   */
  public static Cgi getCgi () {
    return cgi;
  }

  /**
   * Produces a 'ok' response.
   * @param rp Response
   * @return Response
   */
  public static String ok (Map<String, Js> rp) {
    return cgi.ok(rp);
  }

  /**
   * Produces an empty response.
   * @return Response
   */
  public static String empty () {
    return cgi.empty();
  }

  /**
   * Reads 'key' from 'rq' or throw an IllegalArgumentException.
   * @param rq Request
   * @param key Key
   * @return Value of key
   */
  public static Js js (Map<String, Js> rq, String key) {
    Js r = rq.get(key);
    if (r == null) {
      throw new IllegalArgumentException("Key '" + key + "' is missing");
    }
    return r;
  }

  /**
   * Returns data directory
   * @return Data directory
   */
  public static String dataDir () {
    return Std.fpath(Std.home(), "data");
  }

  /**
   * Does a task synchronizedly.
   * @param fn Task to do
   */
  synchronized public static void sync (Runnable fn) {
    fn.run();
  }

  static public class Wrapper<T> {
    public T value;
  }
}

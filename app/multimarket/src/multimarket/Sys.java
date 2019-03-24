// Copyright 24-Mar-2019 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package multimarket;

import java.util.HashMap;
import es.dm.Js;
import multimarket.sys.*;

/** Sys module hub */
public class Sys {
  /**
   * Request processing.
   * @param rq Request
   * @return Response
   */
  public static String process(HashMap<String, Js> rq) {
    String page = Util.js(rq, "page").rString();

    if (page.equals("SysMain")) {
      return SysMain.process(rq);
    } else if (page.equals("Settings")) {
      return Settings.process(rq);
    } else if (page.equals("Chpass")) {
      return Chpass.process(rq);
    }

    throw new IllegalArgumentException("Unknown page '" + page + "'");
  }
}

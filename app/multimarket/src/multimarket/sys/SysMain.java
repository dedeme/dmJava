// Copyright 24-Mar-2019 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package multimarket.sys;

import java.util.HashMap;
import es.dm.Js;
import multimarket.Util;
import multimarket.data.Conf;

/** SysMain page */
public class SysMain {
  /**
   * Request processing.
   * @param rq Request
   * @return Response
   */
  public static String process(HashMap<String, Js> mrq) {
    String rq = Util.js(mrq, "rq").rString();

    if (rq.equals("idata")) {
      return Util.ok(new HashMap<String, Js> () {{
        put("page", Conf.get("sysMenu"));
      }});
    } else if (rq.equals("go")) {
      Conf.set("sysMenu", Util.js(mrq, "option"));
      return Util.empty();
    }

    throw new IllegalArgumentException("Unknown rq '" + rq + "'");
  }
}

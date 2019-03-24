// Copyright 24-Mar-2019 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package multimarket.sys;

import java.util.HashMap;
import es.dm.Js;
import multimarket.Util;
import multimarket.data.Conf;

/** Settings page */
public class Settings {
  /**
   * Request processing.
   * @param rq Request
   * @return Response
   */
  public static String process(HashMap<String, Js> mrq) {
    String rq = Util.js(mrq, "rq").rString();

    if (rq.equals("setLang")) {
      Conf.set("lang", Util.js(mrq, "lang"));
      return Util.empty();
    } else if (rq.equals("getLang")) {
      return Util.ok(new HashMap<String, Js> () {{
        put("lang", Conf.get("lang"));
      }});
    }

    throw new IllegalArgumentException("Unknown rq '" + rq + "'");
  }
}

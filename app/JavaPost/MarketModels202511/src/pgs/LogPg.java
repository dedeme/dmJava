// Copyright 25-Mar-2024 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package pgs;

import kut.*;
import main.Log;

/** Log Page. */
public class LogPg {
  /**
  Process request.
  @param drq Ditto.
  @return JSON string.
  */
  public static String process (Dic<String> drq) {
    String rq = Js.rs(drq.get("rq"));
    switch (rq) {
      case "getLog": return Js.w(Dic.mk(
          "Log", Log.readJs()
        ));
      case "resetLog": {
        Log.reset();
        return "{}";
      }
      default: throw new RuntimeException(
          "Value of rq (" + rq + ") is not valid"
        );
    }
  }
}


// Copyright 19-Mar-2024 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package test;

import mkt.Volumes;
import extDb.CosTb;
import kut.*;
import main.Cts;

/** Test volumes. */
public class J03volume {
  /**
  Run test.
  @param rq Request.
  @return <pre>
    {
      vol: [i.]
    }
    </pre>
  */
  public static Dic<String> run (Dic<String> rq) {
    return Dic.mk(
      "vol", Arr.toJs(Arr.map(
        Volumes.read(Cts.quotesPath, 60, CosTb.read()),
        (n) -> (int)(double)n
        ), Js::w)
    );
  }

}

// Copyright 19-Mar-2024 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package test;

import extDb.QuotesDb;
import kut.*;

/** Test quotes. */
public class J02quotes {
  /**
  Run test.
  @param rq Request.
  @return <pre>
    {
      q: &lt;quotes>
    }
    </pre>
  */
  public static Dic<String> run (Dic<String> rq) {
    return Dic.mk(
      "q", QuotesDb.read().toJs()
    );
  }

}

// Copyright 19-Mar-2024 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package test;

import kut.*;

/** Test test-system. */
public class J01test {
  /**
  Run test.
  @param rq Request.
  @return Response.
  */
  public static Dic<String> run (Dic<String> rq) {
    return Dic.mk(
      "ok", Js.w(true)
    );
  }

}

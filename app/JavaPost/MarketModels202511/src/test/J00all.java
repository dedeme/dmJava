// Copyright 19-Mar-2024 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package test;

import kut.*;

/** All tests. */
public class J00all {
  /**
  Process a test request.
  @param rq JSON request.
  @return JSON response.
  */
  public static String process (String rq) {
    Dic<String> mrq = Js.ro(rq);
    String type = Js.rs(mrq.get("type"));
    if (type.equals("test")) return Js.w(J01test.run(mrq));
    else if (type.equals("quotes")) return Js.w(J02quotes.run(mrq));
    else if (type.equals("volume")) return Js.w(J03volume.run(mrq));
    else if (type.equals("refs")) return Js.w(J04refs.run(mrq));
    else if (type.equals("stSimple")) return Js.w(J05stSimple.run(mrq));
    else if (type.equals("stSimple2")) return Js.w(J06stSimple2.run(mrq));
    else if (type.equals("stGroup")) return Js.w(J07stGroup.run(mrq));
    else if (type.equals("stGroup2")) return Js.w(J08stGroup2.run(mrq));
    else if (type.equals("stOpen")) return Js.w(J09stOpen.run(mrq));
    else throw new RuntimeException("Test type '" + type + "' is not valid.");
  }
}

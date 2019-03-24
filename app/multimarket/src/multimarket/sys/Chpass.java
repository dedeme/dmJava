// Copyright 24-Mar-2019 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package multimarket.sys;

import java.util.HashMap;
import es.dm.Js;
import es.dm.Cgi;
import multimarket.Util;
import multimarket.data.Conf;

/** Change password page */
public class Chpass {
  /**
   * Request processing.
   * @param rq Request
   * @return Response
   */
  public static String process(HashMap<String, Js> mrq) {
    Cgi cgi = Util.getCgi();

    String user = Util.js(mrq, "user").rString();
    String pass = Util.js(mrq, "pass").rString();
    String newPass = Util.js(mrq, "newPass").rString();

    return cgi.changeUserPass(user, pass, newPass);
  }
}

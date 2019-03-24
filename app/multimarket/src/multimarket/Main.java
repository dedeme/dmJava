// Copyright 19-Mar-2019 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package multimarket;

import es.dm.Std;
import multimarket.data.Conf;

/** Main class */
public class Main {

  static void dbInit () {
    String dataDir = Util.dataDir();
    if (!Std.fexists(dataDir)) {
      Std.mkdir(dataDir);
      Conf.create();
    }
    Conf.init();
    Log.init();
  }

  static void process () throws Exception {
    for (;;) {
      Thread.sleep(5000);
      if (Port.isClosed()) {
        break;
      }
    }
  }

  /**
   * Entry point.
   * @param args Arguments
   */
  public static void main(String[] args) {
    Std.sysInit(Ct.APP_NAME);

    dbInit();
    Util.mkCgi();

    Log.writew("Server started");

    Port.open();

    try {
      process();
      Log.writew("Server stoped");
    } catch (Exception e) {
      Log.writew("Server stoped");
    }
  }
}

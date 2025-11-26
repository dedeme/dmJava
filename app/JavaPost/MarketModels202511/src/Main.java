// Copyright 18-Mar-2024 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

import kut.*;
import test.J00all;
import main.*;
import pgs.*;

/** Main class.*/
public class Main {
  static String help () {
    return
      "Use MarketModelsJ [help | version | init | update | test <request> | <request>]\n" +
      "where\n" +
      "  help   : Shows this message.\n" +
      "  version: Shows program version.\n" +
      "  init   : Initializes program.\n" +
      "           Must be called only the first time that the program is called.\n" +
      "  test   : Run tests\n" +
      "  update : Updates data base.\n" +
      "  request: Request sent by browser."
    ;
  }

  /**
    Program entry.
    @param args. Arguments.
  */
  public static void main (String[] args) {
    Log.init(Path.cat(Cts.dataPath, "log.tb"), 100);

    if (args.length == 2 && args[0].equals("test")) {
      Sys.print(J00all.process(args[1]));
      return;
    }

    if (args.length != 1 && (args.length != 2 || !args[1].equals(""))) {
      Sys.println(help());
      return;
    }

    String rq = args[0];
    switch (rq) {
      case "update":
        Updater.run();
        break;
      case "version":
        Sys.println(Cts.version);
        break;
      case "init":
        Db.init();
        break;
      case "help":
      case "":
        Sys.println(help());
        break;
      default: {
        Dic<String> drq = Js.ro(rq);
        String source = Js.rs(drq.get("source"));
        switch (source) {
          case "LogPg":
            Sys.print(LogPg.process(drq));
            break;
          case "GlobalPg":
            Sys.print(GlobalPg.process(drq));
            break;
          case "ModelsPg":
            Sys.print(ModelsPg.process(drq));
            break;
          default: throw new RuntimeException(
              "Value of source (" + source + ") is not valid"
            );
        }
      }
    }
  }

}

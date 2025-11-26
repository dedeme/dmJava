// Copyright 05-Dic-2023 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

import p.*;
import kut.*;

/** Program main */
public class Main {

  static String help = "Use: tcpTest [server | client]";

  /**
   * Program start.
   * @param args Program arguments.
   */
  public static void main(String[] args) throws InterruptedException {
    if (args.length != 1) {
      Sys.println(help);
      return;
    }

    if (args[0].equals("server")) Server.start();
    else if (args[0].equals("client")) Client.start();
    else {
      Sys.println(help);
      return;
    }

    System.out.println("Program end.");
  }
}

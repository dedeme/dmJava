// Copyright 02-Dic-2023 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package b;

import java.util.function.Consumer;
import kut.*;

public class Client {

  public static void makerRun () {
    int clIx = 0;
    while (Shop.isPreopen() || Shop.isOpen()) {
      ++clIx;
      final int i = clIx;
      Thr.run(() -> run(mk(i)));
      Sys.sleep(1 + kut.Mth.rndi(Cts.clientMakerTime));
    }
  }

  static String mk (int clIx) {
    return String.valueOf(clIx);
  }

  static void run (String cl) {
    Consumer<String> msg = (m) ->
      System.out.println(Str.fmt("Client %s: %s", cl, m));

    msg.accept("Arrive to barbery");
    if (!Shop.isOpen()) msg.accept("Go out because barbery is close");
    else if (Shop.isFull()) msg.accept("Go out because barbery is full");
    else Thr.sync(()-> Shop.takeASit(cl));
  }
}

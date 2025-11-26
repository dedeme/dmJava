// Copyright 10-Dic-2023 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package extDb;

import java.util.ArrayList;
import kut.File;
import kut.Js;
import kut.Arr;

/** Company nicks reader */
public class CosTb {
  static String path = "/dm/KtWeb/dmcgi/Market/cos.tb";

  /**
   * Returns companies by ascendent alphabetic order.
   * @return Companies by ascendent alphabetic order.
   */
  public static String[] read () {
    String js = File.read(path);
    ArrayList<String> tb = Js.ra(js);
    ArrayList<ArrayList<String>> es = Arr.map(Js.ra(tb.get(1)), Js::ra);
    ArrayList<String> nicks = Arr.map(
      Arr.filter(es, (tp) -> Js.rb(tp.get(1))), (tp) -> Js.rs(tp.get(0))
    );

    Arr.sort(nicks, (c1, c2) -> c1.compareTo(c2) < 0);

    return nicks.toArray(new String[]{});
  }
}

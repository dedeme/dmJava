// Copyright 20-Mar-2024 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package test;

import java.util.ArrayList;
import java.io.FileOutputStream;
import mkt.Models;
import mkt.Model;
import mkt.Quotes;
import kut.*;
import extDb.QuotesDb;
import data.SimParams;

/** Test references. */
public class J04refs {
  /**
  Run test.
  @param rq Request.
  @return <pre>
    {}
    </pre>
  */
  public static Dic<String> run (Dic<String> rq) {
    String jf = Js.rs(rq.get("jf"));
    Quotes qts = QuotesDb.read();
    double[][] cls = qts.closes;
    ArrayList<Model> list = Models.list();
    Arr.sort(list, (m1, m2) ->
      m1.id.compareTo(m2.id) < 0);

    FileOutputStream f = File.wopen(jf);
    for (Model md : list) {
      String mdId = md.id;
      double[] params = SimParams.paramBases(mdId);
      String rfs = Js.w(Arr.map(
        Arr.mk(md.calc.apply(cls, params)),
        (rs) -> Js.w(Arr.map(Arr.mk(rs), (n) -> Js.w(n)))
      ));
      File.writeText(f, mdId + ":" + rfs + "\n");
    }
    File.close(f);

    return Dic.mk();
  }

}

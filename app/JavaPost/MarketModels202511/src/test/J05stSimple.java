// Copyright 21-Mar-2024 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package test;

import java.util.ArrayList;
import java.io.FileOutputStream;
import mkt.Models;
import mkt.Model;
import mkt.Quotes;
import mkt.Strategy;
import mkt.StSimpleRs;
import kut.*;
import extDb.QuotesDb;
import data.SimParams;

/** Strategy simple test. */
class J05stSimple {
  /**
  Run test.
  @param rq Request.
  @return <pre>
    {[f, f, f, f].} :: {[sales, cash, assets, rfAssets].}
    </pre>
  */
  public static Dic<String> run (Dic<String> rq) {
    Quotes qts = QuotesDb.read();
    ArrayList<Model> list = Models.list();

    Dic<String> mdRs = new Dic<>();
    for (Model md : list) {
      double[] params = SimParams.paramBases(md.id);
      double[][] refs = md.calc.apply(qts.closes, params);
      mdRs.put(md.id, StSimpleRs.toJs(Strategy.openSimple(qts, refs)));
    }

    return mdRs;
  }
}


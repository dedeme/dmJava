// Copyright 21-Mar-2024 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package test;

import java.util.ArrayList;
import java.io.FileOutputStream;
import mkt.Models;
import mkt.Model;
import mkt.Quotes;
import mkt.Strategy;
import mkt.StSimple2Rs;
import kut.*;
import extDb.QuotesDb;
import data.SimParams;

/** Strategy simple test. */
class J06stSimple2 {
  /**
  Run test.
  @param rq Request.
  @return <pre>
    {[f, f, f, f, f, f].} :: <br>
      {[sales, cash, assets, rfAssets, profits, rfProfits].}
    </pre>
  */
  public static Dic<String> run (Dic<String> rq) {
    Quotes qts = QuotesDb.read();
    ArrayList<Model> list = Models.list();

    Dic<String> mdRs = new Dic<>();
    for (Model md : list) {
      double[] params = SimParams.paramBases(md.id);
      double[][] refs = md.calc.apply(qts.closes, params);
      mdRs.put(md.id, StSimple2Rs.toJs(Strategy.openSimple2(qts, refs)));
    }

    return mdRs;
  }
}


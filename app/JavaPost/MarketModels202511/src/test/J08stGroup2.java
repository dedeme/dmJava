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
import mkt.StGroup2Rs;
import kut.*;
import extDb.QuotesDb;
import data.SimParams;

/** Strategy simple test. */
class J08stGroup2 {
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
      double[] ps = SimParams.paramBases(md.id);
      double[] incs = SimParams.paramIncrs(md.id);
      double[][] params = new double[20][ps.length];
      for (int i = 0; i < 20; ++i) {
        for (int j = 0; j < ps.length; ++j)
          params[i][j] = ps[j];
        for (int j = 0; j < ps.length; ++j)
          ps[j] += incs[j];
      }

      StGroup2Rs gr = Strategy.stGroup2(md, qts, params);
      int sales = 0;
      double assets = 0;
      double accs = 0;
      double rfAssets = 0;
      double profits = 0;
      double rfProfits = 0;
      for (int v : gr.sales) sales += v;
      for (double v : gr.assets) assets += v;
      for (double v : gr.accs) accs += v;
      for (double v : gr.rfAssets) rfAssets += v;
      for (double v : gr.profits) profits += v;
      for (double v : gr.rfProfits) rfProfits += v;

      mdRs.put(md.id, StSimple2Rs.toJs(new StSimple2Rs(
        sales, assets, accs, rfAssets, profits, rfProfits
      )));
    }

    return mdRs;
  }
}


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
import mkt.StGroupRs;
import kut.*;
import extDb.QuotesDb;
import data.SimParams;

/** Strategy simple test. */
class J07stGroup {
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
      double[] ps = SimParams.paramBases(md.id);
      double[] incs = SimParams.paramIncrs(md.id);
      double[][] params = new double[20][ps.length];
      for (int i = 0; i < 20; ++i) {
        for (int j = 0; j < ps.length; ++j)
          params[i][j] = ps[j];
        for (int j = 0; j < ps.length; ++j)
          ps[j] += incs[j];
      }

      StGroupRs gr = Strategy.stGroup(md, qts, params);
      int sales = 0;
      double assets = 0;
      double accs = 0;
      double rfAssets = 0;
      for (int v : gr.sales) sales += v;
      for (double v : gr.assets) assets += v;
      for (double v : gr.accs) accs += v;
      for (double v : gr.rfAssets) rfAssets += v;

      mdRs.put(md.id, StSimpleRs.toJs(new StSimpleRs(
        sales, assets, accs, rfAssets
      )));
    }

    return mdRs;
  }
}


// Copyright 21-Mar-2024 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package test;

import java.util.ArrayList;
import java.io.FileOutputStream;
import mkt.Models;
import mkt.Model;
import mkt.Quotes;
import mkt.Strategy;
import mkt.StRs;
import kut.*;
import extDb.QuotesDb;
import data.SimParams;

/** Strategy simple test. */
class J09stOpen {
  /**
  Run test.
  @param rq Request.
  @return <pre>
    {{ref:f, orders:i, hassets:f, hwithdrawals:f, cash:f,
      buys:i, sales:i, profits:f}.} ::
      {modelId: Rs}
    </pre>
  */
  public static Dic<String> run (Dic<String> rq) {
    Quotes qts = QuotesDb.read();
    ArrayList<Model> list = Models.list();

    Dic<String> mdRs = new Dic<>();
    for (Model md : list) {
      Dic<String> entry = new Dic<>();

      double[] params = SimParams.paramBases(md.id);
      double[][] refs = md.calc.apply(qts.closes, params);
      StRs rs = Strategy.open(qts, refs);

      entry.put("ref", Js.w(Arr.reduce(
        Arr.mk(refs[refs.length - 1]), 0.0, (r, n) -> r + n)
      ));
      entry.put("orders", Js.w(rs.orders.size()));
      entry.put("hassets", Js.w(rs.hreals[rs.hreals.length - 1]));
      entry.put("hwithdrawals", Js.w(rs.hwithdrawals[rs.hwithdrawals.length - 1]));
      entry.put("cash", Js.w(rs.cash));
      entry.put("buys", Js.w(rs.buys[rs.buys.length - 1].size()));
      entry.put("sales", Js.w(rs.sales[rs.sales.length - 1].size()));
      entry.put("profits", Js.w(rs.profits[rs.profits.length - 1]));

      mdRs.put(md.id, Js.w(entry));
    }

    return mdRs;
  }
}


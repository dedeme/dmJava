// Copyright 28-Mar-2024 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package pgs;

import java.util.ArrayList;
import kut.*;
import mkt.Models;
import mkt.Model;
import data.SimParams;
import data.Imodel;
import data.Vals;
import main.Fns;
import main.Db;
import main.Cts;

/** Rank global Page. */
public class ModelsPg {
  /**
  Process request.
  @param drq Ditto.
  @return JSON string.
  */
  public static String process (Dic<String> drq) {
    String rq = Js.rs(drq.get("rq"));
    switch (rq) {
      case "idata": {
        return Js.w(Dic.mk(
          "Models", Js.w(Arr.map(Models.list(), (md) -> {
              String mdId = md.id;
              double[] bases = SimParams.paramBases(mdId);
              double[] incrs = SimParams.paramIncrs(mdId);
              return new Imodel(
                mdId, md.name,
                md.paramNames, md.paramTypes, bases, incrs
              ).toJs();
            }))
        ));
      }
      case "rank": {
        String mdId = Js.rs(drq.get("mdId"));
        int i = Js.ri(drq.get("ix"));
        String valType = Js.rs(drq.get("valType"));
        String stats = Js.rs(drq.get("stats"));
        String period = Js.rs(drq.get("period"));
        Vals rss = Db.readResults(
          mdId, period, valType, i, stats.equals("price") ? 1 : Cts.statDates
        );
        if (rss.vals.size() > 0) {
          return Js.w(Dic.mk(
            "rankOp", stats.equals("price")
              ? Js.w(Arr.mk(Js.w(Arr.mk(
                  Js.w(rss.date),
                  Js.w(Arr.map(rss.vals.get(0), Js::w))
                ))))
              : Js.w(Arr.mk(Js.w(Arr.mk(
                  Js.w(rss.date),
                  Js.w(Arr.map(Fns.stats(stats, rss.vals), Js::w))
                ))))
            ));
        } else {
          return Js.w(Dic.mk("rankOp", "[]"));
        }
      }
      case "bests": {
        String mdId = Js.rs(drq.get("mdId"));
        String valType = Js.rs(drq.get("valType"));
        String stats = Js.rs(drq.get("stats"));
        String period = Js.rs(drq.get("period"));
        Vals rss = Db.readResults(
          mdId, period, valType, 0, stats.equals("price") ? 1 : Cts.statDates
        );
        ArrayList<ArrayList<Integer>> vals = rss.vals;
        if (vals.size() > 0) {
          ArrayList<Integer> vs = stats.equals("price")
            ? vals.get(0)
            : Fns.stats(stats, vals)
          ;
          ArrayList<Tp<Integer, Integer>> bestsVs = new ArrayList<>();
          for (int i = 0; i < vs.size(); i++)
            bestsVs.add(new Tp<>(i, vs.get(i)));
          Arr.sort(bestsVs, (v1, v2) -> v1.e2 > v2.e2);
          return Js.w(Dic.mk(
            "Bests", Js.w(Arr.map(Arr.take(bestsVs, 3), (v) -> Js.w(v.e1)))
          ));
        } else {
          return Js.w(Dic.mk("Bests", "[]"));
        }
      }
      case "history": {
        String mdId = Js.rs(drq.get("mdId"));
        int iRs = Js.ri(drq.get("ix"));
        String valType = Js.rs(drq.get("valType"));
        String stats = Js.rs(drq.get("stats"));
        String period = Js.rs(drq.get("period"));

        ArrayList<String> profits = new ArrayList<>();
        ArrayList<String> positions = new ArrayList<>();
        for (int i = Cts.statDates - 1 ; i >= 0 ; --i) {
          Vals rss = Db.readResults(
            mdId, period, valType, i, stats.equals("price") ? 1 : Cts.statDates
          );
          ArrayList<ArrayList<Integer>> vals = rss.vals;
          if (vals.size() > 0) {
            ArrayList<Integer> vs = stats.equals("price")
              ? vals.get(0)
              : Fns.stats(stats, vals)
            ;
            int e0 = vs.get(iRs);
            if (e0 < 0) continue;
            int prf = e0 - (int)mkt.Cts.initialCapital;
            int pV = 1;
            for (int e : vs) if (e > e0) ++pV;
            profits.add(Js.w(prf));
            positions.add(Js.w(pV));
          }
        }
         return Js.w(Dic.mk(
          "Profits", Js.w(profits),
          "Positions", Js.w(positions)
        ));
      }
      default: throw new RuntimeException(
          "Value of rq (" + rq + ") is not valid"
        );
    }
  }
}


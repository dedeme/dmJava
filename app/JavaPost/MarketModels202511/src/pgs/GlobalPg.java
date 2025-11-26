// Copyright 25-Mar-2024 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package pgs;

import java.util.ArrayList;
import mkt.Models;
import mkt.Model;
import kut.*;
import data.RkRow;
import data.Rk;
import data.RkEntry;
import data.Ival;
import data.SimParams;
import data.Imodel;
import main.Cts;
import main.Db;

/** Rank global Page. */
public class GlobalPg {
  /**
  Process request.
  @param drq Ditto.
  @return JSON string.
  */
  public static String process (Dic<String> drq) {
    String rq = Js.rs(drq.get("rq"));
    switch (rq) {
      case "idata": {
        String type = Js.rs(drq.get("type"));
        String stats = Js.rs(drq.get("stats"));
        return Js.w(Dic.mk(
          "initialCapital", Js.w(mkt.Cts.initialCapital),
          "All", Arr.toJs(mkRankRows(type, stats), RkRow::toJs),
          "Models", Arr.toJs(
            Arr.map(Models.list(), (md) -> {
              String mdId = md.id;
              double[] bases = SimParams.paramBases(mdId);
              double[] incrs = SimParams.paramIncrs(mdId);
              return new Imodel(
                mdId, md.name,
                md.paramNames, md.paramTypes, bases, incrs
              );
            }),
            (im) -> im.toJs()
          )
        ));
      }
      default: throw new RuntimeException(
          "Value of rq (" + rq + ") is not valid"
        );
    }
  }

  static ArrayList<RkRow> mkRankRows (String type, String stats) {
    ArrayList<RkRow> r = new ArrayList<>();
    for (String period : Cts.periods)
      r.add(new RkRow(period, mkRanks(type, stats, period)));
    return r;
  }

  static ArrayList<Rk> mkRanks (String type, String stats, String period) {
    ArrayList<Rk> r = new ArrayList<>();
    ArrayList<String> dates = Db.rankDates(period);
    for (int i = 0; i < dates.size(); ++i)
      r.add(new Rk(dates.get(i), mkEntries(type, stats, period, i)));
    return r;
  }

  static ArrayList<RkEntry> mkEntries (
    String type, String stats, String period, int idate
  ) {
    ArrayList<RkEntry> r = new ArrayList<>();
    for (String mdId : Arr.map(Models.list(), (m) -> m.id)) {
      Opt<Ival> inValOp = Db.rankValue(mdId, period, type, idate, stats);
      if (inValOp.isNone())
        r.add(new RkEntry(mdId, 0, Opt.none()));
      else {
        Ival inVal = inValOp.getValue();
        r.add(new RkEntry(mdId, inVal.ix, Opt.some(inVal.val)));
      }
    }

    return r;
  }
}


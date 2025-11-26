// Copyright 22-Mar-2024 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package main;

import java.util.ArrayList;
import java.util.Arrays;
import mkt.Model;
import mkt.Models;
import kut.*;
import main.Cts;
import main.Log;
import data.Vals;
import data.Ival;

/** Data base management. */
public class Db {
  private Db(){};

  /**
  Initializes data base. <p>
  NOTE: For add new models use commented code and calling 'MarketModelsJ init'.
  */
  public static void init () {
    if (!File.exists(Cts.dataPath)) {
      File.mkdir(Cts.resultsDir);
      File.write(Path.cat(Cts.dataPath, "version.txt"), Cts.dataVersion);
      for (Model md : Models.list()) {
        String mdPath = Path.cat(Cts.resultsDir, md.id);
        for (String pr : Cts.periods)
          for (String tp : Cts.types)
            File.mkdir(Path.cat(mdPath, pr, tp));
      }
      Log.reset();
      return;
    }

/*
        String mdPath = Path.cat(Cts.resultsDir, "SSBB2");
        for (String pr : Cts.periods)
          for (String tp : Cts.types)
            File.mkdir(Path.cat(mdPath, pr, tp));
*/

    throw new RuntimeException("Data base already initialized");
  };

  /**
  Returns dates for ranking.
  @param period One of Cts.periods.
  @return Ditto.
  */
  public static ArrayList<String> rankDates (String period) {
    ArrayList<String> baseDates = Arr.mk(File.dir(Path.cat(
      Cts.resultsDir, Cts.modelBase, period, "prices"
    )));
    Arr.sort(baseDates, (d1, d2) -> d1.compareTo(d2) > 0);
    return Arr.take(baseDates, Cts.globalRanks);
  };

  /**
  Returns normalized results of a directory.<p>
  If results can not be read, returns 'r.date = ""' and  'r.Vals = []'
  @param mdId   Model identifier.
  @param period One of 'Cts.Periods'.
  @param type One of 'cts.Types'.
  @param start  Index of the first date to return values.
  @param len    Number of dates to return values. NOTE: It can be returned less
                values if there is some error.
  @return Ditto.
  */
  public static Vals readResults(
    String mdId, String period, String type, int start, int len
  ) {
    ArrayList<String> baseDates = Arr.mk(File.dir(Path.cat(
      Cts.resultsDir, Cts.modelBase, period, type
    )));
    if (baseDates.size() <= start) return new Vals("", new ArrayList<>());

    Arr.sort(baseDates, (d1, d2) -> d1.compareTo(d2) > 0);
    ArrayList<String> rqDates = Arr.take(Arr.drop(baseDates, start), len);

    String dir = Path.cat(Cts.resultsDir, mdId, period, type);

    String date = rqDates.get(0);
    if (!File.exists(Path.cat(dir, date))) return new Vals("", new ArrayList<>());

    ArrayList<ArrayList<Integer>> vals = new ArrayList<>();
    ArrayList<String> errs = new ArrayList<>();
    for (String d : rqDates) {
      String fpath = Path.cat(dir, d);
      if (File.exists(fpath))
        vals.add(Arr.map(Js.ra(File.read(fpath)), Js::ri));
      else {
        errs.add("Date missing " + d);
      }
    }

    if (errs.size() > 0) {
      Log.error(
        "Reading results of " + mdId + "-" + period + "-" + type + ":\n" +
        Arr.join(Arr.map(errs, (e) -> "  - " + e), "\n")
      );
    }
    return new Vals(date, vals);
  }

  /**
  Returns best value for global ranking.<p>
  When 'stats=="deviation"' 'r.ix == 0', because r.val is an average.<p>
  If the function fails, returns 'Opt.none()'.
  @param mdId   Model identifier.
  @param period Period index (one of 'Cts.Periods').
  @param type Value types index (one of 'cts.Types').
  @param idate  date index (first is 0).
  @param stats  One of Cts.stats.
  @return Ditto.
  */
  public static Opt<Ival> rankValue (
    String mdId, String period, String type, int idate, String stats
  ) {
    int len = stats.equals("price") ? 1 : Cts.statDates;
    Vals rs = readResults(mdId, period, type, idate, len);
    ArrayList<ArrayList<Integer>> vals = rs.vals;
    if (vals.size() == 0) return Opt.none();

    if (stats.equals("deviation")) {
      ArrayList<Integer> vs = main.Fns.stats("deviation", vals);
      return Opt.some(new Ival(0, vs.get(0)));
    }
    ArrayList<Integer> vs = stats.equals("price")
      ? vals.get(0)
      : main.Fns.stats(stats, vals)
    ;
    int inv = 0;
    int val = vs.get(0);
    for (int i = 0; i < vs.size(); ++i) {
      int v = vs.get(i);
      if (v > val) {
        inv = i;
        val = v;
      }
    }
    return Opt.some(new Ival(inv, val));
  };

  static int weekInYear (String d) {
    long tm = Time.fromStr(d).getValue();
    long day1 = Time.mk(1, 1, Time.year(tm));
    int wdEn = Time.weekday(day1);
    int wd = wdEn == 0 ? 6 : wdEn - 1;
    int dfDays = Time.dfDays(tm, day1);
    return (dfDays + wd) / 7;
  }

  /**
  Updates data base.
  @param mdId   Model intentifier.
  @param date   Last date of data.
  @param prices Assets by prices.
  @param accs   Assets by accounting.
  @param refs   Assets by references.*/
  /// \s, s, [i.], [i.], [i.] -> ()
  public static void update (
    String mdId, String date, int[] prices, int[] accs, int[] refs
  ) {
    String modelPath = Path.cat(Cts.resultsDir, mdId);
    for (String tp : Cts.types) {
      String data;
      if (tp.equals("prices")) data = Js.w(Arr.map(Arr.mk(prices), Js::w));
      else if (tp.equals("accs")) data = Js.w(Arr.map(Arr.mk(accs), Js::w));
      else data = Js.w(Arr.map(Arr.mk(refs), Js::w));

      File.write(Path.cat(modelPath, Cts.periods[0], tp, date), data);

      String wPath = Path.cat(modelPath, Cts.periods[1], tp);
      String[] wdates = File.dir(wPath);
      if (wdates.length > 0) {
        Arrays.sort(wdates);
        String lastDate = wdates[wdates.length - 1];
        if (lastDate.equals(date) || weekInYear(lastDate) != weekInYear(date))
          File.write(Path.cat(wPath, date), data);
      } else {
        File.write(Path.cat(wPath, date), data);
      }

      String mPath = Path.cat(modelPath, Cts.periods[2], tp);
      String[] mdates = File.dir(mPath);
      if (mdates.length > 0) {
        Arrays.sort(mdates);
        String lastDate = mdates[mdates.length - 1];
        if (
          lastDate.equals(date) ||
          !Str.left(lastDate, 6).equals(Str.left(date, 6))
        )
          File.write(Path.cat(mPath, date), data);
      } else {
        File.write(Path.cat(mPath, date), data);
      }
    }
  }
}

// Copyright 22-Mar-2024 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package main;

import java.util.Arrays;
import java.util.ArrayList;
import kut.Arr;

/** Global functions. */

public class Fns {
  private Fns(){};

  /**
  Calculates statistics.
  @param tp Type of statistics (Cts.stats [1:] -- "average", "crrAverage", "deviation")
  @param rss Values to make calculations. (days (rows) x invs (cols))
  @return For 'average' and 'crrAverage', one value for each investor (col).
            When in a calculus, some historic value is &lt; 0, the final result
            is -1. For deviation only one value with the deviation of the
            greater 'crrAverage' value.
  **/
  public static ArrayList<Integer> stats (
    String tp, ArrayList<ArrayList<Integer>> rss
  ) {
    int nrows = rss.size();
    int ncols = rss.get(0).size();
    double[][] vss = new double[nrows][];
    for (int i = 0; i < nrows; ++i)
      vss[i] = Arr.toDoubles(Arr.map(rss.get(i), (n) -> n.doubleValue()));

    // SUM

    double[] sums = new double[ncols];
    Arrays.fill(sums, 0);
    double[] sNs = new double[ncols];
    Arrays.fill(sNs, 0);
    for (int r = 0; r < nrows; ++r) {
      double[] row = vss[r];
      for (int c = 0; c < ncols; ++c) {
        double v = row[c];
        double sm = sums[c];
        if (sm < 0 || row[c] < 0) sums[c] = -1;
        else {
          sums[c] += v;
          sNs[c] += 1;
        }
      }
    }

    // AVG

    double[] avgs = new double[ncols];
    for (int c = 0; c < ncols; ++c)
      if (sums[c] < 0) avgs[c] = -1;
      else avgs[c] = sums[c] / sNs[c];

    if (tp.equals(Cts.stats[1]))
      return Arr.map(Arr.mk(avgs), (n) -> n.intValue()); // average ------------

    // DEVIATION SUMS

    int nrows1 = nrows - 1;
    double[] slopes = new double[ncols];
    for (int c = 0; c < ncols; ++c)
      slopes[c] = (vss[nrows1][c] - vss[0][c]) / nrows;

    double[] dsums = new double[ncols];
    Arrays.fill(dsums, 0);
    for (int r = 0; r < nrows; ++r) for (int c = 0; c < ncols; ++c) {
      if (avgs[c] < 0) dsums[c] = -1;
      else dsums[c] += Math.abs(vss[r][c] - vss[0][c] + r * slopes[c]);
    }

    double maxDv = -1;
    double maxCrr = -1;
    double[] crrs = new double[ncols];
    for (int c = 0; c < ncols; ++c) {
      if (avgs[c] < 0) crrs[c] = -1;
      else {
        double dv = dsums[c] / sNs[c];
        double v = avgs[c] - dv;
        crrs[c] = v;
        if (v > maxCrr) {
          maxCrr = v;
          maxDv = dv / avgs[c];
        }
      }
    }

    if (tp.equals(Cts.stats[2]))
      return Arr.map(Arr.mk(crrs), (n) -> n.intValue()); // corrected average --

    // DEVIATION

    return Arr.mk(Integer.valueOf((int)(maxDv * 10000)));// deviation ----------
  }
}

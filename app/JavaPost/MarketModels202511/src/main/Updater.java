// Copyright 22-Mar-2024 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package main;

import mkt.Quotes;
import mkt.Model;
import mkt.Models;
import mkt.Strategy;
import mkt.StGroupRs;
import kut.Tp3;
import extDb.QuotesDb;
import data.SimParams;

/** Update data */
public class Updater {
  private Updater(){};

  /** Update data. */
  public static void run () {
    Quotes qts = QuotesDb.read();
    for (Model md : Models.list()) {
      String date = qts.dates[qts.dates.length - 1];
      String mdId = md.id;
      Tp3<int[], int[], int[]> PrAcRf =
        md.paramTypes.length == 1 ? calc1(md, qts) : calc2(md, qts);
      int[] prices = PrAcRf.e1;
      int[] accs = PrAcRf.e2;
      int[] refs = PrAcRf.e3;
      Db.update(mdId, date, prices, accs, refs);
    }
  }

// Calculates pices and references.
// Returns [prices, accounting, references] each with as many elements as
//    Params has.
// \<model>, <quotes>, [[f.].] -> [[i.], [i.], [i.]]
  static Tp3<int[], int[], int[]> calc (
    Model md, Quotes qts, double[][] params
  ) {
    int len = params.length;
    StGroupRs stRs = Strategy.stGroup(md, qts, params);
    int[] prices = new int[len];
    int[] accs = new int[len];
    int[] refs = new int[len];

    int[] sales = stRs.sales;
    double[] assets = stRs.assets;
    double[] acAssets = stRs.accs;
    double[] rfAssets = stRs.rfAssets;
    for (int i = 0; i < len; ++i) {
      if (sales[i] < Cts.minSales) { // Remove lower number of sales.
        prices[i] = -1;
        accs[i] = -1;
        refs[i] = -1;
      } else {
        prices[i] = (int)assets[i];
        accs[i] = (int)acAssets[i];
        refs[i] = (int)rfAssets[i];
      }
    }

    return new Tp3<>(prices, accs, refs);
  };


  // Calculates pices and references of model with 1 parameter.
  // Returns Tp3<prices, accounting, references> each one with cts.simulationSteps
  //    elements.
  static Tp3<int[], int[], int[]> calc1 (Model md, Quotes qts) {
    String mdId = md.id;
    double b = SimParams.paramBases(mdId)[0];
    double inc = SimParams.paramIncrs(mdId)[0];
    double [][] params = new double[Cts.simulationSteps][1];
    for (int i = 0; i < Cts.simulationSteps; ++i)
      params[i] = new double[]{b + inc * (double)i};
    return calc(md, qts, params);
  }

  // Calculates pices and references of model with 2 parameters.
  // Returns [prices, accounting, references] each one with
  //    cts.simulationSteps x cts.simulationSteps elements.
  static Tp3<int[], int[], int[]> calc2 (Model md, Quotes qts) {
    String mdId = md.id;
    double b0 = SimParams.paramBases(mdId)[0];
    double b1 = SimParams.paramBases(mdId)[1];
    double inc0 = SimParams.paramIncrs(mdId)[0];
    double inc1 = SimParams.paramIncrs(mdId)[1];

    double[][] params = new double[Cts.simulationSteps * Cts.simulationSteps][2];
    int i = 0;
    for (int r = 0; r < Cts.simulationSteps; ++r)
      for (int c = 0; c < Cts.simulationSteps; ++c)
        params[i++] = new double[] {
          b0 + inc0 * (double)r, b1 + inc1 * (double)c
        };

    return calc(md, qts, params);
  }
}


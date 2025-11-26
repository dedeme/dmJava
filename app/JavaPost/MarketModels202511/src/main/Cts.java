// Copyright 18-Mar-2024 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package main;

import kut.Path;
import kut.File;

/** Constants. */
public class Cts {
  private Cts(){};

  /** Application name. */
  public static final String appName = "MarketModelsJ";
  /** Application version. */
  public static final String version = "2024.03";
  /** Data version. */
  public static final String dataVersion = "2024.03";
  /** Data path. */
  public static final String dataPath = Path.cat(
    File.home(), ".dmJavaApp", "KutPost", appName
  );
  /** Results directory. */
  public static final String resultsDir = Path.cat(dataPath, "results");
  /** Directory of historic quotes tables. */
  public static final String quotesPath = "/dm/KtWeb/dmcgi/Market/quotes";
  /** Steps number to calculate simulations */
  public static final int simulationSteps = 20;
  /** Minimum sales to compute results. */
  public static final int minSales = 40;

  // ---------------------------------------------------------------------------

  /** Number of global ranks. */
  public static final int globalRanks = 5;
  /** Number of dates to calculate statistics. */
  public static final int statDates = 24;
  /** Number of dates to save. */
  public static final int historicDates = (statDates + 1) * 2;
  /** Period names. */
  public static final String[] periods =
    new String[]{"daily", "weekly", "monthly"};
  /** Values types. */
  public static final String[] types =
    new String[]{"prices", "accs", "refs"};
  /** Statistics names. */
  public static final String [] stats =
    new String[]{"price", "average", "crrAverage", "deviation"};
  /** Investor model base. */
  public static final String modelBase = "SSBB0";
}


// Copyright 25-Mar-2024 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package data;

import java.util.ArrayList;
import kut.*;

/** Ranks of one period. */
public class RkRow {
  /** One of 'Cts.periods' ("daily", "weekly", "monthly"). */
  public final String period;
  /** Unsorted array of ranks. */
  public final ArrayList<Rk> ranks;

  /**
  Constructor.
  @param period One of 'Cts.periods' ("daily", "weekly", "monthly").
  @param ranks  Unsorted array of ranks.
  */
  public RkRow (String period, ArrayList<Rk> ranks) {
    this.period = period;
    this.ranks = ranks;
  }

  /**
  JSON serialization.
  @return JSON.
  */
  public String toJs () {
    return Js.w(Arr.mk(
      Js.w(period),
      Arr.toJs(ranks, Rk::toJs)
    ));
  }
}

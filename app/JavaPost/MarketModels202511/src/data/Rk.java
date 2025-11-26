// Copyright 25-Mar-2024 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package data;

import java.util.ArrayList;
import kut.*;

/** Date global rank. */
public class Rk {
  /** Date in format "YYYYMMDD". */
  public final String date;
  /** Unsorted array of entries. */
  public final ArrayList<RkEntry> entries;

  /**
  Constructor.
  @param date    Date in format "YYYYMMDD".
  @param entries Unsorted array of entries.
  */
  public Rk (String date, ArrayList<RkEntry> entries) {
    this.date = date;
    this.entries = entries;
  }

  /**
  JSON serialization.
  @return JSON.
  */
  public String toJs () {
    return Js.w(Arr.mk(
      Js.w(date),
      Arr.toJs(entries, RkEntry::toJs)
    ));
  }
}

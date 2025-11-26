// Copyright 22-Mar-2024 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package data;

import java.util.ArrayList;

/** Result group values. */
public class Vals {
  /** Date of group values. */
  public final String date;
  /** Values of several dates sorted from 'date' (after) to before. */
  public final ArrayList<ArrayList<Integer>> vals;

  /**
  Constructor.
  @param date Date of group values.
  @param vals Values of several dates sorted from 'date' (after) to before.
  */
  public Vals (String date, ArrayList<ArrayList<Integer>> vals) {
    this.date = date;
    this.vals = vals;
  }
}

// Copyright 22-Mar-2024 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package data;

/** Index-Value of an array of results. */
public class Ival {
  /** Index of results array. */
  public final int ix;
  /** Result value. */
  public final int val;

  /**
  Constructor.
  @param ix Index of results array.
  @param val Result value.
  */
  public Ival (int ix, int val) {
    this.ix = ix;
    this.val = val;
  }
}

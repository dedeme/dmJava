// Copyright 25-Mar-2024 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package data;

import kut.*;

/** Global rank entry. */
public class RkEntry {
  /** Model identifier. */
  public final String id;
  /** Index of investor. */
  public final int ix;
  /**
  Price, reference or deviation. It it is '[]', 'val' is not valid
  and there is a message in log.
  */
  public final Opt<Integer> val;

  /**
  Constructor.
  @param id  Model identifier.
  @param ix  Index of investor.
  @param val Price, reference or deviation. It it is '[]', 'val' is not valid
             and there is a message in log.
  */
  public RkEntry (String id, int ix, Opt<Integer> val) {
    this.id = id;
    this.ix = ix;
    this.val = val;
  }

  /**
  JSON serialization.
  @return JSON.
  */
  public String toJs () {
    return Js.w(Arr.mk(
      Js.w(id),
      Js.w(ix),
      Opt.toJs(val, Js::w)
    ));
  }
}

// Copyright 22-Mar-2024 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package data;

import kut.Js;
import kut.Arr;

/** Investor - model data. */
public class Imodel {
  /** Model identifier. */
  public final String id;
  /** Model complete name. */
  public static String name;
  /** Parameter names. */
  public final String[] pnames;
  /** Parameter types. */
  public final int[] ptypes;
  /** Parameter bases. */
  public final double[] pbases;
  /** Parameter increments. */
  public final double[] pincs;

  /**
  Constructor.
  @param id    Model identifier.
  @param name  : Model complete name.
  @param pnames: Paramter names.
  @param ptypes: Parameter types. One of dayParam or percParam
  @param pbases: Parameter bases.
  @param pincs : Parameter increments.
  */
  public Imodel (
    String id, String name, String[] pnames,
    int[] ptypes, double[] pbases, double[] pincs
  ) {
    this.id = id;
    this.name = name;
    this.pnames = pnames;
    this.ptypes = ptypes;
    this.pbases = pbases;
    this.pincs = pincs;
  }

  /**
  JSON serialization.
  @return JSON.
  */
  public String toJs () {
    return Js.w(Arr.mk(
      Js.w(id),
      Js.w(name),
      Js.w(Arr.map(Arr.mk(pnames), Js::w)),
      Js.w(Arr.map(Arr.mk(ptypes), Js::w)),
      Js.w(Arr.map(Arr.mk(pbases), Js::w)),
      Js.w(Arr.map(Arr.mk(pincs), Js::w))
    ));
  }
}

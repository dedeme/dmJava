// Copyright 20-Mar-2024 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package data;

/** Simulation parameters. */
public class SimParams {
  /**
   * Returns bases for simulation.
   * @param mdId Model identifier.
   * @return Ditto.
   */
  public static double[] paramBases (String mdId) {
    if(mdId.equals("APRX")) return new double[]{0.15, 0.005};
    if(mdId.equals("APRX2")) return new double[]{0.15, 0.005};
    if(mdId.equals("APRX3")) return new double[]{0.15, 0.005};
    if(mdId.equals("ME")) return new double[]{5.0, 0.01};
    if(mdId.equals("ME2")) return new double[]{5.0, 0.01};
    if(mdId.equals("MM")) return new double[]{5.0, 0.01};
    if(mdId.equals("MX_MN")) return new double[]{5.0, 0.01};
    if(mdId.equals("QFIJO")) return new double[]{0.05};
    if(mdId.equals("QMOV")) return new double[]{0.05};
    if(mdId.equals("SPRS")) return new double[]{0.015};
    if(mdId.equals("SS_BB")) return new double[]{0.15, 0.068};
    if(mdId.equals("SSBB0")) return new double[]{0.15};
    if(mdId.equals("SSBB2")) return new double[]{0.15, 0.15};
    if(mdId.equals("SSQF")) return new double[]{0.15, 0.4};
    throw new IllegalArgumentException("Model " + mdId + " not found");
  }

  /**
   * Returns increments for simulation.
   * @param mdId Model identifier.
   * @return Ditto.
   */
  public static double[] paramIncrs (String mdId) {
    if(mdId.equals("APRX")) return new double[]{0.01, 0.005};
    if(mdId.equals("APRX2")) return new double[]{0.01, 0.005};
    if(mdId.equals("APRX3")) return new double[]{0.01, 0.005};
    if(mdId.equals("ME")) return new double[]{2.0, 0.005};
    if(mdId.equals("ME2")) return new double[]{2.0, 0.005};
    if(mdId.equals("MM")) return new double[]{2.0, 0.005};
    if(mdId.equals("MX_MN")) return new double[]{2.0, 0.005};
    if(mdId.equals("QFIJO")) return new double[]{0.01};
    if(mdId.equals("QMOV")) return new double[]{0.01};
    if(mdId.equals("SPRS")) return new double[]{0.001};
    if(mdId.equals("SS_BB")) return new double[]{0.01, 0.004};
    if(mdId.equals("SSBB0")) return new double[]{0.01};
    if(mdId.equals("SSBB2")) return new double[]{0.01, 0.01};
    if(mdId.equals("SSQF")) return new double[]{0.01, 0.025};
    throw new IllegalArgumentException("Model " + mdId + " not found");
  }
}

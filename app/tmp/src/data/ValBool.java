// Copyright 26-Oct-2025 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package data;

public class ValBool extends Val {
  boolean value;

  public ValBool(boolean val) {
    value = val;
  }

  @Override
  public boolean getBool () {
    return value;
  }

  @Override
  public String toString () {
    return String.valueOf(value);
  }
}

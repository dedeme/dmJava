// Copyright 26-Oct-2025 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package data;

public class ValInt extends Val {
  long value;

  public ValInt(long val) {
    value = val;
  }

  @Override
  public long getInt () {
    return value;
  }

  @Override
  public String toString () {
    return String.valueOf(value);
  }

}

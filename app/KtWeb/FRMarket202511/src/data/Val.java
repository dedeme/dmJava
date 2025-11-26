// Copyright 26-Oct-2025 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package data;

public class Val {
  enum Type {
    BOOL,
    INT
  }

  public boolean getBool () {
    throw new IllegalArgumentException("'Val' is not of type BOOL");
  }

  public long getInt () {
    throw new IllegalArgumentException("'Val' is not of type INT");
  }

  public String toString () {
    throw new IllegalArgumentException("Generic Val has not method 'toString'");
  }

}

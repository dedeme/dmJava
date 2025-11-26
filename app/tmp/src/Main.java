// Copyright 26-Oct-2025 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

import data.*;
import kut.*;
import java.util.*;

/** Program main */
public class Main {

  /**
   * Program start.
   * @param args Program arguments.
   */
  public static void main(String[] args)  {
    ArrayList<Val> l = Arr.mk(new ValInt(3), new ValBool(false));
    for (Val v : l) {
      System.out.println(v);
      System.out.println(v.getInt() + 4);
    }
    System.out.println("Program end.");
  }
}

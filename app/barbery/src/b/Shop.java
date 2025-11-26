// Copyright 02-Dic-2023 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package b;

import java.util.ArrayList;
import kut.Arr;
import kut.Str;

public class Shop {
  static boolean preopen = true;
  public static boolean isPreopen () {
    return preopen;
  }
  public static void setPreopen (boolean value) {
    preopen = value;
  }

  static boolean openV = false;
  public static boolean isOpen () {
    return openV;
  }
  public static void setOpen (boolean value) {
    openV = value;
  }

  static ArrayList<String> clients = new ArrayList<>();
  static ArrayList<String> sits = Arr.mk("", "", "", "");

  public static boolean isFull () {
    return clients.size() == 4;
  }

  public static boolean isEmpty () {
    return clients.size() == 0;
  }

  public static String showSits () {
    return "|" +
      Arr.join(Arr.map(sits, (cl) -> cl == "" ? "-" : cl), "|") +
      "|"
    ;
  }

  public static void open () {
    openV = true;
    preopen = false;
  }

  public static void close () {
    openV = false;
  }

  public static void takeASit (String cl) {
    int nClients = clients.size();
    if (nClients >= 4)
      throw new IllegalStateException("Barbery is full");

    int n = kut.Mth.rndi(4 - nClients);
    int ix = 0;
    int ixOk = -1;
    for (int i = 0; i < sits.size(); ++i) {
      if (sits.get(i).equals("")) {
        if (ix == n) {
          ixOk = i;
          break;
        }
        ++ix;
      }
    }
    if (ixOk == -1)
      throw new IllegalStateException("Barbery is full");

    clients.add(cl);
    sits.set(ixOk, cl);

    System.out.println(Str.fmt("Shop: %s", showSits()));
  }

  public static String takeAClient () {
    if (clients.size() == 0)
      throw new IllegalStateException("Barbery is empty");

    String cl = clients.remove(0);
    for (int i = 0; i < sits.size(); ++i) {
      if (cl.equals(sits.get(i))) {
        sits.set(i, "");
        break;
      }
    }

    return cl;
  }
}

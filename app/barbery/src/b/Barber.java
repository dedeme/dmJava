// Copyright 02-Dic-2023 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package b;

import kut.*;

public class Barber {

  public static void cutHair (String cl) {
    System.out.println(Str.fmt("Barber: Cutting hair to %s", cl));
    System.out.println(Str.fmt("Shop: %s", Shop.showSits()));
    Sys.sleep(Cts.barberTime);
  }

  public static void run () {
    System.out.println("Barber: Go out from home");
    Sys.sleep(1000);
    System.out.println("Barber: Open barbery");
    Shop.open();

    Wrap<Boolean> end = new Wrap<>(false);
    Wrap<Boolean> sleeping = new Wrap<>(false);

    while (!end.e) {
      Wrap<String> cl = new Wrap<>("");
      Thr.sync(() -> {
        if (Clock.isTimeOver() && Shop.isOpen()) {
          System.out.println("Barber: Close barbery");
          Shop.close();
        }

        if (Shop.isEmpty()) {
          if (!Shop.isOpen()) end.e = true;
          else if (!sleeping.e) {
            sleeping.e = true;
            System.out.println("Barber: Barber is sleeping");
          }
        } else {
          sleeping.e = false;
          cl.e = Shop.takeAClient();
        }
      });

      if (!cl.e.equals("")) cutHair(cl.e);
      else if (!end.e) Sys.sleep(10);
    }
  }
}

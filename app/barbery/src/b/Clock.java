// Copyright 02-Dic-2023 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package b;

import kut.Sys;

public class Clock {

  static boolean timeOver = false;

  public static void run () {
    Sys.sleep(Cts.clockTime);
    System.out.println("Clock: Time is over");
    timeOver = true;
  }

  public static boolean isTimeOver () {
    return timeOver;
  }
}

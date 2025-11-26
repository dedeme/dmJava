// Copyright 02-Dic-2023 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

import b.*;
import kut.*;

/** Program main */
public class Main {

  /**
   * Program start.
   * @param args Program arguments.
   */
  public static void main(String[] args) throws InterruptedException {
    Thread clockTh = Thr.start(() -> Clock.run());
    Thread clientMakerTh = Thr.start(() -> Client.makerRun());
    Thread barberTh = Thr.start(() -> Barber.run());


    clockTh.join();
    clientMakerTh.join();
    barberTh.join();

    System.out.println("Program end.");
  }
}

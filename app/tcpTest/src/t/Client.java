// Copyright 05-Dic-2023 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package p;

import kut.*;
import java.net.*;

public class Client {

  public static void start () {
    Rs<Socket> connRs = Tcp.dial("127.0.0.1", Server.port);
    if (connRs.isError()) {
      Sys.println(connRs.getError());
      return;
    }
    try (Socket conn = connRs.getValue()) {

      for (;;) {
        Sys.print("Send a message or 'end' to finish: ");
        String msg = Sys.readLine();
        if (msg.equals("")) {
          Sys.println("An empty message is not valid");
          continue;
        }
        String err = Tcp.write(conn, Bytes.fromStr(msg));
        if (!err.equals("")) {
          Sys.println("Writting error: " + err);
          return;
        }

        Tcp.read(conn, 30, (rpRs) -> {
          if (rpRs.isError()) {
            Sys.println("Writting error: " + rpRs.getError());
            return true;
          }
          String rp = Bytes.toStr(rpRs.getValue());
          Sys.println(rp);
          return true;
        });

        if (msg.equals("end")) break;
      }

      Sys.sleep(50);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

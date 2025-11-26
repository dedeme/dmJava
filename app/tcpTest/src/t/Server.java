// Copyright 05-Dic-2023 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package p;

import kut.*;
import java.net.*;

public class Server {

  public static int port = 33423;

  public static void start () {
    Sys.println("Server running");
    try (ServerSocket sv = Tcp.server(port, 10)) {
      for (;;) {
        Rs<Socket> connRs = Tcp.accept(sv);
        if (connRs.isError()) throw new Exception(connRs.getError());
        Socket conn = connRs.getValue();
        Thr.run(() -> {
          for (;;) {
            Wrapper<Boolean> stop = new Wrapper<>(false);
            Tcp.read(conn, 30, (rqRs) -> {
              if (rqRs.isError()) {
                Sys.println("Request error: " + rqRs.getError());
                stop.e = true;
                return true;
              }
              String rq = Bytes.toStr(rqRs.getValue());
              if (rq.equals("end")) stop.e = true;
              Sys.println("Received " + rq);

              String err = Tcp.write(conn, Bytes.fromStr("Received " + rq));
              if (!err.equals("")) Sys.println("Response error: " + err);
              return true;
            });

            if (stop.e) {
              try { sv.close(); } catch (Exception e) { e.printStackTrace(); }
              break;
            }
          }
        });
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

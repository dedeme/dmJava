// Copyright 19-Mar-2019 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package multimarket;

import java.net.Socket;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import es.dm.Std;
import es.dm.Cgi;
import es.dm.Js;
import es.dm.Cryp;
import multimarket.data.Conf;

/** Communcation port */
public class Port {

  static boolean closed = true;

  static String process(String rq) {
    Cgi cgi = Util.getCgi();
    int ix = rq.indexOf(':');
    // ............................................................. CONNECTION
    if (ix == -1) {
      cgi.setKey(rq);
      return cgi.connect(rq);
    // ......................................................... AUTHENTICATION
    } else if (ix == 0) {
      String key = Cryp.key(Ct.APP_NAME, cgi.KLEN);
      cgi.setKey(key);
      String[] a = Cryp.decode(rq.substring(1), key).split(":");
      return cgi.authentication(a[0], a[1], a[2].equals("1"));
    }
    // ......................................................... NORMAL REQUEST
    String sessionId = rq.substring(0, ix);
    Cgi.SessionData sd = cgi.getSessionData(sessionId);

    if (sd.getKey().isEmpty()) {
      cgi.setKey("nosession");
      return cgi.expired();
    }

    cgi.setKey(sd.getKey());

    String data = Cryp.decode(rq.substring(ix + 1), sd.getKey());

    HashMap<String, Js> mrq = new Js(data).rObject();
    Js connectionIdJs = mrq.get("connectionId");
    if (connectionIdJs != null) {
      if (!sd.getConnectionId().equals(connectionIdJs.rString())) {
        cgi.setKey("nosession");
        return cgi.expired();
      }
    }

    Js moduleJs = mrq.get("module");
    if (moduleJs == null) {
      throw new IllegalArgumentException("Key 'module' is missing");
    }
    String module = moduleJs.rString();

    if (module.equals(".")) {
      return Util.ok(new HashMap<String, Js> () {{
        put("lang", Conf.get("lang"));
      }});
    } else if (module.equals("sys")) {
      return Sys.process(mrq);
    }

    throw new IllegalArgumentException("Unknown module '" + module + "'");

  }

  /** Open communication port in a new thread */
  static public void open () {
    closed = false;
    new Thread(() -> {
      try (
        ServerSocket serverSocket = new ServerSocket(Ct.PORT);
      ) {
        for (;;) {
          try {
            Socket clientSocket = serverSocket.accept();
            BufferedReader in = new BufferedReader(
                new InputStreamReader(
                  clientSocket.getInputStream(), "UTF-8"
                )
            );
            PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);
            String tx = in.readLine();
            if (tx != null) {
              if (tx.equals("stop")) {
                closed = true;
                out.print("");
                out.close();
                clientSocket.close();
                break;
              } else {
                new Thread(() -> {
                  try {
                    String rp;
                    try {
                      rp = process(tx);
                    } catch (Exception e) {
                      rp = Std.stackTrace(e);
                    }
                    out.print(rp);
                    out.close();
                    clientSocket.close();
                  } catch (Exception e) {
                  }
                }).start();
              }
            } else {
              clientSocket.close();
            }
          } catch (Exception e) {
            if (!closed) {
              Log.write("Fail accepting client");
            } else {
              break;
            }
          }
        }
      } catch (Exception e) {
        closed = true;
        Log.write("Fail openning server port");
      }
    }).start();
  }

  static public void close () {
    closed = true;
  }

  static public boolean isClosed() {
    return closed;
  }

  /**
   * Returns true is application is running.
   * @return Value
   */
  static public boolean isAlive () {
    boolean r = false;
    try {
      Socket sk = new Socket();
      sk.connect(new InetSocketAddress("localhost", Ct.PORT), 2000);
      sk.close();
      r = true;
    } catch (Exception e) {
    }
    return r;
  }

}

// Copyright 24-Mar-2019 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package multimarket.data;

import java.util.HashMap;
import es.dm.Js;
import es.dm.Std;
import multimarket.Util;

/**
 * Configuration data db.<p>
 * It has the following structure:
 *  {
 *    "lang": String, // Language values. They are "es" (default) | "en"
 *    "sysMenu": String, // Option menu in SysMenu. Default ""
 *  }
 */
public class Conf {

  public static String path;

  /** Create data base */
  public static void create () {
    path = Std.fpath(Util.dataDir(), "conf.db");
    HashMap<String, Js> data = new HashMap<String, Js> () {{
      put("lang", Js.write("es"));
      put("sysMenu", Js.write(""));
    }};
    Std.write(path, Js.write(data).toString());
  }

  /** Initializes data base */
  public static void init () {
    path = Std.fpath(Util.dataDir(), "conf.db");
  }

  /**
   * Gets a property.
   * @param key Key.
   * @return Value
   */
  public static Js get (String key) {
    Util.Wrapper<Js> r = new Util.Wrapper<>();
    Util.sync(
      () -> r.value = Util.js(new Js(Std.read(path)).rObject(), key)
    );
    return r.value;
  }

  /**
   * Sets a property
   * @param key Key
   * @param value Value
   */
  public static void set (String key, Js value) {
    Util.sync(() -> {
      HashMap<String, Js> data = new Js(Std.read(path)).rObject();
      data.put(key, value);
      Std.write(path, Js.write(data).toString());
    });
  }

}

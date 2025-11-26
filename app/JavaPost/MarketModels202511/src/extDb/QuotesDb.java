// Copyright 10-Dic-2023 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>

package extDb;

import mkt.Quotes;
import extDb.CosTb;
import main.Cts;

/** Quotes reader. */
public class QuotesDb {
  /**
  Reads quotes.
  @return Quotes.
  */
  public static Quotes read () {
    String[] cos = CosTb.read();
    return Quotes.read(Cts.quotesPath, cos);
  }
}

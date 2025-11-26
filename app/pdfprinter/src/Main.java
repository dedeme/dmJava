// Copyright 17-Mar-2019 ºDeme
// GNU General Public License - V3 <http://www.gnu.org/licenses/>


import es.dm.Std;
import es.dm.Html2pdf;
import com.itextpdf.text.DocumentException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 *
 * @version 2.0
 * @since 17-may-2014
 * @author deme
 */
public class Main {

  static Charset charset;
  static ArrayList<String> sources;
  static ArrayList<BufferedReader> rsources;
  static File target = null;


  static void help2() {
    System.out.println(
      "Use: "
      + "pdfPrinter options\n"
      + "Options:\n"
      + "-s [file | page]\n"
      + "  Indicates source file or page html. It can have several options -s\n"
      + "-t [file]\n"
      + "  Indicates target file\n"
      + "-h\n"
      + "  Displays help and discards other options.\n"
      + "--charset name\n"
      + "  Default UTF-8\n"
      + "--landscape [true|false]\n"
      + "  Sets paper orientation.\n"
      + "--margins left-right-top-botom\n"
      + "  Sets margins in points. Default 60-50-60-50.\n"
      + "--pagesize width-height\n"
      + "  Sets page size in points. Default 585.0-842.0.\n"
      + "--start number\n"
      + "  First number page. Negative values delay header and footer. e.g.\n"
      + "  -1 does not show header nor footer in first page, -2 does the same\n"
      + "  in first and second page, ect. Default 0.\n"
      + "--h-show [true|false]\n"
      + "  Header show.\n"
      + "--h-y value\n"
      + "  Moves header to up or down. Default 0f pt.\n"
      + "--h-text text\n"
      + "  Text of header in quotes. Can include the expression \"{pN}\" for\n"
      + "  insert the page number. Default \"\".\n"
      + "--h-align [l | c | r]\n"
      + "  Header align. Default r.\n"
      + "--h-ffm name\n"
      + "  Header font family. 'name' can be: times, helvetica, courier,\n"
      + "  zapfdingbats or symbol. Default times.\n"
      + "--h-fsz value\n"
      + "  Header font size in pt. Default 10\n"
      + "--h-fst [n | b | i | u | s ]\n"
      + "  Header font style. You can mixit, e.g. '--h-fst ib'. Default n\n"
      + "--h-line [true|false]\n"
      + "  Sets a header line separator. Default false.\n"
      + "--h-lx value\n"
      + "  Moves left o right the header separation line. Default 0f pt.\n"
      + "--h-ly value\n"
      + "  Moves up o down the header separation line. Default 0f pt.\n"
      + "--f-show [true|false]\n"
      + "  Footer show.\n"
      + "--f-y value\n"
      + "  Moves footer to up or down. Default 0f pt.\n"
      + "--f-text text\n"
      + "  Text of footer in quotes. Can include the expression \"{pN}\" for\n"
      + "  insert the page number. Default \"\".\n"
      + "--f-align [l | c | r]\n"
      + "  Header align. Default r.\n"
      + "--f-ffm name\n"
      + "  Header font family. 'name' can be: times, helvetica, courier,\n"
      + "  zapfdingbats or symbol. Default times.\n"
      + "--f-fsz value\n"
      + "  Header font size in pt. Default 10\n"
      + "--f-fst [n | b | i | u | s ]\n"
      + "  Header font style. You can mixit, e.g. '--h-fst ib'. Default n\n"
      + "--f-line [true|false]\n"
      + "  Sets a header line separator. Default false.\n"
      + "--f-lx value\n"
      + "  Moves left o right the header separation line. Default 0f pt.\n"
      + "--f-ly value\n"
      + "  Moves up o down the header separation line. Default 0f pt.\n"
      + "Order of options is not mandatory.\n"
      + "Examples:\n"
      + "pdfPrinter -s http://foo.com/index.html -t myDoc.pdf\n"
      + "  Converts index.html to myDoc.pdf\n"
      + "pdfPrinter -s /home/john/index.html -t myDoc.pdf\n"
      + "  Converts index.html to myDoc.pdf\n"
      + "pdfPrinter -s i1.html -s i2.html -t myDoc.pdf\n"
      + "  Converts i1.html and i2.html in two pdfs and pastes them "
      + "  into myDoc.pdf\n"
    );
  }

  static void help() {
    System.out.println(
      "Use: "
      + "pdfPrinter -s [page|file] -t file\n"
      + "  -s Source file or page html. It can have several options -s\n"
      + "  -t Target file\n"
      + "  -h Displays help and discards other options.\n"
      + "Order of options is not mandatory.\n"
      + "Examples:\n"
      + "pdfPrinter -s http://foo.com/index.html -t myDoc.pdf\n"
      + "  Converts index.html to myDoc.pdf\n"
      + "pdfPrinter -s /home/john/index.html -t myDoc.pdf\n"
      + "  Converts index.html to myDoc.pdf\n"
      + "pdfPrinter -s i1.html -s i2.html -t myDoc.pdf\n"
      + "  Converts i1.html and i2.html in two pdfs and pastes them "
      + "into myDoc.pdf\n"
    );
  }

  /**
   * @param args the command line arguments
   */
  @SuppressWarnings(
    {"ConvertToStringSwitch", "BroadCatchBlock", "TooBroadCatch"}
  )
  public static void main(String[] args) {
    charset = Charset.forName("UTF-8");
    sources = new ArrayList<>();
    rsources = new ArrayList<>();
    Html2pdf h2p = new Html2pdf();

    for (int i = 0; i < args.length; ++i) {
      String s = args[i];
      if (s.equals("-s")) {
        ++i;
        s = args[i];
        sources.add(s);
      } else if (s.equals("-t")) {
        ++i;
        s = args[i];
        target = new File(s);
      } else if (s.equals("-h")) {
        help2();
        return;
      } else if (s.equals("--charset")) {
        ++i;
        s = args[i];
        try {
          charset = Charset.forName(s);
        } catch (Exception e) {
          System.out.println(e.getMessage());
          help2();
          return;
        }
      } else if (s.equals("--landscape")) {
        ++i;
        s = args[i];
        if (s.equals("true")) {
          h2p.setLandscape(true);
        }
      } else if (s.equals("--landscape")) {
        ++i;
        s = args[i];
        if (s.equals("true")) {
          h2p.setLandscape(true);
        }
      } else if (s.equals("--margins")) {
        ++i;
        String[] ss = args[i].split("-");
        try {
          h2p.setMargins(
            Integer.valueOf(ss[0]),
            Integer.valueOf(ss[1]),
            Integer.valueOf(ss[2]),
            Integer.valueOf(ss[3])
          );
        } catch (Exception e) {
          System.out.println("Error in margins");
          help2();
          return;
        }
      } else if (s.equals("--pagesize")) {
        ++i;
        String[] ss = args[i].split("-");
        try {
          h2p.setPageSize(Float.valueOf(ss[0]), Float.valueOf(ss[1]));
        } catch (Exception e) {
          System.out.println("Error in pagesize");
          help2();
          return;
        }
      } else if (s.equals("--start")) {
        ++i;
        s = args[i];
        try {
          h2p.setStartPageNumber(Integer.valueOf(s));
        } catch (NumberFormatException e) {
          System.out.println("Error in starts");
          help2();
          return;
        }
      } else if (s.equals("--h-show")) {
        ++i;
        s = args[i];
        if (s.equals("true")) {
          h2p.getHeader().show = true;
        }
      } else if (s.equals("--h-y")) {
        ++i;
        s = args[i];
        try {
          h2p.getHeader().yCorrect = Float.valueOf(s);
        } catch (Exception e) {
          System.out.println("Error in --h-y");
          help2();
          return;
        }
      } else if (s.equals("--h-text")) {
        ++i;
        s = args[i];
        h2p.getHeader().text = s;
      } else if (s.equals("--h-align")) {
        ++i;
        s = args[i];
        if (s.length() != 1 || !"lcr".contains(s)) {
          System.out.println("Error in --h-align");
          help2();
          return;
        }
        h2p.getHeader().align = s.charAt(0);
      } else if (s.equals("--h-ffm")) {
        ++i;
        s = args[i];
        h2p.getHeader().fontFamily = s;
      } else if (s.equals("--h-fsz")) {
        ++i;
        s = args[i];
        try {
          h2p.getHeader().fontSize = Float.valueOf(s);
        } catch (Exception e) {
          System.out.println("Error in --h-fsz");
          help2();
          return;
        }
      } else if (s.equals("--h-fst")) {
        ++i;
        s = args[i];
        for (int j = 0; j < s.length(); ++j) {
          if ("nbius".indexOf(s.charAt(j)) == -1) {
            System.out.println("Error in --h-style");
            help2();
            return;
          }
        }
        h2p.getHeader().fontStyle = s;
      } else if (s.equals("--h-line")) {
        ++i;
        s = args[i];
        if (s.equals("true")) {
          h2p.getHeader().line = true;
        }
      } else if (s.equals("--h-lx")) {
        ++i;
        s = args[i];
        try {
          h2p.getHeader().lineXCorrect = Float.valueOf(s);
        } catch (Exception e) {
          System.out.println("Error in --h-lx");
          help2();
          return;
        }
      } else if (s.equals("--h-ly")) {
        ++i;
        s = args[i];
        try {
          h2p.getHeader().lineYCorrect = Float.valueOf(s);
        } catch (Exception e) {
          System.out.println("Error in --h-ly");
          help2();
          return;
        }
      } else if (s.equals("--f-show")) {
        ++i;
        s = args[i];
        if (s.equals("true")) {
          h2p.getFooter().show = true;
        }
      } else if (s.equals("--f-y")) {
        ++i;
        s = args[i];
        try {
          h2p.getFooter().yCorrect = Float.valueOf(s);
        } catch (Exception e) {
          System.out.println("Error in --f-y");
          help2();
          return;
        }
      } else if (s.equals("--f-text")) {
        ++i;
        s = args[i];
        h2p.getFooter().text = s;
      } else if (s.equals("--f-align")) {
        ++i;
        s = args[i];
        if (s.length() != 1 || !"lcr".contains(s)) {
          System.out.println("Error in --f-align");
          help2();
          return;
        }
        h2p.getFooter().align = s.charAt(0);
      } else if (s.equals("--f-ffm")) {
        ++i;
        s = args[i];
        h2p.getFooter().fontFamily = s;
      } else if (s.equals("--f-fsz")) {
        ++i;
        s = args[i];
        try {
          h2p.getFooter().fontSize = Float.valueOf(s);
        } catch (Exception e) {
          System.out.println("Error in --f-fsz");
          help2();
          return;
        }
      } else if (s.equals("--f-fst")) {
        ++i;
        s = args[i];
        for (int j = 0; j < s.length(); ++j) {
          if ("nbius".indexOf(s.charAt(j)) == -1) {
            System.out.println("Error in --f-style");
            help2();
            return;
          }
        }
        h2p.getFooter().fontStyle = s;
      } else if (s.equals("--f-line")) {
        ++i;
        s = args[i];
        if (s.equals("true")) {
          h2p.getFooter().line = true;
        }
      } else if (s.equals("--f-lx")) {
        ++i;
        s = args[i];
        try {
          h2p.getFooter().lineXCorrect = Float.valueOf(s);
        } catch (Exception e) {
          System.out.println("Error in --f-lx");
          help2();
          return;
        }
      } else if (s.equals("--f-ly")) {
        ++i;
        s = args[i];
        try {
          h2p.getFooter().lineYCorrect = Float.valueOf(s);
        } catch (Exception e) {
          System.out.println("Error in --f-ly");
          help2();
          return;
        }
      } else {
        System.out.println(s + ": Unknown option");
        help();
        return;
      }

    }

    if (target == null || sources.isEmpty()) {
      help();
      return;
    }

    for (String s : sources) {
      if (s.startsWith("http:")) {
        try {
          rsources.add(new BufferedReader(
            new InputStreamReader(new URL(s).openStream(), charset)));
        } catch (IOException ex) {
          System.out.println(ex.getMessage());
          help();
          return;
        }
      } else {
        try {
          rsources.add(new BufferedReader(
            new InputStreamReader(new FileInputStream(new File(s)), charset)));
        } catch (FileNotFoundException ex) {
          System.out.println(ex.getMessage());
          help();
          return;
        }
      }
    }

    if (rsources.size() == 1) {
      try {
        h2p.run(rsources.get(0), new FileOutputStream(target));
      } catch (FileNotFoundException ex) {
        System.out.println(ex.getMessage());
        help();
        return;
      }
    } else {
      ArrayList<File> tmpfs = new ArrayList<>();
      File tmpDir = new File(Std.tmpf());
      for (int i = 0; i < sources.size(); ++i) {
        File t = new File(tmpDir, "f" + i);
        tmpfs.add(t);
        try {
          h2p.run(rsources.get(i), new FileOutputStream(t));
        } catch (FileNotFoundException ex) {
          ex.printStackTrace();
        }
      }
      try {
        Html2pdf.paste(target, tmpfs.toArray(new File[0]));
      } catch (IOException ex) {
        Std.del(tmpDir.toString());
        System.out.println(ex.getMessage());
        help();
        return;
      } catch (DocumentException ex) {
        ex.printStackTrace();
      }
      Std.del(tmpDir.toString());
    }
  }
}


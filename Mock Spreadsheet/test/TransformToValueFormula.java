import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import edu.cs3500.spreadsheets.sexp.Parser;
import edu.cs3500.spreadsheets.sexp.SList;
import edu.cs3500.spreadsheets.sexp.SSymbol;
import edu.cs3500.spreadsheets.sexp.Sexp;

import static org.junit.Assert.assertEquals;

/**
 * tests transform to formula class.
 */
public class TransformToValueFormula {


  @Test
  public void TestParser() {

    //this.init();

    assertEquals(new SList(
                    new ArrayList<Sexp>(Arrays.asList(new SSymbol("SUM")))),
            Parser.parse("(SUM (SUM 5 3) 3)"));
  }

}

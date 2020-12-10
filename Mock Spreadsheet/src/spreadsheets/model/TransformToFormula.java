package spreadsheets.model;

import java.util.ArrayList;
import java.util.List;


import edu.cs3500.spreadsheets.sexp.Sexp;
import edu.cs3500.spreadsheets.sexp.SexpVisitor;

/**
 * a class that implements a sexpvisitor which process the sexpvisitor and return a formula.
 */
public class TransformToFormula implements SexpVisitor<Formula> {
  Spreadsheet model;

  public TransformToFormula(Spreadsheet model) {
    this.model = model;
  }

  String operator = "";
  List<Formula> args = new ArrayList<>();
  Function f = new Function(args, operator, this.model); /// something is wrong here


  /**
   * Process a boolean value.
   *
   * @param b the value
   * @return the desired result
   */
  @Override
  public Formula visitBoolean(boolean b) {

    return new ValueBoolean(b);

  }


  /**
   * Process a numeric value.
   *
   * @param d the value
   * @return the desired result
   */
  @Override
  public Formula visitNumber(double d) {
    return new ValueDouble(d);
  }

  /**
   * Process a list value.
   *
   * @param l the contents of the list (not yet visited)
   * @return the desired result
   */
  @Override
  public Formula visitSList(List<Sexp> l) {

    List<Formula> args = new ArrayList<>();
    Sexp op = l.get(0);
    op.accept(this);

    if (operator.equals("")) {

      throw new IllegalArgumentException("Malformed list");

    } else {

      for (int i = 1; i < l.size(); i++) {
        //System.out.println("operator1 " + operator);
        Sexp arg = l.get(i);
        Formula fo = arg.accept(new TransformToFormula(this.model));
        args.add(fo);
        f = new Function(args, operator, this.model);

      }

      return f;
    }
  }


  /**
   * helper method to determine if a given string is a reference or not.
   *
   * @param s the string to be evaluated.
   * @return the list of Coord if it is a reference if not should be empty list.
   */
  private List<Coord> visitSymbolHelp(String s) {

    List<Coord> refs = new ArrayList<>();

    boolean hitNum = false;
    String ref = "";
    String num = "";
    int col = 0;
    int row = 0;
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (Character.isLetter(c) && !hitNum) {
        ref += c;
      }
      col = Coord.colNameToIndex(ref);
      if (Character.isDigit(c)) {

        hitNum = true;
        num += c;
        row = Integer.parseInt(num);

      }

    }
    if (hitNum && ref.length() > 0) {
      Coord coord = new Coord(col, row);
      refs.add(coord);

    }
    return refs;
  }


  /**
   * Process a symbol.
   *
   * @param s the value
   * @return the desired result
   */
  @Override
  public Formula visitSymbol(String s) { // difference bw visit string and visit symbol
    Reference reference = new Reference(this.model);
    List<Coord> foundRef;
    int colonI = s.indexOf(":");
    if (colonI == -1) {
      foundRef = visitSymbolHelp(s);
      if (!foundRef.isEmpty()) {

        reference.getReference().addAll(foundRef);
      } else {
        visitString(s);
      }
    } else {
      String left = s.substring(0, colonI);
      String right = s.substring(colonI + 1);

      if (right.contains(":")) {
        throw new IllegalArgumentException("Invalid reference");
      } else {
        foundRef = visitSymbolHelp(left);
        List<Coord> foundR = visitSymbolHelp(right);
        if (!foundR.isEmpty() && !foundRef.isEmpty()) {
          foundRef.addAll(foundR);
          reference.getReference().addAll(foundRef);
        } else {
          throw new IllegalArgumentException("This is not a valid list of refs");
        }

      }

    }
    return reference;

  }

  /**
   * Process a string value.
   *
   * @param s the value
   * @return the desired result
   */
  @Override
  public Formula visitString(String s) {

    if (s.equals("SUM")) {
      operator = "SUM";
    } else if (s.equals("PRODUCT")) {
      operator = "PRODUCT";
    } else if (s.equals("SUB")) {
      operator = "SUB";
    } else if (s.equals("<")) {
      operator = "<";

    } else if (s.equals("APPEND")) {
      operator = "APPEND";
    }

    return new ValueString(s);


  }
}
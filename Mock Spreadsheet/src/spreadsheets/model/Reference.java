package spreadsheets.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Represents a reference to a Cell or a list of Cells.
 */
public class Reference implements Formula { // reference to cells not cells themselves ( B1, B2)

  private List<Coord> reference;
  private Spreadsheet model;

  /**
   * represents the references of a cell.
   * @param model   represents the model for this reference.
   */
  public Reference(Spreadsheet model) {
    reference = new ArrayList<>();
    this.model = model;

  }


  public List<Coord> getReference() {
    return reference;
  }

  // populates given two indeces
  // TO DO : MAKE SURE THAT 1ST COORD IS LESS THAN 2ND COORD
  private void populateReferences() {
    if (reference.size() == 2) {
      int col1 = reference.get(0).col;
      int col2 = reference.get(1).col;
      int row1 = reference.get(0).row;
      int row2 = reference.get(1).row;

      if ((col1 != col2) && (row1 != row2)) {
        throw new IllegalArgumentException("the reference is invalid. not vertical or horizontal");
      } else if (col1 == col2) {
        for (int i = row1 + 1; i < row2; i++) {
          Coord c = new Coord(col1, i);
          reference.add(c);
        }
      } else {
        for (int j = col1 + 1; j < col2; j++) {
          Coord c = new Coord(j, row1);
          reference.add(c);
        }
      }
    }
  }

  /**
   * accept method that accepts a list of formulaVisitors.
   *
   * @param visitor list of formulaVisitors
   * @param <T>     A Cell
   * @return A Cell
   */
  @Override
  public <T> T accept(FormulaVisitor<T> visitor) {

    if (this.model.visitAll()) {
      throw new IllegalArgumentException("there is a cycle ");
    } else {
      return visitor.referenceVisitor(this.reference);
    }
  }


  /**
   * Evaluates the formula.
   *
   * @return the evaluated formula.
   */
  @Override
  public Value evaluate() {

    Value v = new ValueDouble(0.0);
    for (Coord c : reference) {

      if (model.getCellAt(c) == null) {

        v = new ValueDouble(0.0);

      }
      else {
        Cell cell = (Cell) model.getCellAt(c);
        v = cell.getFormula().evaluate();
      }
    }
    return v;
  }


  /**
   * helps with cycles in a formula.
   *
   * @param seen list of seen coords
   * @return true if there is a loop
   */
  @Override
  public boolean depHelp(Stack<Coord> seen) {
    boolean b = false;
    for (Coord coord : this.reference) {
      if (seen.contains(coord)) {
        b = true;
      }
      seen.push(coord);
      ICell c = this.model.getCellAt(coord);
      b = b || (c.getFormula().depHelp(seen));
      seen.pop();
    }
    return b;
  }


}
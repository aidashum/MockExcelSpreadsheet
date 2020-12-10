package spreadsheets.model;

import java.util.List;

/**
 * An abstracted function object for processing any {@link Formula}ressions.
 *
 * @param <T> The return type of this Formula.
 */
public interface FormulaVisitor<T> {


  /**
   * Process the Boolean class.
   *
   * @param b the value
   * @return the desired result
   */
  T booleanVisitor(boolean b);

  /**
   * Process a Double class.
   *
   * @param b the value
   * @return the desired result
   */
  T doubleVisitor(double b);

  /**
   * Process the Strign class.
   *
   * @param b the value
   * @return the desired result
   */
  T valueStringVisitor(String b);

  /**
   * Process a reference.
   *
   * @param reference the value
   * @return the desired result
   */
  T referenceVisitor(List<Coord> reference);

  /**
   * Process a function.
   *
   * @param f the value
   * @return the desired result
   */
  T functionVisitor(Function f);
}
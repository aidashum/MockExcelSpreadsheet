package spreadsheets.model;

/**
 * getter class for the string value of a cell.
 */
public class GetString implements ValueToArg<String> {


  /**
   * Visitor for a boolean value.
   *
   * @param b boolean value to be visited.
   * @return null becuase a boolean is not a double
   */
  @Override
  public String booleanVisitor(boolean b) {
    return null;
  }

  /**
   * visitor for a string value.
   *
   * @param str string value to be visited.
   * @return str.
   */
  @Override
  public String stringVisitor(String str) {
    return str;
  }

  /**
   * visitor for a double.
   *
   * @param d the double to be visited.
   * @return null because a double is not a string.
   */
  @Override
  public String doubleVisitor(double d) {
    return null;
  }
}

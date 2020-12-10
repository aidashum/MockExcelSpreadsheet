package spreadsheets.model;

/**
 * An abstracted value object for processing any {@link Value}ressions.
 *
 * @param <K> The return type of this function
 */
public interface ValueToArg<K> {

  /**
   * Visitor for a boolean value.
   *
   * @param b boolean value to be visited.
   * @return K.
   */
  K booleanVisitor(boolean b);

  /**
   * visitor for a string value.
   *
   * @param str string value to be visited.
   * @return k.
   */
  K stringVisitor(String str);

  /**
   * visitor for a double.
   *
   * @param d the double to be visited.
   * @return K
   */
  K doubleVisitor(double d);
}

package assign09;

/**
  * A student object with good hash function.
  * @author Camden Lyon, Ibrahim Alasady
  * @version 2026-11-11
  */

public class StudentGoodHash {
  private String firstName;
  private String lastName;
  private int studentID;

/**
  * Constructs a student
  */
public StudentGoodHash(String firstName, String lastName, int studentID) {
  this.firstName = firstName;
  this.lastName = lastName;
  this.studentID = studentID;
}

/**
  *Generates good hash code using student fields
  * @return hash code
  */
@Override
public int hashCode() {
  int result = 17;

  result = 31 * result + firstName.hashCode();
  result = 31 * result + lastName.hashCode();
  result = 31 * result + studentID;

  return result;
}

/**
  * Checks if two students are equal
  * @return true if both students have identical info
  */
@Override
public boolean equals(Object obj) {
  if (this == obj)
    return true;
  if (obj == null || getClass() != obj.getClass())
    return false;

  StudentGoodHash other = (StudentGoodHash) obj;

  return studentID == other.studentID
    && firstName.equals(other.firstName)
    && lastName.equals(other.lastName);
}
}

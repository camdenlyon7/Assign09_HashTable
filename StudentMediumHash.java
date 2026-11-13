package assign09;

/**
  *A student object with medium quality hash function.
  *
  */

public class StudentMediumHash {
  private String firstName;
  private String lastName;
  private int studentID;

/**
  * Constructs a student
  *
  */
public StudentMediumHash(String firstName, String lastName, int studentID) {
  this.firstName = firstName;
  this.lastName = lastName;
  this.studentID = studentID;
}

/**
  * Generates medium quality hash code using student fields
  *@return hash code
  */
@Override
public int hashCode() {
  return firstName.hashCode()
    + lastName.hashCode()
    + studentID;
}

/**
  * Checks whether two student are equal.
  *@return true if both students have identical info
  */
@Override
public boolean equals(Object obj) {
  if (this == obj)
    return true;

  if (obj == null || getClass() != obj.getClass())
    return false;

  StudentMediumHash other = (StudentMediumHash) obj;

  return studentID == other.studentID
    && firstName.equals(other.firstName)
    && lastName.equals(other.lastName);
}
}
  

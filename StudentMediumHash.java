package assign09;

/**
  *A student object with medium quality hash function.
  *
  */

public class StudentMediumHash {
  private int studentID;
  private String firstName;
  private String lastName;
  

/**
  * Constructs a student
  *
  */
public StudentMediumHash(int studentID, String firstName, String lastName) {
  this.studentID = studentID;
  this.firstName = firstName;
  this.lastName = lastName;
  
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
  

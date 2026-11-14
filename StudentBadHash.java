package assign09;

/**
  * A student object with poor hash function.
  *
  *@author Camden Lyon, Ibrahim Alasady
  *@version 2026-11-11
  */

public class StudentBadHash {
  private int studentID;
  private String firstName;
  private String lastName;
  

/**
  * Construct a student
  *
  */

public StudentBadHash(String firstName, String lastName, int studentID) {
  this.studentID = studentID;
  this.firstName = firstName;
  this.lastName = lastName;
  
}

/**
  *Generates poor hash code using only first character
  *
  *@return hash code
  */

@Override 
public int hashCode() {
  return firstName.charAt(0);
}

/**
  *Checks whether two students are equal.
  */

@Override
public boolean equals(Object obj) {
  if (this == obj)
    return true;

  if (obj == null || getClass() != obj.getClass())
    return false;

  StudentBadHash other = (StudentBadHash) obj;

  return studentID == other.studentID
    && firstName.equals(other.firstName)
    && lastName.equals(other.lastName);
}
}

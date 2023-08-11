/***************************************************************************
 * File:  Student.java Course materials (23S) CST 8277
 * 
 * @author Teddy Yap
 * @author Shariar (Shawn) Emami
 * @date August 28, 2022
 * 
 * Updated by:  Group NN
 *   studentId, firstName, lastName (as from ACSIS)
 *   studentId, firstName, lastName (as from ACSIS)
 *   studentId, firstName, lastName (as from ACSIS)
 *   studentId, firstName, lastName (as from ACSIS)
 *   
 */
package acmecollege.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the student database table.
 */
@SuppressWarnings("unused")
//TODO ST01 - Add the missing annotations.
//TODO ST02 - Do we need a mapped super class? If so, which one?
//TODO ST02 - Do we need a mapped super class? If so, which one?
@Entity(name = "STUDENT")
@Table(name = "Student")
@AttributeOverride(name = "id", column = @Column(name="ID"))
@AttributeOverride(name = "version", column = @Column(name="version"))
@AttributeOverride(name = "created", column = @Column(name="created"))
@AttributeOverride(name = "updated", column = @Column(name="updated"))
@NamedQuery(name = Student.GET_ALL_STUDENTS_QUERY_NAME, query = "SELECT s FROM STUDENT s")
public class Student extends PojoBase implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String GET_ALL_STUDENTS_QUERY_NAME = "Student.getAllStudents";

    public Student() {
    	super();
    }

 // TODO ST03 - Add annotation
 	  @Column(name = "first_name")
 	private String firstName;

 	// TODO ST04 - Add annotation
 	  @Column(name = "last_name")
 	private String lastName;

 	// TODO ST05 - Add annotations for 1:M relation.  Changes should not cascade.
 	  @OneToMany(mappedBy="owner")
 	private Set<MembershipCard> membershipCards = new HashSet<>();

 	// TODO ST06 - Add annotations for 1:M relation.  Changes should not cascade.
 	  @OneToMany(mappedBy="student" )
 	private Set<CourseRegistration> courseRegistrations = new HashSet<>();

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

    // Simplify JSON body, skip MembershipCards
    @JsonIgnore
    public Set<MembershipCard> getMembershipCards() {
		return membershipCards;
	}

	public void setMembershipCards(Set<MembershipCard> membershipCards) {
		this.membershipCards = membershipCards;
	}

    // Simplify JSON body, skip CourseRegistrations
    @JsonIgnore
    public Set<CourseRegistration> getCourseRegistrations() {
		return courseRegistrations;
	}

	public void setCourseRegistrations(Set<CourseRegistration> courseRegistrations) {
		this.courseRegistrations = courseRegistrations;
	}

	public void setFullName(String firstName, String lastName) {
		setFirstName(firstName);
		setLastName(lastName);
	}
	
	//Inherited hashCode/equals is sufficient for this entity class

}

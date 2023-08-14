package acmecollege.entity;
/**
 *  * Updated by:  Group 13
 *   041042258, Fatemeh, Baladi (as from ACSIS)
 *   041040628, Parham, Barati (as from ACSIS)
 *   041043087, Justin, Rackus (as from ACSIS)
 *   040863962, Pouya, Varghaei (as from ACSIS)
 */
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2023-07-28T12:58:39.262-0400")
@StaticMetamodel(CourseRegistration.class)
public class CourseRegistration_ extends PojoBaseCompositeKey_ {
	public static volatile SingularAttribute<CourseRegistration, CourseRegistrationPK> id;
	public static volatile SingularAttribute<CourseRegistration, Student> student;
	public static volatile SingularAttribute<CourseRegistration, Course> course;
	public static volatile SingularAttribute<CourseRegistration, Professor> professor;
	public static volatile SingularAttribute<CourseRegistration, Integer> numericGrade;
	public static volatile SingularAttribute<CourseRegistration, String> letterGrade;
}

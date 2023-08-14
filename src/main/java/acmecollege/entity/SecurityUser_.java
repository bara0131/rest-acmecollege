package acmecollege.entity;
/**
 *  * Updated by:  Group 13
 *   041042258, Fatemeh, Baladi (as from ACSIS)
 *   041040628, Parham, Barati (as from ACSIS)
 *   041043087, Justin, Rackus (as from ACSIS)
 *   040863962, Pouya, Varghaei (as from ACSIS)
 */
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2023-08-02T18:16:30.952-0400")
@StaticMetamodel(SecurityUser.class)
public class SecurityUser_ {
	public static volatile SingularAttribute<SecurityUser, Integer> id;
	public static volatile SingularAttribute<SecurityUser, String> username;
	public static volatile SingularAttribute<SecurityUser, String> pwHash;
	public static volatile SingularAttribute<SecurityUser, Student> student;
	public static volatile SetAttribute<SecurityUser, SecurityRole> roles;
}

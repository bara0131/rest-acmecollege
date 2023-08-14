package acmecollege.entity;
/**
 *  * Updated by:  Group 13
 *   041042258, Fatemeh, Baladi (as from ACSIS)
 *   041040628, Parham, Barati (as from ACSIS)
 *   041043087, Justin, Rackus (as from ACSIS)
 *   040863962, Pouya, Varghaei (as from ACSIS)
 */
import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2023-07-28T12:58:39.274-0400")
@StaticMetamodel(PojoBaseCompositeKey.class)
public class PojoBaseCompositeKey_ {
	public static volatile SingularAttribute<PojoBaseCompositeKey, Integer> version;
	public static volatile SingularAttribute<PojoBaseCompositeKey, LocalDateTime> created;
	public static volatile SingularAttribute<PojoBaseCompositeKey, LocalDateTime> updated;
}

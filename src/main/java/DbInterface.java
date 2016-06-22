import java.util.List;
import java.util.Set;

public interface DbInterface {
    public boolean addEntityToDB(Object...objects);
    public long getUniqueAccountNumber();
    public boolean deleteEntityFromBD(Object...objects);
    public List getAllEntities(Class objectClass);
    public Object getEntityById(Class objectClass, long id);
    public List getByEqualUnique(Class objectClass, String parameter, Object value);
}

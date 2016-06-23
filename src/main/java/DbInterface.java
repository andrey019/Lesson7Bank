import java.util.List;

public interface DbInterface {
    public boolean addEntityToDB(Object...objects);
    public long getUniqueAccountNumber();
    public boolean deleteEntityFromBD(Object...objects);
    public List getAllEntities(Class objectClass);
    public Object getEntityById(Class objectClass, long id);
    public Account getAccountByNumber(long number);
}

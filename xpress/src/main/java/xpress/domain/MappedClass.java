package xpress.domain;

import org.mockito.cglib.proxy.Enhancer;

import xpress.ann.Column;
import xpress.exceptions.WrongParameterException;
import xpress.util.StringUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MappedClass
{
    private String databaseName;
    private String alias;
    private Enhancer enhancer;
    private Class mappedClass;

	public MappedClass(Class mappedClass, String databaseName, String alias) {
	    this.mappedClass = mappedClass;
	    this.databaseName = databaseName;
	    this.alias = alias;

	    enhancer = new Enhancer();
	    enhancer.setSuperclass(mappedClass);
	    enhancer.setCallback(new LazyInterceptor(this));
	}

    private IndexField indexField;

	private Map<String, ClassField> classFields = new HashMap<>();
    private List<Relationship> relationships = new ArrayList<>();

    public Object create() { return enhancer.create(); }

    public ClassField addClassField(Field field, String databaseName, int fetchType){
        ClassField n = new ClassField(field, databaseName, fetchType);
        getClassFields().put(field.getName(), n);
        return n;
    }

    public void addIndexField(Field field, String databaseName, int fetchType, boolean identity){
        indexField = new IndexField(field, databaseName, fetchType, identity);
        getClassFields().put(field.getName(), indexField);
    }

    public void addRelationship(MappedClass destiny, Field field, int fetchType, String attribute){
        relationships.add(new Relationship(destiny, field, fetchType, attribute));
    }

    public IndexField getIndexField() { return indexField; }

    public List<Relationship> getRelationships() { return relationships; }

    protected List<String> getDatabaseFiledsName() {
        List<String> ret = new ArrayList<>();
        for (ClassField c : getClassFields().values()) {
            if (c.getFetchType() == ClassField.EAGER) ret.addAll(c.getClassFields(getAlias()));
        }
        return ret;
    }

    protected List<String> getDatabaseFiledsNameExceptIndex() {
        List<String> ret = new ArrayList<>();
        for (ClassField c : getClassFields().values()) {
            if (!((c.getDatabaseName() == getIndexField().getDatabaseName()) && (getIndexField().getIdentity()))) {
                if (c.getFetchType() == ClassField.EAGER) ret.add(c.getDatabaseName());
            }
        }
        return ret;
    }

    protected String getJoins () {
        String q = "";
        for (ClassField c : getClassFields().values()) {
            if (c.getFetchType() == ClassField.EAGER) q += c.getJoins(alias);
        }
        return q;
    }

    protected String getXql (String padreClase, String xql) {
        for (ClassField c : getClassFields().values()) {
            xql = c.getXql(padreClase, getAlias(), xql);
        }
        return xql;
    }

    public String getSelect(String xql) throws WrongParameterException {
        List<String> select = getDatabaseFiledsName();
        String joins = getJoins();

        for (String s:xql.split(" ")) {
            if (s.startsWith("$")) {
                try {
                    if (getClassFields().containsKey(s.substring(1, s.indexOf(".")))) {
                        ClassField lazyField = getClassFields().get(s.substring(1, s.indexOf(".")));

                        if (lazyField.getFetchType() != Column.EAGER) {
                            joins += lazyField.getJoins(alias, false);
                        }
                    }
                }
                catch (Exception ex){ }
            }
        }

        xql = getXql("", xql);

        String q = "SELECT " + StringUtil.join(select, ", ");
        q += " FROM " + getDatabaseName();
        q += " AS " + getAlias();
        q += joins;

        if(xql.length() > 0) {

            q += " WHERE " + xql;
            if (q.contains(" $")) throw new WrongParameterException();
        }

        return q;
    }

    public String getDelete(String xql) throws WrongParameterException {
        String q = "";
        q = "DELETE FROM " + getDatabaseName();

        if(xql.length() > 0) {
            q += " WHERE " + getXql(null, xql);

            if (q.contains(" $")) throw new WrongParameterException();
        }

        return q;
    }

    public String getInsert() throws WrongParameterException {
        List<String> fields = getDatabaseFiledsNameExceptIndex();
        String q = "";
        q = "INSERT INTO " + getDatabaseName();
        q += "(" + StringUtil.join(fields, ", ") + ") VALUES (";

        for(String c : fields)
            q += String.format(":%s, ",c,c);

        return StringUtil.replaceLast(q, ", ") + ")";
    }

    public String getUpdate(String xql) throws WrongParameterException {
        String q = "";
        q = "UPDATE " + getDatabaseName() + " ";

        if(xql.length() > 0) {
            q += getXql(null, xql);

            if (q.contains(" $")) throw new WrongParameterException();
        }

        return q;
    }

    protected String getClassName() { return mappedClass.getSimpleName(); }

    public Class getMappedClass() { return mappedClass; }

    public String getDatabaseName() { return databaseName; }

    public String getAlias() { return alias; }

    public Map<String, ClassField> getClassFields() { return classFields; }
}

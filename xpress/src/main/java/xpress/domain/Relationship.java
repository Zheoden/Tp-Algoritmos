package xpress.domain;

import xpress.ann.Column;
import xpress.exceptions.WrongParameterException;
import xpress.util.StringUtil;

import java.lang.reflect.Field;

/**
 * Created by TATIANA on 23/4/2017.
 */
public class Relationship {
    public static int RELATION = 1;
    public static int LAZY_COLUMN = 2;

    private MappedClass mappedClass; //La mappedClass destino siempre se joinea por id
    private int fetchType = RELATION;
    private String attribute;
    private Field field;

    public Relationship(MappedClass mappedClass, Field field, int fetchType, String attribute) {
        this.mappedClass = mappedClass;
        this.fetchType = fetchType;
        this.attribute = attribute;
        this.field = field;
    }

    public String getSelect() throws WrongParameterException {
        return this.mappedClass.getSelect("$" + attribute + " = ?");
    }

    public String getClassName() { return field.getName(); }

    public String getGetterClassName() { return StringUtil.getGetterName(field.getName()); }

    public MappedClass getMappedClass() {
        return mappedClass;
    }

    public String getAttribute() {
        return attribute;
    }

    public Field getField() { return field; }

    public int getFetchType() { return fetchType; }
}

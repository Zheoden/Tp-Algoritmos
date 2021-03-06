package xpress.domain;

import java.lang.reflect.Field;

import xpress.ann.Id;


public class IndexField extends ClassField {

    private Boolean identity = false;

    public IndexField(Field field, String databaseName, int fetchType, boolean identity) {
        super(field, databaseName, fetchType);
        setIdentity(identity);
    }

    public Boolean getIdentity() {
        return identity;
    }

    public void setIdentity(Boolean identity) {
        this.identity = identity;
    }
}

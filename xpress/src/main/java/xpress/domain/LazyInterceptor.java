package xpress.domain;

import org.mockito.cglib.proxy.MethodInterceptor;
import org.mockito.cglib.proxy.MethodProxy;

import xpress.Utn;
import xpress.UtnConnectionFactory;

import java.lang.reflect.Method;


public class LazyInterceptor implements MethodInterceptor {

    private MappedClass mappedClass;

    public LazyInterceptor(MappedClass mappedClass) {
        this.mappedClass = mappedClass;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        for (Relationship r : mappedClass.getRelationships()) {
            if (method.getName().equals(r.getGetterClassName())) {
                r.getField().setAccessible(true);
                if (r.getField().get(o) == null) {
                    String xql = r.getMappedClass().getAlias() + "." + r.getAttribute();

                    if (r.getFetchType() == Relationship.RELATION)
                        r.getField().set(o, Utn.query(UtnConnectionFactory.getConnection(),
                                r.getMappedClass().getMappedClass(), xql,
                                mappedClass.getIndexField().getField().get(o)));
                    else
                        r.getField().set(o, Utn.query(UtnConnectionFactory.getConnection(),
                                r.getMappedClass().getMappedClass(), xql,
                                mappedClass.getIndexField().getField().get(o)).get(0));
                }
                return r.getField().get(o);
            }
        }

        return methodProxy.invokeSuper(o, objects);
    }
}

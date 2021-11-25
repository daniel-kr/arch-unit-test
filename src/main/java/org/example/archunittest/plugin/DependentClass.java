package org.example.archunittest.plugin;

import org.example.archunittest.platform.ApiClass;

import java.util.List;

public class DependentClass {

    //public ApiClass attribute; //Uncommenting this line makes the test pass
    public Class<?> classReference = ApiClass.class;
    public List<ApiClass> genericType;

    public boolean checkInstanceOf() {
        return genericType instanceof ApiClass;
    }

    public void methodThrowingException() throws ApiClass {
    }
}

package org.example.archunittest.plugin;

import org.example.archunittest.platform.ApiClass;

public class DependentClass {

    //public ApiClass attribute; //Uncommenting this line makes the test pass
    public Class<ApiClass> classReference = ApiClass.class;
}

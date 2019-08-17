package com.scut.se.sehubbackend;

import org.springframework.test.context.*;
import org.springframework.test.context.support.AbstractTestContextBootstrapper;

import java.util.List;

@BootstrapWith
public class BootstrapperDemo extends AbstractTestContextBootstrapper {

    @Override
    protected Class<? extends ContextLoader> getDefaultContextLoaderClass(Class<?> testClass) {
        return null;
    }
}

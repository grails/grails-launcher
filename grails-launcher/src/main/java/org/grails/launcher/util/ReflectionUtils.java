/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.grails.launcher.util;

import java.lang.reflect.InvocationTargetException;

public class ReflectionUtils {

    public static Object invokeMethodWrapException(Object target, String name) {
        return invokeMethodWrapException(target, name, new Object[0]);
    }

    public static Object invokeMethodWrapException(Object target, String name, Object[] args) {
        try {
            return invokeMethod(target, name, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object invokeMethodWrapException(Object target, String name, Class<?>[] argTypes, Object[] args) {
        try {
            return invokeMethod(target, name, argTypes, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object invokeMethod(Object target, String name) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return invokeMethod(target, name, new Object[0]);
    }

    public static Object invokeMethod(Object target, String name, Object[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Class<?>[] argTypes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            argTypes[i] = args[i].getClass();
        }

        return invokeMethod(target, name, argTypes, args);
    }

    public static Object invokeMethod(Object target, String name, Class<?>[] argTypes, Object[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return target.getClass().getMethod(name, argTypes).invoke(target, args);
    }

    public static Object invokeStaticMethodWrapException(Class<?> target, String name) {
        return invokeStaticMethodWrapException(target, name, new Object[0]);
    }

    public static Object invokeStaticMethodWrapException(Class<?> target, String name, Object[] args) {
        try {
            return invokeStaticMethod(target, name, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object invokeStaticMethodWrapException(Class<?> target, String name, Class<?>[] argTypes, Object[] args) {
        try {
            return invokeStaticMethod(target, name, argTypes, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object invokeStaticMethod(Class<?> target, String name) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return invokeStaticMethod(target, name, new Object[0]);
    }

    public static Object invokeStaticMethod(Class<?> target, String name, Object[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Class<?>[] argTypes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            argTypes[i] = args[i].getClass();
        }

        return invokeStaticMethod(target, name, argTypes, args);
    }

    public static Object invokeStaticMethod(Class<?> target, String name, Class<?>[] argTypes, Object[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return target.getMethod(name, argTypes).invoke(null, args);
    }
}

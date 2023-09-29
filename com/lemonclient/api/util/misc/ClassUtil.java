//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\πŸ≈¡ »≠∏È\map"!

//Decompiled by Procyon!

package com.lemonclient.api.util.misc;

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.function.*;
import java.util.zip.*;

public class ClassUtil
{
    public static ArrayList<Class<?>> findClassesInPath(final String classPath) {
        final ArrayList<Class<?>> foundClasses = new ArrayList<Class<?>>();
        final String resource = ClassUtil.class.getClassLoader().getResource(classPath.replace(".", "/")).getPath();
        if (resource.contains("!")) {
            try {
                final ZipInputStream file = new ZipInputStream(new URL(resource.substring(0, resource.lastIndexOf(33))).openStream());
                ZipEntry entry;
                while ((entry = file.getNextEntry()) != null) {
                    final String name = entry.getName();
                    if (name.startsWith(classPath.replace(".", "/") + "/") && name.endsWith(".class")) {
                        try {
                            final Class<?> clazz = Class.forName(name.substring(0, name.length() - 6).replace("/", "."));
                            foundClasses.add(clazz);
                        }
                        catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        else {
            try {
                final URL classPathURL = ClassUtil.class.getClassLoader().getResource(classPath.replace(".", "/"));
                if (classPathURL != null) {
                    final File file2 = new File(classPathURL.getFile());
                    if (file2.exists()) {
                        final String[] classNamesFound = file2.list();
                        if (classNamesFound != null) {
                            for (final String className : classNamesFound) {
                                if (className.endsWith(".class")) {
                                    foundClasses.add(Class.forName(classPath + "." + className.substring(0, className.length() - 6)));
                                }
                            }
                        }
                    }
                }
            }
            catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        foundClasses.sort(Comparator.comparing((Function<? super Class<?>, ? extends Comparable>)Class::getName));
        return foundClasses;
    }
}

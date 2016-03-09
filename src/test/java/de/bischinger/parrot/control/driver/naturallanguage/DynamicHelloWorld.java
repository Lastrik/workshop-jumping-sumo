package de.bischinger.parrot.control.driver.naturallanguage;

import net.openhft.compiler.CachedCompiler;


/**
 * Created by bischofa on 02/03/16.
 */
public class DynamicHelloWorld {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException,
        InstantiationException {

        String className = "mypackage.MyClass";
        String javaCode = "package mypackage;\n"
            + "public class MyClass implements Runnable {\n"
            + "    public void run() {\n"
            + "        System.out.println(\"Hello World\");\n"
            + "    }\n"
            + "}\n";
        Class aClass = new CachedCompiler(null, null).loadFromJava(className, javaCode);
        Runnable runner = (Runnable) aClass.newInstance();
        runner.run();
    }
}

/*
 * Based this blog:http://tutorials.jenkov.com/java-reflection/classes.html
 * I want to try one by one method to get class name. In this class, we will 
 * use Long class sample.
 * 
 */
package practicejava.reflection;

import java.lang.reflect.Modifier;

/**
 *
 * @author Chandra
 */
public class SampleClassReflection {
    Class myClass;
    public SampleClassReflection(Class myClass) {
        this.myClass = myClass; 
    }
    
    /**
     * display class name that's got at compile time
     */
    public void displayClassnameAtRuntime(){
        System.out.println("Name Long class : " + 
                this.myClass.getName());
        System.out.println("Canonical Name Long class : " + 
                this.myClass.getCanonicalName());
    }
    
    public void dipslayModifier(){
        int mod = this.myClass.getModifiers();
        System.out.println("Modifiers : " + mod);
        System.out.println("Modifier.isAbstract:" + Modifier.isAbstract(mod));
        System.out.println("Modifier.isFinal:" + Modifier.isFinal(mod));
        System.out.println("Modifier.isInterface:" + Modifier.isInterface(mod));
        System.out.println("Modifier.isNative:" + Modifier.isNative(mod));
        System.out.println("Modifier.isPrivate:" + Modifier.isPrivate(mod));
        System.out.println("Modifier.isProtected:" + Modifier.isProtected(mod));
        System.out.println("Modifier.isPublic:" + Modifier.isPublic(mod));
        System.out.println("Modifier.isStatic:" + Modifier.isStatic(mod));
        System.out.println("Modifier.isStrict:" + Modifier.isStrict(mod));
        System.out.println("Modifier.isSynchronized:" + Modifier.isSynchronized(mod));
        System.out.println("Modifier.isTransient:" + Modifier.isTransient(mod));
        System.out.println("Modifier.isVolatile:" + Modifier.isVolatile(mod));
    }

    /**
     * create new instance without arguments using newInstance
     *
     * @return Object
     */
    public Object newInstance(){
        try {
            return this.myClass.newInstance();
        } catch(IllegalAccessException e1) {
            e1.printStackTrace();;
        } catch(InstantiationException e2) {
            e2.printStackTrace();;
        }
        //  unreach code
        return null;
    }

}

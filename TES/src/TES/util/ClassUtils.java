package TES.util;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ClassUtils {

    public static List<Class<?>> findClassesInDirectory(String directoryName, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        File directory = new File(directoryName);
        if (!directory.exists()) {
            return classes;
        }

        File[] files = directory.listFiles();
        System.out.println(files.length);
        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(findClassesInDirectory(file.getAbsolutePath(), packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".java")) {
                String className = packageName + "." + file.getName().substring(0, file.getName().length() - 5);
                System.out.println(className);
                Class<?> clazz = Class.forName(className); //Yeni class ekleyince Class.forName'de runtime sırasında güncellenmiyo.
                classes.add(clazz);
            }

        }

        return classes;
    }

    public static List<Class<?>> getClassesExtendingBaseClass(String directoryName, String packageName, Class<?> baseClass) throws ClassNotFoundException {
        List<Class<?>> allClasses = findClassesInDirectory(directoryName, packageName);
        List<Class<?>> extendingClasses = new ArrayList<>();

        for (Class<?> clazz : allClasses) {
            if (baseClass.isAssignableFrom(clazz) && !clazz.equals(baseClass)) {
                extendingClasses.add(clazz);
            }
        }

        return extendingClasses;
    }

}

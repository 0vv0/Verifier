package os.web.verifier.model;


import org.springframework.stereotype.Component;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class AppsListReader implements Applications {
    private List<String> namesOfClasses;
    private static Logger logger = Logger.getLogger(AppsListReader.class.getName());

    @Override
    public List<String> getAllClassNames() {
        reReadFiles();
        return namesOfClasses;
    }

    private synchronized void reReadFiles() {
        String path = getCurrentDirectoryWithPackage() + "Apps";
        logger.log(Level.SEVERE, path);

        File file = new File(path);
        File[] files = file.listFiles();
        logger.log(Level.SEVERE, Arrays.toString(files));

        if (files != null) {
            namesOfClasses = new ArrayList<>(files.length);
            for (File f : files) {
                namesOfClasses.add(f.getName().replace(".class", ""));
//                logger.log(Level.INFO, f.getName());
            }
        } else {
            namesOfClasses = new ArrayList<>(0);
        }
    }

    public static String getCurrentDirectory(Class clazz) {
        String path = clazz.getProtectionDomain().getCodeSource().getLocation().getPath()
//                + clazz.getPackage().getName()
                ;
        logger.log(Level.SEVERE, path);
//        path = path.replace('.', '/');
        return path;
    }

    public String getCurrentDirectory(){
        return getCurrentDirectory(this.getClass());
    }

    public String getCurrentDirectoryWithPackage(){
        return getCurrentDirectory() + getClass().getPackage().getName().replace('.', '/') + "/";
    }

    public Application getClassByName(String className) {
        try {
            String path = getCurrentDirectory();
            File f = new File(path);
            URL[] urls = new URL[]{f.toURI().toURL()};
            logger.log(Level.INFO, Arrays.toString(urls));
            // Create a File object on the root of the directory containing the class file
//            File file = new File("c:\\myclasses\\");
            // Create a new class loader with the directory
            ClassLoader cl = new URLClassLoader(urls, getClass().getClassLoader());

            // Load in the class; MyClass.class should be located in
            // the directory file:/c:/myclasses/com/mycompany
//            Class cls = cl.loadClass("com.mycompany.MyClass");
            Class clazz = cl.loadClass("os.web.verifier.model.Apps." + className );
            return (Application) clazz.newInstance();
        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException | NullPointerException | MalformedURLException e) {
            logger.log(Level.SEVERE, e.toString());
        }
        return null;
    }

}

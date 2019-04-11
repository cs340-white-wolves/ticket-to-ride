package cs340.TicketToRide;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cs340.TicketToRide.model.db.IDaoFactory;

public class PersistencePluginManager {
    private static final String CONFIG_LINE = "^([^ ]+)\\W+([^ ]+)\\W+([^ ]+)$";
    private static final String COMMENT_LINE = "^#(.*)+$";
    private static final String PLUGIN_FILE = "plugins/config.txt";

    private static PersistencePluginManager singleton;

    private Map<String, String> plugins = new HashMap<>();

    public static PersistencePluginManager getInstance () {
        if (singleton == null) {
            singleton = new PersistencePluginManager();
        }

        return singleton;
    }

    private PersistencePluginManager() {
        processConfig();
    }

    public void registerPlugin(String pluginSlug, String className) {
        plugins.put(pluginSlug, className);
    }

    public IDaoFactory createPluginFactory(String pluginSlug) {
        String className = plugins.get(pluginSlug);

        if (className == null) {
            throw new RuntimeException("Cannot find plugin: " + pluginSlug);
        }

        try {
            Class<?> clazz = Class.forName(className);
            return (IDaoFactory) clazz.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void processConfig() {
        Pattern definitionLine = Pattern.compile(CONFIG_LINE);
        Pattern commentLine = Pattern.compile(COMMENT_LINE);

        File file = new File(PLUGIN_FILE);

        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line;

            while ((line = br.readLine()) != null) {
                Matcher commentMatcher = commentLine.matcher(line);
                if (commentMatcher.matches()) {
                    continue;
                }

                Matcher matcher = definitionLine.matcher(line);
                if (! matcher.matches()) {
                    throw new RuntimeException("Invalid configuration line: " + line);
                }

                String slug = matcher.group(1);
                String jarPath = matcher.group(2);
                String classPath = matcher.group(3);

                registerPlugin(slug, classPath);
                addJarToClassLoader(jarPath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Plugin config file not found or could not be read!");
        }
    }

    private void addJarToClassLoader(String pathToJar) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try {
            Method method = classLoader.getClass().getDeclaredMethod("addURL", URL.class);
            method.setAccessible(true);
            method.invoke(classLoader, new File(pathToJar).toURI().toURL());
            return;
        } catch (NoSuchMethodException|MalformedURLException|IllegalAccessException|InvocationTargetException e) {
            // Do nothing
        }

        try {
            Method method = classLoader.getClass().getDeclaredMethod("appendToClassPathForInstrumentation", String.class);
            method.setAccessible(true);
            method.invoke(classLoader, pathToJar);
        } catch (NoSuchMethodException|IllegalAccessException|InvocationTargetException e) {
            // Do nothing
        }
    }
}

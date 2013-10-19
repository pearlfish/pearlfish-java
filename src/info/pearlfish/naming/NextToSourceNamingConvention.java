package info.pearlfish.naming;

import info.pearlfish.FileNamingConvention;
import info.pearlfish.TestSpecific;
import info.pearlfish.internal.PerTestFileNamingConvention;

import java.io.File;

/**
 * Writes the results of a test into a file named after the test class and method, in a nested directory
 * that follows the package structure.
 */
public class NextToSourceNamingConvention extends PerTestFileNamingConvention {
    public NextToSourceNamingConvention(File dir, Class<?> testClass, String testName) {
        super(dir, testClass, testName);
    }

    public static TestSpecific<FileNamingConvention> forDirectory(String sourceDirName) {
        return forDirectory(new File(sourceDirName));
    }

    public static TestSpecific<FileNamingConvention> forDirectory(final File sourceDir) {
        return new TestSpecific<FileNamingConvention>() {
            @Override
            public FileNamingConvention forTest(Class<?> testClass, String testName) {
                return new NextToSourceNamingConvention(sourceDir, testClass, testName);
            }
        };
    }

    @Override
    protected File fileFor(File directory, Class<?> testClass, String testName, String type, String fileExtension) {
        return new File(directory, testClass.getName().replace(".", File.separator) + "." + testName + "-" + type + fileExtension);
    }
}

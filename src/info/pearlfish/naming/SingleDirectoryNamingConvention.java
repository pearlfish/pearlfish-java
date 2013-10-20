package info.pearlfish.naming;

import info.pearlfish.FileNamingConvention;
import info.pearlfish.TestSpecific;
import info.pearlfish.internal.PerTestFileNamingConvention;

import java.io.File;

/**
 * Writes the results of each test into a file in a single directory, and names the file
 * after the test package, class and method.
 */
public class SingleDirectoryNamingConvention extends PerTestFileNamingConvention {
    public SingleDirectoryNamingConvention(File dir, Class<?> testClass, String testName) {
        super(dir, testClass, testName);
    }

    public static TestSpecific<FileNamingConvention> forDirectory(String sourceDirName) {
        return forDirectory(new File(sourceDirName));
    }

    public static TestSpecific<FileNamingConvention> forDirectory(final File sourceDir) {
        return new TestSpecific<FileNamingConvention>() {
            @Override
            public FileNamingConvention forTest(Class<?> testClass, String testName) {
                return new SingleDirectoryNamingConvention(sourceDir, testClass, testName);
            }
        };
    }

    @Override
    protected File fileFor(File directory, Class<?> testClass, String testName, String type, String fileExtension) {
        return new File(directory, testClass.getName() + "." + testName + "-" + type + fileExtension);
    }
}

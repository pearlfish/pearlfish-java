package info.pearlfish.internal;

import info.pearlfish.DifferenceReporter;
import info.pearlfish.FormatType;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

public class TypeDirectedDifferenceReporter implements DifferenceReporter {
    private final Map<FormatType,DifferenceReporter> reportersByType = newHashMap();
    private final DifferenceReporter lastDitchReporter;

    public TypeDirectedDifferenceReporter(DifferenceReporter lastDitchReporter) {
        this.lastDitchReporter = lastDitchReporter;
    }

    public void register(FormatType formatType, DifferenceReporter reporter) {
        reportersByType.put(formatType, reporter);
    }

    @Override
    public void reportDifference(FormatType formatType, File approvedFile, File receivedFile) throws IOException {
        reporterFor(formatType).reportDifference(formatType, approvedFile, receivedFile);
    }

    private DifferenceReporter reporterFor(FormatType formatType) {
        for (;;) {
            if (reportersByType.containsKey(formatType)) {
                return reportersByType.get(formatType);
            }
            else if (formatType.canBeGeneralised()) {
                formatType = formatType.generalised();
            }
            else {
                return lastDitchReporter;
            }
        }
    }
}

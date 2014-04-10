package info.pearlfish.formats;

public class MissingTemplateException extends RuntimeException {
    public MissingTemplateException(String templateName) {
        super("could not load template " + templateName);
    }
}

package info.pearlfish.formats;

public interface TextFilter {
    String filter(String input);

    TextFilter IDENTITY = new TextFilter() {
        @Override
        public String filter(String input) {
            return input;
        }
    };
}

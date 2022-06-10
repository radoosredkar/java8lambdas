package ch5;


public class StringCombiner  {

    private StringBuilder builder;
    private String delim;

    public StringCombiner(String delim) {
        this.builder = new StringBuilder();
        this.delim = delim;
    }

    public StringCombiner add(String element) {
        if (builder.length() > 1) {
            builder
                .append(delim);
        }
        builder
            .append(element);
        return this;
    }

    public StringCombiner merge(StringCombiner other) {
        builder.append(other.builder);
        return this;
    }

    @Override
    public String toString() {
        return builder.toString();
    }
}

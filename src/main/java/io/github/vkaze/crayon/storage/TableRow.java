package io.github.vkaze.crayon.storage;

public record TableRow(String path, Crayon color) implements Comparable<TableRow> {
    @Override
    public int compareTo(TableRow other) {
        return path.compareTo(other.path);
    }
}

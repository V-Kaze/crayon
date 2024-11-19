package io.github.vkaze.crayon.ui.settings.table;

import io.github.vkaze.crayon.Crayon;

public record TableRow(String path, Crayon color) implements Comparable<TableRow> {
    @Override
    public int compareTo(TableRow other) {
        return path.compareTo(other.path);
    }
}

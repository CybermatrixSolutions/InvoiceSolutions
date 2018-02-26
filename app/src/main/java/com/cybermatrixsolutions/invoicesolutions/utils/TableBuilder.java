package com.cybermatrixsolutions.invoicesolutions.utils;





import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Diwakar on 1/2/2018.
 */

public class TableBuilder {
    List<String[]> rows = new LinkedList<String[]>();

    public void addRow(String... cols)
    {
        rows.add(cols);
    }

    private int[] colWidths()
    {
        int cols = -1;

        for(String[] row : rows)
            cols = Math.max(cols, row.length);

        int[] widths = new int[cols];

        for(String[] row : rows) {
            for(int colNum = 0; colNum < row.length; colNum++) {
                widths[colNum] =
                        Math.max(
                                widths[colNum],
                                StringUtils.length(row[colNum]));
            }
        }

        return widths;
    }

    @Override
    public String toString()
    {
        StringBuilder buf = new StringBuilder();

        int[] colWidths = colWidths();

        for(String[] row : rows) {
            for(int colNum = 0; colNum < row.length; colNum++) {
                buf.append(
                        StringUtils.rightPad(
                                StringUtils.defaultString(
                                        row[colNum]), colWidths[colNum]));
                buf.append(' ');
            }

            buf.append('\n');
        }

        return buf.toString();
    }

}


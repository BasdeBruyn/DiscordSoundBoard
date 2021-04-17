package nl.basdebruyn.soundboardbot.util;

import nl.basdebruyn.soundboardbot.models.SoundEffect;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

public class TableUtil {
    private static final int MAX_COLUMN_WIDTH = 11;
    private static final String COLUMN_SEPARATOR = "\u2003\u2007\u2007\u2007";

    public static String[] createTableMessages(SoundEffect[] soundEffects, int columnCount, int rowCount) {
        SoundEffect[][] table = createTable(soundEffects, columnCount);
        String[][] styledTable = createStyledTable(table);
        String[][][] splitTable = splitIntoChunks(styledTable, rowCount);
        return createMessages(splitTable);
    }

    private static String[] createMessages(String[][][] splitTable){
        return collect(splitTable, TableUtil::createMessage, String[]::new);
    }

    private static String createMessage(String[][] table){
        String[] rows = Arrays.stream(table)
                .map(TableUtil::joinRow)
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);
        return join("\n", rows);
    }

    @NotNull
    private static String joinRow(String[] row) {
        String joinedRow = join(COLUMN_SEPARATOR, row);
        if (joinedRow.equals("")) return "";

        return joinedRow + "\n";
    }

    private static String join(String delimiter, String[] row) {
        return row == null ? "" : String.join(delimiter, row);
    }

    private static SoundEffect[][] createTable(SoundEffect[] list, int columnCount) {
        return splitIntoChunks(list, columnCount, SoundEffect[][]::new);
    }

    private static String[][][] splitIntoChunks(String[][] table, int rowCount) {
        return splitIntoChunks(table, rowCount, String[][][]::new);
    }

    private static <T> T[][] splitIntoChunks(T[] list, int chunkSize, IntFunction<T[][]> generator){
        int length = getCeilOfDivision(list.length, chunkSize);
        return iterate(length, i -> getCopy(list, chunkSize, i), generator);
    }

    private static String[][] createStyledTable(SoundEffect[][] table) {
        return iterate(table, i -> createStyledCells(table[i]), String[][]::new);
    }

    private static String[] createStyledCells(SoundEffect[] row) {
        return iterate(row, i -> createStyledCell(row[i]), String[]::new);
    }

    private static <T> T[] iterate(int range, IntFunction<T> action, IntFunction<T[]> generator) {
        return IntStream.range(0, range).mapToObj(action).toArray(generator);
    }

    private static <T, U> U[] iterate(T[] list, IntFunction<U> action, IntFunction<U[]> generator){
        return iterate(list.length, action, generator);
    }

    private static <T, U> U[] collect(T[] list, Function<T, U> action, IntFunction<U[]> generator){
        return Arrays.stream(list).map(action).toArray(generator);
    }

    private static String createStyledCell(SoundEffect cell) {
        if (cell == null) return "";

        String paddedString = createPaddedCell(cell.name);
        return String.format("[**` %s `**](%s)", paddedString, cell.url);
    }

    private static String createPaddedCell(String cell) {
        if (cell.length() > MAX_COLUMN_WIDTH) return cell.substring(0, MAX_COLUMN_WIDTH - 2) + "..";

        int widthDif = (MAX_COLUMN_WIDTH - cell.length());
        String startPadding = createPadding(widthDif / 2);
        String endPadding = createPadding(getCeilOfDivision(widthDif, 2));
        return startPadding + cell + endPadding;
    }

    private static <T> T[] getCopy(T[] table, int copyLength, int index) {
        return Arrays.copyOfRange(table, index * copyLength, index * copyLength + copyLength);
    }

    private static int getCeilOfDivision(int a, int b) {
        return (int) Math.ceil(a / (double) b);
    }

    private static String createPadding(int length) {
        return length == 0 ? "" : new String(new char[length]).replace("\0", " ");
    }
}

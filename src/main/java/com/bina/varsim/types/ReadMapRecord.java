package com.bina.varsim.types;

import com.bina.varsim.fastqLiftover.types.GenomeInterval;
import com.bina.varsim.fastqLiftover.types.GenomeLocation;
import com.google.common.base.Joiner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ReadMapRecord {
    public static final String COLUMN_SEPARATOR = "\t";
    public static final String MAP_BLOCK_SEPARATOR = ":";
    private static final Joiner joiner = Joiner.on(MAP_BLOCK_SEPARATOR).skipNulls();

    protected String readName;

    protected List<Collection<ReadMapBlock>> multiReadMapBlocks;

    public ReadMapRecord() {
    }

    public ReadMapRecord(final String readName, final List<Collection<ReadMapBlock>> multiReadMapBlocks) {
        this.readName = readName;
        this.multiReadMapBlocks = multiReadMapBlocks;
    }

    public ReadMapRecord(final String readName, final Collection<ReadMapBlock> ... multiReadMapBlocks) {
        this.readName = readName;
        this.multiReadMapBlocks = Arrays.asList(multiReadMapBlocks);
    }

    public ReadMapRecord(final String line) {
        final String[] fields = line.trim().split(COLUMN_SEPARATOR);
        readName = fields[0];
        multiReadMapBlocks = new ArrayList<>();
        for (int i = 1; i < fields.length; i++) {
            final Collection<ReadMapBlock> readMapBlocks = new ArrayList<>();
            final String readMapStrings[] = fields[i].split(MAP_BLOCK_SEPARATOR, -1);
            for (final String readMapString : readMapStrings) {
                readMapBlocks.add(new ReadMapBlock(readMapString));
            }
            multiReadMapBlocks.add(readMapBlocks);
        }
    }

    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(readName);
        stringBuilder.append(COLUMN_SEPARATOR);
        for (final Collection<ReadMapBlock> readMapBlocks : multiReadMapBlocks) {
            stringBuilder.append(COLUMN_SEPARATOR);
            stringBuilder.append(joiner.join(readMapBlocks));
        }
        return stringBuilder.toString();
    }

    public String getReadName() {
        return readName;
    }

    public void setReadName(String readName) {
        this.readName = readName;
    }

    public List<Collection<ReadMapBlock>> getMultiReadMapBlocks() {
        return multiReadMapBlocks;
    }

    public void setMultiReadMapBlocks(List<Collection<ReadMapBlock>> multiReadMapBlocks) {
        this.multiReadMapBlocks = multiReadMapBlocks;
    }

    public Collection<ReadMapBlock> getReadMapBlocks(final int index) {
        return multiReadMapBlocks.get(index);
    }

    public Collection<GenomeLocation> getUnclippedStarts(final int index) {
        final Collection<GenomeLocation> locations = new ArrayList<>();
        for (final ReadMapBlock readMapBlock : getReadMapBlocks(index)) {
            locations.add(readMapBlock.getUnclippedStart());
        }
        return locations;
    }
}

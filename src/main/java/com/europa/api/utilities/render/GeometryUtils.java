/*
 * Decompiled with CFR 0.151.
 */
package com.europa.api.utilities.render;

import java.util.HashMap;
import net.minecraft.util.EnumFacing;

public class GeometryUtils {
    public static HashMap<EnumFacing, Integer> FACEMAP = new HashMap();

    static {
        FACEMAP.put(EnumFacing.DOWN, 1);
        FACEMAP.put(EnumFacing.WEST, 16);
        FACEMAP.put(EnumFacing.NORTH, 4);
        FACEMAP.put(EnumFacing.SOUTH, 8);
        FACEMAP.put(EnumFacing.EAST, 32);
        FACEMAP.put(EnumFacing.UP, 2);
    }

    public static final class Line {
        public static int DOWN_WEST;
        public static int UP_WEST;
        public static int DOWN_EAST;
        public static int UP_EAST;
        public static int DOWN_NORTH;
        public static int UP_NORTH;
        public static int DOWN_SOUTH;
        public static int UP_SOUTH;
        public static int NORTH_WEST;
        public static int NORTH_EAST;
        public static int SOUTH_WEST;
        public static int SOUTH_EAST;
        public static int ALL;

        static {
            ALL = 63;
            SOUTH_EAST = 40;
            SOUTH_WEST = 24;
            NORTH_EAST = 36;
            NORTH_WEST = 20;
            UP_SOUTH = 10;
            DOWN_SOUTH = 9;
            UP_NORTH = 6;
            DOWN_NORTH = 5;
            UP_EAST = 34;
            DOWN_EAST = 33;
            UP_WEST = 18;
            DOWN_WEST = 17;
        }
    }

    public static final class Quad {
        public static int DOWN;
        public static int UP;
        public static int NORTH;
        public static int SOUTH;
        public static int WEST;
        public static int EAST;
        public static int ALL;

        static {
            ALL = 63;
            EAST = 32;
            WEST = 16;
            SOUTH = 8;
            NORTH = 4;
            UP = 2;
            DOWN = 1;
        }
    }
}


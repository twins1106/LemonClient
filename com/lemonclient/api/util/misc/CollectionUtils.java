//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\���� ȭ��\map"!

//Decompiled by Procyon!

package com.lemonclient.api.util.misc;

import java.util.function.*;
import java.util.*;

public class CollectionUtils
{
    public static <T> T maxOrNull(final Iterable<T> iterable, final ToIntFunction<T> block) {
        int value = Integer.MIN_VALUE;
        T maxElement = null;
        for (final T element : iterable) {
            final int newValue = block.applyAsInt(element);
            if (newValue > value) {
                value = newValue;
                maxElement = element;
            }
        }
        return maxElement;
    }
}

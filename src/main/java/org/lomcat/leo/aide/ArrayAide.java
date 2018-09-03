/*
 * Copyright Lomcat and/or its affiliates..
 *
 * This file is part of Leo Aide [1].
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 *
 * [1] Leo Aide: http://leo.lomcat.org
 */

package org.lomcat.leo.aide;

import java.lang.reflect.Array;

/**
 * TODO Kweny ArrayAide
 *
 * @author Kweny
 * @since 1.0.0
 */
public class ArrayAide {

    public static final String[] EMPTY_STRING_ARRAY = new String[0];
    public static final int INDEX_NOT_FOUND = -1;

    public static int length(final Object[] array) {
        if (array == null) {
            return 0;
        }
        return Array.getLength(array);
    }

    public static int length(final Object array) {
        if (array == null) {
            return 0;
        }
        return Array.getLength(array);
    }

    public static String[] newStringArray() {
        return new String[0];
    }

    public static boolean isEmpty(final Object[] array) {
        return length(array) == 0;
    }

    public static boolean isEmpty(final int[] array) {
        return length(array) == 0;
    }

    public static boolean isEmpty(final char[] array) {
        return length(array) == 0;
    }

    public static boolean isNotEmpty(final Object[] array) {
        return !isEmpty(array);
    }

    public static int indexOf(final Object[] array, final Object object) {
        return indexOf(array, object, 0);
    }

    public static int indexOf(final Object[] array, final Object object, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        if (object == null) {
            for (int i = startIndex; i < array.length; i++) {
                if (array[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = startIndex; i < array.length; i++) {
                if (object.equals(array[i])) {
                    return i;
                }
            }
        }
        return INDEX_NOT_FOUND;
    }

    public static int indexOf(final int[] array, final int value) {
        return indexOf(array, value, 0);
    }

    public static int indexOf(final int[] array, final int value, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array.length; i++) {
            if (value == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    public static boolean contains(final Object[] array, final Object object) {
        return indexOf(array, object) != INDEX_NOT_FOUND;
    }

    public static boolean contains(final int[] array, final int value) {
        return indexOf(array, value) != INDEX_NOT_FOUND;
    }

    public static <T> T[] subArray(final T[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        final int newLength = endIndexExclusive - startIndexInclusive;
        final Class<?> type = array.getClass().getComponentType();
        if (newLength <= 0) {
            @SuppressWarnings("unchecked") // 这里是安全的，因为 array 就是 T 类型
            final T[] emptyArray = (T[]) Array.newInstance(type, 0);
            return emptyArray;
        }
        @SuppressWarnings("unchecked") // 这里是安全的，因为 array 就是 T 类型
        final T[] subArray = (T[]) Array.newInstance(type, newLength);
        System.arraycopy(array, startIndexInclusive, subArray, 0, newLength);
        return subArray;
    }

}
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

    public static boolean isEmpty(final Object[] array) {
        return length(array) == 0;
    }

    public static boolean isEmpty(final char[] array) {
        return length(array) == 0;
    }

    public static boolean isNotEmpty(final Object[] array) {
        return !isEmpty(array);
    }

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
}
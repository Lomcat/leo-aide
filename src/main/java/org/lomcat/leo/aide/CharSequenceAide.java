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

/**
 * TODO Kweny CharSequenceAide
 *
 * @author Kweny
 * @since 2018-09-01 0:04
 */
public class CharSequenceAide {

    public static boolean isAnyNull(final CharSequence... sequences) {
        if (ArrayAide.isEmpty(sequences)) {
            return false;
        }
        for (final CharSequence sequence : sequences) {
            if (sequence == null) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNoneNull(final CharSequence... sequences) {
        return !isAnyNull(sequences);
    }

    public static boolean isAllNull(final CharSequence... sequences) {
        if (ArrayAide.isEmpty(sequences)) {
            return true;
        }
        for (final CharSequence sequence : sequences) {
            if (sequence != null) {
                return false;
            }
        }
        return true;
    }

    public static boolean isEmpty(final CharSequence sequence) {
        return sequence == null || sequence.length() == 0;
    }

    public static boolean isNotEmpty(final CharSequence sequence) {
        return !isEmpty(sequence);
    }

    public static boolean isAnyEmpty(final CharSequence... sequences) {
        if (ArrayAide.isEmpty(sequences)) {
            return false;
        }
        for (final CharSequence sequence : sequences) {
            if (isEmpty(sequence)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNoneEmpty(final CharSequence... sequences) {
        return !isAnyEmpty(sequences);
    }

    public static boolean isAllEmpty(final CharSequence... sequences) {
        if (ArrayAide.isEmpty(sequences)) {
            return true;
        }
        for (final CharSequence sequence : sequences) {
            if (isNotEmpty(sequence)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isBlank(final CharSequence sequence) {
        int length;
        if (sequence == null || (length = sequence.length()) == 0) {
            return true;
        }
        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(sequence.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(final CharSequence sequence) {
        return !isBlank(sequence);
    }

    public static boolean isAnyBlank(final CharSequence... sequences) {
        if (ArrayAide.isEmpty(sequences)) {
            return false;
        }
        for (final CharSequence sequence : sequences) {
            if (isBlank(sequence)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNoneBlank(final CharSequence... sequences) {
        return !isAnyBlank(sequences);
    }

    public static boolean isAllBlank(final CharSequence... sequences) {
        if (ArrayAide.isEmpty(sequences)) {
            return true;
        }
        for (final CharSequence sequence : sequences) {
            if (isNotBlank(sequence)) {
                return false;
            }
        }
        return true;
    }



    public static CharSequence subSequence(final CharSequence sequence, int begin, int end) {
        return sequence == null ? null : sequence.subSequence(begin, end);
    }

    public static CharSequence subSequence(final CharSequence sequence, final int begin) {
        return subSequence(sequence, begin, sequence.length());
    }

//    public static int indexOf(final CharSequence sequence, final int searchChar, int begin) {
//        if (sequence instanceof String) {
//            return ((String) sequence).indexOf(searchChar, begin);
//        }
//
//    }
}
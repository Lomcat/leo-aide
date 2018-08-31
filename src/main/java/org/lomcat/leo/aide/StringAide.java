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
 * TODO Kweny StringAide
 *
 * @author Kweny
 * @since 2018-09-01 0:06
 */
public class StringAide extends CharSequenceAide {

    public static final String EMPTY = "";
    public static final String SPACE = " ";
    public static final String CR = "\r";
    public static final String LF = "\n";
//    public static final String CRLF = "\r\n";
//    public static final String HYPHEN = "-";
//    public static final String UNDERSCORE = "_";


    public static String trim(final String string) {
        return string == null ? null : string.trim();
    }

    public static String trimToNull(final String string) {
        final String ts = trim(string);
        return isEmpty(ts) ? null : ts;
    }

    public static String trimToEmpty(final String string) {
        return string == null ? EMPTY : string.trim();
    }

    // TODO 去除字符串中的所有空白字符（不止是首尾的） trims/trimsToNull/trimsToEmpty

    // 从指定位置开始截取指定长度的字符串
    public static String truncate(final String string, int offset, int maxLength) {
        if (string == null || maxLength < 0) {
            return null;
        }
        offset = Math.max(offset, 0);
        if (offset > string.length()) {
            return EMPTY;
        }
        if (maxLength < string.length()) {
            int endIndex = Math.min(offset + maxLength, string.length());
            return string.substring(offset, endIndex);
        }
        return string.substring(offset);
    }

    // 从第一个字符开始截取指定长度的字符串
    public static String truncate(final String string, int maxLength) {
        return truncate(string, 0, maxLength);
    }

    public static String substring(final String string, int begin) {
        if (string == null) {
            return null;
        }

        if (begin < 0) {
            begin = string.length() + begin;
        }
        if (begin < 0) {
            begin = 0;
        }

        if (begin > string.length()) {
            return EMPTY;
        }

        return string.substring(begin);
    }

    public static String substring(final String string, int begin, int end) {
        if (string == null) {
            return null;
        }

        if (end < 0) {
            end = string.length() + end;
        }
        if (end < 0) {
            end = 0;
        }
        if (begin < 0) {
            begin = string.length() + begin;
        }
        if (begin < 0) {
            begin = 0;
        }

        if (end > string.length()) {
            end = string.length();
        }

        if (begin > end) {
            return EMPTY;
        }

        return string.substring(begin, end);
    }
}
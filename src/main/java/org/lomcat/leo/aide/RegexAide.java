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

import java.util.regex.Pattern;

/**
 * <p>使用正则表达式处理字符串的辅助类。</p>
 *
 * @author Kweny
 * @since 2018-09-04 2:20
 */
public class RegexAide {

    public static String replaceAll(final String text, final Pattern regex, final String replacement) {
        if (text == null || regex == null || replacement == null) {
            return text;
        }
        return regex.matcher(text).replaceAll(replacement);
    }

    public static String replaceALl(final String text, final String regex, final String replacement) {
        if (text == null || regex == null || replacement == null) {
            return text;
        }
        return text.replaceAll(regex, replacement);
    }

    public static String replaceFirst(final String text, final Pattern regex, final String replacement) {
        if (text == null || regex == null || replacement == null) {
            return text;
        }
        return regex.matcher(text).replaceFirst(replacement);
    }

    public static String replaceFirst(final String text, final String regex, final String replacement) {
        if (text == null || regex == null || replacement == null) {
            return text;
        }
        return text.replaceFirst(regex, replacement);
    }

    public static String replacePattern(final String text, final String regex, final String replacement) {
        if (text == null || regex == null || replacement == null) {
            return text;
        }
        return Pattern.compile(regex, Pattern.DOTALL).matcher(text).replaceAll(replacement);
    }
}
/*
 * Copyright Lomcat and/or its affiliates..
 *
 * This file is part of Lomcat Leo Aide [1].
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
 * [1] Lomcat Leo: http://leo.lomcat.org
 */

package org.lomcat.leo.aide;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * TODO Kweny StringAide
 *
 * @author Kweny
 * @since 1.0.0
 */
public class StringAide extends CharSequenceAide {

    public static final String EMPTY = "";
    public static final String SPACE = " ";
    public static final String CR = "\r";
    public static final String LF = "\n";
//    public static final String CRLF = "\r\n";
//    public static final String HYPHEN = "-";
//    public static final String UNDERSCORE = "_";



    // ----- Trim string ----- start
    // ---------------------------------------------------------------------------------------------------
    /**
     * <p>从字符串的首尾两端移除控制字符（char <= 32），即 {@link String#trim()}。当字符串为 null 时返回 {@code null}。</p>
     *
     * @param string 源字符串，可以为 null
     * @return 修剪后的字符串
     * @since 1.0.0
     */
    public static String trim(final String string) {
        return string == null ? null : string.trim();
    }

    /**
     * <p>从字符串的首尾两端移除控制字符（char <= 32），即 {@link String#trim()}。
     * 当结果为 null 或 空串时，返回 {@code null}。</p>
     *
     * @param string 源字符串，可以为 null
     * @return 修剪后的字符串，或 {@code null}
     * @since 1.0.0
     */
    public static String trimToNull(final String string) {
        final String ts = trim(string);
        return isEmpty(ts) ? null : ts;
    }

    /**
     * <p>从字符串的首尾两端移除控制字符（char <= 32），即 {@link String#trim()}。
     * 当结果为 null 或 空串时，返回 空串</p>
     *
     * @param string 源字符串，可以为 null
     * @return 修剪后的字符串，或 空串
     * @since 1.0.0
     */
    public static String trimToEmpty(final String string) {
        return string == null ? EMPTY : string.trim();
    }
    // ---------------------------------------------------------------------------------------------------
    // ----- Trim string ----- end

    // ----- Strip string ----- start
    // ---------------------------------------------------------------------------------------------------
    /**
     * <p>从字符串的开头剥离指定的一组字符。</p>
     *
     * <p>注意：</p>
     * <ul>
     *     <li>当 {@code string} 为 null 时返回 {@code null}，空串时返回空串。</li>
     *     <li>当 {@code stripChars} 为 null 时，则按 {@link Character#isWhitespace(char)} 的定义来剥离空白符。</li>
     *     <li>当 {@code stripChars} 为空串时，直接返回源字符串 {@code string}。</li>
     * </ul>
     *
     * <pre>
     * StringAide.stripStart(null, *)          = null
     * StringAide.stripStart("", *)            = ""
     * StringAide.stripStart("abc", "")        = "abc"
     * StringAide.stripStart("  abc", "")      = "  abc"
     * StringAide.stripStart("abc", null)      = "abc"
     * StringAide.stripStart("  abc", null)    = "abc"
     * StringAide.stripStart("abc  ", null)    = "abc  "
     * StringAide.stripStart(" abc ", null)    = "abc "
     * StringAide.stripStart("yxabc  ", "xyz") = "abc  "
     * StringAide.stripStart("yxabyc", "xyz")  = "abyc"
     * StringAide.stripStart("  yxabc", "xyz") = "  yxabc"
     * </pre>
     *
     * @param string 源字符串，可以为 null
     * @param stripChars 要剥离的字符集，null 作为 {@link Character#isWhitespace(char)} 定义的空白符处理
     * @return 剥离指定字符后的字符串
     * @since 1.0.0
     */
    public static String stripStart(final String string, final String stripChars) {
        int stringLength;
        if (string == null || (stringLength = string.length()) == 0) {
            return string;
        }
        int start = 0;
        if (stripChars == null) {
            while (start != stringLength && Character.isWhitespace(string.charAt(start))) {
                start++;
            }
        } else if (stripChars.isEmpty()) {
            return string;
        } else {
            while (start != stringLength && stripChars.indexOf(string.charAt(start)) != INDEX_NOT_FOUND) {
                start++;
            }
        }
        return string.substring(start);
    }

    /**
     * <p>从字符串的末尾剥离指定的一组字符。</p>
     *
     * <p>注意：</p>
     * <ul>
     *     <li>当 {@code string} 为 null 时返回 {@code null}，空串时返回空串。</li>
     *     <li>当 {@code stripChars} 为 null 时，则按 {@link Character#isWhitespace(char)} 的定义来剥离空白符。</li>
     *     <li>当 {@code stripChars} 为空串时，直接返回源字符串 {@code string}。</li>
     * </ul>
     *
     * <pre>
     * StringAide.stripEnd(null, *)          = null
     * StringAide.stripEnd("", *)            = ""
     * StringAide.stripEnd("abc", "")        = "abc"
     * StringAide.stripEnd("abc  ", "")      = "abc  "
     * StringAide.stripEnd("abc", null)      = "abc"
     * StringAide.stripEnd("  abc", null)    = "  abc"
     * StringAide.stripEnd("abc  ", null)    = "abc"
     * StringAide.stripEnd(" abc ", null)    = " abc"
     * StringAide.stripEnd("  abcyx", "xyz") = "  abc"
     * StringAide.stripEnd("120.00", ".0")   = "12"
     * StringAide.stripEnd("axbcyx", "xyz")  = "axbc"
     * StringAide.stripEnd("abcyx  ", "xyz") = "abcyx  "
     * </pre>
     *
     * @param string 源字符串，可以为 null
     * @param stripChars 要剥离的字符集，null 作为 {@link Character#isWhitespace(char)} 定义的空白符处理
     * @return 剥离指定字符后的字符串
     * @since 1.0.0
     */
    public static String stripEnd(final String string, final String stripChars) {
        int end;
        if (string == null || (end = string.length()) == 0) {
            return string;
        }
        if (stripChars == null) {
            while (end != 0 && Character.isWhitespace(string.charAt(end - 1))) {
                end--;
            }
        } else if (stripChars.isEmpty()) {
            return string;
        } else {
            while (end != 0 && stripChars.indexOf(string.charAt(end - 1)) != INDEX_NOT_FOUND) {
                end--;
            }
        }
        return string.substring(0, end);
    }

    /**
     * <p>从字符串的开头和末尾剥离掉指定的一组字符。类似 {@link String#trim()}，但可以指定要剥离的字符。</p>
     *
     * <p>注意：</p>
     * <ul>
     *     <li>当 {@code string} 为 null 时返回 {@code null}，空串时返回空串。</li>
     *     <li>当 {@code stripChars} 为 null 时，则按 {@link Character#isWhitespace(char)} 的定义来剥离空白符。</li>
     *     <li>当 {@code stripChars} 为空串时，直接返回源字符串 {@code string}。</li>
     * </ul>
     *
     * <pre>
     * StringAide.strip(null, *)          = null
     * StringAide.strip("", *)            = ""
     * StringAide.strip("  abc  ", "")    = "  abc  "
     * StringAide.strip("abc", null)      = "abc"
     * StringAide.strip("  abc", null)    = "abc"
     * StringAide.strip("abc  ", null)    = "abc"
     * StringAide.strip(" abc ", null)    = "abc"
     * StringAide.strip("  abcyx", "xyz") = "  abc"
     * StringAide.strip("yxabyczxy", "xyz") = "abyc"
     * StringAide.strip("  yxabyczxy  ", "xyz") = "  yxabyczxy  "
     * </pre>
     *
     * @param string 源字符串，可以为 null
     * @param stripChars 要剥离的字符集，null 作为 {@link Character#isWhitespace(char)} 定义的空白符处理
     * @return 剥离指定字符后的字符串
     * @since 1.0.0
     */
    public static String strip(final String string, final String stripChars) {
        if (isEmpty(string)) {
            return string;
        }
        return stripEnd(stripStart(string, stripChars), stripChars);
    }

    /**
     * <p>从字符串的开头和末尾剥离空白符。类似 {@link String#trim()}，
     * 但剥离的是按 {@link Character#isWhitespace(char)} 定义的空白符。</p>
     *
     * <pre>
     * StringAide.strip(null)     = null
     * StringAide.strip("")       = ""
     * StringAide.strip("   ")    = ""
     * StringAide.strip("abc")    = "abc"
     * StringAide.strip("  abc")  = "abc"
     * StringAide.strip("abc  ")  = "abc"
     * StringAide.strip(" abc ")  = "abc"
     * StringAide.strip(" ab c ") = "ab c"
     * </pre>
     *
     * @param string 源字符串，可以为 null
     * @return 剥离空白符之后的字符串
     * @since 1.0.0
     */
    public static String strip(final String string) {
        return strip(string, null);
    }

    /**
     * <p>去掉字符串开头和末尾的空白符。类似 {@link String#trim()}，
     * 但去掉的是按 {@link Character#isWhitespace(char)} 定义的空白符。</p>
     *
     * <p>当结果为 null 或 空串 时，返回 {@code null}。</p>
     *
     * @param string 源字符串，可以为 null
     * @return 去掉首尾空白符之后的字符串，或 {@code null}
     * @since 1.0.0
     */
    public static String stripToNull(String string) {
        if (isEmpty(string)) {
            return null;
        }
        string = strip(string);
        return string.isEmpty() ? null : string;
    }

    /**
     * <p>去掉字符串开头和末尾的空白符。类似 {@link String#trim()}，
     * 但去掉的是按 {@link Character#isWhitespace(char)} 定义的空白符。</p>
     *
     * <p>当结果为 null 或 空串 时，返回 空串。</p>
     *
     * @param string 源字符串，可以为 null
     * @return 去掉首尾空白符之后的字符串，或 空串
     * @since 1.0.0
     */
    public static String stripToEmpty(String string) {
        return string == null ? EMPTY : strip(string, null);
    }

    /**
     * <p>删除字符串中的变音符号，如 '&agrave;' 替换为 'a'。</p>
     *
     * <pre>
     * StringAide.stripAccents(null)                = null
     * StringAide.stripAccents("")                  = ""
     * StringAide.stripAccents("control")           = "control"
     * StringAide.stripAccents("&eacute;clair")     = "eclair"
     * </pre>
     *
     * @param string 源字符串
     * @return 删除了变音符号的字符串
     * @since 1.0.0
     */
    public static String stripAccents(final String string) {
        if (string == null) {
            return null;
        }
        final Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        final StringBuilder decomposed = new StringBuilder(Normalizer.normalize(string, Normalizer.Form.NFD));
        convertRemainingAccentCharacters(decomposed);
        return pattern.matcher(decomposed).replaceAll(EMPTY);
    }

    private static void convertRemainingAccentCharacters(final StringBuilder decomposed) {
        for (int i = 0; i < decomposed.length(); i++) {
            if (decomposed.charAt(i) == '\u0141') {
                decomposed.deleteCharAt(i);
                decomposed.insert(i, 'L');
            } else if (decomposed.charAt(i) == '\u0142') {
                decomposed.deleteCharAt(i);
                decomposed.insert(i, 'l');
            }
        }
    }
    // ---------------------------------------------------------------------------------------------------
    // ----- Strip string ----- end

    // ----- Delete ----- start
    // ---------------------------------------------------------------------------------------------------
    // TODO delete string
    public static String delete(final String string, final String... substrings) {
        return null; // TODO delete
    }

    public static String deleteIgnoreCase(final String string, final String... substrings) {
        return null; // TODO deleteIgnoreCase
    }

    private static String delete(final String string, final String deleteString, final boolean ignoreCase) {
        return null; // TODO delete with ignoreCase
    }

    /**
     * <p>从 {@code string} 中将指定类型的字符删除。</p>
     *
     * <pre>
     * StringAide.deleteByCharacterType("aBc+123", Character.UPPERCASE_LETTER)    = "ac+123"
     * StringAide.deleteByCharacterType("aBc+123", Character.LOWERCASE_LETTER)    = "B+123"
     * StringAide.deleteByCharacterType("aBc+123", Character.DECIMAL_DIGIT_NUMBER)    = "aBc+"
     * StringAide.deleteByCharacterType("aBc+123", Character.MATH_SYMBOL)    = "aBc123"
     * StringAide.deleteByCharacterType("aBc+123", Character.UPPERCASE_LETTER, Character.LOWERCASE_LETTER)    = "+123"
     * </pre>
     *
     * @param string 源字符串
     * @param characterTypes 字符类型
     * @return 删除指定类型字符后的字符串
     * @since 1.0.0
     */
    public static String deleteByCharacterType(final String string, final int... characterTypes) {
        int length;
        if (string == null || (length = string.length()) == 0 || ArrayAide.isEmpty(characterTypes)) {
            return string;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char ch = string.charAt(i);
            if (!ArrayAide.contains(characterTypes, Character.getType(ch))) {
                builder.append(ch);
            }
        }
        return builder.toString();
    }

    /**
     * <p>从字符串中删除指定字符。</p>
     *
     * @param string 源字符串，可以为 null
     * @param deleteChar 要删除的字符
     * @return 删除指定字符后的字符串
     * @since 1.0.0
     */
    public static String deleteChar(final String string, final char deleteChar) {
        return deleteChars(string, String.valueOf(deleteChar));
    }

    /**
     * <p>从字符串中删除指定字符，忽略大小写（无论大写小写都删除。</p>
     *
     * @param string 源字符串，可以为 null
     * @param deleteChar 要删除的字符
     * @return 删除指定字符后的字符串
     * @since 1.0.0
     */
    public static String deleteCharIgnoreCase(final String string, final char deleteChar) {
        return deleteCharsIgnoreCase(string, String.valueOf(deleteChar));
    }

    /**
     * <p>从字符串中删除指定的一组字符。
     * 针对 {@code deleteChars} 中的每一个字符单独进行删除操作。</p>
     *
     * <p>注意：</p>
     * <ul>
     *     <li>当 {@code string} 为 null 时返回 {@code null}，空串时返回空串。</li>
     *     <li>当 {@code deleteChars} 为 null 时，则按 {@link Character#isWhitespace(char) 的定义来剔除空白符。</li>
     *     <li>当 {@code deleteChars} 为空串时，直接返回源字符串 {@code string}。</li>
     * </ul>
     *
     * <pre>
     * StringAide.delete(null, *)               = null
     * StringAide.delete("", *)                 = ""
     * StringAide.delete("  ab c", "")          = "  ab c"
     * StringAide.delete("abxcxdexfg  ", "xde") = "abcfg  "
     * StringAide.delete("abc", null)           = "abc"
     * StringAide.delete("  ab c  ", null)      = "abc"
     * </pre>
     *
     * @param string 源字符串，可以为 null
     * @param deleteChars 要剔除的字符集，null 作为 {@link Character#isWhitespace(char) 定义的空白符处理
     * @return 删除指定字符后的字符串
     * @since 1.0.0
     */
    public static String deleteChars(final String string, final String deleteChars) {
        return deleteChars(string, deleteChars, false);
    }

    /**
     * <p>从字符串中删除指定的一组字符，忽略大小写（无论大写小写都删除）。
     * 针对 {@code deleteChars} 中的每一个字符单独进行删除操作。</p>
     *
     * <p>注意：</p>
     * <ul>
     *     <li>当 {@code string} 为 null 时返回 {@code null}，空串时返回空串。</li>
     *     <li>当 {@code deleteChars} 为 null 时，则按 {@link Character#isWhitespace(char) 的定义来剔除空白符。</li>
     *     <li>当 {@code deleteChars} 为空串时，直接返回源字符串 {@code string}。</li>
     * </ul>
     *
     * <pre>
     * StringAide.deleteIgnoreCase(null, *)               = null
     * StringAide.deleteIgnoreCase("", *)                 = ""
     * StringAide.deleteIgnoreCase("  ab c", "")          = "  ab c"
     * StringAide.deleteIgnoreCase("abxcxdexfg  ", "xde") = "abcfg  "
     * StringAide.deleteIgnoreCase("abc", null)           = "abc"
     * StringAide.deleteIgnoreCase("  ab c  ", null)      = "abc"
     * </pre>
     *
     * @param string 源字符串，可以为 null
     * @param deleteChars 要剔除的字符集，null 作为 {@link Character#isWhitespace(char) 定义的空白符处理
     * @return 删除指定字符后的字符串
     * @since 1.0.0
     */
    public static String deleteCharsIgnoreCase(final String string, final String deleteChars) {
        return deleteChars(string, deleteChars, true);
    }

    /**
     * <p>从字符串中删除所有按 {@link Character#isWhitespace(char)} 定义的空白符。</p>
     *
     * <pre>
     * StringAide.deleteWhitespace(null)        = null
     * StringAide.deleteWhitespace("")          = ""
     * StringAide.deleteWhitespace("  ab c  ")  = "abc"
     * </pre>
     *
     * @param string 源字符串，可以为 null
     * @return 删除所有空白符之后的字符串
     * @since 1.0.0
     */
    public static String deleteWhitespace(final String string) {
        return delete(string, null, false);
    }

    /**
     * <p>从字符串中删除指定的一组字符。可以指定是否忽略大小写</p>
     *
     * <p>注意：</p>
     * <ul>
     *     <li>当 {@code string} 为 null 时返回 {@code null}，空串时返回空串。</li>
     *     <li>当 {@code deleteChars} 为 null 时，则按 {@link Character#isWhitespace(char) 的定义来剔除空白符。</li>
     *     <li>当 {@code deleteChars} 为空串时，直接返回源字符串 {@code string}。</li>
     * </ul>
     *
     * @param string 源字符串，可以为 null
     * @param deleteChars 要剔除的字符集，null 作为 {@link Character#isWhitespace(char) 定义的空白符处理
     * @param ignoreCase 是否忽略大小写，true：无论大小写都删除；false：只删除大小写匹配的
     * @return 删除指定字符后的字符串
     * @since 1.0.0
     */
    private static String deleteChars(final String string, final String deleteChars, boolean ignoreCase) {
        int length;
        if (string == null || (length = string.length()) == 0) {
            return string;
        }
        if (deleteChars == null) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                char ch = string.charAt(i);
                if (!Character.isWhitespace(ch)) {
                    builder.append(ch);
                }
            }
            return builder.toString();
        } else if (deleteChars.isEmpty()) {
            return string;
        } else {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                char ch = string.charAt(i);
                if (ignoreCase) {
                    if (indexOfIgnoreCase(deleteChars, String.valueOf(ch)) >= 0) {
                        continue;
                    }
                } else {
                    if (deleteChars.indexOf(ch) >= 0) {
                        continue;
                    }
                }
                builder.append(ch);
            }
            return builder.toString();
        }
    }

    /**
     * <p>当 {@code string} 以 {@code deleteString} 开头时，将开头的 {@code deleteString} 删除。</p>
     *
     * <p>{@code string} 为 null 时返回 {@code null}，空串 时返回 空串。
     * {@code deleteString} 为 null 或 空串 时直接返回 {@code string}。</p>
     *
     * <pre>
     * StringAide.deleteStart(null, *)      = null
     * StringAide.deleteStart("", *)        = ""
     * StringAide.deleteStart(*, null)      = *
     * StringAide.deleteStart("www.domain.com", "www.")   = "domain.com"
     * StringAide.deleteStart("domain.com", "www.")       = "domain.com"
     * StringAide.deleteStart("www.domain.com", "domain") = "www.domain.com"
     * StringAide.deleteStart("abc", "")    = "abc"
     * </pre>
     *
     * @param string 源字符串
     * @param deleteString 要删除的字符串
     * @return 去掉开头 {@code deleteString} 之后的字符串
     * @since 1.0.0
     */
    public static String deleteStart(final String string, final String deleteString) {
        if (isEmpty(string) || isEmpty(deleteString)) {
            return string;
        }
        if (string.startsWith(deleteString)) {
            return string.substring(deleteString.length());
        }
        return string;
    }

    /**
     * <p>当 {@code string} 以 {@code deleteString} 开头时，将开头的 {@code deleteString} 删除，不区分大小写。</p>
     *
     * <p>{@code string} 为 null 时返回 {@code null}，空串 时返回 空串。
     * {@code deleteString} 为 null 或 空串 时直接返回 {@code string}。</p>
     *
     * <pre>
     * StringAide.deleteStartIgnoreCase(null, *)      = null
     * StringAide.deleteStartIgnoreCase("", *)        = ""
     * StringAide.deleteStartIgnoreCase(*, null)      = *
     * StringAide.deleteStartIgnoreCase("www.domain.com", "www.")   = "domain.com"
     * StringAide.deleteStartIgnoreCase("www.domain.com", "WWW.")   = "domain.com"
     * StringAide.deleteStartIgnoreCase("domain.com", "www.")       = "domain.com"
     * StringAide.deleteStartIgnoreCase("www.domain.com", "domain") = "www.domain.com"
     * StringAide.deleteStartIgnoreCase("abc", "")    = "abc"
     * </pre>
     *
     * @param string 源字符串
     * @param deleteString 要删除的字符串
     * @return 去掉开头 {@code deleteString} 之后的字符串
     * @since 1.0.0
     */
    public static String deleteStartIgnoreCase(final String string, final String deleteString) {
        if (isEmpty(string) || isEmpty(deleteString)) {
            return string;
        }
        if (startsWithIgnoreCase(string, deleteString)) {
            return string.substring(deleteString.length());
        }
        return string;
    }

    /**
     * <p>当 {@code string} 以 {@code deleteString} 结尾时，将结尾的 {@code deleteString} 删除。</p>
     *
     * <p>{@code string} 为 null 时返回 {@code null}，空串 时返回 空串。
     * {@code deleteString} 为 null 或 空串 时直接返回 {@code string}。</p>
     *
     * <pre>
     * StringAide.deleteEnd(null, *)      = null
     * StringAide.deleteEnd("", *)        = ""
     * StringAide.deleteEnd(*, null)      = *
     * StringAide.deleteEnd("www.domain.com", ".com.")  = "www.domain.com"
     * StringAide.deleteEnd("www.domain.com", ".com")   = "www.domain"
     * StringAide.deleteEnd("www.domain.com", "domain") = "www.domain.com"
     * StringAide.deleteEnd("abc", "")    = "abc"
     * </pre>
     *
     * @param string 源字符串
     * @param deleteString 要删除的字符串
     * @return 去掉结尾 {@code deleteString} 之后的字符串
     * @since 1.0.0
     */
    public static String deleteEnd(final String string, final String deleteString) {
        if (isEmpty(string) || isEmpty(deleteString)) {
            return string;
        }
        if (string.endsWith(deleteString)) {
            return string.substring(0, string.length() - deleteString.length());
        }
        return string;
    }

    /**
     * <p>当 {@code string} 以 {@code deleteString} 结尾时，将结尾的 {@code deleteString} 删除，不区分大小写。</p>
     *
     * <p>{@code string} 为 null 时返回 {@code null}，空串 时返回 空串。
     * {@code deleteString} 为 null 或 空串 时直接返回 {@code string}。</p>
     *
     * <pre>
     * StringAide.deleteEndIgnoreCase(null, *)      = null
     * StringAide.deleteEndIgnoreCase("", *)        = ""
     * StringAide.deleteEndIgnoreCase(*, null)      = *
     * StringAide.deleteEndIgnoreCase("www.domain.com", ".com.")  = "www.domain.com"
     * StringAide.deleteEndIgnoreCase("www.domain.com", ".com")   = "www.domain"
     * StringAide.deleteEndIgnoreCase("www.domain.com", "domain") = "www.domain.com"
     * StringAide.deleteEndIgnoreCase("abc", "")    = "abc"
     * StringAide.deleteEndIgnoreCase("www.domain.com", ".COM") = "www.domain")
     * StringAide.deleteEndIgnoreCase("www.domain.COM", ".com") = "www.domain")
     * </pre>
     *
     * @param string 源字符串
     * @param deleteString 要删除的字符串
     * @return 去掉结尾 {@code deleteString} 之后的字符串
     * @since 1.0.0
     */
    public static String deleteEndIgnoreCase(final String string, final String deleteString) {
        if (isEmpty(string) || isEmpty(deleteString)) {
            return string;
        }
        if (endsWithIgnoreCase(string, deleteString)) {
            return string.substring(0, string.length() - deleteString.length());
        }
        return string;
    }
    // ---------------------------------------------------------------------------------------------------
    // ----- Delete ----- end

    // ----- Truncate string ----- start
    // ---------------------------------------------------------------------------------------------------
    /**
     * <p>从指定位置开始截取指定长度的字符串。</p>
     *
     * <p>注意：</p>
     * <ul>
     *     <li>如果源字符串 {@code string} 或者 {@code maxLength} 小于 0，则返回 {@code null}。</li>
     *     <li>如果 {@code offset} 小于 0 则从 {@code string} 的起始位置开始截取。</li>
     *     <li>如果 {@code offset} 大于 {@code string} 的长度，则返回一个空串。</li>
     *     <li>如果 {@code maxLength} 大于 {@code string} 的长度，则截取到最后。</li>
     * </ul>
     *
     * <pre>
     * StringAide.truncate(null, 0, 0) = null
     * StringAide.truncate(null, 2, 4) = null
     * StringAide.truncate("", 0, 10) = ""
     * StringAide.truncate("", 2, 10) = ""
     * StringAide.truncate("abcdefghij", 0, 3) = "abc"
     * StringAide.truncate("abcdefghij", 5, 6) , "fghij"
     * StringAide.truncate("raspberry peach", 10, 15) = "peach"
     * StringAide.truncate("abcdefghijklmno", 0, 10) = "abcdefghij"
     * StringAide.truncate("abcdefghijklmno", -1, 10) = "abcdefghij"
     * StringAide.truncate("abcdefghijklmno", Integer.MIN_VALUE, 10) = "abcdefghij"
     * StringAide.truncate("abcdefghijklmno", Integer.MIN_VALUE, Integer.MAX_VALUE) = "abcdefghijklmno"
     * StringAide.truncate("abcdefghijklmno", 0, Integer.MAX_VALUE) = "abcdefghijklmno"
     * StringAide.truncate("abcdefghijklmno", 1, 10) = "bcdefghijk"
     * StringAide.truncate("abcdefghijklmno", 2, 10) = "cdefghijkl"
     * StringAide.truncate("abcdefghijklmno", 3, 10) = "defghijklm"
     * StringAide.truncate("abcdefghijklmno", 4, 10) = "efghijklmn"
     * StringAide.truncate("abcdefghijklmno", 5, 10) = "fghijklmno"
     * StringAide.truncate("abcdefghijklmno", 5, 5) = "fghij"
     * StringAide.truncate("abcdefghijklmno", 5, 3) = "fgh"
     * StringAide.truncate("abcdefghijklmno", 10, 3) = "klm"
     * StringAide.truncate("abcdefghijklmno", 10, Integer.MAX_VALUE) = "klmno"
     * StringAide.truncate("abcdefghijklmno", 13, 1) = "n"
     * StringAide.truncate("abcdefghijklmno", 13, Integer.MAX_VALUE) = "no"
     * StringAide.truncate("abcdefghijklmno", 14, 1) = "o"
     * StringAide.truncate("abcdefghijklmno", 14, Integer.MAX_VALUE) = "o"
     * StringAide.truncate("abcdefghijklmno", 15, 1) = ""
     * StringAide.truncate("abcdefghijklmno", 15, Integer.MAX_VALUE) = ""
     * StringAide.truncate("abcdefghijklmno", Integer.MAX_VALUE, Integer.MAX_VALUE) = ""
     * StringAide.truncate("abcdefghij", 3, -1) = null
     * StringAide.truncate("abcdefghij", -2, 4) = "abcd"
     * </pre>
     *
     * @param string 要截断的字符串，可以为 null
     * @param offset 左偏移量
     * @param maxLength 要截取的最大长度
     * @return 截取到的字符串，若 {@code string} 为 null 或 {@code maxLength} 小于 0 时返回 {@code null}
     * @since 1.0.0
     */
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

    /**
     * <p>截取指定长度的字符串。</p>
     *
     * <p>注意：</p>
     * <ul>
     *     <li>如果源字符串 {@code string} 或者 {@code maxLength} 小于 0，则返回 {@code null}。</li>
     *     <li>如果 {@code maxLength} 大于 {@code string} 的长度，则截取到最后。</li>
     * </ul>
     *
     * <pre>
     * StringAide.truncate(null, 0) = null
     * StringAide.truncate(null, 2) = null
     * StringAide.truncate("", 4) = ""
     * StringAide.truncate("abcdefg", 4) = "abcd"
     * StringAide.truncate("abcdefg", 6) = "abcdef"
     * StringAide.truncate("abcdefg", 7) = "abcdefg"
     * StringAide.truncate("abcdefg", 8) = "abcdefg"
     * StringAide.truncate("abcdefg", -1) = null
     * </pre>
     *
     * @param string 要截断的字符串，可以为 null
     * @param maxLength 要截取的最大长度
     * @return 截取到的字符串，若 {@code string} 为 null 或 {@code maxLength} 小于 0 时返回 {@code null}
     * @since 1.0.0
     */
    public static String truncate(final String string, int maxLength) {
        return truncate(string, 0, maxLength);
    }
    // ---------------------------------------------------------------------------------------------------
    // ----- Truncate string ----- end

    // ----- Compare string ----- start
    // ---------------------------------------------------------------------------------------------------
    /**
     * <p>按字典顺序比较两个字符串。</p>
     *
     * <pre>
     * StringAide.compare(null, null, *)     = 0
     * StringAide.compare(null , "a", true)  &lt; 0
     * StringAide.compare(null , "a", false) &gt; 0
     * StringAide.compare("a", null, true)   &gt; 0
     * StringAide.compare("a", null, false)  &lt; 0
     * StringAide.compare("abc", "abc", *)   = 0
     * StringAide.compare("a", "b", *)       &lt; 0
     * StringAide.compare("b", "a", *)       &gt; 0
     * StringAide.compare("a", "B", *)       &gt; 0
     * StringAide.compare("ab", "abc", *)    &lt; 0
     * </pre>
     *
     * @param str1 一个字符串
     * @param str2 另一个字符串
     * @param nullIsLess 如果为 true 则 {@code null} 小于 {@code non-null}
     * @return 0：相等；正数：{@code str1} 大于 {@code str2}；负数：{@code str1} 小于 {@code str2}
     * @since 1.0.0
     */
    public static int compare(final String str1, final String str2, final boolean nullIsLess) {
        if (equals(str1, str2)) {
            return 0;
        }
        if (str1 == null) {
            return nullIsLess ? -1 : 1;
        }
        if (str2 == null) {
            return nullIsLess ? 1 : -1;
        }
        return str1.compareTo(str2);
    }

    /**
     * <p>按字典顺序比较两个字符串（{@code null} 小于 {@code non-null}）。</p>
     *
     * <pre>
     * StringAide.compare(null, null)   = 0
     * StringAide.compare(null , "a")   &lt; 0
     * StringAide.compare("a", null)    &gt; 0
     * StringAide.compare("abc", "abc") = 0
     * StringAide.compare("a", "b")     &lt; 0
     * StringAide.compare("b", "a")     &gt; 0
     * StringAide.compare("a", "B")     &gt; 0
     * StringAide.compare("ab", "abc")  &lt; 0
     * </pre>
     *
     * @param str1 一个字符串
     * @param str2 另一个字符串
     * @return 0：相等；正数：{@code str1} 大于 {@code str2}；负数：{@code str1} 小于 {@code str2}
     * @since 1.0.0
     */
    public static int compare(final String str1, final String str2) {
        return compare(str1, str2, true);
    }

    /**
     * <p>按字典顺序比较两个字符串，忽略大小写。</p>
     *
     * <pre>
     * StringAide.compareIgnoreCase(null, null, *)     = 0
     * StringAide.compareIgnoreCase(null , "a", true)  &lt; 0
     * StringAide.compareIgnoreCase(null , "a", false) &gt; 0
     * StringAide.compareIgnoreCase("a", null, true)   &gt; 0
     * StringAide.compareIgnoreCase("a", null, false)  &lt; 0
     * StringAide.compareIgnoreCase("abc", "abc", *)   = 0
     * StringAide.compareIgnoreCase("abc", "ABC", *)   = 0
     * StringAide.compareIgnoreCase("a", "b", *)       &lt; 0
     * StringAide.compareIgnoreCase("b", "a", *)       &gt; 0
     * StringAide.compareIgnoreCase("a", "B", *)       &lt; 0
     * StringAide.compareIgnoreCase("A", "b", *)       &lt; 0
     * StringAide.compareIgnoreCase("ab", "abc", *)    &lt; 0
     * </pre>
     *
     * @param str1 一个字符串
     * @param str2 另一个字符串
     * @param nullIsLess 如果为 true 则 {@code null} 小于 {@code non-null}
     * @return 0：相等；正数：{@code str1} 大于 {@code str2}；负数：{@code str1} 小于 {@code str2}
     * @since 1.0.0
     */
    public static int compareIgnoreCase(final String str1, final String str2, final boolean nullIsLess) {
        if (equals(str1, str2)) {
            return 0;
        }
        if (str1 == null) {
            return nullIsLess ? -1 : 1;
        }
        if (str2 == null) {
            return nullIsLess ? 1 : -1;
        }
        return str1.compareToIgnoreCase(str2);
    }

    /**
     * <p>按字典顺序比较两个字符串，忽略大小写（{@code null} 小于 {@code non-null}）。</p>
     *
     * <pre>
     * StringAide.compareIgnoreCase(null, null)   = 0
     * StringAide.compareIgnoreCase(null , "a")   &lt; 0
     * StringAide.compareIgnoreCase("a", null)    &gt; 0
     * StringAide.compareIgnoreCase("abc", "abc") = 0
     * StringAide.compareIgnoreCase("abc", "ABC") = 0
     * StringAide.compareIgnoreCase("a", "b")     &lt; 0
     * StringAide.compareIgnoreCase("b", "a")     &gt; 0
     * StringAide.compareIgnoreCase("a", "B")     &lt; 0
     * StringAide.compareIgnoreCase("A", "b")     &lt; 0
     * StringAide.compareIgnoreCase("ab", "ABC")  &lt; 0
     * </pre>
     *
     * @param str1 一个字符串
     * @param str2 另一个字符串
     * @return 0：相等；正数：{@code str1} 大于 {@code str2}；负数：{@code str1} 小于 {@code str2}
     * @since 1.0.0
     */
    public static int compareIgnoreCase(final String str1, final String str2) {
        return compareIgnoreCase(str1, str2, true);
    }
    // ---------------------------------------------------------------------------------------------------
    // ----- Compare string ----- end

    // ----- Substring ----- start
    // ---------------------------------------------------------------------------------------------------
    /**
     * <p>从指定位置开始截取子串。</p>
     *
     * <p>负的起始位置表示从末尾开始倒数多少个字符。</p>
     *
     * <p>null 返回 {@code null}，空串 返回 空串。</p>
     *
     * <pre>
     * StringAide.substring(null, *)   = null
     * StringAide.substring("", *)     = ""
     * StringAide.substring("abc", 0)  = "abc"
     * StringAide.substring("abc", 2)  = "c"
     * StringAide.substring("abc", 4)  = ""
     * StringAide.substring("abc", -2) = "bc"
     * StringAide.substring("abc", -4) = "abc"
     * </pre>
     *
     * @param string 源字符串
     * @param start 起始位置，负数意味着从字符串末尾倒数这么多个字符
     * @return 起始范围内的子串，若源字符串为 null 则返回 {@code null}
     * @since 1.0.0
     */
    public static String substring(final String string, int start) {
        if (string == null) {
            return null;
        }

        if (start < 0) {
            start = string.length() + start;
        }
        if (start < 0) {
            start = 0;
        }

        if (start > string.length()) {
            return EMPTY;
        }

        return string.substring(start);
    }

    /**
     * <p>从指定字符串中获取子串。</p>
     *
     * <p>返回的子串以 {@code start} 位置的字符开头，在 {@code end} 之前结束。
     * 负的起始位置可用于指定相对于字符串结尾的偏移量（详见参数列表）。</p>
     *
     * <pre>
     * StringAide.substring(null, *, *)    = null
     * StringAide.substring("", * ,  *)    = "";
     * StringAide.substring("abc", 0, 2)   = "ab"
     * StringAide.substring("abc", 2, 0)   = ""
     * StringAide.substring("abc", 2, 4)   = "c"
     * StringAide.substring("abc", 4, 6)   = ""
     * StringAide.substring("abc", 2, 2)   = ""
     * StringAide.substring("abc", -2, -1) = "b"
     * StringAide.substring("abc", -4, 2)  = "ab"
     * </pre>
     *
     * @param string 源字符串
     * @param start 起始位置，负数意味着从字符串末尾倒数这么多个字符
     * @param end 结束位置，负数意味着从字符串末尾倒数这么多个字符
     * @return 起始范围内的子串，若源字符串为 null 则返回 {@code null}
     * @since 1.0.0
     */
    public static String substring(final String string, int start, int end) {
        if (string == null) {
            return null;
        }

        if (end < 0) {
            end = string.length() + end;
        }
        if (start < 0) {
            start = string.length() + start;
        }

        if (end > string.length()) {
            end = string.length();
        }

        if (start > end) {
            return EMPTY;
        }

        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 0;
        }

        return string.substring(start, end);
    }

    /**
     * <p>获取字符串最左边的 {@code len} 个字符</p>
     *
     * <p>{@code str} 为 null 时返回 {@code null}；
     * {@code len} 小于 0 时返回 空串；
     * {@code len} 大于等于 {@code str.length()} 时返回 {@code str}。</p>
     *
     * <pre>
     * StringAide.left(null, *)    = null
     * StringAide.left(*, -ve)     = ""
     * StringAide.left("", *)      = ""
     * StringAide.left("abc", 0)   = ""
     * StringAide.left("abc", 2)   = "ab"
     * StringAide.left("abc", 4)   = "abc"
     * </pre>
     *
     * @param str  源字符串
     * @param len  截取的长度
     * @return 子串
     * @since 1.0.0
     */
    public static String left(final String str, final int len) {
        if (str == null) {
            return null;
        }
        if (len < 0) {
            return EMPTY;
        }
        if (len >= str.length()) {
            return str;
        }
        return str.substring(0, len);
    }

    /**
     * <p>获取字符串最右边的 {@code len} 个字符。</p>
     *
     * <p>{@code str} 为 null 时返回 {@code null}；
     * {@code len} 小于 0 时返回 空串；
     * {@code len} 大于等于 {@code str.length()} 时返回 {@code str}。</p>
     *
     * <pre>
     * StringAide.right(null, *)    = null
     * StringAide.right(*, -ve)     = ""
     * StringAide.right("", *)      = ""
     * StringAide.right("abc", 0)   = ""
     * StringAide.right("abc", 2)   = "bc"
     * StringAide.right("abc", 4)   = "abc"
     * </pre>
     *
     * @param str  源字符串
     * @param len  截取的长度
     * @return 子串
     * @since 1.0.0
     */
    public static String right(final String str, final  int len) {
        if (str == null) {
            return null;
        }
        if (len < 0) {
            return EMPTY;
        }
        if (len >= str.length()) {
            return str;
        }
        return str.substring(str.length() - len);
    }

    /**
     * <p>从字符串的中间获取 {@code len} 个字符。</p>
     *
     * <p>{@code str} 为 null 时返回 {@code null}；
     * {@code len} 小于 0 或超过 {@code str} 长度时返回 空串；
     * {@code from + len} 超出长度时取到源字符串最后。</p>
     *
     * <pre>
     * StringAide.mid(null, *, *)    = null
     * StringAide.mid(*, *, -ve)     = ""
     * StringAide.mid("", 0, *)      = ""
     * StringAide.mid("abc", 0, 2)   = "ab"
     * StringAide.mid("abc", 0, 4)   = "abc"
     * StringAide.mid("abc", 2, 4)   = "c"
     * StringAide.mid("abc", 4, 2)   = ""
     * StringAide.mid("abc", -2, 2)  = "ab"
     * </pre>
     *
     * @param str 源字符串
     * @param from 起始位置，负数视为 0
     * @param len 要获取的子串长度
     * @return 子串
     * @since 1.0.0
     */
    public static String middle(final String str, int from, final int len) {
        if (str == null) {
            return null;
        }
        if (len < 0 || from > str.length()) {
            return EMPTY;
        }
        if (from < 0) {
            from = 0;
        }
        if (from + len >= str.length()) {
            return str.substring(from);
        }
        return str.substring(from, from + len);
    }

    /**
     * <p>截取字符串 {@code string} 的子串，从 0 到 {@code separator} 第一次出现的位置，不包含 {@code separator}。</p>
     *
     * <p>注意：</p>
     * <ul>
     *     <li>{@code string} 为 null 时返回 {@code null}</li>
     *     <li>{@code string} 为 空串 时返回 空串</li>
     *     <li>{@code separator} 为 null 时直接返回 {@code string}</li>
     *     <li>{@code string} 非空 且 {@code separator} 为 空串 时直接返回 空串</li>
     *     <li>如果未在 {@code string} 中找到 {@code separator}，则返回 {@code string}</li>
     * </ul>
     *
     * <pre>
     * StringAide.substringBefore(null, *)      = null
     * StringAide.substringBefore("", *)        = ""
     * StringAide.substringBefore("abc", "a")   = ""
     * StringAide.substringBefore("abcba", "b") = "a"
     * StringAide.substringBefore("abc", "c")   = "ab"
     * StringAide.substringBefore("abc", "d")   = "abc"
     * StringAide.substringBefore("abc", "")    = ""
     * StringAide.substringBefore("abc", null)  = "abc"
     * </pre>
     *
     * @param string 源字符串
     * @param separator 要查找的字符串
     * @return 从 0 到 {@code separator} 第一次出现的位置的子串
     * @since 1.0.0
     */
    public static String substringBefore(final String string, final String separator) {
        if (isEmpty(string) || separator == null) {
            return string;
        }
        if (separator.isEmpty()) {
            return EMPTY;
        }
        int separatorIndex = string.indexOf(separator);
        if (separatorIndex == INDEX_NOT_FOUND) {
            return string;
        }
        return string.substring(0, separatorIndex);
    }

    /**
     * <p>截取字符串 {@code string} 的子串，从第一个 {@code separator} 之后开始向后截取，不包含第一个 {@code separator}。</p>
     *
     * <p>注意：</p>
     * <ul>
     *     <li>{@code string} 为 null 或 空串 时返回 {@code null} 或 空串</li>
     *     <li>{@code separator} 为 null 时直接返回 空串</li>
     *     <li>如果未在 {@code string} 中找到 {@code separator}，则返回 空串</li>
     * </ul>
     *
     * <pre>
     * StringAide.substringAfter(null, *)      = null
     * StringAide.substringAfter("", *)        = ""
     * StringAide.substringAfter(*, null)      = ""
     * StringAide.substringAfter("abc", "a")   = "bc"
     * StringAide.substringAfter("abcba", "b") = "cba"
     * StringAide.substringAfter("abc", "c")   = ""
     * StringAide.substringAfter("abc", "d")   = ""
     * StringAide.substringAfter("abc", "")    = "abc"
     * </pre>
     *
     * @param string 源字符串
     * @param separator 要查找的字符串
     * @return 从 {@code separator} 第一次出现的位置之后到最后的子串
     * @since 1.0.0
     */
    public static String substringAfter(final String string, final String separator) {
        if (isEmpty(string)) {
            return string;
        }
        if (separator == null) {
            return EMPTY;
        }
        final int separatorIndex = string.indexOf(separator);
        if (separatorIndex == INDEX_NOT_FOUND) {
            return EMPTY;
        }
        return string.substring(separatorIndex + separator.length());
    }

    /**
     * <p>截取字符串 {@code string} 的子串，从 0 到 {@code separator} 最后一次出现的位置，不包含最后一个 {@code separator}。</p>
     *
     * <p>注意：</p>
     * <ul>
     *     <li>{@code string} 为 null 或 空串 时返回 {@code null} 或 空串</li>
     *     <li>{@code separator} 为 null 或 空串 时直接返回 {@code string}</li>
     *     <li>如果未在 {@code string} 中找到 {@code separator}，则返回 {@code string}</li>
     * </ul>
     *
     * <pre>
     * StringAide.substringBeforeLast(null, *)      = null
     * StringAide.substringBeforeLast("", *)        = ""
     * StringAide.substringBeforeLast("abcba", "b") = "abc"
     * StringAide.substringBeforeLast("abc", "c")   = "ab"
     * StringAide.substringBeforeLast("a", "a")     = ""
     * StringAide.substringBeforeLast("a", "z")     = "a"
     * StringAide.substringBeforeLast("a", null)    = "a"
     * StringAide.substringBeforeLast("a", "")      = "a"
     * </pre>
     *
     * @param string 源字符串
     * @param separator 要查找的字符串
     * @return 从 0 到 {@code separator} 最后一次出现的位置的子串
     * @since 1.0.0
     */
    public static String substringBeforeLast(final String string, final String separator) {
        if (isEmpty(string) || isEmpty(separator)) {
            return string;
        }
        final int lastIndex = string.lastIndexOf(separator);
        if (lastIndex == INDEX_NOT_FOUND) {
            return string;
        }
        return string.substring(0, lastIndex);
    }

    /**
     * <p>截取字符串 {@code string} 的子串，从最后一个 {@code separator} 之后开始向后截取，不包含最后一个 {@code separator}。</p>
     *
     * <p>注意：</p>
     * <ul>
     *     <li>{@code string} 为 null 或 空串 时返回 {@code null} 或 空串</li>
     *     <li>若 {@code string} 非空，{@code separator} 为 null 或 空串 时返回 空串</li>
     *     <li>如果未在 {@code string} 中找到 {@code separator}，则返回 空串</li>
     * </ul>
     *
     * <pre>
     * StringAide.substringAfterLast(null, *)      = null
     * StringAide.substringAfterLast("", *)        = ""
     * StringAide.substringAfterLast(*, "")        = ""
     * StringAide.substringAfterLast(*, null)      = ""
     * StringAide.substringAfterLast("abc", "a")   = "bc"
     * StringAide.substringAfterLast("abcba", "b") = "a"
     * StringAide.substringAfterLast("abc", "c")   = ""
     * StringAide.substringAfterLast("a", "a")     = ""
     * StringAide.substringAfterLast("a", "z")     = ""
     * </pre>
     *
     * @param string 源字符串
     * @param separator 要查找的字符串
     * @return 从最后一个 {@code separator} 之后开始到最后的子串
     * @since 1.0.0
     */
    public static String substringAfterLast(final String string, final String separator) {
        if (isEmpty(string)) {
            return string;
        }
        if (isEmpty(separator)) {
            return EMPTY;
        }
        final int lastIndex = string.lastIndexOf(separator);
        if (lastIndex == INDEX_NOT_FOUND || lastIndex == string.length() - separator.length()) {
            return EMPTY;
        }
        return string.substring(lastIndex + separator.length());
    }

    /**
     * <p>截取字符串 {@code string} 中位于开始标记 {@code open} 和结束标记 {@code close} 之间的子串，仅返回第一个匹配的结果。</p>
     *
     * <p>当 {@code string}、{@code open}、{@code close} 任一为 null 时 返回 {@code null}</p>
     *
     * <pre>
     * StringAide.substringBetween("wx[b]yz", "[", "]") = "b"
     * StringAide.substringBetween(null, *, *)          = null
     * StringAide.substringBetween(*, null, *)          = null
     * StringAide.substringBetween(*, *, null)          = null
     * StringAide.substringBetween("", "", "")          = ""
     * StringAide.substringBetween("", "", "]")         = null
     * StringAide.substringBetween("", "[", "]")        = null
     * StringAide.substringBetween("yabcz", "", "")     = ""
     * StringAide.substringBetween("yabcz", "y", "z")   = "abc"
     * StringAide.substringBetween("yabczyabcz", "y", "z")   = "abc"
     * </pre>
     *
     * @param string 源字符串
     * @param open 开始标记
     * @param close 结束标记
     * @return 子串，无匹配时返回 {@code null}
     * @since 1.0.0
     */
    public static String substringBetween(final String string, final String open, final String close) {
        if (string == null || open == null || close == null) {
            return null;
        }
        final int start = string.indexOf(open);
        if (start != INDEX_NOT_FOUND) {
            final int end = string.indexOf(close, start + open.length());
            if (end != INDEX_NOT_FOUND) {
                return string.substring(start + open.length(), end);
            }
        }
        return null;
    }

    /**
     * <p>截取字符串 {@code string} 中位于一对 {@code tag} 之间的子串，仅返回第一个匹配的结果。</p>
     *
     * <p>当 {@code string} 和 {@code tag} 任一为 null 时 返回 {@code null}</p>
     *
     * <pre>
     * StringAide.substringBetween(null, *)            = null
     * StringAide.substringBetween("", "")             = ""
     * StringAide.substringBetween("", "tag")          = null
     * StringAide.substringBetween("tagabctag", null)  = null
     * StringAide.substringBetween("tagabctag", "")    = ""
     * StringAide.substringBetween("tagabctag", "tag") = "abc"
     * </pre>
     *
     * @param string 源字符串
     * @param tag 起始标记
     * @return 子串，无匹配时返回 {@code null}
     * @since 1.0.0
     */
    public static String substringBetween(final String string, final String tag) {
        return substringBetween(string, tag, tag);
    }

    /**
     * <p>返回源字符串 {@code string} 中位于开始标记 {@code open} 和结束标记 {@code close} 之间的子串，所有匹配的结果作为一个数组返回。</p>
     *
     * <p>{@code string} 为 null 时返回 {@code null}，为 空串 时返回一个空数组。
     * {@code open/close} 为 null 或 空串 时返回 {@code null}。</p>
     *
     * <pre>
     * StringAide.substringsBetween("[a][b][c]", "[", "]") = ["a","b","c"]
     * StringAide.substringsBetween(null, *, *)            = null
     * StringAide.substringsBetween(*, null, *)            = null
     * StringAide.substringsBetween(*, *, null)            = null
     * StringAide.substringsBetween("", "[", "]")          = []
     * </pre>
     *
     * @param string 源字符串
     * @param open 开始标记
     * @param close 结束标记
     * @return 子串数组
     * @since 1.0.0
     */
    public static String[] substringsBetween(final String string, final String open, final String close) {
        if (string == null || isEmpty(open) || isEmpty(close)) {
            return null;
        }
        final int strLength = string.length();
        if (strLength == 0) {
            return new String[0];
        }
        final int openLength = open.length();
        final int closeLength = close.length();
        final List<String> list = new ArrayList<>();
        int index = 0;
        while (index < strLength - closeLength) {
            int start = string.indexOf(open, index);
            if (start < 0) {
                break;
            }
            start += openLength;
            final int end = string.indexOf(close, start);
            if (end < 0) {
                break;
            }
            list.add(string.substring(start, end));
            index = end + closeLength;
        }
        if (list.isEmpty()) {
            return null;
        }
        return list.toArray(new String[0]);
    }
    // ---------------------------------------------------------------------------------------------------
    // ----- Substring ----- end

    // ----- Split ----- start
    // ---------------------------------------------------------------------------------------------------
    /**
     * <p>将 {@code str} 使用 {@link Character#isWhitespace(char)} 定义的空白符作为分隔符拆分成一个字符串数组。</p>
     *
     * <p>分隔符不包含在返回的数组中。相邻的分隔符视为一个分隔符。</p>
     *
     * <p>{@code str} 为 null 时返回 null，空串时返回一个空数组。</p>
     *
     * <pre>
     * StringAide.split(null)       = null
     * StringAide.split("")         = []
     * StringAide.split("abc def")  = ["abc", "def"]
     * StringAide.split("abc  def") = ["abc", "def"]
     * StringAide.split(" abc ")    = ["abc"]
     * </pre>
     *
     * @param str 源字符串
     * @return 一个已分隔的字符串数组，如果输入的字符串为 null 则返回 {@code null}
     * @since 1.0.0
     */
    public static String[] split(final String str) {
        return split(str, null, -1);
    }

    /**
     * <p>将 {@code str} 使用 {@code separatorChar} 作为分隔符拆分成一个字符串数组。</p>
     *
     * <p>分隔符不包含在返回的数组中。相邻的分隔符视为一个分隔符。</p>
     *
     * <p>{@code str} 为 null 时返回 null，空串时返回一个空数组。</p>
     *
     * <pre>
     * StringAide.split(null, *)         = null
     * StringAide.split("", *)           = []
     * StringAide.split("a.b.c", '.')    = ["a", "b", "c"]
     * StringAide.split("a..b.c", '.')   = ["a", "b", "c"]
     * StringAide.split("a:b:c", '.')    = ["a:b:c"]
     * StringAide.split("a b c", ' ')    = ["a", "b", "c"]
     * </pre>
     *
     * @param str 源字符串
     * @param separatorChar 分隔符
     * @return 一个已分隔的字符串数组，如果输入的字符串为 null 则返回 {@code null}
     * @since 1.0.0
     */
    public static String[] split(final String str, final char separatorChar) {
        return splitWorker(str, separatorChar, false);
    }

    /**
     * <p>将 {@code str} 使用 {@code separatorChars} 中的每个字符作为分隔符拆分成一个字符串数组。</p>
     *
     * <p>分隔符不包含在返回的数组中。相邻的分隔符视为一个分隔符。</p>
     *
     * <p>{@code str} 为 null 时返回 null，空串时返回一个空数组。
     * {@code separatorChars} 为 null 时使用 {@link Character#isWhitespace(char)} 定义的空白符 进行分隔。</p>
     *
     * <pre>
     * StringAide.split(null, *)         = null
     * StringAide.split("", *)           = []
     * StringAide.split("abc def", null) = ["abc", "def"]
     * StringAide.split("abc def", " ")  = ["abc", "def"]
     * StringAide.split("abc  def", " ") = ["abc", "def"]
     * StringAide.split("ab:cd:ef", ":") = ["ab", "cd", "ef"]
     * </pre>
     *
     * @param str 源字符串
     * @param separatorChars 分隔符，{@code null} 被视为 {@link Character#isWhitespace(char)} 定义的空白符
     * @return 一个已分隔的字符串数组，如果输入的字符串为 null 则返回 {@code null}
     * @since 1.0.0
     */
    public static String[] split(final String str, final String separatorChars) {
        return splitWorker(str, separatorChars, -1, false);
    }

    /**
     * <p>将 {@code str} 使用 {@code separatorChars} 中的每个字符作为分隔符拆分成一个字符串数组。</p>
     *
     * <p>分隔符不包含在返回的数组中。相邻的分隔符视为一个分隔符。</p>
     *
     * <p>{@code str} 为 null 时返回 null，空串时返回一个空数组。
     * {@code separatorChars} 为 null 时使用 {@link Character#isWhitespace(char)} 定义的空白符 进行分隔。</p>
     *
     * <p>{@code max} 指定最多分隔成多少个字符串，即最终返回的数组最大长度。
     * 分隔过程中若达到该值，后面的所有字符（包括分隔符）将作为数组的最后一个元素。
     * 0 或 负值 表示不限制数量。</p>
     *
     * <pre>
     * StringAide.split(null, *, *)            = null
     * StringAide.split("", *, *)              = []
     * StringAide.split("ab cd ef", null, 0)   = ["ab", "cd", "ef"]
     * StringAide.split("ab   cd ef", null, 0) = ["ab", "cd", "ef"]
     * StringAide.split("ab:cd:ef", ":", 0)    = ["ab", "cd", "ef"]
     * StringAide.split("ab:cd:ef", ":", 2)    = ["ab", "cd:ef"]
     * </pre>
     *
     * @param str 源字符串
     * @param separatorChars 分隔符，{@code null} 被视为 {@link Character#isWhitespace(char)} 定义的空白符
     * @param max 包含在数组中的最大元素数，0 或 负值 表示无限制
     * @return 一个已分隔的字符串数组，如果输入的字符串为 null 则返回 {@code null}
     * @since 1.0.0
     */
    public static String[] split(final String str, final String separatorChars, final int max) {
        return splitWorker(str, separatorChars, max, false);
    }

    /**
     * <p>将 {@code str} 使用整个 {@code separator} 为分隔符拆分成一个字符串数组。
     * 可以指定最多分隔成多少个。</p>
     *
     * <p>分隔符不包含在返回的数组中。相邻的分隔符视为一个分隔符。</p>
     *
     * <p>{@code str} 为 null 时返回 null，空串时返回一个空数组。
     * {@code separatorChars} 为 null 时使用 {@link Character#isWhitespace(char)} 定义的空白符 进行分隔。</p>
     *
     * <pre>
     * StringAide.splitByWholeSeparator(null, *)               = null
     * StringAide.splitByWholeSeparator("", *)                 = []
     * StringAide.splitByWholeSeparator("ab de fg", null)      = ["ab", "de", "fg"]
     * StringAide.splitByWholeSeparator("ab   de fg", null)    = ["ab", "de", "fg"]
     * StringAide.splitByWholeSeparator("ab:cd:ef", ":")       = ["ab", "cd", "ef"]
     * StringAide.splitByWholeSeparator("ab-!-cd-!-ef", "-!-") = ["ab", "cd", "ef"]
     * </pre>
     *
     * @param str 源字符串
     * @param separator 分隔符，{@code null} 被视为 {@link Character#isWhitespace(char)} 定义的空白符
     * @return 一个已分隔的字符串数组，如果输入的字符串为 null 则返回 {@code null}
     * @since 1.0.0
     */
    public static String[] splitByWholeSeparator(final String str, final String separator) {
        return splitByWholeSeparatorWorker(str, separator, -1, false ) ;
    }

    /**
     * <p>将 {@code str} 使用整个 {@code separator} 为分隔符拆分成一个字符串数组。
     * 可以指定最多分隔成多少个。</p>
     *
     * <p>分隔符不包含在返回的数组中。相邻的分隔符视为一个分隔符。</p>
     *
     * <p>{@code str} 为 null 时返回 null，空串时返回一个空数组。
     * {@code separatorChars} 为 null 时使用 {@link Character#isWhitespace(char)} 定义的空白符 进行分隔。</p>
     *
     * <p>{@code max} 指定最多分隔成多少个字符串，即最终返回的数组最大长度。
     * 分隔过程中若达到该值，后面的所有字符（包括分隔符）将作为数组的最后一个元素。
     * 0 或 负值 表示不限制数量。</p>
     *
     * <pre>
     * StringAide.splitByWholeSeparator(null, *, *)               = null
     * StringAide.splitByWholeSeparator("", *, *)                 = []
     * StringAide.splitByWholeSeparator("ab de fg", null, 0)      = ["ab", "de", "fg"]
     * StringAide.splitByWholeSeparator("ab   de fg", null, 0)    = ["ab", "de", "fg"]
     * StringAide.splitByWholeSeparator("ab:cd:ef", ":", 2)       = ["ab", "cd:ef"]
     * StringAide.splitByWholeSeparator("ab-!-cd-!-ef", "-!-", 5) = ["ab", "cd", "ef"]
     * StringAide.splitByWholeSeparator("ab-!-cd-!-ef", "-!-", 2) = ["ab", "cd-!-ef"]
     * </pre>
     *
     * @param str 源字符串
     * @param separator 分隔符，{@code null} 被视为 {@link Character#isWhitespace(char)} 定义的空白符
     * @param max 包含在数组中的最大元素数，0 或 负值 表示无限制
     * @return 一个已分隔的字符串数组，如果输入的字符串为 null 则返回 {@code null}
     * @since 1.0.0
     */
    public static String[] splitByWholeSeparator(final String str, final String separator, final int max) {
        return splitByWholeSeparatorWorker(str, separator, max, false);
    }

    /**
     * <p>将 {@code str} 使用整个 {@code separator} 为分隔符拆分成一个字符串数组。
     * 保留所有符号，包括由相邻的分隔符构成的空符号。</p>
     *
     * <p>分隔符不包含在返回的数组中。相邻的分隔符视为空符号分隔符。</p>
     *
     * <p>{@code str} 为 null 时返回 null，空串时返回一个空数组。
     * {@code separatorChars} 为 null 时使用 {@link Character#isWhitespace(char)} 定义的空白符 进行分隔。</p>
     *
     * <pre>
     * StringAide.splitByWholeSeparatorPreserveAllTokens(null, *)               = null
     * StringAide.splitByWholeSeparatorPreserveAllTokens("", *)                 = []
     * StringAide.splitByWholeSeparatorPreserveAllTokens("ab de fg", null)      = ["ab", "de", "fg"]
     * StringAide.splitByWholeSeparatorPreserveAllTokens("ab   de fg", null)    = ["ab", "", "", "de", "fg"]
     * StringAide.splitByWholeSeparatorPreserveAllTokens("ab:cd:ef", ":")       = ["ab", "cd", "ef"]
     * StringAide.splitByWholeSeparatorPreserveAllTokens("ab-!-cd-!-ef", "-!-") = ["ab", "cd", "ef"]
     * </pre>
     *
     * @param str 源字符串
     * @param separator 分隔符，{@code null} 被视为 {@link Character#isWhitespace(char)} 定义的空白符
     * @return 一个已分隔的字符串数组，如果输入的字符串为 null 则返回 {@code null}
     * @since 1.0.0
     */
    public static String[] splitByWholeSeparatorPreserveAllTokens(final String str, final String separator) {
        return splitByWholeSeparatorWorker(str, separator, -1, true);
    }

    /**
     * <p>将 {@code str} 使用整个 {@code separator} 为分隔符拆分成一个字符串数组。
     * 保留所有符号，包括由相邻的分隔符构成的空符号。
     * 可以指定最多分隔成多少个。</p>
     *
     * <p>分隔符不包含在返回的数组中。相邻的分隔符视为空符号分隔符。</p>
     *
     * <p>{@code str} 为 null 时返回 null，空串时返回一个空数组。
     * {@code separatorChars} 为 null 时使用 {@link Character#isWhitespace(char)} 定义的空白符 进行分隔。</p>
     *
     * <p>{@code max} 指定最多分隔成多少个字符串，即最终返回的数组最大长度。
     * 分隔过程中若达到该值，后面的所有字符（包括分隔符）将作为数组的最后一个元素。
     * 0 或 负值 表示不限制数量。</p>
     *
     * <pre>
     * StringAide.splitByWholeSeparatorPreserveAllTokens(null, *, *)               = null
     * StringAide.splitByWholeSeparatorPreserveAllTokens("", *, *)                 = []
     * StringAide.splitByWholeSeparatorPreserveAllTokens("ab de fg", null, 0)      = ["ab", "de", "fg"]
     * StringAide.splitByWholeSeparatorPreserveAllTokens("ab   de fg", null, 0)    = ["ab", "", "", "de", "fg"]
     * StringAide.splitByWholeSeparatorPreserveAllTokens("ab:cd:ef", ":", 2)       = ["ab", "cd:ef"]
     * StringAide.splitByWholeSeparatorPreserveAllTokens("ab-!-cd-!-ef", "-!-", 5) = ["ab", "cd", "ef"]
     * StringAide.splitByWholeSeparatorPreserveAllTokens("ab-!-cd-!-ef", "-!-", 2) = ["ab", "cd-!-ef"]
     * </pre>
     *
     * @param str 源字符串
     * @param separator 分隔符，{@code null} 被视为 {@link Character#isWhitespace(char)} 定义的空白符
     * @param max 包含在数组中的最大元素数，0 或 负值 表示无限制
     * @return 一个已分隔的字符串数组，如果输入的字符串为 null 则返回 {@code null}
     * @since 1.0.0
     */
    public static String[] splitByWholeSeparatorPreserveAllTokens(final String str, final String separator, final int max) {
        return splitByWholeSeparatorWorker(str, separator, max, true);
    }

    /**
     * <p>执行 {@code splitByWholeSeparatorPreserveAllTokens} 方法的逻辑。</p>
     *
     * @param str 源字符串
     * @param separator 分隔符，，{@code null} 被视为 {@link Character#isWhitespace(char)} 定义的空白符
     * @param max 包含在数组中的最大元素数，0 或 负值 表示无限制
     * @param preserveAllTokens {@code true}：相邻的分隔符被视为空符号分隔符；{@code false}：相邻的分隔符被视为一个分隔符
     * @return 一个已分隔的字符串数组，如果输入的字符串为 null 则返回 {@code null}
     * @since 1.0.0
     */
    private static String[] splitByWholeSeparatorWorker(final String str, final String separator,
                                                        final int max, final boolean preserveAllTokens) {
        if (str == null) {
            return null;
        }

        final int strLen = str.length();
        if (strLen == 0) {
            return new String[0];
        }

        if (separator == null) {
            return splitWorker(str, null, max, preserveAllTokens);
        }

        final int separatorLen = separator.length();

        final List<String> list = new ArrayList<>();
        int numberOfSubstrings = 0;
        int start = 0;
        int end = 0;
        while (end < strLen) {
            end = str.indexOf(separator, start);
            if (end >= 0) {
                if (end > start) {
                    numberOfSubstrings++;

                    if (numberOfSubstrings == max) {
                        end = strLen;
                        list.add(str.substring(start));
                    } else {
                        list.add(str.substring(start, end));
                        start = end + separatorLen;
                    }
                } else {
                    if (preserveAllTokens) {
                        numberOfSubstrings++;

                        if (numberOfSubstrings == max) {
                            end = strLen;
                            list.add(str.substring(start));
                        } else {
                            list.add(EMPTY);
                        }
                    }
                    start = end + separatorLen;
                }
            } else {
                list.add(str.substring(start));
                end = strLen;
            }
        }

        return list.toArray(new String[0]);
    }

    /**
     * <p>将 {@code str} 使用 {@link Character#isWhitespace(char)} 定义的空白符拆分为一个字符串数组。
     * 保留所有符号，包括由相邻的分隔符构成的空符号。</p>
     *
     * <p>分隔符不包含在返回的数组中。相邻的分隔符被视为空符号的分隔符。</p>
     *
     * <p>{@code str} 为 null 时返回 null，空串时返回一个空数组。</p>
     *
     * <pre>
     * StringAide.splitPreserveAllTokens(null)       = null
     * StringAide.splitPreserveAllTokens("")         = []
     * StringAide.splitPreserveAllTokens("abc def")  = ["abc", "def"]
     * StringAide.splitPreserveAllTokens("abc  def") = ["abc", "", "def"]
     * StringAide.splitPreserveAllTokens(" abc ")    = ["", "abc", ""]
     * </pre>
     *
     * @param str 源字符串
     * @return 一个已分隔的字符串数组，如果输入的字符串为 null 则返回 {@code null}
     * @since 1.0.0
     */
    public static String[] splitPreserveAllTokens(final String str) {
        return splitWorker(str, null, -1, true);
    }

    /**
     * <p>将 {@code str} 使用 {@code separatorChar} 拆分为一个字符串数组。
     * 保留所有符号，包括由相邻的分隔符构成的空符号。</p>
     *
     * <p>分隔符不包含在返回的数组中。相邻的分隔符被视为空符号的分隔符。</p>
     *
     * <p>{@code str} 为 null 时返回 null，空串时返回一个空数组。</p>
     *
     * <pre>
     * StringAide.splitPreserveAllTokens(null, *)         = null
     * StringAide.splitPreserveAllTokens("", *)           = []
     * StringAide.splitPreserveAllTokens("a.b.c", '.')    = ["a", "b", "c"]
     * StringAide.splitPreserveAllTokens("a..b.c", '.')   = ["a", "", "b", "c"]
     * StringAide.splitPreserveAllTokens("a:b:c", '.')    = ["a:b:c"]
     * StringAide.splitPreserveAllTokens("a\tb\nc", null) = ["a", "b", "c"]
     * StringAide.splitPreserveAllTokens("a b c", ' ')    = ["a", "b", "c"]
     * StringAide.splitPreserveAllTokens("a b c ", ' ')   = ["a", "b", "c", ""]
     * StringAide.splitPreserveAllTokens("a b c  ", ' ')   = ["a", "b", "c", "", ""]
     * StringAide.splitPreserveAllTokens(" a b c", ' ')   = ["", a", "b", "c"]
     * StringAide.splitPreserveAllTokens("  a b c", ' ')  = ["", "", a", "b", "c"]
     * StringAide.splitPreserveAllTokens(" a b c ", ' ')  = ["", a", "b", "c", ""]
     * </pre>
     *
     * @param str 源字符串
     * @param separatorChar 分隔符
     * @return 一个已分隔的字符串数组，如果输入的字符串为 null 则返回 {@code null}
     * @since 1.0.0
     */
    public static String[] splitPreserveAllTokens(final String str, final char separatorChar) {
        return splitWorker(str, separatorChar, true);
    }

    /**
     * <p>执行 {@code split} 和 {@code splitPreserveAllTokens} 的逻辑。</p>
     *
     * @param str 源字符串
     * @param separatorChar 分隔符
     * @param preserveAllTokens {@code true}：相邻的分隔符被视为空符号分隔符；{@code false}：相邻的分隔符被视为一个分隔符
     * @return 一个已分隔的字符串数组，如果输入的字符串为 null 则返回 {@code null}
     * @since 1.0.0
     */
    private static String[] splitWorker(final String str, final char separatorChar, final boolean preserveAllTokens) {
        if (str == null) {
            return null;
        }
        final int strLen = str.length();
        if (strLen == 0) {
            return new String[0];
        }
        final List<String> list = new ArrayList<>();
        int i = 0, start = 0;
        boolean match = false;
        while (i < strLen) {
            if (str.charAt(i) == separatorChar) {
                if (match || preserveAllTokens) {
                    list.add(str.substring(start, i));
                    match = false;
                }
                start = ++i;
                continue;
            }
            match = true;
            i++;
        }
        if (match || preserveAllTokens) {
            list.add(str.substring(start, i));
        }
        return list.toArray(new String[0]);
    }

    /**
     * <p>将 {@code str} 使用 {@code separatorChars} 中的字符拆分为一个字符串数组。
     * 保留所有符号（""不会被忽略），相邻的分隔符视为空符号分隔符。</p>
     *
     * <p>分隔符不包含在返回的数组中。相邻的分隔符被视为空符号的分隔符。</p>
     *
     * <p>{@code str} 为 null 时返回 null，空串时返回一个空数组。
     * {@code separatorChars} 为 null 时使用 {@link Character#isWhitespace(char)} 定义的空白符 进行分隔。</p>
     *
     * <pre>
     * StringAide.splitPreserveAllTokens(null, *)           = null
     * StringAide.splitPreserveAllTokens("", *)             = []
     * StringAide.splitPreserveAllTokens("abc def", null)   = ["abc", "def"]
     * StringAide.splitPreserveAllTokens("abc def", " ")    = ["abc", "def"]
     * StringAide.splitPreserveAllTokens("abc  def", " ")   = ["abc", "", def"]
     * StringAide.splitPreserveAllTokens("ab:cd:ef", ":")   = ["ab", "cd", "ef"]
     * StringAide.splitPreserveAllTokens("ab:cd:ef:", ":")  = ["ab", "cd", "ef", ""]
     * StringAide.splitPreserveAllTokens("ab:cd:ef::", ":") = ["ab", "cd", "ef", "", ""]
     * StringAide.splitPreserveAllTokens("ab::cd:ef", ":")  = ["ab", "", cd", "ef"]
     * StringAide.splitPreserveAllTokens(":cd:ef", ":")     = ["", cd", "ef"]
     * StringAide.splitPreserveAllTokens("::cd:ef", ":")    = ["", "", cd", "ef"]
     * StringAide.splitPreserveAllTokens(":cd:ef:", ":")    = ["", cd", "ef", ""]
     * </pre>
     *
     * @param str 源字符串
     * @param separatorChars 分隔符，{@code null} 被视为 {@link Character#isWhitespace(char)} 定义的空白符
     * @return 一个已分隔的字符串数组，如果输入的字符串为 null 则返回 {@code null}
     * @since 1.0.0
     */
    public static String[] splitPreserveAllTokens(final String str, final String separatorChars) {
        return splitWorker(str, separatorChars, -1, true);
    }

    /**
     * <p>将 {@code str} 使用 {@code separatorChars} 中的字符拆分为一个字符串数组。
     * 保留所有符号（""不会被忽略），相邻的分隔符视为空符号分隔符。
     * 可以指定最多分隔成多少组。</p>
     *
     * <p>分隔符不包含在返回的数组中。相邻的分隔符视为空符号分隔符。</p>
     *
     * <p>{@code str} 为 null 时返回 null，空串时返回一个空数组。
     * {@code separatorChars} 为 null 时使用 {@link Character#isWhitespace(char)} 定义的空白符 进行分隔。</p>
     *
     * <p>{@code max} 指定最多分隔成多少个字符串，即最终返回的数组最大长度。
     * 分隔过程中若达到该值，后面的所有字符（包括分隔符）将作为数组的最后一个元素。
     * 0 或 负值 表示不限制数量。</p>
     *
     * <pre>
     * StringAide.splitPreserveAllTokens(null, *, *)            = null
     * StringAide.splitPreserveAllTokens("", *, *)              = []
     * StringAide.splitPreserveAllTokens("ab de fg", null, 0)   = ["ab", "cd", "ef"]
     * StringAide.splitPreserveAllTokens("ab   de fg", null, 0) = ["ab", "cd", "ef"]
     * StringAide.splitPreserveAllTokens("ab:cd:ef", ":", 0)    = ["ab", "cd", "ef"]
     * StringAide.splitPreserveAllTokens("ab:cd:ef", ":", 2)    = ["ab", "cd:ef"]
     * StringAide.splitPreserveAllTokens("ab   de fg", null, 2) = ["ab", "  de fg"]
     * StringAide.splitPreserveAllTokens("ab   de fg", null, 3) = ["ab", "", " de fg"]
     * StringAide.splitPreserveAllTokens("ab   de fg", null, 4) = ["ab", "", "", "de fg"]
     * </pre>
     *
     * @param str 源字符串
     * @param separatorChars 分隔符，{@code null} 被视为 {@link Character#isWhitespace(char)} 定义的空白符
     * @param max 包含在数组中的最大元素数，0 或 负值 表示无限制
     * @return 一个已分隔的字符串数组，如果输入的字符串为 null 则返回 {@code null}
     * @since 1.0.0
     */
    public static String[] splitPreserveAllTokens(final String str, final String separatorChars, final int max) {
        return splitWorker(str, separatorChars, max, true);
    }

    /**
     * <p>执行 {@code split} 和 {@code splitPreserveAllTokens} 返回指定最大长度数组的逻辑。</p>
     *
     * @param str 源字符串
     * @param separatorChars 分隔符，{@code null} 被视为 {@link Character#isWhitespace(char)} 定义的空白符
     * @param max 包含在数组中的最大元素数，0 或 负值 表示无限制
     * @param preserveAllTokens {@code true}：相邻的分隔符被视为空符号分隔符；{@code false}：相邻的分隔符被视为一个分隔符
     * @return 一个已分隔的字符串数组，如果输入的字符串为 null 则返回 {@code null}
     * @since 1.0.0
     */
    private static String[] splitWorker(final String str, final String separatorChars,
                                        final int max, final boolean preserveAllTokens) {
        if (str == null) {
            return null;
        }
        final int strLen = str.length();
        if (strLen == 0) {
            return new String[0];
        }
        final List<String> list = new ArrayList<>();
        int sizePlus1 = 1;
        int i = 0, start = 0;
        boolean match = false;
        boolean lastMath = false;

        while (i < strLen) {
            boolean separateConditions;
            if (separatorChars == null) { // 分隔符为 null 时意味着使用 空白符 分隔
                separateConditions = Character.isWhitespace(str.charAt(i));

            } else if (separatorChars.length() == 1) {// 只指定了一个分隔符时
                separateConditions = str.charAt(i) == separatorChars.charAt(0);

            } else { // 指定了多个分隔符时
                separateConditions = separatorChars.indexOf(str.charAt(i)) >= 0;
            }

            if (separateConditions) {
                if (match || preserveAllTokens) {
                    lastMath = true;
                    if (sizePlus1++ == max) {
                        i = strLen;
                        lastMath = false;
                    }
                    list.add(str.substring(start, i));
                    match = false;
                }
                start = ++i;
                continue;
            }
            lastMath = false;
            match = true;
            i++;
        }

        if (match || preserveAllTokens && lastMath) {
            list.add(str.substring(start, i));
        }
        return list.toArray(new String[0]);
    }

    /**
     * <p>
     * 按 {@link Character#getType(char)} 返回的字符类型拆分字符串。
     * 相同类型的连续字符将被分为一组。
     * </p>
     *
     * <pre>
     * StringAide.splitByCharacterType(null)         = null
     * StringAide.splitByCharacterType("")           = []
     * StringAide.splitByCharacterType("ab de fg")   = ["ab", " ", "de", " ", "fg"]
     * StringAide.splitByCharacterType("ab   de fg") = ["ab", "   ", "de", " ", "fg"]
     * StringAide.splitByCharacterType("ab:cd:ef")   = ["ab", ":", "cd", ":", "ef"]
     * StringAide.splitByCharacterType("number5")    = ["number", "5"]
     * StringAide.splitByCharacterType("fooBar")     = ["foo", "B", "ar"]
     * StringAide.splitByCharacterType("foo200Bar")  = ["foo", "200", "B", "ar"]
     * StringAide.splitByCharacterType("ASFRules")   = ["ASFR", "ules"]
     * </pre>
     *
     * @param string 要分割的字符串
     * @return 一个已分隔的字符串数组，如果输入的字符串为 null 则返回 {@code null}
     * @since 1.0.0
     */
    public static String[] splitByCharacterType(final String string) {
        return splitByCharacterType(string, false);
    }

    /**
     * <p>
     * 按 {@link Character#getType(char)} 返回的字符类型拆分字符串。
     * 相同类型的连续字符将被分为一组。但以下情况除外：
     * </p>
     * <p>
     * 在 {@link Character#LOWERCASE_LETTER } 类型字符前面的那一个的 {@link Character#UPPERCASE_LETTER} 字符
     * 将被划分给后面的组，而非前面的，
     * 如 "abcDEfg"，将被拆分成 [abc, D, Efg]，而非 [abc, DE, fg]。
     * </p>
     *
     * <pre>
     * StringAide.splitByCharacterTypeCamelCase(null)         = null
     * StringAide.splitByCharacterTypeCamelCase("")           = []
     * StringAide.splitByCharacterTypeCamelCase("ab de fg")   = ["ab", " ", "de", " ", "fg"]
     * StringAide.splitByCharacterTypeCamelCase("ab   de fg") = ["ab", "   ", "de", " ", "fg"]
     * StringAide.splitByCharacterTypeCamelCase("ab:cd:ef")   = ["ab", ":", "cd", ":", "ef"]
     * StringAide.splitByCharacterTypeCamelCase("number5")    = ["number", "5"]
     * StringAide.splitByCharacterTypeCamelCase("fooBar")     = ["foo", "Bar"]
     * StringAide.splitByCharacterTypeCamelCase("foo200Bar")  = ["foo", "200", "Bar"]
     * StringAide.splitByCharacterTypeCamelCase("ASFRules")   = ["ASF", "Rules"]
     * </pre>
     *
     * @param string 要分割的字符串
     * @return 一个已分割的字符串数组，如果输入的字符串为 null 则返回 {@code null}
     * @since 1.0.0
     */
    public static String[] splitByCharacterTypeCamelCase(final String string) {
        return splitByCharacterType(string, true);
    }

    /**
     * <p>
     * 按 {@link Character#getType(char)} 返回的字符类型拆分字符串。
     * 相同类型的连续字符将被分为一组。但以下情况除外：
     * </p>
     * <p>
     * 如果 {@code camelCase} 为 true，
     * 在 {@link Character#LOWERCASE_LETTER } 类型字符前面的那一个 {@link Character#UPPERCASE_LETTER} 字符
     * 将被划分给后面的组，而非前面的，
     * 如 "abcDEfg"，当 {@code camelCase} 为 true 时，将被拆分成 [abc, D, Efg]，而非 [abc, DE, fg]。
     * </p>
     *
     * @param str 要分割的字符串
     * @param camelCase 是否使用驼峰式
     * @return 一个已分割的字符串数组，如果输入的字符串为 null 则返回 {@code null}
     * @since 1.0.0
     */
    private static String[] splitByCharacterType(final String str, final boolean camelCase) {
        if (str == null) {
            return null;
        }
        if (str.isEmpty()) {
            return new String[0];
        }
        char[] chars = str.toCharArray();
        List<String> list = new ArrayList<>();
        int tokenStart = 0;
        int currentType = Character.getType(chars[tokenStart]);
        for (int pos = tokenStart + 1; pos < chars.length; pos++) {
            int type = Character.getType(chars[pos]);
            if (type == currentType) {
                continue;
            }
            if (camelCase && type == Character.LOWERCASE_LETTER && currentType == Character.UPPERCASE_LETTER) {
                int newTokenStart = pos - 1;
                if (newTokenStart != tokenStart) {
                    list.add(new String(chars, tokenStart, newTokenStart - tokenStart));
                    tokenStart = newTokenStart;
                }
            } else {
                list.add(new String(chars, tokenStart, pos - tokenStart));
                tokenStart = pos;
            }
            currentType = type;
        }
        list.add(new String(chars, tokenStart, chars.length - tokenStart));
        return list.toArray(new String[0]);
    }
    // ---------------------------------------------------------------------------------------------------
    // ----- Split ----- end

    // ----- Joining ----- start
    // ---------------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------------
    // ----- Joining ----- end
}
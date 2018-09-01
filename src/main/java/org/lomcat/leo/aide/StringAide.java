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

import java.text.Normalizer;
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



    // ----- Trim string ----- begin
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

    // ----- Strip string ----- begin
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
     * StringAide.stripLeft(null, *)          = null
     * StringAide.stripLeft("", *)            = ""
     * StringAide.stripLeft("abc", "")        = "abc"
     * StringAide.stripLeft("  abc", "")      = "  abc"
     * StringAide.stripLeft("abc", null)      = "abc"
     * StringAide.stripLeft("  abc", null)    = "abc"
     * StringAide.stripLeft("abc  ", null)    = "abc  "
     * StringAide.stripLeft(" abc ", null)    = "abc "
     * StringAide.stripLeft("yxabc  ", "xyz") = "abc  "
     * StringAide.stripLeft("yxabyc", "xyz")  = "abyc"
     * StringAide.stripLeft("  yxabc", "xyz") = "  yxabc"
     * </pre>
     *
     * @param string 源字符串，可以为 null
     * @param stripChars 要剥离的字符集，null 作为 {@link Character#isWhitespace(char)} 定义的空白符处理
     * @return 剥离指定字符后的字符串
     * @since 1.0.0
     */
    public static String stripLeft(final String string, final String stripChars) {
        int stringLength;
        if (string == null || (stringLength = string.length()) == 0) {
            return string;
        }
        int begin = 0;
        if (stripChars == null) {
            while (begin != stringLength && Character.isWhitespace(string.charAt(begin))) {
                begin++;
            }
        } else if (stripChars.isEmpty()) {
            return string;
        } else {
            while (begin != stringLength && stripChars.indexOf(string.charAt(begin)) != INDEX_NOT_FOUND) {
                begin++;
            }
        }
        return string.substring(begin);
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
     * StringAide.stripRight(null, *)          = null
     * StringAide.stripRight("", *)            = ""
     * StringAide.stripRight("abc", "")        = "abc"
     * StringAide.stripRight("abc  ", "")      = "abc  "
     * StringAide.stripRight("abc", null)      = "abc"
     * StringAide.stripRight("  abc", null)    = "  abc"
     * StringAide.stripRight("abc  ", null)    = "abc"
     * StringAide.stripRight(" abc ", null)    = " abc"
     * StringAide.stripRight("  abcyx", "xyz") = "  abc"
     * StringAide.stripRight("120.00", ".0")   = "12"
     * StringAide.stripRight("axbcyx", "xyz")  = "axbc"
     * StringAide.stripRight("abcyx  ", "xyz") = "abcyx  "
     * </pre>
     *
     * @param string 源字符串，可以为 null
     * @param stripChars 要剥离的字符集，null 作为 {@link Character#isWhitespace(char)} 定义的空白符处理
     * @return 剥离指定字符后的字符串
     * @since 1.0.0
     */
    public static String stripRight(final String string, final String stripChars) {
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
        return stripRight(stripLeft(string, stripChars), stripChars);
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

    // ----- Cull string ----- begin
    // ---------------------------------------------------------------------------------------------------
    /**
     * <p>从字符串中剔除指定的一组字符。</p>
     *
     * <p>注意：</p>
     * <ul>
     *     <li>当 {@code string} 为 null 时返回 {@code null}，空串时返回空串。</li>
     *     <li>当 {@code cullChars} 为 null 时，则按 {@link Character#isWhitespace(char) 的定义来剔除空白符。</li>
     *     <li>当 {@code cullChars} 为空串时，直接返回源字符串 {@code string}。</li>
     * </ul>
     *
     * <pre>
     * StringAide.cull(null, *)               = null
     * StringAide.cull("", *)                 = ""
     * StringAide.cull("  ab c", "")          = "  ab c"
     * StringAide.cull("abxcxdexfg  ", "xde") = "abcfg  "
     * StringAide.cull("abc", null)           = "abc"
     * StringAide.cull("  ab c  ", null)      = "abc"
     * </pre>
     *
     * @param string 源字符串，可以为 null
     * @param cullChars 要剔除的字符集，null 作为 {@link Character#isWhitespace(char) 定义的空白符处理
     * @return 剔除指定字符后的字符串
     * @since 1.0.0
     */
    public static String cull(final String string, final String cullChars) {
        int length;
        if (string == null || (length = string.length()) == 0) {
            return string;
        }
        if (cullChars == null) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                char c = string.charAt(i);
                if (!Character.isWhitespace(c)) {
                    builder.append(c);
                }
            }
            return builder.toString();
        } else if (cullChars.isEmpty()) {
            return string;
        } else {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                char c = string.charAt(i);
                if (cullChars.indexOf(c) == INDEX_NOT_FOUND) {
                    builder.append(c);
                }
            }
            return builder.toString();
        }
    }

    /**
     * <p>从字符串中剔除所有按 {@link Character#isWhitespace(char)} 定义的空白符。</p>
     *
     * <pre>
     * StringAide.cull(null)        = null
     * StringAide.cull("")          = ""
     * StringAide.cull("  ab c  ")  = "abc"
     * </pre>
     *
     * @param string 源字符串，可以为 null
     * @return 剔除所有空白符之后的字符串
     * @since 1.0.0
     */
    public static String cull(final String string) {
        return cull(string, null);
    }

    /**
     * <p>从字符串中剔除所有按 {@link Character#isWhitespace(char)} 定义的空白符。
     * 当结果为 null 或 空串 时，返回 {@code null}。</p>
     *
     * @param string 源字符串，可以为 null
     * @return 去掉所有空白符之后的字符串，或 {@code null}
     * @since 1.0.0
     */
    public static String cullToNull(String string) {
        if (isEmpty(string)) {
            return null;
        }
        string = cull(string);
        return string.isEmpty() ? null : string;
    }

    /**
     * <p>从字符串中剔除所有按 {@link Character#isWhitespace(char)} 定义的空白符。
     * 当结果为 null 或 空串 时，返回 空串。</p>
     *
     * @param string 源字符串，可以为 null
     * @return 去掉所有空白符之后的字符串，或 空串
     * @since 1.0.0
     */
    public static String cullToEmpty(String string) {
        return string == null ? EMPTY : cull(string);
    }
    // ---------------------------------------------------------------------------------------------------
    // ----- Cull string ----- end

    // ----- Truncate string ----- begin
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

    // ----- Compare string ----- begin
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

    // ----- Substring ----- begin
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
     * @param begin 起始位置，负数意味着从字符串末尾倒数这么多个字符
     * @return 起始范围内的子串，若源字符串为 null 则返回 {@code null}
     * @since 1.0.0
     */
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

    /**
     * <p>从指定字符串中获取子串。</p>
     *
     * <p>返回的子串以 {@code begin} 位置的字符开头，在 {@code end} 之前结束。
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
     * @param begin 起始位置，负数意味着从字符串末尾倒数这么多个字符
     * @param end 结束位置，负数意味着从字符串末尾倒数这么多个字符
     * @return 起始范围内的子串，若源字符串为 null 则返回 {@code null}
     * @since 1.0.0
     */
    public static String substring(final String string, int begin, int end) {
        if (string == null) {
            return null;
        }

        if (end < 0) {
            end = string.length() + end;
        }
        if (begin < 0) {
            begin = string.length() + begin;
        }

        if (end > string.length()) {
            end = string.length();
        }

        if (begin > end) {
            return EMPTY;
        }

        if (begin < 0) {
            begin = 0;
        }
        if (end < 0) {
            end = 0;
        }

        return string.substring(begin, end);
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
    // ---------------------------------------------------------------------------------------------------
    // ----- Substring ----- begin
}
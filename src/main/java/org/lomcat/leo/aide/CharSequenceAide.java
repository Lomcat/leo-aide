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
 * @since 1.0.0
 */
public class CharSequenceAide {

    public static final int INDEX_NOT_FOUND = -1;

    /**
     * <p>获取字符序列的长度，若字符序列为 {@code null} 则返回 0。</p>
     *
     * @param sequence 字符序列，可以为 {@code null}
     * @return 字符序列长度或0
     * @since 1.0.0
     */
    public static int length(final CharSequence sequence) {
        return sequence == null ? 0 : sequence.length();
    }

    /**
     * <p>将字符序列装换为 char 数组。</p>
     *
     * @param sequence 要处理的字符序列，当为 null 时返回 null
     * @return char 数组
     * @since 1.0.0
     */
    public static char[] toCharArray(final CharSequence sequence) {
        if (sequence == null) {
            return null;
        }
        if (sequence instanceof String) {
            return ((String) sequence).toCharArray();
        }
        final int length = sequence.length();
        final char[] chars = new char[length];
        for (int i = 0; i < length; i++) {
            chars[i] = sequence.charAt(i);
        }
        return chars;
    }

    /**
     * <p>检查字符序列中是否包含指定字符。</p>
     *
     * <p>{@code sequence} 为 null 或 empty 时返回 {@code false}。</p>
     *
     * <pre>
     * CharSequenceAide.contains(null, *)    = false
     * CharSequenceAide.contains("", *)      = false
     * CharSequenceAide.contains("abc", 'a') = true
     * CharSequenceAide.contains("abc", 'z') = false
     * </pre>
     *
     * @param sequence 字符序列，可以为 null
     * @param searchChar 要查找的字符
     * @return true：{@code sequence} 中包含了 {@code searchChar}，
     *          false：不包含
     * @since 1.0.0
     */
    public static boolean contains(final CharSequence sequence, final int searchChar) {
        if (isEmpty(sequence)) {
            return false;
        }
        return indexOf(sequence, searchChar) >= 0;
    }

    /**
     * <p>检查字符序列中是否包含指定字符序列。</p>
     *
     * <p>{@code sequence} 或 {@code searchSequence} 为 null 时返回 {@code false}。</p>
     *
     * <pre>
     * CharSequenceAide.contains(null, *)     = false
     * CharSequenceAide.contains(*, null)     = false
     * CharSequenceAide.contains("", "")      = true
     * CharSequenceAide.contains("abc", "")   = true
     * CharSequenceAide.contains("abc", "a")  = true
     * CharSequenceAide.contains("abc", "z")  = false
     * </pre>
     *
     * @param sequence 字符序列，可以为 null
     * @param searchSequence 要查找的字符序列
     * @return true：{@code sequence} 中包含了 {@code searchSequence}，
     *          false：不包含
     * @since 1.0.0
     */
    public static boolean contains(final CharSequence sequence, final CharSequence searchSequence) {
        if (sequence == null || searchSequence == null) {
            return false;
        }
        return indexOf(sequence, searchSequence) >= 0;
    }

    /**
     * <p>检查字符序列中是否包含指定字符序列，忽略大小写。</p>
     *
     * <p>{@code sequence} 或 {@code searchSequence} 为 null 时返回 {@code false}。</p>
     *
     * <pre>
     * CharSequenceAide.containsIgnoreCase(null, *) = false
     * CharSequenceAide.containsIgnoreCase(*, null) = false
     * CharSequenceAide.containsIgnoreCase("", "") = true
     * CharSequenceAide.containsIgnoreCase("abc", "") = true
     * CharSequenceAide.containsIgnoreCase("abc", "a") = true
     * CharSequenceAide.containsIgnoreCase("abc", "z") = false
     * CharSequenceAide.containsIgnoreCase("abc", "A") = true
     * CharSequenceAide.containsIgnoreCase("abc", "Z") = false
     * </pre>
     *
     * @param sequence 字符序列，可以为 null
     * @param searchSequence 要查找的字符序列
     * @return true：{@code sequence} 中包含了 {@code searchSequence}，
     *          false：不包含
     * @since 1.0.0
     */
    public static boolean containsIgnoreCase(final CharSequence sequence, final CharSequence searchSequence) {
        if (sequence == null || searchSequence == null) {
            return false;
        }
        final int searchLength = searchSequence.length();
        final int max = sequence.length() - searchLength;
        for (int i = 0; i < max; i++) {
            if (regionMatches(true, sequence, i, searchSequence, 0, searchLength)) {
                return true;
            }
        }
        return false;
    }

    /**
     * <p>检查字符序列中是否包含空白符。</p>
     *
     * <p>空白符由 {@link Character#isWhitespace(char)} 定义。</p>
     *
     * @param sequence 字符序列
     * @return 是否包含
     * @since 1.0.0
     */
    public static boolean containsWhitespace(final CharSequence sequence) {
        int length;
        if ((length = length(sequence)) == 0) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (Character.isWhitespace(sequence.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    // ----- Checking ----- begin
    // ---------------------------------------------------------------------------------------------------
    /**
     * <p>检查多个字符序列中是否存在 null。</p>
     *
     * <p>注意：当 {@code sequences} 为 null 或 empty 时，返回 @{code false}。</p>
     *
     * @param sequences 字符序列数组
     * @return 存在 null 则返回 {@code true}，不存在 null 则返回 {@code false}
     * @since 1.0.0
     */
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

    /**
     * <p>检查多个字符序列是否不包含 null。</p>
     *
     * <p>注意：当 {@code sequences} 为 null 或 empty 时，返回 @{code true}。</p>
     *
     * @param sequences 字符序列数组
     * @return 不包含 null 则返回 {@code true}，包含 null 则返回 {@code false}
     * @since 1.0.0
     */
    public static boolean isNoneNull(final CharSequence... sequences) {
        return !isAnyNull(sequences);
    }

    /**
     * <p>检查多个字符序列是否全部为 null。</p>
     *
     * @param sequences 字符序列数组
     * @return 全部都是 null 时返回 {@code true}，否则 {@code false}
     * @since 1.0.0
     */
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

    /**
     * <p>检查字符序列是否为 {@code null} 或 空串。</p>
     *
     * <pre>
     * CharSequenceAide.isEmpty(null)      = true
     * CharSequenceAide.isEmpty("")        = true
     * CharSequenceAide.isEmpty(" ")       = false
     * CharSequenceAide.isEmpty("bob")     = false
     * CharSequenceAide.isEmpty("  bob  ") = false
     * </pre>
     *
     * @param sequence 一个字符序列
     * @return 当 {@code sequence} 为 为 {@code null} 或 空串 时返回 {@code true}， 否则 {@code false}
     * @since 1.0.0
     */
    public static boolean isEmpty(final CharSequence sequence) {
        return sequence == null || sequence.length() == 0;
    }

    /**
     * <p>检查字符序列是否 non-null 且 非空串。</p>
     *
     * <pre>
     * CharSequenceAide.isNotEmpty(null)      = false
     * CharSequenceAide.isNotEmpty("")        = false
     * CharSequenceAide.isNotEmpty(" ")       = true
     * CharSequenceAide.isNotEmpty("bob")     = true
     * CharSequenceAide.isNotEmpty("  bob  ") = true
     * </pre>
     *
     * @param sequence 一个字符序列
     * @return non-null 且 非空串 时返回 {@code false}，否则 {@code true}
     * @since 1.0.0
     */
    public static boolean isNotEmpty(final CharSequence sequence) {
        return !isEmpty(sequence);
    }

    /**
     * <p>检查多个字符序列中是否包含 null 或 空串。</p>
     *
     * <p>注意：当 {@code sequences} 为 null 或 empty 时，返回 @{code false}。</p>
     *
     * @param sequences 字符序列数组
     * @return 包含 null 或 空串 时返回 {@code true}，否则 {@code false}
     * @since 1.0.0
     */
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

    /**
     * <p>检查多个字符序列中是否不包含 null 和 空串。</p>
     *
     * <p>注意：当 {@code sequences} 为 null 或 empty 时，返回 @{code true}。</p>
     *
     * @param sequences 字符序列数组
     * @return 不包含 null 和 空串 时返回 {@code true}，否则 {@code false}
     * @since 1.0.0
     */
    public static boolean isNoneEmpty(final CharSequence... sequences) {
        return !isAnyEmpty(sequences);
    }

    /**
     * <p>检查多个字符序列是否全都是 null 或 空串。</p>
     *
     * <p>注意：当 {@code sequences} 为 null 或 empty 时，返回 @{code true}。</p>
     *
     * @param sequences 字符序列数组
     * @return 全都是 null 或 空串 时返回 {@code true}，否则 {@code false}
     * @since 1.0.0
     */
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

    /**
     * <p>检查字符序列是否 null 或 空白。</p>
     *
     * <pre>
     * CharSequenceAide.isBlank("")     = true
     * CharSequenceAide.isBlank("\t")   = true
     * CharSequenceAide.isBlank(" ")    = true
     * CharSequenceAide.isBlank("\n")   = true
     * CharSequenceAide.isBlank("\r")   = true
     * CharSequenceAide.isBlank("a")    = false
     * CharSequenceAide.isBlank(null)   = true
     * </pre>
     *
     * @param sequence 字符序列
     * @return 字符序列为 null 或 空白 时返回 {@code true}，否则 {@code false}
     * @since 1.0.0
     */
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

    /**
     * <p>检查字符序列是否 non-null 且 非空白。</p>
     *
     * @param sequence 字符序列
     * @return 字符序列 non-null 且 非空白 时返回 {@code true}，否则 {@code false}
     * @since 1.0.0
     */
    public static boolean isNotBlank(final CharSequence sequence) {
        return !isBlank(sequence);
    }

    /**
     * <p>检查多个字符序列中是否包含 null 或 空白。</p>
     *
     * <p>注意：当 {@code sequences} 为 null 或 empty 时，返回 @{code false}。</p>
     *
     * @param sequences 字符序列数组
     * @return 包含 null 或 空白 时返回 {@code true}，否则 {@code false}
     * @since 1.0.0
     */
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

    /**
     * <p>检查多个字符序列中是否不包含 null 和 空白。</p>
     *
     * <p>注意：当 {@code sequences} 为 null 或 empty 时，返回 @{code true}。</p>
     *
     * @param sequences 字符序列数组
     * @return 不包含 null 和 空白 时返回 {@code true}，否则 {@code false}
     * @since 1.0.0
     */
    public static boolean isNoneBlank(final CharSequence... sequences) {
        return !isAnyBlank(sequences);
    }

    /**
     * <p>检查多个字符序列是否全都是 null 或 空白。</p>
     *
     * <p>注意：当 {@code sequences} 为 null 或 empty 时，返回 @{code true}。</p>
     *
     * @param sequences 字符序列数组
     * @return 全都是 null 或 空白 时返回 {@code true}，否则 {@code false}
     * @since 1.0.0
     */
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
    // ---------------------------------------------------------------------------------------------------
    // ----- Checking ----- end

    /**
     * <p>从数组中获取第一个 non-null 的字符序列。</p>
     *
     * <p>如果数组为 null 或 empty 或数组中没有 non-null 的元素，则返回 {@code null}。</p>
     *
     * @param sequences 字符序列数组
     * @param <T> 数组元素的类型，{@link CharSequence} 的子类
     * @return 第一个 non-null 的字符序列，或 {@code null}
     * @since 1.0.0
     */
    @SafeVarargs
    public static <T extends CharSequence> T firstNonNull(final T... sequences) {
        if (ArrayAide.isNotEmpty(sequences)) {
            for (final T sequence : sequences) {
                if (sequence != null) {
                    return sequence;
                }
            }
        }
        return null;
    }

    /**
     * <p>返回数组中第一个 non-empty 的字符序列。</p>
     *
     * <p>如果数组为 null 或 empty 或数组中没有 non-empty 字符串，则返回 {@code null}。</p>
     *
     * <pre>
     * CharSequenceAide.firstNonEmpty(null, null)      = null
     * CharSequenceAide.firstNonEmpty(null, "")        = null
     * CharSequenceAide.firstNonEmpty(null, null, "")  = null
     * CharSequenceAide.firstNonEmpty(null, "zz")      = "zz"
     * CharSequenceAide.firstNonEmpty("abc", *)        = "abc"
     * CharSequenceAide.firstNonEmpty("", "xyz", *)    = "xyz"
     * CharSequenceAide.firstNonEmpty()                = null
     * </pre>
     *
     * @param sequences 字符序列数组
     * @param <T> 数组元素的类型，{@link CharSequence} 的子类
     * @return 第一个 non-empty 的字符序列，或 {@code null}
     * @since 1.0.0
     */
    @SafeVarargs
    public static <T extends CharSequence> T firstNonEmpty(final T... sequences) {
        if (ArrayAide.isNotEmpty(sequences)) {
            for (final T cs : sequences) {
                if (isNotEmpty(cs)) {
                    return cs;
                }
            }
        }
        return null;
    }

    /**
     * <p>返回数组中第一个 non-blank 的字符串</p>
     *
     * <p>如果数组为 null 或 empty 或数组中没有 non-blank 字符串，则返回 {@code null}。</p>
     *
     * <pre>
     * CharSequenceAide.firstNonEmpty(null, null)      = null
     * CharSequenceAide.firstNonEmpty(null, "")        = null
     * CharSequenceAide.firstNonEmpty(null, null, "")  = null
     * CharSequenceAide.firstNonEmpty(null, "zz")      = "zz"
     * CharSequenceAide.firstNonEmpty("abc", *)        = "abc"
     * CharSequenceAide.firstNonEmpty("", "xyz", *)    = "xyz"
     * CharSequenceAide.firstNonEmpty()                = null
     * </pre>
     *
     * @param sequences 字符序列数组
     * @param <T> 数组元素的类型，{@link CharSequence} 的子类
     * @return 第一个 non-blank 的字符序列，或 {@code null}
     * @since 1.0.0
     */
    @SafeVarargs
    public static <T extends CharSequence> T firstNonBlank(final T... sequences) {
        if (ArrayAide.isNotEmpty(sequences)) {
            for (final T sequence : sequences) {
                if (isNotBlank(sequence)) {
                    return sequence;
                }
            }
        }
        return null;
    }

    // ----- Sub sequence ----- begin
    // ---------------------------------------------------------------------------------------------------
    /**
     * <p>从字符序列中截取指定范围的子序列</p>
     *
     * @param sequence 源字符序列，可以为 null
     * @param begin 起始位置
     * @param end 结束位置
     * @return 子序列
     * @since 1.0.0
     */
    public static CharSequence subSequence(final CharSequence sequence, int begin, int end) {
        return sequence == null ? null : sequence.subSequence(begin, end);
    }

    /**
     * <p>从字符序列中截取指定位置后的子序列</p>
     *
     * @param sequence 源字符序列，可以为 null
     * @param begin 起始位置
     * @return 子序列
     * @since 1.0.0
     */
    public static CharSequence subSequence(final CharSequence sequence, final int begin) {
        return subSequence(sequence, begin, sequence.length());
    }
    // ---------------------------------------------------------------------------------------------------
    // ----- Sub sequence ----- end

    // ----- Equal ----- begin
    // ---------------------------------------------------------------------------------------------------
    /**
     * <p>比较两个字符序列的相等性。</p>
     *
     * <pre>
     * CharSequenceAide.equals(null, null)   = true
     * CharSequenceAide.equals(null, "abc")  = false
     * CharSequenceAide.equals("abc", null)  = false
     * CharSequenceAide.equals("abc", "abc") = true
     * CharSequenceAide.equals("abc", "ABC") = false
     * </pre>
     *
     * @param sequence1 第一个字符序列
     * @param sequence2 第二个字符序列
     * @return 如果两个字符序列相等，则返回 {@code true}；否则返回 {@code false}。
     * @since 1.0.0
     */
    public static boolean equals(final CharSequence sequence1, final CharSequence sequence2) {
        if (sequence1 == sequence2) {
            return true;
        }
        if (sequence1 == null || sequence2 == null) {
            return false;
        }
        if (sequence1.length() != sequence2.length()) {
            return false;
        }
        if (sequence1 instanceof String && sequence2 instanceof String) {
            return sequence1.equals(sequence2);
        }
        return regionMatches(false, sequence1, 0, sequence2, 0, sequence1.length());
    }

    /**
     * <p>比较两个字符序列的相等性，忽略大小写。</p>
     *
     * <pre>
     * CharSequenceAide.equalsIgnoreCase(null, null)   = true
     * CharSequenceAide.equalsIgnoreCase(null, "abc")  = false
     * CharSequenceAide.equalsIgnoreCase("abc", null)  = false
     * CharSequenceAide.equalsIgnoreCase("abc", "abc") = true
     * CharSequenceAide.equalsIgnoreCase("abc", "ABC") = true
     * </pre>
     *
     * @param sequence1 第一个字符序列
     * @param sequence2 第二个字符序列
     * @return 如果两个字符序列相等（忽略大小写），则返回 {@code true}；否则返回 {@code false}。
     * @since 1.0.0
     */
    public static boolean equalsIgnoreCase(final CharSequence sequence1, final CharSequence sequence2) {
        if (sequence1 == sequence2) {
            return true;
        }
        if (sequence1 == null || sequence2 == null) {
            return false;
        }
        if (sequence1.length() != sequence2.length()) {
            return false;
        }
        return regionMatches(true, sequence1, 0, sequence2, 0, sequence1.length());
    }

    /**
     * <p>检查指定字符序列是否与数组中的任意元素相等。</p>
     *
     * <pre>
     * CharSequenceAide.equalsAny(null, null)                   = false
     * CharSequenceAide.equalsAny(null, null, null)             = true
     * CharSequenceAide.equalsAny("", new String[0])            = false
     * CharSequenceAide.equalsAny(null, "abc", "def")           = false
     * CharSequenceAide.equalsAny("abc", null, "def")           = false
     * CharSequenceAide.equalsAny("abc", "abc", "def")          = true
     * CharSequenceAide.equalsAny("abc", "ABC", "DEF")          = false
     * </pre>
     *
     * @param sequence 字符序列
     * @param sequences 字符序列数组
     * @return 若 {@code cs} 与 {@code css} 中的任意一个元素相等，则返回 true， 否则 false
     */
    public static boolean equalsAny(final CharSequence sequence, final CharSequence... sequences) {
        if (ArrayAide.isNotEmpty(sequences)) {
            for (CharSequence element : sequences) {
                if (equals(sequence, element)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * <p>检查指定字符序列是否与数组中的任意元素相等（忽略大小写）。</p>
     *
     * <pre>
     * CharSequenceAide.equalsAnyIgnoreCase(null, null)                     = false
     * CharSequenceAide.equalsAnyIgnoreCase(null, null, null)               = true
     * CharSequenceAide.equalsAnyIgnoreCase("", new String[0])              = false
     * CharSequenceAide.equalsAnyIgnoreCase(null, "abc", "def")             = false
     * CharSequenceAide.equalsAnyIgnoreCase("abc", null, "def")             = false
     * CharSequenceAide.equalsAnyIgnoreCase("abc", "abc", "def")            = true
     * CharSequenceAide.equalsAnyIgnoreCase("abc", "ABC", "DEF")            = true
     * </pre>
     *
     * @param sequence 字符序列
     * @param sequences 字符序列数组
     * @return 若 {@code cs} 与 {@code css} 中的任意一个元素相等（忽略大小写），则返回 true， 否则 false
     */
    public static boolean equalsAnyIgnoreCase(final CharSequence sequence, final CharSequence... sequences) {
        if (ArrayAide.isNotEmpty(sequences)) {
            for (CharSequence next : sequences) {
                if (equalsIgnoreCase(sequence, next)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * <p>比较两个字符序列 {@code sequence1} 和 {@code sequence2} 指定范围，如果表示相同的字符序列，则结果为 {@code true}。</p>
     *
     * <p>当 {@code ignoreCase} 为 {@code true} 时忽略大小写。</p>
     *
     * <p>{@code sequence1} 从索引 {@code offset1} 处开始，长度为 {@code length}，
     * {@code sequence2} 从索引 {@code offset2} 处开始，长度为 {@code length}。</p>
     *
     * <p>当 {@code offset1} 、 {@code offset2} 、 {@code length} 任一为负时，结果为 {@code false}。</p>
     *
     * <p>当 {@code offset1 + length} 大于 {@code sequence1} 的长度时，结果为 {@code false}。</p>
     *
     * <p>当 {@code offset2 + length} 大于 {@code sequence2} 的长度时，结果为 {@code false}。</p>
     *
     * @param ignoreCase 是否忽略大小写，true 忽略，false 不忽略
     * @param sequence1 要比较的第一个字符序列
     * @param offset1 第一个字符序列的开始索引
     * @param sequence2 要比较的第二个字符序列
     * @param offset2 第二个字符序列的开始索引
     * @param length 要比较的区域长度
     * @return 如果两个字符序列的指定范围相匹配，则返回 {@code true}，否则返回 {@code false}。
     */
    public static boolean regionMatches(final boolean ignoreCase,
                                        final CharSequence sequence1, final int offset1,
                                        final CharSequence sequence2, final int offset2,
                                        final int length) {
        if (sequence1 instanceof String && sequence2 instanceof String) {
            return ((String) sequence1).regionMatches(ignoreCase, offset1, (String) sequence2, offset2, length);
        }

        if (offset1 < 0 || offset2 < 0 || length < 0) {
            return false;
        }

        int index1 = offset1;
        int index2 = offset2;
        int tmpLength = length;

        final int length1 = sequence1.length() - offset1;
        final int length2 = sequence2.length() - offset2;

        if (length1 < length || length2 < length) {
            return false;
        }

        while (tmpLength-- > 0) {
            final char c1 = sequence1.charAt(index1++);
            final char c2 = sequence2.charAt(index2++);

            if (c1 == c2) {
                continue;
            }

            if (!ignoreCase) {
                return false;
            }

            if (Character.toUpperCase(c1) != Character.toUpperCase(c2)
                    && Character.toLowerCase(c1) != Character.toLowerCase(c2)) {
                return false;
            }
        }

        return true;
    }
    // ---------------------------------------------------------------------------------------------------
    // ----- Equal ----- end

    // ----- Index of ----- begin
    // ---------------------------------------------------------------------------------------------------
    /**
     * <p>查找指定字符 {@code searchChar} 在字符序列 {@code sequence} 从指定位置 {@code fromIndex} 之后首次出现的索引。
     * 如果字符序列为 null 或 empty，则返回 {@link #INDEX_NOT_FOUND} (-1)。
     * 如果 {@code fromIndex} 超出字符序列长度（>= sequence.length），则返回 -1。</p>
     *
     * @param sequence 字符序列
     * @param searchChar 要查找的字符
     * @param fromIndex 起始位置，小于 0 时从 0 开始，超出长度返回 -1
     * @return 索引值，可能为 -1
     * @since 1.0.0
     */
    public static int indexOf(final CharSequence sequence, final int searchChar, int fromIndex) {
        if (isEmpty(sequence)) {
            return INDEX_NOT_FOUND;
        }

        final int length = sequence.length();
        if (fromIndex >= length) {
            return INDEX_NOT_FOUND;
        }
        if (fromIndex < 0) {
            fromIndex = 0;
        }

        if (sequence instanceof String) {
            return ((String) sequence).indexOf(searchChar, fromIndex);
        }

        // 若 searchChar 为BMP（基本多语言面，从 U+0000 至 U+FFFF 之间），一个代码单元（16bits）表示一个字符
        if (searchChar < Character.MIN_SUPPLEMENTARY_CODE_POINT) {
            for (int i = fromIndex; i < length; i++) {
                if (sequence.charAt(i) == searchChar) {
                    return i;
                }
            }
        }

        // 若 searchChar 为增补字符（0x010000 至 0X10FFFF 之间），需两个代码单元（两个16bits）表示一个字符
        if (searchChar <= Character.MAX_CODE_POINT) {
            final char[] chars = Character.toChars(searchChar);
            for (int i = fromIndex; i < length - 1; i ++) {
                char high = sequence.charAt(i);
                char low = sequence.charAt(i + 1);
                if (high == chars[0] && low == chars[1]) {
                    return i;
                }
            }
        }

        return INDEX_NOT_FOUND;
    }

    /**
     * <p>查找指定字符在字符序列中首次出现的索引。
     * 如果字符序列为 null 或 empty，则返回 {@link #INDEX_NOT_FOUND} (-1)。</p>
     *
     * <pre>
     * CharSequenceAide.indexOf(null, *)         = -1
     * CharSequenceAide.indexOf("", *)           = -1
     * CharSequenceAide.indexOf("aabaabaa", 'a') = 0
     * CharSequenceAide.indexOf("aabaabaa", 'b') = 2
     * </pre>
     *
     * @param sequence 字符序列
     * @param searchChar 要查找的字符
     * @return {@code searchChar} 在 {@code sequence} 中的第一个索引，
     *          若 {@code sequence} 为 null 返回 -1，
     *          若 {@code sequence} 不包含 {@code searchChar} 返回 -1
     * @since 1.0.0
     */
    public static int indexOf(final CharSequence sequence, final int searchChar) {
        return isEmpty(sequence) ? INDEX_NOT_FOUND : indexOf(sequence, searchChar, 0);
    }

    /**
     * <p>返回指定字符最后一次出现在字符序列中的索引，从指定索引 {@code lastIndex} 开始向左搜索。
     * 如果字符序列为 null 或 empty，返回 -1。
     * 如果 {@code lastIndex} 小于 0，则返回 -1。<</p>
     *
     * @param sequence 字符序列
     * @param searchChar 要查找的字符
     * @param lastIndex 最后的索引位置，小于 0 时 返回 -1，超出长度时从 length - 1 开始
     * @return 索引值
     * @since 1.0.0
     */
    public static int lastIndexOf(final CharSequence sequence, final int searchChar, int lastIndex) {
        if (isEmpty(sequence)) {
            return INDEX_NOT_FOUND;
        }

        if (sequence instanceof String) {
            return ((String) sequence).lastIndexOf(searchChar, lastIndex);
        }

        if (lastIndex < 0) {
            return INDEX_NOT_FOUND;
        }

        final int length = sequence.length();
        if (lastIndex >= length) {
            lastIndex = length - 1;
        }

        // 若 searchChar 为BMP（基本多语言面，从 U+0000 至 U+FFFF 之间），一个代码单元（16bits）表示一个字符
        if (searchChar < Character.MIN_SUPPLEMENTARY_CODE_POINT) {
            for (int i = lastIndex; i >= 0; --i) {
                if (sequence.charAt(i) == searchChar) {
                    return i;
                }
            }
        }

        // 若 searchChar 为增补字符（0x010000 至 0X10FFFF 之间），需两个代码单元（两个16bits）表示一个字符
        if (searchChar <= Character.MAX_CODE_POINT) {
            final char[] chars = Character.toChars(searchChar);
            for (int i = lastIndex; i >= 0; i--) {
                final char high = sequence.charAt(i);
                final char low = sequence.charAt(i + 1);
                if (chars[0] == high && chars[1] == low) {
                    return i;
                }
            }
        }

        return INDEX_NOT_FOUND;
    }

    /**
     * <p>查找指定字符在字符序列中最后出现的索引。
     * 如果字符序列为 null 或 empty，则返回 {@link #INDEX_NOT_FOUND} (-1)。</p>
     *
     * @param sequence 字符序列
     * @param searchChar 要查找的字符
     * @return 索引值
     * @since 1.0.0
     */
    public static int lastIndexOf(final CharSequence sequence, final int searchChar) {
        return isEmpty(sequence) ? INDEX_NOT_FOUND : lastIndexOf(sequence, searchChar, sequence.length());
    }

    /**
     * <p>在字符序列中查找指定字符序列从指定位置开始往后第一次出现的索引。
     * 当 {@code sequence} 或 {@code searchSequence} 为 null 时返回 -1。</p>
     *
     * @param sequence 字符序列，为 null 时返回 -1
     * @param searchSequence 要查找的字符序列，null 时返回 -1
     * @param fromIndex 起始位置，小于 0 时从 0 开始，超出长度返回 -1
     * @return 索引值
     * @since 1.0.0
     */
    public static int indexOf(final CharSequence sequence, final CharSequence searchSequence, int fromIndex) {
        if (sequence == null || searchSequence == null) {
            return INDEX_NOT_FOUND;
        }
        if (fromIndex < 0) {
            fromIndex = 0;
        }
        if (fromIndex >= sequence.length()) {
            return INDEX_NOT_FOUND;
        }
        return sequence.toString().indexOf(searchSequence.toString(), fromIndex);
    }

    /**
     * <p>在字符序列中查找指定字符序列第一次出现的索引。
     * 当 {@code sequence} 或 {@code searchSequence} 为 null 时返回 -1。</p>
     *
     * @param sequence 字符序列，为 null 时返回 -1
     * @param searchSequence 要查找的字符序列，null 时返回 -1
     * @return 索引值
     * @since 1.0.0
     */
    public static int indexOf(final CharSequence sequence, final CharSequence searchSequence) {
        if (sequence == null || searchSequence == null) {
            return INDEX_NOT_FOUND;
        }
        return indexOf(sequence, searchSequence, 0);
    }

    /**
     * <p>返回指定字符序列最后一次出现在字符序列中的索引，从指定索引 {@code lastIndex} 开始向左搜索。
     * 当 {@code sequence} 或 {@code searchSequence} 为 null 时返回 -1。
     * 如果 {@code lastIndex} 小于 0，则返回 -1。</p>
     *
     * @param sequence 字符序列
     * @param searchSequence 要查找的字符序列
     * @param lastIndex 最后的索引位置，小于 0 时 返回 -1
     * @return 索引值
     * @since 1.0.0
     */
    public static int lastIndexOf(final CharSequence sequence, final CharSequence searchSequence, final int lastIndex) {
        if (sequence == null || searchSequence == null) {
            return INDEX_NOT_FOUND;
        }
        if (lastIndex < 0) {
            return INDEX_NOT_FOUND;
        }
        return sequence.toString().lastIndexOf(searchSequence.toString(), lastIndex);
    }

    /**
     * <p>返回指定字符序列最后一次出现在字符序列中的索引。
     * 当 {@code sequence} 或 {@code searchSequence} 为 null 时返回 -1。</p>
     *
     * @param sequence 字符序列
     * @param searchSequence 要查找的字符序列
     * @return 索引值
     * @since 1.0.0
     */
    public static int lastIndexOf(final CharSequence sequence, final CharSequence searchSequence) {
        if (sequence == null || searchSequence == null) {
            return INDEX_NOT_FOUND;
        }
        return lastIndexOf(sequence, searchSequence, sequence.length());
    }

    /**
     * <p>查找 {@code searchSequence} 在 {@code sequence} 中第 {@code ordinal} 次出现的索引。
     * 查找方向根据 {@code lastIndex} 确定。</p>
     *
     * @param sequence 字符序列
     * @param searchSequence 要查找的字符序列
     * @param ordinal 字符序列出现的次序
     * @param lastIndex true: {@link #ordinalIndexOf(CharSequence, CharSequence, int)};
     *                  false: {@link #lastOrdinalIndexOf(CharSequence, CharSequence, int)}
     * @return 索引值
     * @since 1.0.0
     */
    public static int ordinalIndexOf(final CharSequence sequence, final CharSequence searchSequence, final int ordinal, final boolean lastIndex) {
        if (sequence == null || searchSequence == null || ordinal <= 0) {
            return INDEX_NOT_FOUND;
        }
        if (searchSequence.length() == 0) {
            return lastIndex ? sequence.length() : 0;
        }
        int foundCount = 0;
        int index = lastIndex ? sequence.length() : INDEX_NOT_FOUND;
        do {
            if (lastIndex) {
                index = lastIndexOf(sequence, searchSequence, index - 1);
            } else {
                index = indexOf(sequence, searchSequence, index + 1);
            }
            if (index < 0) {
                return index;
            }
            foundCount ++;
        } while (foundCount < ordinal);
        return index;
    }

    /**
     * <p>查找 {@code searchSequence} 在 {@code sequence} 中第 {@code ordinal} 次出现的索引。
     * 从 {@code sequence} 的开始位置向后查找。</p>
     *
     * @param sequence 字符序列
     * @param searchSequence 要查找的字符序列
     * @param ordinal 字符序列出现的次序
     * @return 索引值
     * @since 1.0.0
     */
    public static int ordinalIndexOf(final CharSequence sequence, final CharSequence searchSequence, final int ordinal) {
        return ordinalIndexOf(sequence, searchSequence, ordinal, false);
    }

    /**
     * <p>查找 {@code searchSequence} 在 {@code sequence} 中第 {@code ordinal} 次出现的索引。
     * 从 {@code sequence} 的最后位置向前查找。</p>
     *
     * @param sequence 字符序列
     * @param searchSequence 要查找的字符序列
     * @param ordinal 字符序列出现的次序
     * @return 索引值
     * @since 1.0.0
     */
    public static int lastOrdinalIndexOf(final CharSequence sequence, final CharSequence searchSequence, final int ordinal) {
        return ordinalIndexOf(sequence, searchSequence, ordinal, true);
    }

    /**
     * <p>查找 {@code searchChar} 在 {@code sequence} 中第 {@code ordinal} 次出现的索引。
     * 从 {@code sequence} 的开始位置向后查找。</p>
     *
     * @param sequence 字符序列
     * @param searchChar 要查找的字符
     * @param ordinal 字符序列出现的次序
     * @return 索引值
     * @since 1.0.0
     */
    public static int ordinalIndexOf(final CharSequence sequence, final int searchChar, final int ordinal) {
        return ordinalIndexOf(sequence, String.valueOf((char) searchChar), ordinal, false);
    }

    /**
     * <p>查找 {@code searchChar} 在 {@code sequence} 中第 {@code ordinal} 次出现的索引。
     * 从 {@code sequence} 的最后位置向前查找。</p>
     *
     * @param sequence 字符序列
     * @param searchChar 要查找的字符
     * @param ordinal 字符序列出现的次序
     * @return 索引值
     * @since 1.0.0
     */
    public static int lastOrdinalIndexOf(final CharSequence sequence, final int searchChar, final int ordinal) {
        return ordinalIndexOf(sequence, String.valueOf((char) searchChar), ordinal, true);
    }
    /**
     * <p>查找 {@code searchSequence} 在 {@code sequence} 中从 {@code fromIndex} 开始第一次出现的索引（忽略大小写）。</p>
     *
     * <p>注意：</p>
     * <ul>
     *     <li>{@code sequence} 或 {@code searchSequence} 为 null 时返回 -1</li>
     *     <li>{@code fromIndex} 小于 0 时从 0 开始</li>
     *     <li>{@code searchSequence} 为 空串 时始终匹配</li>
     *     <li>起始位置大于字符序列长度时仅匹配 {@code searchSequence} 为空串的情况</li>
     * </ul>
     *
     * <pre>
     * CharSequenceAide.indexOfIgnoreCase(null, *, *)          = -1
     * CharSequenceAide.indexOfIgnoreCase(*, null, *)          = -1
     * CharSequenceAide.indexOfIgnoreCase("", "", 0)           = 0
     * CharSequenceAide.indexOfIgnoreCase("aabaabaa", "A", 0)  = 0
     * CharSequenceAide.indexOfIgnoreCase("aabaabaa", "B", 0)  = 2
     * CharSequenceAide.indexOfIgnoreCase("aabaabaa", "AB", 0) = 1
     * CharSequenceAide.indexOfIgnoreCase("aabaabaa", "B", 3)  = 5
     * CharSequenceAide.indexOfIgnoreCase("aabaabaa", "B", 9)  = -1
     * CharSequenceAide.indexOfIgnoreCase("aabaabaa", "B", -1) = 2
     * CharSequenceAide.indexOfIgnoreCase("aabaabaa", "", 2)   = 2
     * CharSequenceAide.indexOfIgnoreCase("abc", "", 9)        = -1
     * </pre>
     *
     * @param sequence 字符序列，null 返回 -1
     * @param searchSequence 要查找的字符序列，null 返回 -1
     * @param fromIndex 查找的起始位置，负数视为 0
     * @return 索引值
     * @since 1.0.0
     */
    public static int indexOfIgnoreCase(final CharSequence sequence, final CharSequence searchSequence, int fromIndex) {
        if (sequence == null || searchSequence == null) {
            return INDEX_NOT_FOUND;
        }
        if (fromIndex < 0) {
            fromIndex = 0;
        }
        final int endLimit = sequence.length() - searchSequence.length() + 1;
        if (fromIndex > endLimit) {
            return INDEX_NOT_FOUND;
        }
        if (searchSequence.length() == 0) {
            return fromIndex;
        }
        for (int i = fromIndex; i < endLimit; i++) {
            if (regionMatches(true, sequence, i, searchSequence, 0, searchSequence.length())) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * <p>查找 {@code searchSequence} 在 {@code sequence} 中第一次出现的索引（忽略大小写）。</p>
     *
     * <p>注意：</p>
     * <ul>
     *     <li>{@code sequence} 或 {@code searchSequence} 为 null 时返回 -1</li>
     *     <li>{@code searchSequence} 为 空串 时始终匹配</li>
     * </ul>
     *
     * <pre>
     * CharSequenceAide.indexOfIgnoreCase(null, *)          = -1
     * CharSequenceAide.indexOfIgnoreCase(*, null)          = -1
     * CharSequenceAide.indexOfIgnoreCase("", "")           = 0
     * CharSequenceAide.indexOfIgnoreCase("aabaabaa", "a")  = 0
     * CharSequenceAide.indexOfIgnoreCase("aabaabaa", "b")  = 2
     * CharSequenceAide.indexOfIgnoreCase("aabaabaa", "ab") = 1
     * </pre>
     *
     * @param sequence 字符序列，null 返回 -1
     * @param searchSequence 要查找的字符序列，null 返回 -1
     * @return 索引值
     * @since 1.0.0
     */
    public static int indexOfIgnoreCase(final CharSequence sequence, final CharSequence searchSequence) {
        return indexOfIgnoreCase(sequence, searchSequence, 0);
    }

    /**
     * <p>返回指定字符序列最后一次出现在字符序列中的索引，从指定索引 {@code lastIndex} 开始向左搜索，忽略大小写。</p>
     *
     * <p>注意：</p>
     * <ul>
     *     <li>{@code sequence} 或 {@code searchSequence} 为 null 时返回 -1</li>
     *     <li>起始位置 {@code fromIndex} 小于 0 时返回 -1</li>
     *     <li>除非起始位置为负，否则 {@code searchSequence} 为 空串 时始终匹配</li>
     *     <li>起始位置大于字符序列长度时将搜索整个字符序列</li>
     * </ul>
     *
     * <pre>
     * CharSequenceAide.lastIndexOfIgnoreCase(null, *, *)          = -1
     * CharSequenceAide.lastIndexOfIgnoreCase(*, null, *)          = -1
     * CharSequenceAide.lastIndexOfIgnoreCase("aabaabaa", "A", 8)  = 7
     * CharSequenceAide.lastIndexOfIgnoreCase("aabaabaa", "B", 8)  = 5
     * CharSequenceAide.lastIndexOfIgnoreCase("aabaabaa", "AB", 8) = 4
     * CharSequenceAide.lastIndexOfIgnoreCase("aabaabaa", "B", 9)  = 5
     * CharSequenceAide.lastIndexOfIgnoreCase("aabaabaa", "B", -1) = -1
     * CharSequenceAide.lastIndexOfIgnoreCase("aabaabaa", "A", 0)  = 0
     * CharSequenceAide.lastIndexOfIgnoreCase("aabaabaa", "B", 0)  = -1
     * </pre>
     *
     * @param sequence 字符序列
     * @param searchSequence 要查找的字符序列
     * @param lastIndex 最后的索引位置，小于 0 时 返回 -1
     * @return 索引值
     * @since 1.0.0
     */
    public static int lastIndexOfIgnoreCase(final CharSequence sequence, final CharSequence searchSequence, int lastIndex) {
        if (sequence == null || searchSequence == null) {
            return INDEX_NOT_FOUND;
        }
        if (lastIndex > sequence.length() - searchSequence.length()) {
            lastIndex = sequence.length() - searchSequence.length();
        }
        if (lastIndex < 0) {
            return INDEX_NOT_FOUND;
        }
        if (searchSequence.length() == 0) {
            return lastIndex;
        }

        for (int i = lastIndex; i >= 0; i--) {
            if (regionMatches(true, sequence, i, searchSequence, 0, searchSequence.length())) {
                return i;
            }
        }

        return INDEX_NOT_FOUND;
    }

    /**
     * <p>返回指定字符序列最后一次出现在字符序列中的索引，忽略大小写。</p>
     *
     * <p>注意：</p>
     * <ul>
     *     <li>{@code sequence} 或 {@code searchSequence} 为 null 时返回 -1</li>
     *     <li>起始位置 {@code fromIndex} 小于 0 时返回 -1</li>
     *     <li>除非起始位置为负，否则 {@code searchSequence} 为 空串 时始终匹配</li>
     *     <li>起始位置大于字符序列长度时将搜索整个字符序列</li>
     * </ul>
     *
     * <pre>
     * CharSequenceAide.lastIndexOfIgnoreCase(null, *)          = -1
     * CharSequenceAide.lastIndexOfIgnoreCase(*, null)          = -1
     * CharSequenceAide.lastIndexOfIgnoreCase("aabaabaa", "A")  = 7
     * CharSequenceAide.lastIndexOfIgnoreCase("aabaabaa", "B")  = 5
     * CharSequenceAide.lastIndexOfIgnoreCase("aabaabaa", "AB") = 4
     * </pre>
     *
     * @param sequence 字符序列
     * @param searchSequence 要查找的字符序列
     * @return 索引值
     * @since 1.0.0
     */
    public static int lastIndexOfIgnoreCase(final CharSequence sequence, final CharSequence searchSequence) {
        if (sequence == null || searchSequence == null) {
            return INDEX_NOT_FOUND;
        }
        return lastIndexOfIgnoreCase(sequence, searchSequence, sequence.length());
    }
    // ---------------------------------------------------------------------------------------------------
    // ----- Index of ----- end

    // ----- Index of any ----- begin
    // ---------------------------------------------------------------------------------------------------
    /**
     * <p>查找指定字符集中的字符在字符序列中第一次出现的索引。</p>
     *
     * <p>{@code sequence} 为 null 或 empty 时返回 -1；
     * {@code searchChars} 为 empty 时返回 -1。</p>
     *
     * <pre>
     * CharSequenceAide.indexOfAny(null, *)                = -1
     * CharSequenceAide.indexOfAny("", *)                  = -1
     * CharSequenceAide.indexOfAny(*, null)                = -1
     * CharSequenceAide.indexOfAny(*, [])                  = -1
     * CharSequenceAide.indexOfAny("zzabyycdxx",['z','a']) = 0
     * CharSequenceAide.indexOfAny("zzabyycdxx",['b','y']) = 3
     * CharSequenceAide.indexOfAny("aba", ['z'])           = -1
     * </pre>
     *
     * @param sequence 字符序列
     * @param searchChars 要查找的字符集
     * @return 索引值
     * @since 1.0.0
     */
    public static int indexOfAny(final CharSequence sequence, final char... searchChars) {
        if (isEmpty(sequence) || ArrayAide.isEmpty(searchChars)) {
            return INDEX_NOT_FOUND;
        }
        int sequenceLength = sequence.length();
        int sequenceLast = sequenceLength - 1;
        int searchLength = searchChars.length;
        int searchLast = searchLength - 1;
        for (int i = 0; i < sequenceLength; i++) {
            char c = sequence.charAt(i);
            for (int j = 0; j < searchLength; j++) {
                if (searchChars[j] == c) {
                    if (i < sequenceLast && j < searchLast && Character.isHighSurrogate(c)) {
                        if (searchChars[j + 1] == sequence.charAt(i + 1)) {
                            return i;
                        }
                    } else {
                        return i;
                    }
                }
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * <p>查找指定字符集中的字符在字符序列中第一次出现的索引。</p>
     *
     * <p>{@code sequence} 为 null 或 empty 时返回 -1；
     * {@code searchChars} 为 null 或 empty 时返回 -1。</p>
     *
     * <pre>
     * CharSequenceAide.indexOfAny(null, *)            = -1
     * CharSequenceAide.indexOfAny("", *)              = -1
     * CharSequenceAide.indexOfAny(*, null)            = -1
     * CharSequenceAide.indexOfAny(*, "")              = -1
     * CharSequenceAide.indexOfAny("zzabyycdxx", "za") = 0
     * CharSequenceAide.indexOfAny("zzabyycdxx", "by") = 3
     * CharSequenceAide.indexOfAny("aba","z")          = -1
     * </pre>
     *
     * @param sequence 字符序列
     * @param searchChars 要查找的字符集
     * @return 索引值
     * @since 1.0.0
     */
    public static int indexOfAny(final CharSequence sequence, final CharSequence searchChars) {
        if (isAnyEmpty(sequence, searchChars)) {
            return INDEX_NOT_FOUND;
        }
        return indexOfAny(sequence, toCharArray(searchChars));
    }
    // ---------------------------------------------------------------------------------------------------
    // ----- Index of any ----- end
}
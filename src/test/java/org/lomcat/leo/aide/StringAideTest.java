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

import junit.framework.TestCase;
import org.junit.Test;

/**
 * TODO Kweny StringAideTest
 *
 * @author Kweny
 * @since 2018-09-01 1:29
 */
public class StringAideTest extends TestCase {

//    public static void main(String[] args) {
//        System.out.println(StringAide.truncate("abcdefghij", 1, 4));
//        System.out.println(StringAide.substring("abcdefghij", 1, 4));
//    }


    @Test
    public void testTruncate1() {
        assertNull(StringAide.truncate(null, 0));
        assertNull(StringAide.truncate(null, 2));
        assertEquals(StringAide.truncate("", 4), "");
        assertEquals(StringAide.truncate("abcdefg", 4)  ,"abcd");
        assertEquals(StringAide.truncate("abcdefg", 6)  ,  "abcdef");
        assertEquals(StringAide.truncate("abcdefg", 7)  ,  "abcdefg");
        assertEquals(StringAide.truncate("abcdefg", 8)  ,  "abcdefg");
        assertNull(StringAide.truncate("abcdefg", -1));
    }

    @Test
    public void testTruncate2() {
        assertNull(StringAide.truncate(null, 0, 0));
        assertNull(StringAide.truncate(null, 2, 4));
        assertEquals(StringAide.truncate("", 0, 10) , "");
        assertEquals(StringAide.truncate("", 2, 10) , "");
        assertEquals(StringAide.truncate("abcdefghij", 0, 3) , "abc");
        assertEquals(StringAide.truncate("abcdefghij", 5, 6) , "fghij");
        assertEquals(StringAide.truncate("raspberry peach", 10, 15) , "peach");
        assertEquals(StringAide.truncate("abcdefghijklmno", 0, 10) , "abcdefghij");
        assertEquals(StringAide.truncate("abcdefghijklmno", -1, 10), "abcdefghij");
        assertEquals(StringAide.truncate("abcdefghijklmno", Integer.MIN_VALUE, 10) , "abcdefghij");
        assertEquals(StringAide.truncate("abcdefghijklmno", Integer.MIN_VALUE, Integer.MAX_VALUE) , "abcdefghijklmno");
        assertEquals(StringAide.truncate("abcdefghijklmno", 0, Integer.MAX_VALUE) , "abcdefghijklmno");
        assertEquals(StringAide.truncate("abcdefghijklmno", 1, 10) , "bcdefghijk");
        assertEquals(StringAide.truncate("abcdefghijklmno", 2, 10) , "cdefghijkl");
        assertEquals(StringAide.truncate("abcdefghijklmno", 3, 10) , "defghijklm");
        assertEquals(StringAide.truncate("abcdefghijklmno", 4, 10) , "efghijklmn");
        assertEquals(StringAide.truncate("abcdefghijklmno", 5, 10) , "fghijklmno");
        assertEquals(StringAide.truncate("abcdefghijklmno", 5, 5) , "fghij");
        assertEquals(StringAide.truncate("abcdefghijklmno", 5, 3) , "fgh");
        assertEquals(StringAide.truncate("abcdefghijklmno", 10, 3) , "klm");
        assertEquals(StringAide.truncate("abcdefghijklmno", 10, Integer.MAX_VALUE) , "klmno");
        assertEquals(StringAide.truncate("abcdefghijklmno", 13, 1) , "n");
        assertEquals(StringAide.truncate("abcdefghijklmno", 13, Integer.MAX_VALUE) , "no");
        assertEquals(StringAide.truncate("abcdefghijklmno", 14, 1) , "o");
        assertEquals(StringAide.truncate("abcdefghijklmno", 14, Integer.MAX_VALUE) , "o");
        assertEquals(StringAide.truncate("abcdefghijklmno", 15, 1) , "");
        assertEquals(StringAide.truncate("abcdefghijklmno", 15, Integer.MAX_VALUE) , "");
        assertEquals(StringAide.truncate("abcdefghijklmno", Integer.MAX_VALUE, Integer.MAX_VALUE) , "");
        assertNull(StringAide.truncate("abcdefghij", 3, -1));
        assertEquals(StringAide.truncate("abcdefghij", -2, 4), "abcd");
    }

    @Test
    public void testStripLeft() {
//        System.out.println(StringAide.strip("yxabyczxy", "xyz"));
//        System.out.println('\u0141');
//        System.out.println('\u0142');

//        System.out.println("aaaa".lastIndexOf('a', 4));
//        System.out.println(CharSequenceAide.indexOf("asfadfafd", "fa", -500));

//        System.out.println(StringAide.substring("abcdef", -2, -1));
        System.out.println((char) 32);
        System.out.println('\u0020');

        for (int i = 0; i <= 32; i++) {
            System.out.println("-" + ((char) i) + "-" + Character.isWhitespace((char)i));
        }

    }

    public static void main(String[] args) {
        System.out.println(StringAide.delete("abXcxdexfg  ", "xde"));
    }

}
package cn.edu.gzmu.center.util

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

/**
 * AntPathMatcherTest.
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/6 下午5:08
 */
internal class AntPathMatcherTest {

  private lateinit var pathMatcher: AntPathMatcher

  @BeforeEach
  internal fun setUp() {
    pathMatcher = AntPathMatcher()
  }

  @Test
  fun `Set Path Separator`() {
    pathMatcher.setPathSeparator("-")
    assertTrue(pathMatcher.match("-*", "-test"))
  }

  @Test
  fun `Is Pattern`() {
    assertTrue(pathMatcher.isPattern("/test/*"))
    assertTrue(pathMatcher.isPattern("/test/**"))
    assertTrue(pathMatcher.isPattern("/tes?/*"))
    assertTrue(pathMatcher.isPattern("t?s?"))
    assertTrue(pathMatcher.isPattern("t**/s?"))
    assertTrue(pathMatcher.isPattern("/**"))
    assertFalse(pathMatcher.isPattern("/"))
    assertFalse(pathMatcher.isPattern("test"))
    assertFalse(pathMatcher.isPattern("test/"))
    assertFalse(pathMatcher.isPattern("/test"))
  }

  @Test
  fun `Ant Path Match Full`() {
    assertTrue(pathMatcher.match("test", "test"))
    assertTrue(pathMatcher.match("/test", "/test"))
    assertTrue(pathMatcher.match("http://example.org", "http://example.org")) // SPR-14141
    assertFalse(pathMatcher.match("/test.jpg", "test.jpg"))
    assertFalse(pathMatcher.match("test", "/test"))
    assertFalse(pathMatcher.match("/test", "test"))

    // test matching with ?'s
    assertTrue(pathMatcher.match("t?st", "test"))
    assertTrue(pathMatcher.match("??st", "test"))
    assertTrue(pathMatcher.match("tes?", "test"))
    assertTrue(pathMatcher.match("te??", "test"))
    assertTrue(pathMatcher.match("?es?", "test"))
    assertFalse(pathMatcher.match("tes?", "tes"))
    assertFalse(pathMatcher.match("tes?", "testt"))
    assertFalse(pathMatcher.match("tes?", "tsst"))

    // test matching with *'s
    assertTrue(pathMatcher.match("*", "test"))
    assertTrue(pathMatcher.match("test*", "test"))
    assertTrue(pathMatcher.match("test*", "testTest"))
    assertTrue(pathMatcher.match("test/*", "test/Test"))
    assertTrue(pathMatcher.match("test/*", "test/t"))
    assertTrue(pathMatcher.match("test/*", "test/"))
    assertTrue(pathMatcher.match("*test*", "AnothertestTest"))
    assertTrue(pathMatcher.match("*test", "Anothertest"))
    assertTrue(pathMatcher.match("*.*", "test."))
    assertTrue(pathMatcher.match("*.*", "test.test"))
    assertTrue(pathMatcher.match("*.*", "test.test.test"))
    assertTrue(pathMatcher.match("test*aaa", "testechoaaa"))
    assertFalse(pathMatcher.match("test*", "tst"))
    assertFalse(pathMatcher.match("test*", "tsttest"))
    assertFalse(pathMatcher.match("test*", "test/"))
    assertFalse(pathMatcher.match("test*", "test/t"))
    assertFalse(pathMatcher.match("test/*", "test"))
    assertFalse(pathMatcher.match("*test*", "tsttst"))
    assertFalse(pathMatcher.match("*test", "tsttst"))
    assertFalse(pathMatcher.match("*.*", "tsttst"))
    assertFalse(pathMatcher.match("test*aaa", "test"))
    assertFalse(pathMatcher.match("test*aaa", "testechoaab"))

    // test matching with ?'s and /'s
    assertTrue(pathMatcher.match("/?", "/a"))
    assertTrue(pathMatcher.match("/?/a", "/a/a"))
    assertTrue(pathMatcher.match("/a/?", "/a/b"))
    assertTrue(pathMatcher.match("/??/a", "/aa/a"))
    assertTrue(pathMatcher.match("/a/??", "/a/bb"))
    assertTrue(pathMatcher.match("/?", "/a"))

    // test matching with **'s
    assertTrue(pathMatcher.match("/**", "/testing/testing"))
    assertTrue(pathMatcher.match("/*/**", "/testing/testing"))
    assertTrue(pathMatcher.match("/**/*", "/testing/testing"))
    assertTrue(pathMatcher.match("/echo/**/echo", "/echo/testing/testing/echo"))
    assertTrue(pathMatcher.match("/echo/**/echo", "/echo/testing/testing/echo/echo"))
    assertTrue(pathMatcher.match("/**/test", "/echo/echo/test"))
    assertTrue(pathMatcher.match("/echo/**/**/echo", "/echo/echo/echo/echo/echo/echo"))
    assertTrue(pathMatcher.match("/echo*echo/test", "/echoXXXecho/test"))
    assertTrue(pathMatcher.match("/*echo/test", "/XXXecho/test"))
    assertFalse(pathMatcher.match("/echo*echo/test", "/echoXXXbl/test"))
    assertFalse(pathMatcher.match("/*echo/test", "XXXechob/test"))
    assertFalse(pathMatcher.match("/*echo/test", "XXXbl/test"))

    assertFalse(pathMatcher.match("/????", "/bala/echo"))
    assertFalse(pathMatcher.match("/**/*echo", "/echo/echo/echo/bbb"))

    assertTrue(pathMatcher.match("/*echo*/**/echo/**", "/XXXechoXXXX/testing/testing/echo/testing/testing/"))
    assertTrue(pathMatcher.match("/*echo*/**/echo/*", "/XXXechoXXXX/testing/testing/echo/testing"))
    assertTrue(pathMatcher.match("/*echo*/**/echo/**", "/XXXechoXXXX/testing/testing/echo/testing/testing"))
    assertTrue(pathMatcher.match("/*echo*/**/echo/**", "/XXXechoXXXX/testing/testing/echo/testing/testing.jpg"))

    assertTrue(pathMatcher.match("*echo*/**/echo/**", "XXXechoXXXX/testing/testing/echo/testing/testing/"))
    assertTrue(pathMatcher.match("*echo*/**/echo/*", "XXXechoXXXX/testing/testing/echo/testing"))
    assertTrue(pathMatcher.match("*echo*/**/echo/**", "XXXechoXXXX/testing/testing/echo/testing/testing"))
    assertFalse(pathMatcher.match("*echo*/**/echo/*", "XXXechoXXXX/testing/testing/echo/testing/testing"))

    assertFalse(pathMatcher.match("/x/x/**/echo", "/x/x/x/"))

    assertTrue(pathMatcher.match("/foo/bar/**", "/foo/bar"))
    assertTrue(pathMatcher.match("", ""))
  }

  @Test
  fun `Ant Path Match Not Full`() {
    assertTrue(pathMatcher.match("test", "test", false))
    assertTrue(pathMatcher.match("/test", "/test", false))
    assertFalse(pathMatcher.match("/test.jpg", "test.jpg", false))
    assertFalse(pathMatcher.match("test", "/test", false))
    assertFalse(pathMatcher.match("/test", "test", false))

    // test matching with ?'s
    assertTrue(pathMatcher.match("t?st", "test", false))
    assertTrue(pathMatcher.match("??st", "test", false))
    assertTrue(pathMatcher.match("tes?", "test", false))
    assertTrue(pathMatcher.match("te??", "test", false))
    assertTrue(pathMatcher.match("?es?", "test", false))
    assertFalse(pathMatcher.match("tes?", "tes", false))
    assertFalse(pathMatcher.match("tes?", "testt", false))
    assertFalse(pathMatcher.match("tes?", "tsst", false))

    // test matchin with *'s
    assertTrue(pathMatcher.match("*", "test", false))
    assertTrue(pathMatcher.match("test*", "test", false))
    assertTrue(pathMatcher.match("test*", "testTest", false))
    assertTrue(pathMatcher.match("test/*", "test/Test", false))
    assertTrue(pathMatcher.match("test/*", "test/t", false))
    assertTrue(pathMatcher.match("test/*", "test/", false))
    assertTrue(pathMatcher.match("*test*", "AnothertestTest", false))
    assertTrue(pathMatcher.match("*test", "Anothertest", false))
    assertTrue(pathMatcher.match("*.*", "test.", false))
    assertTrue(pathMatcher.match("*.*", "test.test", false))
    assertTrue(pathMatcher.match("*.*", "test.test.test", false))
    assertTrue(pathMatcher.match("test*aaa", "testblaaaa", false))
    assertFalse(pathMatcher.match("test*", "tst", false))
    assertFalse(pathMatcher.match("test*", "test/", false))
    assertFalse(pathMatcher.match("test*", "tsttest", false))
    assertFalse(pathMatcher.match("test*", "test/", false))
    assertFalse(pathMatcher.match("test*", "test/t", false))
    assertTrue(pathMatcher.match("test/*", "test", false))
    assertTrue(pathMatcher.match("test/t*.txt", "test", false))
    assertFalse(pathMatcher.match("*test*", "tsttst", false))
    assertFalse(pathMatcher.match("*test", "tsttst", false))
    assertFalse(pathMatcher.match("*.*", "tsttst", false))
    assertFalse(pathMatcher.match("test*aaa", "test", false))
    assertFalse(pathMatcher.match("test*aaa", "testblaaab", false))

    // test matching with ?'s and /'s
    assertTrue(pathMatcher.match("/?", "/a", false))
    assertTrue(pathMatcher.match("/?/a", "/a/a", false))
    assertTrue(pathMatcher.match("/a/?", "/a/b", false))
    assertTrue(pathMatcher.match("/??/a", "/aa/a", false))
    assertTrue(pathMatcher.match("/a/??", "/a/bb", false))
    assertTrue(pathMatcher.match("/?", "/a", false))

    // test matching with **'s
    assertTrue(pathMatcher.match("/**", "/testing/testing", false))
    assertTrue(pathMatcher.match("/*/**", "/testing/testing", false))
    assertTrue(pathMatcher.match("/**/*", "/testing/testing", false))
    assertTrue(pathMatcher.match("test*/**", "test/", false))
    assertTrue(pathMatcher.match("test*/**", "test/t", false))
    assertTrue(pathMatcher.match("/bla/**/bla", "/bla/testing/testing/bla", false))
    assertTrue(pathMatcher.match("/bla/**/bla", "/bla/testing/testing/bla/bla", false))
    assertTrue(pathMatcher.match("/**/test", "/bla/bla/test", false))
    assertTrue(pathMatcher.match("/bla/**/**/bla", "/bla/bla/bla/bla/bla/bla", false))
    assertTrue(pathMatcher.match("/bla*bla/test", "/blaXXXbla/test", false))
    assertTrue(pathMatcher.match("/*bla/test", "/XXXbla/test", false))
    assertFalse(pathMatcher.match("/bla*bla/test", "/blaXXXbl/test", false))
    assertFalse(pathMatcher.match("/*bla/test", "XXXblab/test", false))
    assertFalse(pathMatcher.match("/*bla/test", "XXXbl/test", false))

    assertFalse(pathMatcher.match("/????", "/bala/bla", false))
    assertTrue(pathMatcher.match("/**/*bla", "/bla/bla/bla/bbb", false))

    assertTrue(pathMatcher.match("/*bla*/**/bla/**", "/XXXblaXXXX/testing/testing/bla/testing/testing/", false))
    assertTrue(pathMatcher.match("/*bla*/**/bla/*", "/XXXblaXXXX/testing/testing/bla/testing", false))
    assertTrue(pathMatcher.match("/*bla*/**/bla/**", "/XXXblaXXXX/testing/testing/bla/testing/testing", false))
    assertTrue(pathMatcher.match("/*bla*/**/bla/**", "/XXXblaXXXX/testing/testing/bla/testing/testing.jpg", false))

    assertTrue(pathMatcher.match("*bla*/**/bla/**", "XXXblaXXXX/testing/testing/bla/testing/testing/", false))
    assertTrue(pathMatcher.match("*bla*/**/bla/*", "XXXblaXXXX/testing/testing/bla/testing", false))
    assertTrue(pathMatcher.match("*bla*/**/bla/**", "XXXblaXXXX/testing/testing/bla/testing/testing", false))
    assertTrue(pathMatcher.match("*bla*/**/bla/*", "XXXblaXXXX/testing/testing/bla/testing/testing", false))

    assertTrue(pathMatcher.match("/x/x/**/bla", "/x/x/x/", false))

    assertTrue(pathMatcher.match("", "", false))
  }

  @Test
  fun `Extract Path Within Pattern`() {
    assertEquals("", pathMatcher.extractPathWithinPattern("/docs/commit.html", "/docs/commit.html"))
    assertEquals("cvs/commit", pathMatcher.extractPathWithinPattern("/docs/*", "/docs/cvs/commit"))
    assertEquals("commit.html", pathMatcher.extractPathWithinPattern("/docs/cvs/*.html", "/docs/cvs/commit.html"))
    assertEquals("cvs/commit", pathMatcher.extractPathWithinPattern("/docs/**", "/docs/cvs/commit"))
    assertEquals("cvs/commit.html", pathMatcher.extractPathWithinPattern("/docs/**/*.html", "/docs/cvs/commit.html"))
    assertEquals("commit.html", pathMatcher.extractPathWithinPattern("/docs/**/*.html", "/docs/commit.html"))
    assertEquals("commit.html", pathMatcher.extractPathWithinPattern("/*.html", "/commit.html"))
    assertEquals("docs/commit.html", pathMatcher.extractPathWithinPattern("/*.html", "/docs/commit.html"))
    assertEquals("/commit.html", pathMatcher.extractPathWithinPattern("*.html", "/commit.html"))
    assertEquals("/docs/commit.html", pathMatcher.extractPathWithinPattern("*.html", "/docs/commit.html"))
    assertEquals("/docs/commit.html", pathMatcher.extractPathWithinPattern("**/*.*", "/docs/commit.html"))
    assertEquals("/docs/commit.html", pathMatcher.extractPathWithinPattern("*", "/docs/commit.html"))

    assertEquals("docs/cvs/commit", pathMatcher.extractPathWithinPattern("/d?cs/*", "/docs/cvs/commit"))
    assertEquals("cvs/commit.html", pathMatcher.extractPathWithinPattern("/docs/c?s/*.html", "/docs/cvs/commit.html"))
    assertEquals("docs/cvs/commit", pathMatcher.extractPathWithinPattern("/d?cs/**", "/docs/cvs/commit"))
    assertEquals("docs/cvs/commit.html", pathMatcher.extractPathWithinPattern("/d?cs/**/*.html", "/docs/cvs/commit.html"))

  }
}

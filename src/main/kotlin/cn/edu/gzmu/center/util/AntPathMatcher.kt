package cn.edu.gzmu.center.util

import java.util.*

/**
 * Come from Spring AntPathMatcher.
 */
class AntPathMatcher {

  companion object {
    private const val DEFAULT_PATH_SEPARATOR = "/"
  }

  private var pathSeparator = DEFAULT_PATH_SEPARATOR

  fun setPathSeparator(pathSeparator: String?) {
    this.pathSeparator = pathSeparator ?: DEFAULT_PATH_SEPARATOR
  }

  fun isPattern(path: String): Boolean {
    return path.indexOf('*') != -1 || path.indexOf('?') != -1
  }

  fun match(pattern: String, path: String, fullMatch: Boolean = true): Boolean {
    if (path.startsWith(pathSeparator) != pattern.startsWith(pathSeparator)) {
      return false
    }
    val pattDirs: Array<String> = StringUtils.tokenizeToStringArray(pattern, pathSeparator)
    val pathDirs: Array<String> = StringUtils.tokenizeToStringArray(path, pathSeparator)
    var pattIdxStart = 0
    var pattIdxEnd = pattDirs.size - 1
    var pathIdxStart = 0
    var pathIdxEnd = pathDirs.size - 1
    // Match all elements up to the first **
    while (pattIdxStart <= pattIdxEnd && pathIdxStart <= pathIdxEnd) {
      val patDir = pattDirs[pattIdxStart]
      if ("**" == patDir) {
        break
      }
      if (!matchStrings(patDir, pathDirs[pathIdxStart])) {
        return false
      }
      pattIdxStart++
      pathIdxStart++
    }
    if (pathIdxStart > pathIdxEnd) { // Path is exhausted, only match if rest of pattern is * or **'s
      if (pattIdxStart > pattIdxEnd) {
        return if (pattern.endsWith(pathSeparator)) path.endsWith(pathSeparator) else !path.endsWith(pathSeparator)
      }
      if (!fullMatch) {
        return true
      }
      if (pattIdxStart == pattIdxEnd && pattDirs[pattIdxStart] == "*" &&
        path.endsWith(pathSeparator)
      ) {
        return true
      }
      for (i in pattIdxStart..pattIdxEnd) {
        if (pattDirs[i] != "**") {
          return false
        }
      }
      return true
    } else if (pattIdxStart > pattIdxEnd) { // String not exhausted, but pattern is. Failure.
      return false
    } else if (!fullMatch && "**" == pattDirs[pattIdxStart]) { // Path start definitely matches due to "**" part in pattern.
      return true
    }
    // up to last '**'
    while (pattIdxStart <= pattIdxEnd && pathIdxStart <= pathIdxEnd) {
      val patDir = pattDirs[pattIdxEnd]
      if (patDir == "**") {
        break
      }
      if (!matchStrings(patDir, pathDirs[pathIdxEnd])) {
        return false
      }
      pattIdxEnd--
      pathIdxEnd--
    }
    if (pathIdxStart > pathIdxEnd) { // String is exhausted
      for (i in pattIdxStart..pattIdxEnd) {
        if (pattDirs[i] != "**") {
          return false
        }
      }
      return true
    }
    while (pattIdxStart != pattIdxEnd && pathIdxStart <= pathIdxEnd) {
      var patIdxTmp = -1
      for (i in pattIdxStart + 1..pattIdxEnd) {
        if (pattDirs[i] == "**") {
          patIdxTmp = i
          break
        }
      }
      if (patIdxTmp == pattIdxStart + 1) { // '**/**' situation, so skip one
        pattIdxStart++
        continue
      }
      // Find the pattern between padIdxStart & padIdxTmp in str between
// strIdxStart & strIdxEnd
      val patLength = patIdxTmp - pattIdxStart - 1
      val strLength = pathIdxEnd - pathIdxStart + 1
      var foundIdx = -1
      strLoop@ for (i in 0..strLength - patLength) {
        for (j in 0 until patLength) {
          if (!matchStrings(pattDirs[pattIdxStart + j + 1], pathDirs[pathIdxStart + i + j])) {
            continue@strLoop
          }
        }
        foundIdx = pathIdxStart + i
        break
      }
      if (foundIdx == -1) {
        return false
      }
      pattIdxStart = patIdxTmp
      pathIdxStart = foundIdx + patLength
    }
    for (i in pattIdxStart..pattIdxEnd) {
      if (pattDirs[i] != "**") {
        return false
      }
    }
    return true
  }

  private fun matchStrings(pattern: String, str: String): Boolean {
    val patArr = pattern.toCharArray()
    val strArr = str.toCharArray()
    var patIdxStart = 0
    var patIdxEnd = patArr.size - 1
    var strIdxStart = 0
    var strIdxEnd = strArr.size - 1
    var ch: Char
    var containsStar = false
    for (aPatArr in patArr) {
      if (aPatArr == '*') {
        containsStar = true
        break
      }
    }
    if (!containsStar) { // No '*'s, so we make a shortcut
      if (patIdxEnd != strIdxEnd) {
        return false // Pattern and string do not have the same size
      }
      for (i in 0..patIdxEnd) {
        ch = patArr[i]
        if (ch != '?') {
          if (ch != strArr[i]) {
            return false // Character mismatch
          }
        }
      }
      return true // String matches against pattern
    }
    if (patIdxEnd == 0) {
      return true // Pattern contains only '*', which matches anything
    }
    // Process characters before first star
    while (patArr[patIdxStart].also { ch = it } != '*' && strIdxStart <= strIdxEnd) {
      if (ch != '?') {
        if (ch != strArr[strIdxStart]) {
          return false // Character mismatch
        }
      }
      patIdxStart++
      strIdxStart++
    }
    if (strIdxStart > strIdxEnd) { // All characters in the string are used. Check if only '*'s are
// left in the pattern. If so, we succeeded. Otherwise failure.
      for (i in patIdxStart..patIdxEnd) {
        if (patArr[i] != '*') {
          return false
        }
      }
      return true
    }
    // Process characters after last star
    while (patArr[patIdxEnd].also { ch = it } != '*' && strIdxStart <= strIdxEnd) {
      if (ch != '?') {
        if (ch != strArr[strIdxEnd]) {
          return false // Character mismatch
        }
      }
      patIdxEnd--
      strIdxEnd--
    }
    if (strIdxStart > strIdxEnd) { // All characters in the string are used. Check if only '*'s are
// left in the pattern. If so, we succeeded. Otherwise failure.
      for (i in patIdxStart..patIdxEnd) {
        if (patArr[i] != '*') {
          return false
        }
      }
      return true
    }
    // process pattern between stars. padIdxStart and patIdxEnd point
// always to a '*'.
    while (patIdxStart != patIdxEnd && strIdxStart <= strIdxEnd) {
      var patIdxTmp = -1
      for (i in patIdxStart + 1..patIdxEnd) {
        if (patArr[i] == '*') {
          patIdxTmp = i
          break
        }
      }
      if (patIdxTmp == patIdxStart + 1) { // Two stars next to each other, skip the first one.
        patIdxStart++
        continue
      }
      // Find the pattern between padIdxStart & padIdxTmp in str between
// strIdxStart & strIdxEnd
      val patLength = patIdxTmp - patIdxStart - 1
      val strLength = strIdxEnd - strIdxStart + 1
      var foundIdx = -1
      strLoop@ for (i in 0..strLength - patLength) {
        for (j in 0 until patLength) {
          ch = patArr[patIdxStart + j + 1]
          if (ch != '?') {
            if (ch != strArr[strIdxStart + i + j]) {
              continue@strLoop
            }
          }
        }
        foundIdx = strIdxStart + i
        break
      }
      if (foundIdx == -1) {
        return false
      }
      patIdxStart = patIdxTmp
      strIdxStart = foundIdx + patLength
    }
    // All characters in the string are used. Check if only '*'s are left
    // in the pattern. If so, we succeeded. Otherwise failure.
    for (i in patIdxStart..patIdxEnd) {
      if (patArr[i] != '*') {
        return false
      }
    }
    return true
  }

  fun extractPathWithinPattern(pattern: String, path: String): String? {
    val patternParts: Array<String> = StringUtils.tokenizeToStringArray(pattern, pathSeparator)
    val pathParts: Array<String> = StringUtils.tokenizeToStringArray(path, pathSeparator)
    val buffer = StringBuilder()
    // Add any path parts that have a wildcarded pattern part.
    var puts = 0
    for (i in patternParts.indices) {
      val patternPart = patternParts[i]
      if ((patternPart.indexOf('*') > -1 || patternPart.indexOf('?') > -1) && pathParts.size >= i + 1) {
        if (puts > 0 || i == 0 && !pattern.startsWith(pathSeparator)) {
          buffer.append(pathSeparator)
        }
        buffer.append(pathParts[i])
        puts++
      }
    }
    // Append any trailing path parts.
    for (i in patternParts.size until pathParts.size) {
      if (puts > 0 || i > 0) {
        buffer.append(pathSeparator)
      }
      buffer.append(pathParts[i])
    }
    return buffer.toString()
  }
}

private class StringUtils {
  companion object {
    fun tokenizeToStringArray(
      str: String?, delimiters: String?, trimTokens: Boolean = true, ignoreEmptyTokens: Boolean = true
    ): Array<String> {
      if (str == null) {
        return arrayOf()
      }
      val st = StringTokenizer(str, delimiters)
      val tokens: MutableList<String> = ArrayList()
      while (st.hasMoreTokens()) {
        var token = st.nextToken()
        if (trimTokens) {
          token = token.trim { it <= ' ' }
        }
        if (!ignoreEmptyTokens || token.isNotEmpty()) {
          tokens.add(token)
        }
      }
      return tokens.toTypedArray()
    }
  }
}

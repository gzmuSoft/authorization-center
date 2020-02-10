package cn.edu.gzmu.center.other

import cn.edu.gzmu.center.model.extension.KotlinJson
import cn.edu.gzmu.center.model.entity.SysUser
import io.vertx.kotlin.core.json.jsonObjectOf
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/5 下午5:26
 */
@Suppress("SpellCheckingInspection")
internal class JsonTest {

  @Test
  internal fun `Mapping json to entity and entity to json when passed`() {
    // This entity has all fields and standard type.
    val userJson = jsonObjectOf(
      "id" to 1L,
      "name" to "admin",
      "spell" to "admin",
      "password" to "123456789",
      "status" to "NORMAL",
      "image" to "http://www.baidu.com",
      "avatar" to "http://www.baidu.com",
      "email" to "lzy@echocow.cn",
      "phone" to "13712341234",
      "onlineStatus" to true,
      "sort" to 5L,
      "createUser" to "admin",
      "createTime" to "2019-01-24 11:11:11",
      "modifyUser" to "admin",
      "modifyTime" to "2019-01-24 11:11:11",
      "remark" to "",
      "isEnable" to true
    )
    // Use kotlinx.serializer instead of jackson.
    val entity = KotlinJson.json().parse(SysUser.serializer(), userJson.toString())
    assertEquals(entity.id, userJson.getLong("id"))
    val stringify = KotlinJson.json().stringify(SysUser.serializer(), entity)
    assertEquals(userJson.encode(), stringify)
  }

  @Test
  internal fun `Json object mapper`() {
    val toString = jsonObjectOf(
      "name" to "username",
      "uuuu" to "11"
    ).toString()
    println(toString)
    val user = KotlinJson.json().parse(SysUser.serializer(), toString)
    println(user::name.get())
    println(user::name.name)
  }
}

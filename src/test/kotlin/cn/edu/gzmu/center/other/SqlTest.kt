package cn.edu.gzmu.center.other

import cn.edu.gzmu.center.model.Sql
import cn.edu.gzmu.center.model.and
import cn.edu.gzmu.center.model.entity.SysUser
import io.vertx.kotlin.core.json.jsonObjectOf
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/9 下午11:56
 */
class SqlTest {
  companion object {
    private const val EXPECT_SELECT1 =
      "SELECT id, name FROM sys_data WHERE is_enable = true AND name = \$1 AND spell = \$2"
    private const val EXPECT_SELECT2 =
      "SELECT id, name, spell FROM sys_data WHERE is_enable = true AND name = \$1 AND spell = \$2"
    private const val EXPECT_SELECT3 =
      "SELECT id, name FROM sys_data WHERE is_enable = true OR name = \$1 OR spell = \$2"
    private const val EXPECT_SELECT4 =
      "SELECT id, name, spell FROM sys_data WHERE is_enable = true OR name = \$1 OR spell = \$2"
    private const val EXPECT_SELECT5 =
      "SELECT id, name, spell FROM sys_data WHERE sort = \$1"
    private const val EXPECT_SELECT6 =
      "SELECT name, spell FROM sys_user WHERE is_enable = null AND name = \$1 AND create_time = \$2"
    private const val EXPECT_UPDATE =
      "UPDATE sys_data SET remark = \$1 , name = \$2 , spell = \$3 WHERE id = 1"
  }

  @Test
  internal fun `Test select sql`() {
    val select = Sql("sys_data")
      .select { "id, name" }
      .where { "is_enable = true" }
      .and { "name" to "" }
      .and { "spell" to "" }
    assertEquals(EXPECT_SELECT1, select.get().trim())
    val condition = jsonObjectOf("name" to "", "spell" to "")
    select
      .select { "id" and "name" and "spell" }
      .where { "is_enable = true" }
      .and(condition)
    assertEquals(EXPECT_SELECT2, select.get().trim())
    select
      .select { "id, name" }
      .where { "is_enable = true" }
      .or { "name" to "" }
      .or { "spell" to "" }
    assertEquals(EXPECT_SELECT3, select.get().trim())
    select
      .select { "id" and "name" and "spell" }
      .where { "is_enable = true" }
      .or(condition)
    assertEquals(EXPECT_SELECT4, select.get().trim())

    select
      .select { "id" and "name" and "spell" }
      .whereIf { "is_enable" to null }
      .whereIf { "name" to null }
      .whereIf { "spell" to null }
      .whereIf { "sort" to "null" }
    assertEquals(EXPECT_SELECT5, select.get().trim())

    val user = SysUser(name = "test", spell = "test")
    val selectEntity = Sql("sys_user")
      .select { "name" and "spell" }
      .where { "is_enable = null" }
      .and(user::name)
      .and(user::email)
      .and(user::createTime)
    assertEquals(EXPECT_SELECT6, selectEntity.get().trim())
  }

  @Test
  internal fun `Test update sql`() {
    val user = SysUser(name = "test", spell = "test")
    val update =
      Sql("sys_data")
        .update()
        .set { "remark" }
        .setIf(user::id)
        .setIf(user::image)
        .setIf(user::name)
        .setIf(user::spell)
        .where { "id = 1" }
    assertEquals(EXPECT_UPDATE, update.get().trim())
  }
}



package cn.edu.gzmu.center.other

import cn.edu.gzmu.center.model.Sql
import cn.edu.gzmu.center.model.and
import cn.edu.gzmu.center.model.entity.Student
import cn.edu.gzmu.center.model.entity.SysRole
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
      "SELECT name, spell FROM sys_user WHERE is_enable = true AND name = \$1 AND create_time = \$2"
    private const val EXPECT_SELECT7 =
      "SELECT create_time, create_user, des, icon_cls, id, is_enable, modify_time, modify_user, name, parent_id, remark, sort, spell FROM sys_role WHERE is_enable = true AND parent_id = \$1"
    private const val EXPECT_SELECT8 =
      "SELECT create_user, des, icon_cls, is_enable, modify_time, modify_user, name, remark, sort, spell FROM sys_role WHERE is_enable = true AND parent_id = \$1"
    private const val EXPECT_SELECT9 =
      "SELECT academic, birthday, classes_id, college_id, create_time, create_user, dep_id, enter_date, gender, graduate_institution, graduation_date, id, id_number, is_enable, modify_time, modify_user, name, nation, no, original_major, remark, resume, school_id, sort, specialty_id, spell, user_id FROM student WHERE is_enable = true ORDER BY id LIMIT 10 OFFSET 0"
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
      .where { "is_enable = true" }
      .and(user::name)
      .and(user::email)
      .and(user::createTime)
    assertEquals(EXPECT_SELECT6, selectEntity.get().trim())

    val role = Sql("sys_role")
      .select(SysRole::class)
      .where { "is_enable = true" }
      .and { "parent_id" to "" }
    assertEquals(EXPECT_SELECT7, role.get().trim())
    role.select(SysRole::class, "id", "parent_id", "create_time")
      .where { "is_enable = true" }
      .and { "parent_id" to "" }
    assertEquals(EXPECT_SELECT8, role.get().trim())
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

  @Test
  internal fun `Test select page sql`() {
    val page = Sql("student")
      .select(Student::class)
      .whereEnable()
      .page("id")
    println(page.count().trim())
  }
}



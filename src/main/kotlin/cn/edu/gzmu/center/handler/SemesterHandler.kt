package cn.edu.gzmu.center.handler

import cn.edu.gzmu.center.base.BaseHandler
import cn.edu.gzmu.center.model.address.Address
import cn.edu.gzmu.center.model.address.Semester
import io.vertx.core.eventbus.EventBus
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.core.json.jsonObjectOf

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/14 下午12:03
 */
class SemesterHandler(router: Router, eventBus: EventBus) : BaseHandler(eventBus) {

  init {
    router.get("/semester")
      .handler { Address.parameterHandler.requireParam(it, "schoolId") }
      .handler { handlerPage(it, Semester.ADDRESS_SEMESTER_SCHOOL, this::page) }
    router.patch("/semester")
      .handler { Address.parameterHandler.requireJson(it, "id", "name", "endDate", "startDate", "isEnable") }
      .handler { handlerPatch(it, Semester.ADDRESS_SEMESTER_UPDATE) }
    router.post("/semester")
      .handler { Address.parameterHandler.requireJson(it, "name", "endDate", "startDate", "isEnable", "schoolId") }
      .handler { handlerCreate(it, Semester.ADDRESS_SEMESTER_CREATE) }
  }

  /**
   * @api {GET} /semester semester page
   * @apiVersion 1.0.0
   * @apiName SemesterPage
   * @apiDescription Get semester page
   * @apiGroup Semester
   * @apiExample Example usage:
   *      curl --location --request GET 'http://127.0.0.1:8889/semester?sort=id&page=1&size=5&name=&type=0&schoolId=1' \
   *             --header 'Authorization: Bearer ......'
   * @apiUse Bearer
   * @apiSuccess {Array}    content              page
   * @apiSuccess {Long}     itemsLength          all count
   * @apiSuccessExample {json} Success-response:
   *      HTTP/1.1 200 OK
   *      {
   *        "content": [
   *            { "id": 1, "name": "...", "startDate": "....", "endDate": "...." },
   *            { "id": 2, "name": "...", "startDate": "....", "endDate": "....},
   *            { "id": 3, "name": "...", "startDate": "....", "endDate": ".... }
   *        ],
   *        "itemsLength": 6
   *      }
   */
  private fun page(context: RoutingContext): JsonObject =
    jsonObjectOf(
      "schoolId" to context.request().getParam("schoolId").toLong(),
      "name" to (context.request().getParam("name") ?: "")
    )

  /**
   * @api {POST} /semester add semester
   * @apiVersion 1.0.0
   * @apiName SemesterAdd
   * @apiDescription Add Data
   * @apiGroup Semester
   * @apiUse Bearer
   * @apiExample Example usage:
   *      curl --location --request POST 'http://127.0.0.1:8889/data' \
   *        --header 'Authorization: Bearer token'
   *        --data-raw '{
   *                      "id": 1,
   *                      "name": "test"
   *                    }'
   * @apiParam {Long}     schoolId              schoolId
   * @apiParam {String}   name                  name
   * @apiParam {String}   startDate             startDate
   * @apiParam {String}   endDate               startDate
   * @apiParam {String}   [remark ]             remark
   * @apiSuccessExample {json} Success-response:
   *      HTTP/1.1 201 Created
   */
  /**
   * @api {PATCH} /semester update semester
   * @apiVersion 1.0.0
   * @apiName SemesterUpdate
   * @apiDescription Update semester
   * @apiGroup Semester
   * @apiUse Bearer
   * @apiExample Example usage:
   *      curl --location --request POST 'http://127.0.0.1:8889/data' \
   *        --header 'Authorization: Bearer token'
   *        --data-raw '{
   *                      "id": 1,
   *                      "name": "test"
   *                    }'
   * @apiParam {Long}     id                    data id
   * @apiParam {Long}     schoolId              schoolId
   * @apiParam {String}   name                  name
   * @apiParam {String}   startDate             startDate
   * @apiParam {String}   endDate               startDate
   * @apiParam {String}   [remark ]             remark
   * @apiSuccessExample {json} Success-response:
   *      HTTP/1.1 201 Created
   */
}

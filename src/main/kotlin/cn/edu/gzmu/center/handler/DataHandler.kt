package cn.edu.gzmu.center.handler

import cn.edu.gzmu.center.base.BaseHandler
import cn.edu.gzmu.center.model.address.Address
import cn.edu.gzmu.center.model.entity.SysData
import io.vertx.core.eventbus.EventBus
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.core.json.jsonObjectOf

/**
 * .
 *
 * @author <a href="https://echocow.cn">EchoCow</a>
 * @date 2020/2/12 下午9:23
 */
class DataHandler(router: Router, eventBus: EventBus) : BaseHandler(eventBus) {
  init {
    router.get("/data/type/:id")
      .handler { handlerGet<Long, JsonArray>(it, SysData.ADDRESS_DATA_TYPE, this::paramId) }
    router.get("/data/parent/:id")
      .handler { handlerGet<Long, JsonArray>(it, SysData.ADDRESS_DATA_PARENT, this::paramId) }
    router.patch("/data")
      .handler { Address.parameterHandler.requireJson(it, "id", "isEnable") }
      .handler { handlerPatch(it, SysData.ADDRESS_DATA_UPDATE) }
    router.post("/data")
      .handler { Address.parameterHandler.requireJson(it, "name", "type", "parentId") }
      .handler { handlerCreate(it, SysData.ADDRESS_DATA_CREATE) }
    router.delete("/data/:id")
      .handler { handlerDelete(it, SysData.ADDRESS_DATA_DELETE) }
    router.get("/data")
      .handler { Address.parameterHandler.requireParam(it, "type") }
      .handler { handlerPage(it, SysData.ADDRESS_DATA_PAGE, this::dataPage) }
  }

  /**
   * @api {GET} /data/type/:id sysData query
   * @apiVersion 1.0.0
   * @apiName SysDataByType
   * @apiDescription Get sys data by type.
   * @apiGroup Data
   * @apiExample Example usage:
   *      curl --location --request GET 'http://127.0.0.1:8889/data/type/1'
   *        --header 'Authorization: Bearer token'
   * @apiUse Bearer
   * @apiSuccess {Long}     id       data id
   * @apiSuccess {String}   name     data name
   * @apiSuccess {String}   brief    data brief
   * @apiSuccessExample {json} Success-response:
   *      HTTP/1.1 200 OK
   *      [
   *        { "id": 1, "name": "...", "brief": "...." },
   *        { "id": 2, "name": "...", "brief": "...." },
   *        { "id": 3, "name": "...", "brief": "...." }
   *      ]
   */
  /**
   * @api {GET} /data/parent/:id sysData query
   * @apiVersion 1.0.0
   * @apiName SysDataByParent
   * @apiDescription Get sys data by type.
   * @apiGroup Data
   * @apiExample Example usage:
   *      curl --location --request GET 'http://127.0.0.1:8889/data/parent/1'
   *        --header 'Authorization: Bearer token'
   * @apiUse Bearer
   * @apiSuccess {Long}     id       data id
   * @apiSuccess {String}   name     data name
   * @apiSuccess {String}   brief    data brief
   * @apiSuccessExample {json} Success-response:
   *      HTTP/1.1 200 OK
   *      [
   *        { "id": 1, "name": "...", "brief": "...." },
   *        { "id": 2, "name": "...", "brief": "...." },
   *        { "id": 3, "name": "...", "brief": "...." }
   *      ]
   */
  /**
   * @api {DELETE} /data/:id sysData delete
   * @apiVersion 1.0.0
   * @apiName SysDataByType
   * @apiDescription Get sys data by type.
   * @apiGroup Data
   * @apiExample Example usage:
   *      curl --location --request DELETE 'http://127.0.0.1:8889/data/1'
   *        --header 'Authorization: Bearer token'
   * @apiUse Bearer
   * @apiSuccessExample {json} Success-response:
   *      HTTP/1.1 204 No Content
   */
  /**
   * @api {GET} /data sysData page
   * @apiVersion 1.0.0
   * @apiName DataPage
   * @apiDescription Get data page
   * @apiGroup Data
   * @apiExample Example usage:
   *      curl --location --request GET 'http://127.0.0.1:8889/data?sort=id&page=1&size=5&name=&type=0' \
   *             --header 'Authorization: Bearer ......'
   * @apiUse Bearer
   * @apiSuccess {Array}    content              page
   * @apiSuccess {Long}     itemsLength          all count
   * @apiSuccessExample {json} Success-response:
   *      HTTP/1.1 200 OK
   *      {
   *        "content": [
   *            { "id": 1, "name": "...", "brief": "...." },
   *            { "id": 2, "name": "...", "brief": "...." },
   *            { "id": 3, "name": "...", "brief": "...." }
   *        ],
   *        "itemsLength": 6
   *      }
   */
  /**
   * @api {POST} /sysData add data
   * @apiVersion 1.0.0
   * @apiName DataAdd
   * @apiDescription Add Data
   * @apiGroup Data
   * @apiUse Bearer
   * @apiExample Example usage:
   *      curl --location --request POST 'http://127.0.0.1:8889/data' \
   *        --header 'Authorization: Bearer token'
   *        --data-raw '{
   *                      "id": 1,
   *                      "name": "test"
   *                    }'
   * @apiParam {Long}     id                    data id
   * @apiParam {Long}     type                  data type
   * @apiParam {String}   [name ]               data name
   * @apiParam {String}   [brief ]              brief
   * @apiParam {String}   [sort ]               sort
   * @apiParam {String}   [remark ]             remark
   * @apiSuccessExample {json} Success-response:
   *      HTTP/1.1 201 Created
   */
  /**
   * @api {PATCH} /sysData update data
   * @apiVersion 1.0.0
   * @apiName DataUpdate
   * @apiDescription Update Data
   * @apiGroup Data
   * @apiUse Bearer
   * @apiExample Example usage:
   *      curl --location --request POST 'http://127.0.0.1:8889/data' \
   *        --header 'Authorization: Bearer token'
   *        --data-raw '{
   *                      "id": 1,
   *                      "name": "test"
   *                    }'
   * @apiParam {Long}     id                    data id
   * @apiParam {Long}     type                  data type
   * @apiParam {String}   [name ]               data name
   * @apiParam {String}   [brief ]              brief
   * @apiParam {String}   [sort ]               sort
   * @apiParam {String}   [remark ]             remark
   * @apiSuccessExample {json} Success-response:
   *      HTTP/1.1 201 Created
   */
  private fun dataPage(context: RoutingContext): JsonObject =
    jsonObjectOf(
      "type" to (context.request().getParam("type") ?: "0").toLong(),
      "name" to (context.request().getParam("name") ?: "")
    )

}

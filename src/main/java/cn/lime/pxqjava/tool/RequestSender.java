package cn.lime.pxqjava.tool;

import cn.lime.pxqjava.tool.bean.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: RequestSender
 * @Description: TODO
 * @Author: Lime
 * @Date: 2024/3/26 11:47
 */
public class RequestSender {

    private static final OkHttpClient client = new OkHttpClient();

    public static List<SessionInfo> getSessions(String showId) throws IOException {
        String url = "https://m.piaoxingqiu.com/cyy_gatewayapi/show/pub/v3/show/" + showId + "/sessions_dynamic_data";
        Request request = new Request.Builder()
                .url(url)
                .addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.75 Mobile Safari/537.36")
                .addHeader("Content-Type", "application/json")
                .build();
        try (Response response = client.newCall(request).execute()) {
            String responseData = null;
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            responseData = response.body().string();

            JSONObject jsonObject = JSON.parseObject(responseData);
            if (jsonObject.getInteger("statusCode") == 200) {
                return JSONObject.parseArray(jsonObject.getJSONObject("data").getJSONArray("sessionVOs").toJSONString(), SessionInfo.class);
            } else {
                throw new IOException("get_sessions异常" + responseData);
            }
            // Parse responseData to get the required list
        }
    }

    public static List<SeatPlanInfo> getSeatPlans(String showId, String sessionId) throws IOException {
        String url = "https://m.piaoxingqiu.com/cyy_gatewayapi/show/pub/v3/show/" + showId + "/show_session/" + sessionId + "/seat_plans_static_data";
        Request request = new Request.Builder()
                .url(url)
                .addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.75 Mobile Safari/537.36")
                .addHeader("Content-Type", "application/json")
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            String responseData = response.body().string();

            JSONObject jsonObject = JSON.parseObject(responseData);
            if (jsonObject.getInteger("statusCode") == 200) {
                return JSONObject.parseArray(jsonObject.getJSONObject("data").getJSONArray("seatPlans").toJSONString(), SeatPlanInfo.class);
            } else {
                throw new IOException("get_sessions异常" + responseData);
            }
            // Parse responseData to get the required list
        }
    }


    public static List<SeatCountInfo> getSeatCount(String showId, String sessionId) throws IOException {
        String url = "https://m.piaoxingqiu.com/cyy_gatewayapi/show/pub/v3/show/" + showId + "/show_session/" + sessionId + "/seat_plans_dynamic_data";
        Request request = new Request.Builder()
                .url(url)
                .addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.75 Mobile Safari/537.36")
                .addHeader("Content-Type", "application/json")
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            String responseData = response.body().string();

            JSONObject jsonObject = JSONObject.parseObject(responseData);
            if (jsonObject.getInteger("statusCode") == 200) {
                return JSONObject.parseArray(jsonObject.getJSONObject("data").getJSONArray("seatPlans").toJSONString(), SeatCountInfo.class);
            } else {
                throw new IOException("get_seat_count异常" + responseData);
            }
            // Parse responseData to get the required list

        }
    }

    public static String getDeliverMethod(String showId, String sessionId, String seatPlanId, double price, int qty, String token) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        String data = "{\"items\":[{\"skus\":[{\"seatPlanId\":\"" + seatPlanId + "\",\"sessionId\":\"" + sessionId + "\",\"showId\":\"" + showId + "\",\"skuId\":\"" + seatPlanId + "\",\"skuType\":\"SINGLE\",\"ticketPrice\":" + price + ",\"qty\":" + qty + "}],\"spu\":{\"id\":\"" + showId + "\",\"spuType\":\"SINGLE\"}}]}";
        RequestBody body = RequestBody.create(mediaType, data);
        Request request = new Request.Builder()
                .url("https://m.piaoxingqiu.com/cyy_gatewayapi/trade/buyer/order/v3/pre_order")
                .post(body)
                .addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.75 Mobile Safari/537.36")
                .addHeader("Content-Type", "application/json")
                .addHeader("access-token", token)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            String responseData = response.body().string();

            JSONObject jsonObject = JSONObject.parseObject(responseData);
            if (jsonObject.getInteger("statusCode") == 200) {
                return (jsonObject.getJSONObject("data").getJSONArray("supportDeliveries").getObject(0, JSONObject.class).getString("name"));
            } else {
                throw new IOException("get_deliver_method异常" + responseData);
            }
            // Parse responseData to get the required delivery method
        }
    }

    public static List<AudienceInfo> getAudiences(String token) throws IOException {
        String url = "https://m.piaoxingqiu.com/cyy_gatewayapi/user/buyer/v3/user_audiences";
        Request request = new Request.Builder()
                .url(url)
                .addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.75 Mobile Safari/537.36")
                .addHeader("Content-Type", "application/json")
                .addHeader("access-token", token)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            String responseData = response.body().string();

            JSONObject jsonObject = JSONObject.parseObject(responseData);
            if (jsonObject.getInteger("statusCode") == 200) {
                return JSON.parseArray(jsonObject.getJSONArray("data").toJSONString(), AudienceInfo.class);
            } else {
                throw new IOException("getAudiences异常" + responseData);
            }
            // Parse responseData to get the required list
        }
    }

    public static AddressInfo getAddress(String token) throws IOException {
        String url = "https://m.piaoxingqiu.com/cyy_gatewayapi/user/buyer/v3/user/addresses/default";
        Request request = new Request.Builder()
                .url(url)
                .addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.75 Mobile Safari/537.36")
                .addHeader("Content-Type", "application/json")
                .addHeader("access-token", token)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            String responseData = response.body().string();

            JSONObject jsonObject = JSONObject.parseObject(responseData);
            if (jsonObject.getInteger("statusCode") == 200) {
                return JSON.parseObject(jsonObject.getJSONObject("data").toJSONString(), AddressInfo.class);
            } else {
                throw new IOException("getAddress异常" + responseData);
            }
            // Parse responseData to get the required address
        }
    }

    public static ExpressFeeInfo getExpressFee(String showId, String sessionId, String seatPlanId, double price, int qty, String locationCityId, String token) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        String data = "{\"items\":[{\"skus\":[{\"seatPlanId\":\"" + seatPlanId + "\",\"sessionId\":\"" + sessionId + "\",\"showId\":\"" + showId + "\",\"skuId\":\"" + seatPlanId + "\",\"skuType\":\"SINGLE\",\"ticketPrice\":" + price + ",\"qty\":" + qty + ",\"deliverMethod\":\"EXPRESS\"}],\"spu\":{\"id\":\"" + showId + "\",\"spuType\":\"SINGLE\"}}],\"locationCityId\":\"" + locationCityId + "\"}";
        RequestBody body = RequestBody.create(mediaType, data);
        Request request = new Request.Builder()
                .url("https://m.piaoxingqiu.com/cyy_gatewayapi/trade/buyer/order/v3/price_items")
                .post(body)
                .addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.75 Mobile Safari/537.36")
                .addHeader("Content-Type", "application/json")
                .addHeader("access-token", token)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            String responseData = response.body().string();
            
            JSONObject jsonObject = JSONObject.parseObject(responseData);
            if (jsonObject.getInteger("statusCode") == 200) {
                return JSON.parseObject(jsonObject.getJSONArray("data").getJSONObject(0).toJSONString(), ExpressFeeInfo.class);
            } else {
                throw new IOException("getExpressFee异常" + responseData);
            }
            // Parse responseData to get the required express fee
        }
    }

    public static void createOrder(String showId, String sessionId, String seatPlanId, double price, int qty, String deliverMethod,
                                   double expressFee, String receiver, String cellphone, String addressId, String detailAddress,
                                   String locationCityId, List<String> audienceIds, String token) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        JSONObject data = new JSONObject();
        switch (deliverMethod) {
            case "EXPRESS" -> {
                // 价格项目参数列表
                List<JSONObject> priceItemParam = new ArrayList<>();

                // 第一个价格项目
                JSONObject priceItem1 = new JSONObject();
                priceItem1.put("applyTickets", new JSONArray());
                priceItem1.put("priceItemName", "票款总额");
                priceItem1.put("priceItemVal", price * qty); // 请注意，price和qty需要提前定义
                priceItem1.put("priceItemType", "TICKET_FEE");
                priceItem1.put("priceItemSpecies", "SEAT_PLAN");
                priceItem1.put("direction", "INCREASE");
                priceItem1.put("priceDisplay", "￥" + (price * qty));
                priceItemParam.add(priceItem1);

                // 第二个价格项目
                JSONObject priceItem2 = new JSONObject();
                priceItem2.put("applyTickets", new JSONArray());
                priceItem2.put("priceItemName", "快递费");
                priceItem2.put("priceItemVal", expressFee); // 请注意，express_fee需要提前定义
                priceItem2.put("priceItemId", showId);
                priceItem2.put("priceItemSpecies", "SEAT_PLAN");
                priceItem2.put("priceItemType", "EXPRESS_FEE");
                priceItem2.put("direction", "INCREASE");
                priceItem2.put("priceDisplay", "￥" + expressFee);
                priceItemParam.add(priceItem2);

                // 项列表
                List<JSONObject> items = new ArrayList<>();

                // SKU列表
                List<JSONObject> skus = new ArrayList<>();
                JSONObject sku = new JSONObject();
                sku.put("seatPlanId", seatPlanId);
                sku.put("sessionId", sessionId);
                sku.put("showId", showId);
                sku.put("skuId", seatPlanId);
                sku.put("skuType", "SINGLE");
                sku.put("ticketPrice", price);
                sku.put("qty", qty);
                sku.put("deliverMethod", deliverMethod);
                skus.add(sku);

                // SPU
                JSONObject spu = new JSONObject();
                spu.put("id", showId);
                spu.put("spuType", "SINGLE");

                // 项
                JSONObject item = new JSONObject();
                item.put("skus", skus);
                item.put("spu", spu);
                items.add(item);

                // 联系参数
                JSONObject contactParam = new JSONObject();
                contactParam.put("receiver", receiver); // 张三
                contactParam.put("cellphone", cellphone); // 13812345678

                // one2oneAudiences
                List<JSONObject> one2oneAudiences = new ArrayList<>();
                for (String audienceId : audienceIds) {
                    JSONObject audience = new JSONObject();
                    audience.put("audienceId", audienceId);
                    audience.put("sessionId", sessionId);
                    one2oneAudiences.add(audience);
                }

                // 地址参数
                JSONObject addressParam = new JSONObject();
                addressParam.put("address", detailAddress); // 星巴克咖啡门口
                addressParam.put("district", locationCityId.substring(4));
                addressParam.put("city", locationCityId.substring(2, 4));
                addressParam.put("province", locationCityId.substring(0, 2));
                addressParam.put("addressId", addressId);

                // 构建最终的JSONObject
                data.put("priceItemParam", priceItemParam);
                data.put("items", items);
                data.put("contactParam", contactParam);
                data.put("one2oneAudiences", one2oneAudiences);
                data.put("addressParam", addressParam);
                break;
            }
            case "E_TICKET" -> {
                // 价格项目参数列表
                List<JSONObject> priceItemParam = new ArrayList<>();

                // 第一个价格项目
                JSONObject priceItem1 = new JSONObject();
                priceItem1.put("applyTickets", new JSONArray());
                priceItem1.put("priceItemName", "票款总额");
                priceItem1.put("priceItemVal", price * qty); // 请注意，price和qty需要提前定义
                priceItem1.put("priceItemType", "TICKET_FEE");
                priceItem1.put("priceItemSpecies", "SEAT_PLAN");
                priceItem1.put("direction", "INCREASE");
                priceItem1.put("priceDisplay", "￥" + (price * qty));
                priceItemParam.add(priceItem1);

                // 项列表
                List<JSONObject> items = new ArrayList<>();

                // SKU列表
                List<JSONObject> skus = new ArrayList<>();
                JSONObject sku = new JSONObject();
                sku.put("seatPlanId", seatPlanId);
                sku.put("sessionId", sessionId);
                sku.put("showId", showId);
                sku.put("skuId", seatPlanId);
                sku.put("skuType", "SINGLE");
                sku.put("ticketPrice", price);
                sku.put("qty", qty);
                sku.put("deliverMethod", deliverMethod);
                skus.add(sku);

                // SPU
                JSONObject spu = new JSONObject();
                spu.put("id", showId);
                spu.put("spuType", "SINGLE");

                // 项
                JSONObject item = new JSONObject();
                item.put("skus", skus);
                item.put("spu", spu);
                items.add(item);

                // many2OneAudience
                JSONObject many2OneAudience = new JSONObject();
                many2OneAudience.put("audienceId", audienceIds.get(0));
                JSONArray sessionIdsArray = new JSONArray();
                sessionIdsArray.add(sessionId);
                many2OneAudience.put("sessionIds", sessionIdsArray);

                // one2oneAudiences
                List<JSONObject> one2oneAudiences = new ArrayList<>();
                for (String audienceId : audienceIds) {
                    JSONObject audience = new JSONObject();
                    audience.put("audienceId", audienceId);
                    audience.put("sessionId", sessionId);
                    one2oneAudiences.add(audience);
                }

                // 构建最终的JSONObject
                data.put("priceItemParam", priceItemParam);
                data.put("items", items);
                data.put("many2OneAudience", many2OneAudience);
                data.put("one2oneAudiences", one2oneAudiences);
                break;
            }
            case "VENUE" -> {
                // 价格项目参数列表
                List<JSONObject> priceItemParam = new ArrayList<>();

                // 第一个价格项目
                JSONObject priceItem1 = new JSONObject();
                priceItem1.put("applyTickets", new JSONArray());
                priceItem1.put("priceItemName", "票款总额");
                priceItem1.put("priceItemVal", price * qty); // 请注意，price和qty需要提前定义
                priceItem1.put("priceItemType", "TICKET_FEE");
                priceItem1.put("priceItemSpecies", "SEAT_PLAN");
                priceItem1.put("direction", "INCREASE");
                priceItem1.put("priceDisplay", "￥" + (price * qty));
                priceItemParam.add(priceItem1);

                // 项列表
                List<JSONObject> items = new ArrayList<>();

                // SKU列表
                List<JSONObject> skus = new ArrayList<>();
                JSONObject sku = new JSONObject();
                sku.put("seatPlanId", seatPlanId);
                sku.put("sessionId", sessionId);
                sku.put("showId", showId);
                sku.put("skuId", seatPlanId);
                sku.put("skuType", "SINGLE");
                sku.put("ticketPrice", price);
                sku.put("qty", qty);
                sku.put("deliverMethod", deliverMethod);
                skus.add(sku);

                // SPU
                JSONObject spu = new JSONObject();
                spu.put("id", showId);
                spu.put("spuType", "SINGLE");

                // 项
                JSONObject item = new JSONObject();
                item.put("skus", (skus));
                item.put("spu", spu);
                items.add(item);

                // one2oneAudiences
                List<JSONObject> one2oneAudiences = new ArrayList<>();
                for (String audienceId : audienceIds) {
                    JSONObject audience = new JSONObject();
                    audience.put("audienceId", audienceId);
                    audience.put("sessionId", sessionId);
                    one2oneAudiences.add(audience);
                }

                // 构建最终的JSONObject
                data.put("priceItemParam", (priceItemParam));
                data.put("items", (items));
                data.put("one2oneAudiences", (one2oneAudiences));
                break;
            }
            case "VENUE_E" -> {
                // 构建 priceItemParam
                JSONObject priceItem = new JSONObject();
                JSONArray applyTickets = new JSONArray();
                priceItem.put("applyTickets", applyTickets);
                priceItem.put("priceItemName", "票款总额");
                priceItem.put("priceItemVal", price * qty);
                priceItem.put("priceItemType", "TICKET_FEE");
                priceItem.put("priceItemSpecies", "SEAT_PLAN");
                priceItem.put("direction", "INCREASE");
                priceItem.put("priceDisplay", "￥" + (price * qty));

                JSONArray priceItemParam = new JSONArray();
                priceItemParam.add(priceItem);

                // 构建 skus
                JSONObject sku = new JSONObject();
                sku.put("seatPlanId", seatPlanId);
                sku.put("sessionId", sessionId);
                sku.put("showId", showId);
                sku.put("skuId", seatPlanId);
                sku.put("skuType", "SINGLE");
                sku.put("ticketPrice", price);
                sku.put("qty", qty);
                sku.put("deliverMethod", deliverMethod);

                JSONArray skusArray = new JSONArray();
                skusArray.add(sku);

                // 构建 spu
                JSONObject spu = new JSONObject();
                spu.put("id", showId);
                spu.put("spuType", "SINGLE");

                // 构建 items
                JSONObject item = new JSONObject();
                item.put("skus", skusArray);
                item.put("spu", spu);

                JSONArray itemsArray = new JSONArray();
                itemsArray.add(item);

                // 构建最终的 JSONObject
                data.put("priceItemParam", priceItemParam);
                data.put("items", itemsArray);

                break;
            }
            case "ID_CARD" -> {
                // 构建 priceItemParam
                JSONObject priceItem = new JSONObject();
                JSONArray applyTickets = new JSONArray();
                priceItem.put("applyTickets", applyTickets);
                priceItem.put("priceItemName", "票款总额");
                priceItem.put("priceItemVal", price * qty);
                priceItem.put("priceItemType", "TICKET_FEE");
                priceItem.put("priceItemSpecies", "SEAT_PLAN");
                priceItem.put("direction", "INCREASE");
                priceItem.put("priceDisplay", "￥" + (price * qty));

                JSONArray priceItemParam = new JSONArray();
                priceItemParam.add(priceItem);

                // 构建 one2oneAudiences
                JSONArray one2oneAudiences = new JSONArray();
                for (String audienceId : audienceIds) {
                    JSONObject audience = new JSONObject();
                    audience.put("audienceId", audienceId);
                    audience.put("sessionId", sessionId);
                    one2oneAudiences.add(audience);
                }

                // 构建 skus
                JSONObject sku = new JSONObject();
                sku.put("seatPlanId", seatPlanId);
                sku.put("sessionId", sessionId);
                sku.put("showId", showId);
                sku.put("skuId", seatPlanId);
                sku.put("skuType", "SINGLE");
                sku.put("ticketPrice", price);
                sku.put("qty", qty);
                sku.put("deliverMethod", deliverMethod);

                JSONArray skusArray = new JSONArray();
                skusArray.add(sku);

                // 构建 spu
                JSONObject spu = new JSONObject();
                spu.put("id", showId);
                spu.put("spuType", "SINGLE");

                // 构建 items
                JSONObject item = new JSONObject();
                item.put("skus", skusArray);
                item.put("spu", spu);

                JSONArray itemsArray = new JSONArray();
                itemsArray.add(item);

                // 构建最终的 JSONObject
                data.put("priceItemParam", priceItemParam);
                data.put("one2oneAudiences", one2oneAudiences);
                data.put("items", itemsArray);
                break;
            }
            default -> {
                throw new IllegalArgumentException("不支持的deliver_method:" + deliverMethod);
            }
        }
        RequestBody body = RequestBody.create(mediaType, data.toJSONString());
        Request request = new Request.Builder()
                .url("https://m.piaoxingqiu.com/cyy_gatewayapi/trade/buyer/order/v3/create_order")
                .post(body)
                .addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.75 Mobile Safari/537.36")
                .addHeader("Content-Type", "application/json")
                .addHeader("access-token", token)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseData = response.body().string();

                JSONObject jsonObject = JSONObject.parseObject(responseData);
                if (jsonObject.getInteger("statusCode") == 200) {
                    System.out.println("下单成功！请尽快支付！");
                    System.out.println(jsonObject.toJSONString());
                } else {
                    throw new IOException("createOrder异常" + responseData);
                }
            } else {
                throw new IOException("下单异常: " + response.body().string());
            }
        }
    }


}

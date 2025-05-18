package com.example.spv2.test1;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Ra {
    public static void main(String[] args) {
        // 文件路径
//        String filePath = "D:\\Downloads\\content (2).json";
        String filePath = "D:\\Downloads\\content-2.txt";

        // 使用 BufferedReader 逐行读取文件
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // 解析每一行的 JSON 数据
//                toto(line);
//                optimizedToto(line);
//                v2(line).;
                v3(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void toto(String toString) {
        JSONObject jsonObject = JSONObject.parseObject(toString);
        HashMap<String, Object> res = new HashMap<>();

        for (java.util.Map.Entry<String, Object> jsonObjectEntry : jsonObject.entrySet()) {
            if (jsonObjectEntry.getKey().equals("content")) {
                JSONObject contentObject = JSONObject.parseObject(jsonObjectEntry.getValue().toString());
                HashMap<String, Object> contentMap = new HashMap<>();
                for (java.util.Map.Entry<String, Object> contentObjectEntry : contentObject.entrySet()) {
                    if (contentObjectEntry.getKey().equals("data")) {
                        JSONArray dataLists = JSON.parseArray(contentObjectEntry.getValue().toString());
                        // 遍历 JSONArray
                        ArrayList<Object> dataArrayLists = new ArrayList<>();
                        for (int i = 0; i < dataLists.size(); i++) {
                            Object dataObject = dataLists.get(i);
                            JSONObject dObject = JSONObject.parseObject(dataObject.toString());
                            HashMap<String, Object> dMap = new HashMap<>();
                            for (java.util.Map.Entry<String, Object> dObjectEntry : dObject.entrySet()) {
                                if (dObjectEntry.getKey().equals("msg")) {
                                    String msgValue = dObjectEntry.getValue().toString();
                                    JSONObject msgJsonObject = JSONObject.parseObject(msgValue);
                                    HashMap<String, Object> msgMap = new HashMap<>();
                                    for (java.util.Map.Entry<String, Object> msgJsonObjectEntry : msgJsonObject.entrySet()) {
                                        if (msgJsonObjectEntry.getKey().equals("resp info")) {
                                            String respInfoStr = msgJsonObjectEntry.getValue().toString();
                                            JSONObject respInfoStrObject = JSONObject.parseObject(respInfoStr);
                                            HashMap<String, Object> respInfoMap = new HashMap<>();
                                            for (java.util.Map.Entry<String, Object> respInfoStrObjectEntry : respInfoStrObject.entrySet()) {
                                                if (respInfoStrObjectEntry.getKey().equals("result")) {
                                                    JSONArray resultJSONArray = JSON.parseArray(respInfoStrObjectEntry.getValue().toString());
                                                    ArrayList<Object> resultArrayLists = new ArrayList<>();
                                                    for (Object resultObj : resultJSONArray) {
                                                        JSONObject resJsonObject = JSONObject.parseObject(resultObj.toString());
                                                        HashMap<String, Object> resJsonObjectMap = new HashMap<>();
                                                        for (java.util.Map.Entry<String, Object> resJsonObjectEntry : resJsonObject.entrySet()) {
                                                            if (resJsonObjectEntry.getKey().equals("url")) {
                                                                String url = resJsonObject.getString("url");
                                                                url = url + "last";
                                                                resJsonObjectMap.put(resJsonObjectEntry.getKey(), url);
                                                            } else {
                                                                resJsonObjectMap.put(resJsonObjectEntry.getKey(), resJsonObjectEntry.getValue());
                                                            }
                                                        }
                                                        JSONObject converResJsonObjectMap = new JSONObject(resJsonObjectMap);
                                                        resultArrayLists.add(converResJsonObjectMap);
                                                    }
                                                    respInfoMap.put(respInfoStrObjectEntry.getKey(), resultArrayLists);

                                                } else {
                                                    respInfoMap.put(respInfoStrObjectEntry.getKey(), respInfoStrObjectEntry.getValue());
                                                }
                                            }

                                            JSONObject converRespInfoMap = new JSONObject(respInfoMap);
                                            msgMap.put(msgJsonObjectEntry.getKey(), converRespInfoMap);

                                        } else {
                                            msgMap.put(msgJsonObjectEntry.getKey(), msgJsonObjectEntry.getValue());
                                        }
                                    }
                                    dMap.put(dObjectEntry.getKey(), msgMap);
                                } else {
                                    dMap.put(dObjectEntry.getKey(), dObjectEntry.getValue());
                                }

                            }
                            JSONObject converdMap = new JSONObject(dMap);
                            dataArrayLists.add(converdMap);
                        }
                        contentMap.put(contentObjectEntry.getKey(), dataArrayLists);
                    } else {
                        contentMap.put(contentObjectEntry.getKey(), contentObjectEntry.getValue());
                    }
                }


                res.put(jsonObjectEntry.getKey(), contentMap);
            } else {
                res.put(jsonObjectEntry.getKey(), jsonObjectEntry.getValue());
            }
        }
        JSONObject jsonObject1 = new JSONObject(res);
        System.out.println(jsonObject1.toString());


    }
    public static void v3(String line) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<String> modifiedLines = new ArrayList<>();
        if (line.startsWith("\uFEFF")) {
            line = line.substring(1);
        }

        // 解析单行 JSON
        JsonNode root;
        try {
            root = mapper.readTree(line);
        } catch (Exception e) {
            System.err.println("JSON 解析失败: " + line);
            return; // 跳过无效行
        }

        // 检查根节点是否为 Object 类型
        if (!root.isObject()) {
            System.err.println("无效的 JSON 结构: " + line);
            return;
        }

        // 定位到 content 字段
        if (!root.has("content")) {
            System.err.println("缺失 content 字段: " + line);
            return;
        }
        JsonNode contentNode = root.get("content");

        // 检查 content 是否为 Object 类型
        if (!contentNode.isObject()) {
            System.err.println("content 字段不是对象: " + line);
            return;
        }
        ObjectNode content = (ObjectNode) contentNode;

        // 定位到 data 数组
        if (!content.has("data")) {
            System.err.println("缺失 data 字段: " + line);
            return;
        }
        JsonNode dataNode = content.get("data");

        // 检查 data 是否为数组
        if (!dataNode.isArray()) {
            System.err.println("data 字段不是数组: " + line);
            return;
        }
        ArrayNode dataArray = (ArrayNode) dataNode;

        // 遍历 data 数组中的每个元素
        for (JsonNode dataItem : dataArray) {
            if (!dataItem.isObject()) {
                System.err.println("data 元素不是对象: " + dataItem);
                continue;
            }
            ObjectNode dataItemObj = (ObjectNode) dataItem;

            // 检查 msg 字段
            if (!dataItemObj.has("msg")) {
                System.err.println("缺失 msg 字段: " + dataItem);
                continue;
            }
            JsonNode msgNode = dataItemObj.get("msg");

            // 检查 msg 是否为 Object 类型
            if (!msgNode.isObject()) {
                System.err.println("msg 字段不是对象: " + dataItem);
                continue;
            }
            ObjectNode msg = (ObjectNode) msgNode;

            // 检查 resp info 字段（注意空格）
            if (!msg.has("resp info")) {
                System.err.println("缺失 resp info 字段: " + dataItem);
                continue;
            }
            JsonNode respInfoNode = msg.get("resp info");

            // 检查 resp info 是否为 Object 类型
            if (!respInfoNode.isObject()) {
                System.err.println("resp info 字段不是对象: " + dataItem);
                continue;
            }
            ObjectNode respInfo = (ObjectNode) respInfoNode;

            // 检查 result 数组
            if (!respInfo.has("result")) {
                System.err.println("缺失 result 字段: " + dataItem);
                continue;
            }
            JsonNode resultNode = respInfo.get("result");

            // 检查 result 是否为数组
            if (!resultNode.isArray()) {
                System.err.println("result 字段不是数组: " + dataItem);
                continue;
            }
            ArrayNode resultArray = (ArrayNode) resultNode;

            // 遍历 result 数组，修改每个 url
            for (JsonNode resultItem : resultArray) {
                if (!resultItem.isObject()) {
                    System.err.println("result 元素不是对象: " + resultItem);
                    continue;
                }
                ObjectNode resultItemObj = (ObjectNode) resultItem;

                // 检查 url 字段是否存在
                if (!resultItemObj.has("url")) {
                    System.err.println("缺失 url 字段: " + resultItem);
                    continue;
                }

                // 修改 url
                String oldUrl = resultItemObj.get("url").asText();
                String newUrl = oldUrl + "aaa";
                resultItemObj.put("url", newUrl);
            }
        }

        try {
            // 将 Java 对象转换为 JSON 字符串
            String jsonString = mapper.writeValueAsString(root);
            System.out.println(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void v2(String jsonInput) throws JsonProcessingException {
        if (jsonInput.startsWith("\uFEFF")) {
            jsonInput = jsonInput.substring(1);
        }
        ObjectMapper mapper = new ObjectMapper();

        // 解析 JSON
        ObjectNode root = (ObjectNode) mapper.readTree(jsonInput);

        // 定位到 content -> data 数组
        ObjectNode content = (ObjectNode) root.get("content");
        ArrayNode dataArray = (ArrayNode) content.get("data");

        // 遍历 data 数组中的每个元素
        for (JsonNode dataItem : dataArray) {
            // 定位到 msg -> resp info -> result 数组
            ObjectNode msg = (ObjectNode) dataItem.get("msg");
            ObjectNode respInfo = (ObjectNode) msg.get("resp info");
            ArrayNode resultArray = (ArrayNode) respInfo.get("result");

            // 遍历 result 数组，修改每个 url
            for (JsonNode resultItem : resultArray) {
                if (resultItem.has("url")) {
                    String oldUrl = resultItem.get("url").asText();
                    String newUrl = oldUrl + "aaa";
                    ((ObjectNode) resultItem).put("url", newUrl);
                }
            }
        }

        // 输出修改后的 JSON（格式化后的字符串）
//        String jsonOutput = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);
//        System.out.println(jsonOutput);

        try {
            // 将 Java 对象转换为 JSON 字符串
            String jsonString = mapper.writeValueAsString(root);
            System.out.println(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void optimizedToto(String jsonStr) {
        JSONObject root = JSONObject.parseObject(jsonStr);
        traverseAndModify(root);
        System.out.println(root.toString());
    }

    private static void traverseAndModify(Object node) {
        if (node instanceof JSONObject) {
            JSONObject obj = (JSONObject) node;
            for (String key : obj.keySet()) {
                Object value = obj.get(key);
                if (value instanceof String && "url".equals(key)) {
                    obj.put(key, ((String) value) + "last");
                } else {
                    traverseAndModify(value);
                }
            }
        } else if (node instanceof JSONArray) {
            JSONArray arr = (JSONArray) node;
            for (int i = 0; i < arr.size(); i++) {
                traverseAndModify(arr.get(i));
            }
        }
    }

}

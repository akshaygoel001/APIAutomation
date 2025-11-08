package utils;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;

public class JSONReader {

    public static String getTestData(String key) {
        return (String) getJsonData().get(key);//return value
    }

    private static JSONObject getJsonData() {
        //pass the path of the testdata.json file
        File filename = new File("resources//testData//TestData.json");
        //convert json file into string
        String json ;
        try {
            json = FileUtils.readFileToString(filename, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //parse the string into object
        Object obj;
        try {
            obj = new JSONParser().parse(json);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        //give jsonobject so that I can return it to the function everytime it get called
        JSONObject jsonObject = (JSONObject) obj;
        return jsonObject;

    }

    public static JSONArray getJSONArrayData(String key) {
        JSONObject jsonObject ;
        try {
            jsonObject = getJsonData();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        JSONArray jsonArray = (JSONArray) jsonObject.get(key);
        return jsonArray;
    }
    public static Object getJSONArrayData(String key, int index) {
        JSONObject jsonObject ;
        try {
            jsonObject = getJsonData();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        JSONArray jsonArray = (JSONArray) jsonObject.get(key);
        return jsonArray.get(index);
    }

}

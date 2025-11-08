package utils;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;

public class JSONReader {

    public static String getTestData(String key)  {
        try {
            return (String) getJsonData().get(key);//return value
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static JSONObject getJsonData() {
        //pass the path of the testdata.json file
        File filename = new File("resources//testData//TestData.json");
        //convert json file into string
        String json;
        JSONObject jsonObject;
        try {
            json = FileUtils.readFileToString(filename, "UTF-8");
            //parse the string into object
            Object obj = new JSONParser().parse(json);
            //give jsonobject so that I can return it to the function everytime it get called
            jsonObject = (JSONObject) obj;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return jsonObject;
    }

    public static JSONArray getJSONArrayData(String key) throws IOException, ParseException {
        JSONObject jsonObject = getJsonData();
        JSONArray jsonArray = (JSONArray) jsonObject.get(key);
        return jsonArray;
    }
    public static Object getJSONArrayData(String key, int index) throws IOException, ParseException {
        JSONObject jsonObject = getJsonData();
        JSONArray jsonArray = (JSONArray) jsonObject.get(key);
        return jsonArray.get(index);
    }

}

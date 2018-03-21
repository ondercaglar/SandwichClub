package com.udacity.sandwichclub.utils;


import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils
{

    private static final String JSON_NAME            = "name";
    private static final String JSON_MAIN_NAME       = "mainName";
    private static final String JSON_ALSO_KNOWN_AS   = "alsoKnownAs";
    private static final String JSON_PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String JSON_DESCRIPTION     = "description";
    private static final String JSON_IMAGE           = "image";
    private static final String JSON_INGREDIENTS     = "ingredients";


    public static Sandwich parseSandwichJson(String json)
    {

        Sandwich mSandwich = new Sandwich();

        List<String> alsoKnownAsList = new ArrayList<String>();
        List<String> ingredientsList = new ArrayList<String>();

        try
        {
            JSONObject reader = new JSONObject(json);
            JSONObject name   = reader.getJSONObject(JSON_NAME);

            String mainName   = name.optString(JSON_MAIN_NAME);
            mSandwich.setMainName(mainName);

            JSONArray alsoKnownAsJsonArray = name.getJSONArray(JSON_ALSO_KNOWN_AS);
            for (int i=0; i < alsoKnownAsJsonArray.length(); i++)
            {
                String mAlsoKnownAs = alsoKnownAsJsonArray.optString(i);
                alsoKnownAsList.add(mAlsoKnownAs);
            }
            mSandwich.setAlsoKnownAs(alsoKnownAsList);

            String placeOfOrigin = reader.optString(JSON_PLACE_OF_ORIGIN);
            mSandwich.setPlaceOfOrigin(placeOfOrigin);

            String description = reader.optString(JSON_DESCRIPTION);
            mSandwich.setDescription(description);

            String image = reader.optString(JSON_IMAGE);
            mSandwich.setImage(image);


            JSONArray ingredientsJsonArray = reader.getJSONArray(JSON_INGREDIENTS);
            for(int i=0; i < ingredientsJsonArray.length(); i++)
            {
                String mIngredients = ingredientsJsonArray.optString(i);
                ingredientsList.add(mIngredients);
            }
            mSandwich.setIngredients(ingredientsList);

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return mSandwich;

    }

}

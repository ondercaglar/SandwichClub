package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @BindView(R.id.image_iv)      ImageView ingredientsIv ;
    @BindView(R.id.also_known_tv) TextView  alsoKnownAsTxt;
    @BindView(R.id.origin_tv)     TextView  placeOfOriginTxt;
    @BindView(R.id.description_tv)TextView  descriptionTxt;
    @BindView(R.id.ingredients_tv)TextView  ingredientsTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent == null)
        {
            closeOnError();
        }

        int position = intent != null ? intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION) : 0;
        if (position == DEFAULT_POSITION)
        {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null)
        {
            // Sandwich data unavailable
            closeOnError();
            return;
        }


        Picasso.with(this)
                .load(sandwich.getImage())
                //.placeholder(R.drawable.user_placeholder)  SUGGESTION
                //.error(R.drawable.user_placeholder_error)  SUGGESTION
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());


        StringBuilder mAlsoKnownAs = new StringBuilder();
        List<String> alsoKnownAsList;
        alsoKnownAsList = sandwich.getAlsoKnownAs();
        for(int i=0; i < alsoKnownAsList.size(); i++)
        {
            mAlsoKnownAs.append(alsoKnownAsList.get(i)).append(", ");
        }


        StringBuilder mIngredients = new StringBuilder();
        List<String> ingredientsList;
        ingredientsList = sandwich.getIngredients();
        for(int i=0; i < ingredientsList.size(); i++)
        {
            mIngredients.append(ingredientsList.get(i)).append(", ");
        }


        alsoKnownAsTxt.setText(mAlsoKnownAs.toString());
        placeOfOriginTxt.setText(sandwich.getPlaceOfOrigin());
        descriptionTxt.setText(sandwich.getDescription());
        ingredientsTxt.setText(mIngredients.toString());

    }

    private void closeOnError()
    {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

}

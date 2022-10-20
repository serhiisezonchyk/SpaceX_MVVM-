package com.example.spacexships.details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.spacexships.App;
import com.example.spacexships.R;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
    public static final String EXTRA_ID ="EXTRA_NAME";
    private TextView nameTextView;
    private TextView yearTextView;
    private ImageView image;
    private ProgressBar progress;
    private TextView ship_type;
    private TextView weight_kg;
    private TextView home_port;
    private Button button;

    private DetailsViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        nameTextView = findViewById(R.id.nameTextView);
        yearTextView = findViewById(R.id.yearTextView);
        progress = findViewById(R.id.progress);
        ship_type = findViewById(R.id.ship_type);
        weight_kg = findViewById(R.id.weight_kg);
        home_port = findViewById(R.id.home_port);
        image = findViewById(R.id.shipImageView);
        button = findViewById(R.id.moreButton);

        String ship_id = getIntent().getStringExtra(EXTRA_ID);

        App app = (App) getApplication();
        ViewModelProvider viewModelProvider = new ViewModelProvider(this, app.getViewModelFactory());
        viewModel = viewModelProvider.get(DetailsViewModel.class);

        viewModel.loadShipById(ship_id);
        viewModel.getResults().observe(this, result ->{
            switch (result.getStatus()){
                case SUCCESS:{
                    nameTextView.setText(result.getData().getShip_name());
                    yearTextView.setText(
                            result.getData().getYear_built()!=0?
                                    String.valueOf("Year of built: "+result.getData().getYear_built()):
                            "Year of built is not found");
                    if(result.getData().getImage() != null)
                        Picasso.get().load(result.getData().getImage()).into(image);
                    ship_type.setText(result.getData().getShip_type().isEmpty()?
                            "Type of ship is not found":"Type of ship is "+result.getData().getShip_type());
                    weight_kg .setText(
                            result.getData().getWeight_kg() != 0?
                                    "Weight of ship " + String.valueOf(result.getData().getWeight_kg()) + " kg":
                            "Weight of ship is not found");
                    home_port.setText(result.getData().getHome_port().isEmpty()?
                            "Home port is not found":"Home port is "+result.getData().getHome_port());
                    progress.setVisibility(View.GONE);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent browserIntent = new
                                    Intent(Intent.ACTION_VIEW, Uri.parse(result.getData().getUrl()));
                            startActivity(browserIntent);

                        }
                    });
                    break;
                }
                case EMPTY:{
                    image.setVisibility(View.INVISIBLE);
                    nameTextView.setText("Nothing found");
                    yearTextView.setText("");
                    ship_type .setText("");
                    weight_kg .setText("");
                    home_port.setText("");
                    button.setVisibility(View.INVISIBLE);
                    progress.setVisibility(View.GONE);
                    break;
                }
                case LOADING:{
                    nameTextView.setText("");
                    yearTextView.setText("");
                    ship_type .setText("");
                    weight_kg .setText("");
                    home_port.setText("");
                    progress.setVisibility(View.GONE);
                }

            }
        });

    }
}
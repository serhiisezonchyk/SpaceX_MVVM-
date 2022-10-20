package com.example.spacexships.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.spacexships.App;
import com.example.spacexships.R;
import com.example.spacexships.details.DetailsActivity;

public class MainActivity extends AppCompatActivity {

    private Button searchButton;
    private RecyclerView spacesList;
    private EditText nameEditText;
    private ProgressBar progress;
    private TextView emptyTextView;
    private TextView errorTextView;

    private MainViewModel viewModel;

    private ShipsAdapter adapter;
    public static final String EXTRA_ID ="EXTRA_NAME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchButton = findViewById(R.id.searchButton);
        spacesList = findViewById(R.id.spacesList);
        nameEditText = findViewById(R.id.nameEditText);
        progress = findViewById(R.id.proggress);
        emptyTextView = findViewById(R.id.emptyTextView);
        errorTextView = findViewById(R.id.errorTextView);

        App app = (App) getApplication();
        ViewModelProvider viewModelProvider = new ViewModelProvider(this, app.getViewModelFactory());
        viewModel = viewModelProvider.get(MainViewModel.class);

        viewModel.getViewState().observe(this,state->{
            searchButton.setEnabled(state.isEnableSearchButton());
            spacesList.setVisibility(toVisibility(state.isShowList()));
            progress.setVisibility(toVisibility(state.isShowProgress()));
            emptyTextView.setVisibility(toVisibility(state.isShowEmptyHint()));
            errorTextView.setVisibility(toVisibility(state.isShowError()));

            adapter.setShipsList(state.getShips());
        });

        searchButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString();
            if(name.isEmpty())
                viewModel.getShips();
            else{
                Intent intent = new Intent(this,DetailsActivity.class);
                intent.putExtra(EXTRA_ID, name);
                startActivity(intent);}
        });
        initShipsList();
    }

    private void initShipsList(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        spacesList.setLayoutManager(layoutManager);

        adapter = new ShipsAdapter(ship ->{
            Intent intent = new Intent (this, DetailsActivity.class);
            intent.putExtra(DetailsActivity.EXTRA_ID,ship.getShip_id());
            startActivity(intent);
        });
        spacesList.setAdapter(adapter);
    }

    static int toVisibility (boolean show){
        return  show ? View.VISIBLE : View.GONE;
    }
}
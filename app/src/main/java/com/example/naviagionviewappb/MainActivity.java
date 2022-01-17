package com.example.naviagionviewappb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.naviagionviewappb.fragments.AdministradorHomeFragment;
import com.example.naviagionviewappb.fragments.BasicoHomeFragment;
import com.example.naviagionviewappb.fragments.LoginFragment;
import com.example.naviagionviewappb.fragments.SistemaHomeFragment;
import com.example.naviagionviewappb.modelo.Usuario;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView navHeaderTextViewUser;
    ShapeableImageView navHeaderImageView;
    final String defaultImage = "https://user-images.githubusercontent.com/67810669/149703538-444daf98-9116-4ad1-abd9-95c78a711308.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(getNavigationListener());
        View header = navigationView.getHeaderView(0);
        navHeaderTextViewUser =  header.findViewById(R.id.textViewUser);
        navHeaderImageView =  header.findViewById(R.id.imageUser);
        Glide.with(this).load(defaultImage).error(defaultImage)
                .into(navHeaderImageView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    NavigationView.OnNavigationItemSelectedListener getNavigationListener()
    {
        return new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.menuLogout:
                        configureUser(null);
                        break;
                }
                return false;
            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item != null && item.getItemId() == android.R.id.home) {
            toggle();
            return true;
        }

        switch (item.getItemId())
        {

        }
        return super.onOptionsItemSelected(item);
    }

    private void toggle() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    public void configureUser(Usuario user) {


        navigationView.getMenu().setGroupVisible(R.id.menuGroupAdministrador, false);
        navigationView.getMenu().setGroupVisible(R.id.menuGroupBasico,false);
        navigationView.getMenu().setGroupVisible(R.id.menuGroupSistema,false);
        navigationView.getMenu().setGroupVisible(R.id.menuGroupAuthenticated,false);

        if(user== null)
        {
            Glide.with(this).load(defaultImage).error(defaultImage)
                    .into(navHeaderImageView);
            navHeaderTextViewUser.setText("Bienvenido");
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new LoginFragment()).commit();
            return;
        }

        switch (user.getRol()) {
            case 1://Administrador
                navigationView.getMenu().setGroupVisible(R.id.menuGroupAdministrador,true);
                navigationView.getMenu().setGroupVisible(R.id.menuGroupAuthenticated,true);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new AdministradorHomeFragment()).commit();
                break;
            case 2://Sistema
                navigationView.getMenu().setGroupVisible(R.id.menuGroupSistema,true);
                navigationView.getMenu().setGroupVisible(R.id.menuGroupAuthenticated,true);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new SistemaHomeFragment()).commit();
                break;
            case 3://Básico
                navigationView.getMenu().setGroupVisible(R.id.menuGroupBasico,true);
                navigationView.getMenu().setGroupVisible(R.id.menuGroupAuthenticated,true);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new BasicoHomeFragment()).commit();
                break;
            default:
                Toast.makeText(this, "Rol de usuario no válido.", Toast.LENGTH_LONG).show();
                return;
        }

        navHeaderTextViewUser.setText(user.getNombre() + " - " + user.getUsuario());
        Glide.with(this).load(user.getImagen()).error(defaultImage)
                .into(navHeaderImageView);
    }





}
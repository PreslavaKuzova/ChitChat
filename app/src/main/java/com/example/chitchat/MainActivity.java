package com.example.chitchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.chitchat.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ChatsFragment chatsFragment;
    private GroupChatsFragment groupChatsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLocalVariables();
        setUpToolbar();

        setUpBottomNavigation();
    }

    private void initLocalVariables() {
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        this.chatsFragment = new ChatsFragment();
        this.groupChatsFragment = new GroupChatsFragment();
    }

    private void setUpBottomNavigation() {
        final FragmentManager manager = getSupportFragmentManager();

        binding.bottomNavBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                binding.txtDescription.setText(item.getTitle().toString());
                FragmentTransaction transaction = manager.beginTransaction();

                if (item.getItemId() == R.id.chats_page) {
                    transaction.replace(R.id.grp_container, chatsFragment);
                    transaction.commit();
                } else if (item.getItemId() == R.id.group_chats_page) {
                    transaction.replace(R.id.grp_container, groupChatsFragment);
                    transaction.commit();
                }
                return true;
            }
        });
        binding.bottomNavBar.setSelectedItemId(R.id.chats_page);
    }

    private void setUpToolbar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

}
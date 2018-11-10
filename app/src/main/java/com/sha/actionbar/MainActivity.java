package com.sha.actionbar;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

     ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Analyser l'etat d'Internet
       if(Util.getNetState(this)==Util.NET_NONE)
       {
           Toast.makeText(getApplicationContext(),"Connextion est mauvais",Toast.LENGTH_LONG).show();

       }
       else{

           Toast.makeText(getApplicationContext(),"Connextion est bon",Toast.LENGTH_LONG).show();
       }
       // ajouter les tabs dans notre actionbar
        actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.Tab tab;
        tab = actionBar.newTab().setText("Lille").setTabListener(new TabListener<Tab1Fragment>(getApplicationContext(), "page1", Tab1Fragment.class));
        actionBar.addTab(tab);
        tab = actionBar.newTab().setText("Paris").setTabListener(new TabListener<Tab2Fragment>(getApplicationContext(), "page2", Tab2Fragment.class));
        actionBar.addTab(tab);
        tab = actionBar.newTab().setText("Lyon").setTabListener(new TabListener<Tab3Fragment>(getApplicationContext(), "page3", Tab3Fragment.class));
        actionBar.addTab(tab);


    }

    public void hideOrShow(View view) {
        if (actionBar.isShowing()) {
            actionBar.hide();
        } else {
            actionBar.show();
        }
    }
//Le listener de menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem menuItem;
        SearchView searchView;

        getMenuInflater().inflate(R.menu.menu, menu);
        menuItem = menu.findItem(R.id.menu_search);

        //Le listener pour searchview
        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        MenuItemCompat.setOnActionExpandListener(menuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Toast.makeText(MainActivity.this, "Expand", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Toast.makeText(MainActivity.this, "Collapse", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, "fini: "+query, Toast.LENGTH_SHORT).show();
                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(MainActivity.this, "change: "+newText, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Toast.makeText(MainActivity.this, "Close", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
//chaque fois quand on choisit un item. l'application va appeler une methode selon l'id de cet item
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("Add").setMessage("Vous voulez ajouter?").setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"add,ok",Toast.LENGTH_SHORT).show();

                    }}).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"add,no",Toast.LENGTH_LONG).show();
                    }
                }).show();
                break;
            case R.id.menu_delete:
                builder=new AlertDialog.Builder(this);
                builder.setTitle("Delete").setMessage("Vous voulez supprimer?").setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"delete,ok",Toast.LENGTH_SHORT).show();

                    }}).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"delete,no",Toast.LENGTH_LONG).show();
                    }
                }).show();
                break;
            case R.id.menu_update:
                builder=new AlertDialog.Builder(this);
                builder.setTitle("Update").setMessage("Vous voulez modifier?").setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"update,yes",Toast.LENGTH_LONG).show();

                    }}).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),"update,no",Toast.LENGTH_LONG).show();

                    }
                }).show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
//le listener de tab
    static class TabListener<T extends Fragment> implements ActionBar.TabListener {

        private Fragment mFragment;
        private Context mContext;
        private final String mTag;
        private final Class<T> mClass;


        public TabListener(Context context, String tag, Class<T> clazz) {
            mContext = context;
            mTag = tag;
            mClass = clazz;
        }

        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

            if (mFragment == null) {
                mFragment = Fragment.instantiate(mContext, mClass.getName());
                ft.add(R.id.fragment_content, mFragment, mTag);
            } else {
                ft.attach(mFragment);
            }

        }


        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
            if (mFragment != null) {
                ft.detach(mFragment);
            }
        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
            // User selected the already selected tab. Usually do nothing.
        }




        }




    }

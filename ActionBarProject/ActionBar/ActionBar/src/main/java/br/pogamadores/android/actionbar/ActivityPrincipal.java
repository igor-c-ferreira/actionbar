package br.pogamadores.android.actionbar;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

public class ActivityPrincipal extends ActionBarActivity implements ActionBar.OnNavigationListener {

    /**
     * Essa será a chave de serialização usada para armazenar o estado atual do seletor de seção.
     */
    private static final String ESTADO_ATUAL_NAVIGATION_SELECTOR = "navigation_item_selecionado";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        // Configuração inicial da action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        // Configura o drop down que será colocado na Action Bar.
        actionBar.setListNavigationCallbacks(
                // Cria um array adapter simples para exibir a listagens de opções.
                new ArrayAdapter<String>(
                        actionBar.getThemedContext(),
                        android.R.layout.simple_list_item_1, //Esse é um layout de célula simples (apenas texto)
                        android.R.id.text1, //Na célula simples, o text view tem o id text1
                        new String[] {
                                getString(R.string.titulo_secao_1),
                                getString(R.string.titulo_secao_2),
                                getString(R.string.titulo_secao_3),
                        }),
                this);

        getSupportFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        if(getSupportFragmentManager().getBackStackEntryCount() == 0)
                        {
                            voltarActionBarStatusInicial();
                        }
                    }
                });
    }

    @Override
    public void onRestoreInstanceState(Bundle estadoSalvo) {
        // Recupera o estado do seletor da action bar guardado em bundle
        if (estadoSalvo.containsKey(ESTADO_ATUAL_NAVIGATION_SELECTOR)) {
            getSupportActionBar().setSelectedNavigationItem(
                    estadoSalvo.getInt(ESTADO_ATUAL_NAVIGATION_SELECTOR));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle estadoASerSalvo) {
        // Salva o estado atual do seletor da action bar
        estadoASerSalvo.putInt(ESTADO_ATUAL_NAVIGATION_SELECTOR,
                getSupportActionBar().getSelectedNavigationIndex());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Criação do menu. Basicamente, inflar o layout do xml
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Método chamado quando um item do menu é selecionado.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            pushNovoFragmento(getString(R.string.configuracoes), getString(R.string.configuracoes));
            return true;
        }else if(id == android.R.id.home)
        {
            voltarActionBarStatusInicial();
            getSupportActionBar().setSelectedNavigationItem(0);
            getSupportFragmentManager().popBackStack();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(int posicao, long id) {
        // Quando uma nova seção for selecionada, atualizar o conteúdo.
        switch (posicao)
        {
            case 0:
                mudarFragmento(getString(R.string.titulo_secao_1));
                break;
            case 1:
                mudarFragmento(getString(R.string.titulo_secao_2));
                break;
            case 2:
                mudarFragmento(getString(R.string.titulo_secao_3));
                break;
            default:
                mudarFragmento(getString(R.string.ola_mundo));
                break;
        }
        return true;
    }

    protected void pushNovoFragmento(String titulo, String mensagem)
    {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, FragmentoPrincipal.newInstance(mensagem))
                .setTransitionStyle(FragmentTransaction.TRANSIT_UNSET)
                .addToBackStack(null)
                .commit();
        getSupportActionBar().setTitle(titulo);
        if(getSupportActionBar().getNavigationMode() != ActionBar.NAVIGATION_MODE_STANDARD)
            mudarActionBar();
    }

    protected void mudarFragmento(String mensagem)
    {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, FragmentoPrincipal.newInstance(mensagem))
                .commit();
        if(getSupportActionBar().getNavigationMode() != ActionBar.NAVIGATION_MODE_LIST)
            voltarActionBarStatusInicial();
    }

    protected void mudarActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
    }

    protected void voltarActionBarStatusInicial()
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.app_name);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
    } 
}

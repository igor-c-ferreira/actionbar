// **************************************************************
// Copyright (c) POGAmadores
// Este documento foi feito para o ensino de programação
// através de vídeos e textos. Tendo isso em mente e garantindo
// a aplicação deste copyright para projetos futuros, o uso deste
// documento é livre.
// **************************************************************

package br.pogamadores.android.actionbar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Uma simples derivação da classe {@link android.support.v4.app.Fragment}.
 * Essa impletação usa o método {@link FragmentoPrincipal#newInstance} como fábrica de objetos.
 */
public class FragmentoPrincipal extends Fragment {
    // Essa é a chava de argumento que a fabrica de objetos usará para passar o parâmetro de mensagem
    private static final String MENSAGEM = "MESSAGEM";

    private String mMensagem;


    /**
     * Esse é um método que segue o modelo de fábrica de objetos
     *
     * @param mensagem Mensagem que será exibida no fragmento.
     * @return Uma nova instância do fragmento FragmentoPrincipal.
     */
    public static FragmentoPrincipal newInstance(String mensagem) {
        FragmentoPrincipal fragment = new FragmentoPrincipal();
        Bundle args = new Bundle();
        args.putString(MENSAGEM, mensagem);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentoPrincipal()
    {
        mMensagem = "";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMensagem = getArguments().getString(MENSAGEM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewPrincipal = inflater.inflate(R.layout.fragmento_principal, container, false);
        TextView mensagemTextView = (TextView)viewPrincipal.findViewById(R.id.mensagem_label);
        mensagemTextView.setText(mMensagem);
        return viewPrincipal;
    }


}

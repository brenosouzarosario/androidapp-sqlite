package com.example.sqlite_atividade;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText edtCodAtividade;
    private EditText edtNomeAtividade;
    private EditText edtResponsavel;
    private EditText edtPrioridade;
    private ListView lstAtividade;
    private BottomNavigationView btnNavegacao;
    private FloatingActionButton btnExcluirTodos;

    Atividade atividade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtCodAtividade = findViewById(R.id.edtCodAtividade);
        edtNomeAtividade = findViewById(R.id.edtNomeAtividade);
        edtResponsavel = findViewById(R.id.edtResponsavel);
        edtPrioridade = findViewById(R.id.edtPrioridade);
        lstAtividade = findViewById(R.id.lstAtividade);
        btnNavegacao = findViewById(R.id.btnNavegacao);
        btnExcluirTodos = findViewById(R.id.btnExcluirTodos);

        this.btnNavegacao.setOnNavigationItemSelectedListener(navListener);

        this.btnExcluirTodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
                dataBaseHelper.excluirTodos();
                pesquisarTodos();
            }

        });

        lstAtividade.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Atividade atividade = (Atividade) parent.getItemAtPosition(position);
                edtCodAtividade.setText(String.valueOf(atividade.getCodigo()));
                edtNomeAtividade.setText(atividade.getNomeAtividade());
                edtResponsavel.setText(atividade.getResponsavel());
                edtPrioridade.setText(String.valueOf(atividade.getPrioridade()));
            }
        });
    }
        public BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                DataBaseHelper dataBaseHelper;
                int codigo;
                switch (item.getItemId()) {
                    case R.id.nav_incluir:
                        atividade = new Atividade();
                        atividade.setCodigo(Integer.valueOf(edtCodAtividade.getText().toString()));
                        atividade.setNomeAtividade(edtNomeAtividade.getText().toString());
                        atividade.setResponsavel(edtResponsavel.getText().toString());
                        atividade.setPrioridade(Integer.valueOf(edtPrioridade.getText().toString()));

                        dataBaseHelper = new DataBaseHelper(MainActivity.this);
                        boolean resultado = dataBaseHelper.inserir(atividade);

                        if (resultado) {
                            Toast.makeText(MainActivity.this, "Inserido com sucesso!", Toast.LENGTH_SHORT).show();
                            pesquisarTodos();
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Erro!", Toast.LENGTH_SHORT).show();
                        }

                        break;

                    case R.id.nav_pesquisar:
                        DataBaseHelper db = new DataBaseHelper(MainActivity.this);
                        codigo = Integer.valueOf(edtCodAtividade.getText().toString());
                        List<Atividade> vetorAtividade = db.pesquisar(codigo);
                        ArrayAdapter atividadeAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, vetorAtividade);
                        lstAtividade.setAdapter(atividadeAdapter);
                        break;

                    case R.id.nav_pesquisar_todos:
                        pesquisarTodos();
                        break;

                    case R.id.nav_atualizar:
                        atividade = new Atividade();
                        atividade.setCodigo(Integer.valueOf(edtCodAtividade.getText().toString()));
                        atividade.setNomeAtividade(edtNomeAtividade.getText().toString());
                        atividade.setResponsavel(edtResponsavel.getText().toString());
                        atividade.setPrioridade(Integer.valueOf(edtPrioridade.getText().toString()));
                        dataBaseHelper = new DataBaseHelper(MainActivity.this);
                        dataBaseHelper.alterar(atividade);
                        pesquisarTodos();
                        break;

                    case R.id.nav_excluir:
                        codigo = Integer.valueOf(edtCodAtividade.getText().toString());
                        dataBaseHelper = new DataBaseHelper(MainActivity.this);
                        dataBaseHelper.excluir(codigo);
                        pesquisarTodos();
                        break;
                }
                return true;
            }
        };

        public void pesquisarTodos ()
        {
            DataBaseHelper db = new DataBaseHelper(MainActivity.this);
            List<Atividade> vetorAtividade = db.pesquisarTodos();
            ArrayAdapter atividadeAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, vetorAtividade);
            lstAtividade.setAdapter(atividadeAdapter);
        }
}
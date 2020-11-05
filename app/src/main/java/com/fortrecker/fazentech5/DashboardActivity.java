package com.fortrecker.fazentech5;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class DashboardActivity extends Activity {
    private Button logout;

    int quantidade = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }
    public void somar1(View view) {
        quantidade = quantidade + 1;
        displayQuantidade(quantidade);
    }

    public void subtrair1(View view) {
        quantidade = quantidade - 1;
        displayQuantidade(quantidade);
    }

    public void displayQuantidade(int qtdProd1) {
        TextView qtdTextview = (TextView) findViewById(R.id.qtdItem1);
        qtdTextview.setText("" + qtdProd1);
    }

    public void enviarPedido(View view) {
        double valor = calcularPreco();
        String mensagem = "Leite: " + valor;

        TextView pedidoTextview = (TextView) findViewById(R.id.resumo_pedido);
        pedidoTextview.setText(mensagem);

        //Send Email
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"srwayne00@gmail.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Total Pedido" );
        i.putExtra(Intent.EXTRA_TEXT   , "Você fez o pedido de :"+ mensagem);
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(DashboardActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public double calcularPreco() {
        double precoBase = 3.00;
        return precoBase*quantidade;
    }
}
package br.com.alura.ceep.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import br.com.alura.ceep.R;

public class FeedbackActivity extends AppCompatActivity {

    private static final String FEEDBACK_TITULO_APPBAR = "Feedback";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        setTitle(FEEDBACK_TITULO_APPBAR);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_feedback, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_enviar_feedback:
                Intent irParaLinhaNotasActivity = new Intent(this, ListaNotasActivity.class);
                startActivity(irParaLinhaNotasActivity);

                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}

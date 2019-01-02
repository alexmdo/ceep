package br.com.alura.ceep.recyclerview.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.model.PaletaCorEnum;

public class ListaPaletaCorAdapter extends RecyclerView.Adapter<ListaPaletaCorAdapter.PaletaCorViewHolder> {

    private Context context;
    private List<PaletaCorEnum> listaPaletaCor;
    private OnItemClickListener onItemClickListener;

    public ListaPaletaCorAdapter(Context context, List<PaletaCorEnum> listaPaletaCor) {
        this.context = context;
        this.listaPaletaCor = listaPaletaCor;
    }

    @NonNull
    @Override
    public PaletaCorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.item_paleta_cor, viewGroup, false);

        return new PaletaCorViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull PaletaCorViewHolder corPaletaViewHolder, int posicao) {
        PaletaCorEnum paletaCorEnum = listaPaletaCor.get(posicao);

        corPaletaViewHolder.definirPaletaCor(paletaCorEnum);
    }

    @Override
    public int getItemCount() {
        return listaPaletaCor.size();
    }

    public class PaletaCorViewHolder extends RecyclerView.ViewHolder {

        private View paletaCor;
        private PaletaCorEnum paletaCorEnum;

        public PaletaCorViewHolder(@NonNull View itemView) {
            super(itemView);

            paletaCor = itemView.findViewById(R.id.item_paleta_cor);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(paletaCorEnum);
                }
            });
        }

        public void definirPaletaCor(PaletaCorEnum paletaCorEnum) {
            this.paletaCorEnum = paletaCorEnum;

            Drawable background = paletaCor.getBackground();
            background.setColorFilter(Color.parseColor(paletaCorEnum.getCorHexadecimal()), PorterDuff.Mode.SRC_ATOP);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {

        void onItemClick(PaletaCorEnum paletaCorEnum);

    }
}

package com.mobiai.base.lqlbasecode.language;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;
import com.mobiai.R;
import com.mobiai.databinding.ItemLanguageFirstOpenAppBinding;
import java.util.List;


public class LanguageFirstOpenAdapter extends RecyclerView.Adapter<LanguageFirstOpenAdapter.ViewHolder> {
    private final Context context;
    private final List<Language> languages;
    private final OnLanguageClickListener listening;

    public LanguageFirstOpenAdapter(Context context, List<Language> languages, OnLanguageClickListener listening) {
        this.context = context;
        this.languages = languages;
        this.listening = listening;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemLanguageFirstOpenAppBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Language language = languages.get(position);

        holder.binding.txtNameLanguage.setText(language.getTitle());
        holder.binding.imgIconLanguage.setImageDrawable(AppCompatResources.getDrawable(context, language.getFlag()));
        if (language.isChoose()) {
            holder.binding.imgChooseLanguage
                    .setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.ic_select_language));
        } else {
            holder.binding.imgChooseLanguage
                    .setImageDrawable(null);
        }

        holder.itemView.setOnClickListener(v -> {
            listening.onClickItemListener(language);
            for (int i = 0; i < languages.size(); i++) {
                languages.get(i).setChoose(i == position);
            }
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return languages.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemLanguageFirstOpenAppBinding binding;

        public ViewHolder(ItemLanguageFirstOpenAppBinding itemLanguageFirstOpenAppBinding) {
            super(itemLanguageFirstOpenAppBinding.getRoot());
            this.binding = itemLanguageFirstOpenAppBinding;
        }
    }

    public interface OnLanguageClickListener {
        void onClickItemListener(Language language);
    }
}

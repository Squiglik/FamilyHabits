package com.example.familyhabits;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.VH> {

    public interface Listener {
        void onToggle(Habit h, boolean done);
        void onDelete(Habit h);
    }

    private final List<Habit> items;
    private final Listener listener;

    public HabitAdapter(List<Habit> items, Listener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_habit, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        Habit habit = items.get(position);
        h.tvHabit.setText(habit.name);
        h.tvMember.setText(habit.member);

        h.cbDone.setOnCheckedChangeListener(null);
        h.cbDone.setChecked(habit.doneToday);
        h.cbDone.setOnCheckedChangeListener((b, checked) -> listener.onToggle(habit, checked));

        h.btnDelete.setOnClickListener(v -> listener.onDelete(habit));
    }

    @Override
    public int getItemCount() { return items.size(); }

    static class VH extends RecyclerView.ViewHolder {
        CheckBox cbDone;
        TextView tvHabit, tvMember;
        Button btnDelete;
        VH(@NonNull View itemView) {
            super(itemView);
            cbDone   = itemView.findViewById(R.id.cbDone);
            tvHabit  = itemView.findViewById(R.id.tvHabit);
            tvMember = itemView.findViewById(R.id.tvMember);
            btnDelete= itemView.findViewById(R.id.btnDelete);
        }
    }
}
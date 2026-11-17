package com.example.familyhabits;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements HabitAdapter.Listener {

    private EditText etMember, etHabit;
    private HabitAdapter adapter;
    private List<Habit> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etMember = findViewById(R.id.etMember);
        etHabit  = findViewById(R.id.etHabit);
        Button btnAdd = findViewById(R.id.btnAdd);
        RecyclerView recycler = findViewById(R.id.recycler);

        items = HabitStore.load(this);
        adapter = new HabitAdapter(items, this);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);

        btnAdd.setOnClickListener(v -> addHabit());
    }

    private void addHabit() {
        String member = etMember.getText().toString().trim();
        String habit  = etHabit.getText().toString().trim();

        if (member.isEmpty() || habit.isEmpty()) {
            Toast.makeText(this, "Заполни оба поля", Toast.LENGTH_SHORT).show();
            return;
        }

        Habit h = new Habit(
                UUID.randomUUID().toString(),
                member, habit,
                false,
                HabitStore.todayIndex()
        );
        items.add(h);
        adapter.notifyItemInserted(items.size() - 1);
        etHabit.setText("");
        persist();
    }

    @Override
    public void onToggle(Habit h, boolean done) {
        h.doneToday = done;
        h.lastDate  = HabitStore.todayIndex();
        persist();
    }

    @Override
    public void onDelete(Habit h) {
        int idx = items.indexOf(h);
        if (idx >= 0) {
            items.remove(idx);
            adapter.notifyItemRemoved(idx);
            persist();
        }
    }

    private void persist() {
        HabitStore.save(this, items);
    }
}
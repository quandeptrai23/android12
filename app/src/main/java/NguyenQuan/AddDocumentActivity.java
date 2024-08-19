package NguyenQuan;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android12.R;

public class AddDocumentActivity extends AppCompatActivity {

    private EditText editMaHP, editTen, editLink, editMoTa;
    private Button btnThem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_document);

        // Initialize UI components
        editMaHP = findViewById(R.id.editMaHP);
        editTen = findViewById(R.id.editTen);
        editLink = findViewById(R.id.editLink);
        editMoTa = findViewById(R.id.editMoTa);
        btnThem = findViewById(R.id.btnThem);

        // Set an OnClickListener on the save button
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the save action here
                String ma = editMaHP.getText().toString();
                String ten = editTen.getText().toString();
                //String ten = editTen.getText().toString();

                // For example, you could show a Toast message
//                Toast.makeText(AddDocumentActivity.this, "Document Saved: " + title, Toast.LENGTH_SHORT).show();

                // Perform the save operation (e.g., saving to a database)
            }
        });
    }
}
